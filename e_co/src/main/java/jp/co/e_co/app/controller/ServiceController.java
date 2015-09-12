package jp.co.e_co.app.controller;

import java.util.ArrayList;
import java.util.List;

import jp.co.e_co.app.bean.MenuInfoBean;
import jp.co.e_co.app.response.GetMenuInfoResponseModel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

	@RequestMapping(value="/getMenuInfo", method=RequestMethod.GET)
	public GetMenuInfoResponseModel getMenuInfo() {
		GetMenuInfoResponseModel response = new GetMenuInfoResponseModel();
		
		MenuInfoBean main1 = new MenuInfoBean();
		main1.setId("/運動会");
		main1.setName("運動会");
		main1.setType(0);
		List<MenuInfoBean> main1SubMenuList = new ArrayList<MenuInfoBean>();
		MenuInfoBean main1_sub1 = new MenuInfoBean();
		main1_sub1.setId("/運動会/入場");
		main1_sub1.setName("入場");
		main1_sub1.setType(1);
			List<MenuInfoBean> main1Sub1SubMenuList = new ArrayList<MenuInfoBean>();
			MenuInfoBean main1_sub1_sub1 = new MenuInfoBean();
			main1_sub1_sub1.setId("/運動会/入場/前半");
			main1_sub1_sub1.setName("前半");
			main1_sub1_sub1.setType(1);
			main1Sub1SubMenuList.add(main1_sub1_sub1);
			MenuInfoBean main1_sub1_sub2 = new MenuInfoBean();
			main1_sub1_sub2.setId("/運動会/入場/後半");
			main1_sub1_sub2.setName("後半");
			main1_sub1_sub2.setType(1);
			main1Sub1SubMenuList.add(main1_sub1_sub2);
		main1_sub1.setSubMenuList(main1Sub1SubMenuList);
		main1SubMenuList.add(main1_sub1);
		
		MenuInfoBean main1_sub2 = new MenuInfoBean();
		main1_sub2.setId("/運動会/組体操");
		main1_sub2.setName("組体操");
		main1_sub2.setType(1);
			List<MenuInfoBean> main1Sub2SubMenuList = new ArrayList<MenuInfoBean>();
			MenuInfoBean main1_sub2_sub1 = new MenuInfoBean();
			main1_sub2_sub1.setId("/運動会/組体操/前半");
			main1_sub2_sub1.setName("前半");
			main1_sub2_sub1.setType(1);
			main1Sub2SubMenuList.add(main1_sub2_sub1);
			MenuInfoBean main1_sub2_sub2 = new MenuInfoBean();
			main1_sub2_sub2.setId("/運動会/組体操/後半");
			main1_sub2_sub2.setName("後半");
			main1_sub2_sub2.setType(1);
			main1Sub2SubMenuList.add(main1_sub2_sub2);
		main1_sub2.setSubMenuList(main1Sub2SubMenuList);
		main1SubMenuList.add(main1_sub2);	
			
		response.setMenuList(main1SubMenuList);
		
		return response;
	}
}
