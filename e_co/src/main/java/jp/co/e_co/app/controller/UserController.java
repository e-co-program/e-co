package jp.co.e_co.app.controller;

import java.util.List;

import javax.validation.Valid;

import jp.co.e_co.app.controller.form.RegistrationForm;
import jp.co.e_co.app.entity.ParentUser;
import jp.co.e_co.app.repository.ParentUserRepositry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	@Autowired
	ParentUserRepositry parentUserRepositry;

	@RequestMapping("/registration")
	public String registration() {
		System.out.println("registration");
		return "registration";
	}
	
	/**
	 * 初回登録
	 * @param form
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/initial_registration", method=RequestMethod.POST)
	public ModelAndView initialRegistration(
			@ModelAttribute("form") @Valid RegistrationForm form,
			BindingResult bindingResult) {
		System.out.println(form.toString());
		
		// 幼稚園コードに変換
		String sisetuFirst = form.getSisetuFirst();
		String sisetuMiddle = form.getSisetuMiddle();
		String sisetuLatter = form.getSisetuLatter();
		long youchienCode = Long.parseLong(
				String.valueOf(sisetuFirst) + String.valueOf(sisetuMiddle) + String.valueOf(sisetuLatter));
		// ユーザー情報検索
		List<ParentUser> parentUsers = parentUserRepositry.findByYouchienCodeAndParentUserCode(youchienCode, form.getUserCode());
		if (parentUsers == null || parentUsers.size() == 0) {
			System.out.println("initialRegistration(): ユーザー情報がありません。 youchienCode="
					+ youchienCode + ", userCOde=" + form.getUserCode());
			ModelAndView mv = new ModelAndView("registration");
			mv.addObject("message", "ユーザー情報が見つかりません。");
			return mv;
		}
		ParentUser parentUser = parentUsers.get(0);
		if (parentUser.getMailadd() != null || parentUser.getPw() != null) {
			System.out.println("initialRegistration(): ユーザー情報登録済みです。youchienCode="
					+ youchienCode + ", userCode=" + form.getUserCode());
			ModelAndView mv = new ModelAndView("registration");
			mv.addObject("message", "初回登録済です。");
			return mv;
		}
		parentUser.setMailadd(form.getEmail());
		parentUser.setPw(form.getPassword());
		// ユーザー情報更新(メールアドレス & パスワード)
		ParentUser resultEntity = parentUserRepositry.save(parentUser);
		// 更新チェック
		if (!resultEntity.getMailadd().equals(form.getEmail())
				|| !resultEntity.getPw().equals(form.getPassword())) {
			System.out.println("initialRegistration(): ユーザー情報の更新に失敗しました。youchienCode="
					+ youchienCode + ", userCode=" + form.getUserCode());
			ModelAndView mv = new ModelAndView("registration");
			mv.addObject("message", "初回登録に失敗しました。");
			return mv;
		}
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("message", "登録が完了しました。");
		return mv;
	}
}
