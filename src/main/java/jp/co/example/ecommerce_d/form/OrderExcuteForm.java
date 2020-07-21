package jp.co.example.ecommerce_d.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class OrderExcuteForm {

	/** 宛先氏名 */
	@NotBlank(message="名前を入力してください")
	private String destinationName;
	/** 宛先Eメール */
	@NotBlank(message="メールアドレスを入力して下さい")
	@Email(message="メールアドレスの形式が不正です")
	private String destinationEmail;
	/** 宛先郵便番号 */
	@NotBlank(message="郵便番号を入力して下さい")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$",message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String destinationZipcode;
	/** 宛先住所 */
	@NotBlank(message = "住所を入力して下さい")
	private String destinationAddress;
	/** 宛先TEL */
	@NotBlank(message = "電話番号を入力して下さい")
	@Pattern(regexp="^0[0-9]{1,3}-[0-9]{3,4}-[0-9]{4}$", message="電話番号は0XX-XXXX-XXXXの形式で入力してください")
	private String destinationTel;
	/** 配達時間 */
	@NotEmpty(message = "配達時間を選択してください")
	private String date;
	private String time;
	/** 支払方法 */
	
	private String paymentMethod;

	@Override
	public String toString() {
		return "OrderExcuteForm [destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", date=" + date + ", time=" + time + ", paymentMethod="
				+ paymentMethod + "]";
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
