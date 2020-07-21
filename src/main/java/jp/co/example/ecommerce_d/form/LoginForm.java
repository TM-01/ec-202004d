package jp.co.example.ecommerce_d.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginForm {
	@Email(message="Eメールの形式が不正です")
	private String email;
	@NotBlank(message="パスワードを入力してください")
	private String password;
	private String errormessage;
	
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
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + ", errormessage=" + errormessage + "]";
	}
	
}
