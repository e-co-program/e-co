package jp.co.e_co.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.e_co.app.bean.EcoInfo;
import jp.co.e_co.app.common.Utils;
import jp.co.e_co.app.constant.Constants;
import jp.co.e_co.app.entity.ParentUser;
import jp.co.e_co.app.entity.Youchien;
import jp.co.e_co.app.repository.ParentUserRepositry;
import jp.co.e_co.app.repository.YouchienRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableRedisHttpSession
public class LoginController {
	
	@Autowired
	YouchienRepository youchienRepository;
	
	@Autowired
	ParentUserRepositry parentUserRepositry;
	
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@RequestMapping("/eco/login")
	public String login() {
		return "login";
	}
	
	/**
	 * ログイン
	 * @param mail
	 * @param pass
	 * @return
	 */
	@RequestMapping(value="/eco/search", method=RequestMethod.POST)
	public ModelAndView post(@RequestParam("mail") String mail, @RequestParam("pass") String pass,
			HttpServletRequest httprRequest,
			HttpServletResponse httpResponse,
			Locale locale, Model model) {
		System.out.println("mail:" + mail + " pass:" + pass);
		ModelAndView mv = new ModelAndView("search_index");
		
		// ユーザー検索
		List<ParentUser> parentUsers = parentUserRepositry.findByMailaddAndPw(mail, pass);
		if (null == parentUsers || parentUsers.size() == 0) {
			System.out.println("Failed to Find ParentUser.");
			return new ModelAndView("login");
		}
		ParentUser parentUser = parentUsers.get(0);
		System.out.println("Find ParentUser.");
		System.out.println("parent_user_code: " + parentUser.getParentUserCode());
		
		// 幼稚園情報検索
		Youchien youchien = youchienRepository.findOne(parentUser.getYouchienCode());
		if (youchien == null) {
			System.out.println("Failed to Find Youchien.");
			return new ModelAndView("login");
		}
		System.out.println("Find Youchien.");
		System.out.println("youchien_code: " + youchien.getYouchienCode());
		
		// セッションID用ランダム文字列生成
		String sessionStr = RandomStringUtils.randomAlphanumeric(12);
		System.out.println("sessionStr: " + sessionStr);
		httpResponse.addCookie(new Cookie("eco-session", sessionStr));
		// トランザクションサポート有効化
		redisTemplate.setEnableTransactionSupport(true);
		HashOperations<String, String, String> ops = redisTemplate.opsForHash();
		ops.put(sessionStr, "USER_CODE", String.valueOf(parentUser.getParentUserCode()));
		ops.put(sessionStr, "YOUCHIEN_CODE", String.valueOf(youchien.getYouchienCode()));
		redisTemplate.expire(sessionStr, Constants.REDIS_TIME_OUT, TimeUnit.SECONDS);
		
		
		// 今年度取得
		int fiscaYear = Utils.getFiscalYear();
		// 年度タブ情報
		String[] years = {String.valueOf(fiscaYear), String.valueOf(fiscaYear - 1), String.valueOf(fiscaYear - 2), String.valueOf(fiscaYear -3)};
		// Eco情報
		EcoInfo ecoInfo = new EcoInfo();
//		ecoInfo.setBaseUrl(Constants.BASE_URL + Constants.PROTOCOR);
		ecoInfo.setBaseUrl(Constants.BASE_URL);
		ecoInfo.setKindergartenName(youchien.getYname());
		ecoInfo.setInitYear(String.valueOf(fiscaYear));
		ecoInfo.setYears(years);
		mv.addObject("EcoInfo", ecoInfo);
		mv.addObject("years", years);
		
		return mv;
	}
	
	@RequestMapping("/eco/password")
	public String password() {
		return "password";
	}
	
	@RequestMapping(value="/eco/chengPassword", method=RequestMethod.POST)
	public String post(@RequestParam("mail") String mail) {
		System.out.println("mail:" + mail);
		return "login";
	}
	
	@RequestMapping("/eco/userpolicy")
	public String userpolicy() {
		return "userpolicy";
	}
	
	@RequestMapping("/eco/privacy")
	public String privacy() {
		return "privacy";
	}

	@RequestMapping("/eco/qa")
	public String qa() {
		return "qa";
	}

	// 今年度を返す
	private int getFiscalYear() {
		Date date = new Date();
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfM = new SimpleDateFormat("MM");
		
		String yearStr = sdfY.format(date);
		String monthStr = sdfM.format(date);
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		if (month < 4) {
			year--;
		}
		return year;
	}

}
