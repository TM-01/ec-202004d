package jp.co.example.ecommerce_d.common;

/**
 * 支払方法に関する列挙型です
 * 
 * @author kazuteru.takahashi
 *
 */
public enum PaymentMethod {
	CASH(1, "代引き"), CREDITCARD(2, "クレジットカード");

	private final int paymentId;
	private final String PaymentName;

	private PaymentMethod(int paymentId, String paymentName) {
		this.paymentId = paymentId;
		this.PaymentName = paymentName;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public String getPaymentName() {
		return PaymentName;
	}

	public static PaymentMethod getPaymentMethod(int paymentId) {
		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			if (paymentMethod.getPaymentId() == paymentId) {
				return paymentMethod;
			}
		}
		throw new IllegalArgumentException("不正な決済方法です");
	}
}
