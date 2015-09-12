package jp.co.e_co.app.bean;

import java.util.List;

public class MenuInfoBean {
	// メニューID
	private String id;
	// メニュータイプ　0:メインメニュー　1：サブメニュー
	private int type;
	// メニュー名
	private String name;
	// サブメニュー
	private List<MenuInfoBean> subMenuList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MenuInfoBean> getSubMenuList() {
		return subMenuList;
	}
	public void setSubMenuList(List<MenuInfoBean> subMenuList) {
		this.subMenuList = subMenuList;
	}
}
