package jp.co.e_co.app.response;

import java.util.List;

import jp.co.e_co.app.bean.MenuInfoBean;

public class GetMenuInfoResponseModel {
	// 対象年度
	private String mYear;
	// メニュー情報
	private List<MenuInfoBean> menuList;

	public String getmYear() {
		return mYear;
	}

	public void setmYear(String mYear) {
		this.mYear = mYear;
	}

	public List<MenuInfoBean> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuInfoBean> menuList) {
		this.menuList = menuList;
	}
}
