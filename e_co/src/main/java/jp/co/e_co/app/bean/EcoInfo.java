package jp.co.e_co.app.bean;

public class EcoInfo {

	/** ベースURL */
	private String baseUrl;
	/** 幼稚園名 */
	private String kindergartenName;
	/** 初期表示メニュー年度(今年度) */
	private String initYear;
	/** 年度タブ表示の年度 */
	private String[] years;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getKindergartenName() {
		return kindergartenName;
	}

	public void setKindergartenName(String kindergartenName) {
		this.kindergartenName = kindergartenName;
	}

	public String getInitYear() {
		return initYear;
	}

	public void setInitYear(String initYear) {
		this.initYear = initYear;
	}

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
		this.years = years;
	}
	
}
