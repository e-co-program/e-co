package jp.co.e_co.app.controller.form;

public class EditProfileForm {
	
	private String email;
	
	private String password;

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
		return "EditProfileForm[email=" + email
				+", password=" + password + "]";
	}

}
