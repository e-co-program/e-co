package jp.co.e_co.app.response;

import java.util.List;

import jp.co.e_co.app.bean.MenuInfoBean;

public class GetMenuInfoResponseModel {
	// メニュー情報
	private List<MenuInfoBean> menuList;

	public List<MenuInfoBean> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuInfoBean> menuList) {
		this.menuList = menuList;
	}
}
