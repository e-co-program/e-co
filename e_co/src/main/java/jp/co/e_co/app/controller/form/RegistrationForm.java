package jp.co.e_co.app.controller.form;

public class RegistrationForm {
	
	private String sisetuFirst;
	
	private String sisetuMiddle;
	
	private String sisetuLatter;
	
	private long userCode;
	
	private String email;
	
	private String password;

	public String getSisetuFirst() {
		return sisetuFirst;
	}

	public void setSisetuFirst(String sisetuFirst) {
		this.sisetuFirst = sisetuFirst;
	}

	public String getSisetuMiddle() {
		return sisetuMiddle;
	}

	public void setSisetuMiddle(String sisetuMiddle) {
		this.sisetuMiddle = sisetuMiddle;
	}

	public String getSisetuLatter() {
		return sisetuLatter;
	}

	public void setSisetuLatter(String sisetuLatter) {
		this.sisetuLatter = sisetuLatter;
	}

	public long getUserCode() {
		return userCode;
	}

	public void setUserCode(long userCode) {
		this.userCode = userCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "RegistrationForm[sisetuFirst=" + sisetuFirst
				+ ", sisetuMiddle=" + sisetuMiddle
				+ ", sisetuLatter=" + sisetuLatter
				+ ", userCode=" + userCode
				+ ", email=" + email
				+ ", password=" + password;
	}
	
}
