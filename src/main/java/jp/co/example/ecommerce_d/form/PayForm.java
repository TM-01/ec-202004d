package jp.co.example.ecommerce_d.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

/**
 * クレジットカード情報.
 * @author tatsuro.miyazaki
 *
 */
public class PayForm {
	/** カードナンバー	 */
	@CreditCardNumber(message="形式が不正です")
	private String card_number;
	/** カード仕様期限(年)	 */
	private String card_exp_year;
	/** カード仕様期限(月)	 */
	private String card_exp_month;
	/** カード名義	 */
	@NotBlank(message="名前を入力してください")
	@Size(max=50,message="名義は50文字以下で入力してください")
	private String card_name;
	/** セキュリティコード	 */
	@Size(min=3,max=4,message="形式が不正です")
	private String card_cvv;
	/** ユーザID	 */
	private String user_id;
	/** 合計金額	 */
	private String amount;
	
	private String order_number;
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCard_exp_year() {
		return card_exp_year;
	}
	public void setCard_exp_year(String card_exp_year) {
		this.card_exp_year = card_exp_year;
	}
	public String getCard_exp_month() {
		return card_exp_month;
	}
	public void setCard_exp_month(String card_exp_month) {
		this.card_exp_month = card_exp_month;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_cvv() {
		return card_cvv;
	}
	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	@Override
	public String toString() {
		return "PayForm [card_number=" + card_number + ", card_exp_year=" + card_exp_year + ", card_exp_month="
				+ card_exp_month + ", card_name=" + card_name + ", card_cvv=" + card_cvv + ", user_id=" + user_id
				+ ", amount=" + amount + ", order_number=" + order_number + "]";
	}
	
	
}
