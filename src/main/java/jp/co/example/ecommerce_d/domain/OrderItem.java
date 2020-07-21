package jp.co.example.ecommerce_d.domain;

import java.util.List;

/**
 * 注文商品を表すドメイン.
 * 
 * @author yu.konishi
 *
 */
public class OrderItem {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 商品ID
	 */
	private Integer itemId;
	/**
	 * 注文ID
	 */
	private Integer orderId;
	/**
	 * 数量
	 */
	private Integer quantity;
	/**
	 * サイズ
	 */
	private Character size;
	/**
	 * 商品
	 */
	private Item item;
	/**
	 * 注文トッピング一覧
	 */
	private List<OrderTopping> orderToppingList;

	public OrderItem() {
		super();
	}

	public OrderItem(Integer id, Integer itemId, Integer orderId, Integer quantity, Character size, Item item,
			List<OrderTopping> orderToppingList) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.orderId = orderId;
		this.quantity = quantity;
		this.size = size;
		this.item = item;
		this.orderToppingList = orderToppingList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", orderToppingList=" + orderToppingList + "]";
	}

	/**
	 * 注文商品の小計を計算する.
	 * 
	 * @return 小計
	 */
	public int getSubTotal() {
		int subtotal = 0;
		if (size == 'M') {
			if (orderToppingList.isEmpty()) {
				subtotal = item.getPriceM() * quantity;
			} else {
				subtotal = (item.getPriceM()
						+ orderToppingList.get(0).getTopping().getPriceM() * orderToppingList.size()) * quantity;
			}
		} else if (size == 'L') {
			if (orderToppingList.isEmpty()) {
				subtotal = item.getPriceL() * quantity;
			} else {
				subtotal = (item.getPriceL()
						+ orderToppingList.get(0).getTopping().getPriceL() * orderToppingList.size()) * quantity;
			}
		}
		return subtotal;
	}

}
