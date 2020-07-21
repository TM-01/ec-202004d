package jp.co.example.ecommerce_d.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SupportForm {

	/** 氏名 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String email;
	/** 電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "^0[0-9]{1,3}-[0-9]{3,4}-[0-9]{4}$", message = "電話番号は0XX-XXXX-XXXXの形式で入力してください")
	private String telephone;
	/** お問い合わせ内容 */
	@NotBlank(message = "お問い合わせ内容を入力してください")
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "SupportForm [name=" + name + ", email=" + email + ", telephone=" + telephone + ", comment=" + comment
				+ "]";
	}

}
