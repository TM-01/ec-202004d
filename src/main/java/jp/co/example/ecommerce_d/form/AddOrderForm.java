package jp.co.example.ecommerce_d.form;

import java.util.Arrays;

/**
 * 注文情報を保持するフォームです.
 * 
 * @author kazuteru.takahashi
 *
 */

public class AddOrderForm {
	private String itemId;
	private Character size;
	private String[] toppings;
	private String quantity;

	@Override
	public String toString() {
		return "AddOrderForm [itemId=" + itemId + ", size=" + size + ", toppings=" + Arrays.toString(toppings)
				+ ", quantity=" + quantity + "]";
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public String[] getToppings() {
		return toppings;
	}

	public void setToppings(String[] toppings) {
		this.toppings = toppings;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
