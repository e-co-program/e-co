package jp.co.e_co.app.response;

import java.util.List;

import jp.co.e_co.app.bean.PhotoInfo;

public class GetPhotoListResponseModel {
	// 取得開始位置
	private int offset = 0;
	// 全写真数
	private int countAll = 0;
	// 現在ページ数
	private int page = 0;
	// 全ページ数
	private int allPage = 0;
	// 写真情報
	private List<PhotoInfo> photoList;
	// メニューID(対象ディレクトリまでのパス)
	private String menuId;
	// 選択したメニュータイトル
	private String title;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCountAll() {
		return countAll;
	}

	public void setCountAll(int countAll) {
		this.countAll = countAll;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

	public List<PhotoInfo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<PhotoInfo> photoList) {
		this.photoList = photoList;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
