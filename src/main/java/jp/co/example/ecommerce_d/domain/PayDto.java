package jp.co.example.ecommerce_d.domain;
/**
 * クレジットカード認証のAPIから受け取る情報.
 * @author tatsuro.miyazaki
 *
 */
public class PayDto {
    /** 状態    */
	private String status;
	/** メッセージ    */
	private String message;
	/** エラーメッセージ   */
	private String error_code;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	@Override
	public String toString() {
		return "PayDto [status=" + status + ", message=" + message + ", error_code=" + error_code + "]";
	}
	
}
