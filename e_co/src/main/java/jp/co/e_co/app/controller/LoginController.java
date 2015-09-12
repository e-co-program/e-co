package jp.co.e_co.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * ログイン
	 * @param mail
	 * @param pass
	 * @return
	 */
	@RequestMapping(value="/authentication", method=RequestMethod.POST)
	public String post(@RequestParam("mail") String mail, @RequestParam("pass") String pass) {
		System.out.println("mail:" + mail + " pass:" + pass);
		ModelAndView mv = new ModelAndView("search_index");
		//return "search_index";
		return "test";
	}
	
	@RequestMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	/**
	 * アカウント登録
	 * @param sisetu
	 * @param sei
	 * @param sei_kana
	 * @param mail
	 * @param mail_confirmation
	 * @param pass
	 * @param pass_confirmation
	 * @param enjisei
	 * @param enjisei_kana
	 * @param year
	 * @return
	 */
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String post(@RequestParam("sisetu") String sisetu,
			@RequestParam("sei") String sei,
			@RequestParam("sei_kana") String sei_kana,
			@RequestParam("mail") String mail,
			@RequestParam("mail_confirmation") String mail_confirmation,
			@RequestParam("pass") String pass,
			@RequestParam("pass_confirmation") String pass_confirmation,
			@RequestParam("enjisei") String enjisei,
			@RequestParam("enjisei_kana") String enjisei_kana,
			@RequestParam("year") String year) {
		System.out.println("sisetu:" + sisetu
				+ " sei:" + sei
				+ ", sei_kana:" + sei_kana
				+ ", mail:" + mail
				+ ", mail_confirmation:" + mail_confirmation
				+ ", pass:" + pass
				+ ", pass_confirmation:" + pass_confirmation
				+ ", enjisei:" + enjisei
				+ ", enjisei_kana:" + enjisei_kana
				+ ", year:" + year);
		//return "search_index";
		return "test";
	}
	
	@RequestMapping("/password")
	public String password() {
		return "password";
	}
	
	@RequestMapping(value="/chengPassword", method=RequestMethod.POST)
	public String post(@RequestParam("mail") String mail) {
		System.out.println("mail:" + mail);
		return "login";
	}
	
	@RequestMapping("/userpolicy")
	public String userpolicy() {
		return "userpolicy";
	}
	
	@RequestMapping("/privacy")
	public String privacy() {
		return "privacy";
	}

}
