package jp.co.example.ecommerce_d.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.common.Status;
import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.domain.OrderTopping;
import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.repository.ItemRepository;
import jp.co.example.ecommerce_d.repository.OrderItemRepository;
import jp.co.example.ecommerce_d.repository.OrderRepository;
import jp.co.example.ecommerce_d.repository.OrderToppingRepository;
import jp.co.example.ecommerce_d.repository.UserRepository;

/**
 * ショッピングカートの表示のサービス.
 * 
 * @author yu.konishi
 *
 */
@Service
@Transactional
public class ViewCartService {

	/**
	 * usersを操作するリポジトリ
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * ordersを操作するリポジトリ
	 */
	@Autowired
	private OrderRepository orderRepository;
	/**
	 * order_itemsを操作するリポジトリ
	 */
	@Autowired
	private OrderItemRepository orderItemRepository;
	/**
	 * order_toppingsを操作するリポジトリ
	 */
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	/**
	 * itemsを操作するリポジトリ
	 */
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 注文商品を取得し、注文情報に合計金額を設定する.
	 * 
	 * @param userId ユーザID
	 * @return 注文情報
	 */
	public Order viewCart(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		if (orderList == null) {
			User user = userRepository.load(userId);
			if (user == null) {
				createCart(userId);
			} else {
				createCart(user);
			}
			orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		}
		Order order = orderList.get(0);
		List<OrderItem> orderItemList = orderItemRepository.findByOrderIdAndStatus(order.getId(), 0);
		int totalPrice = 0;
		for (OrderItem orderItem : orderItemList) {
			Item item = itemRepository.load(orderItem.getItemId());
			orderItem.setItem(item);
			List<OrderTopping> orderToppingList = orderToppingRepository.findByOrderItemId(orderItem.getId());
			orderItem.setOrderToppingList(orderToppingList);
			totalPrice += orderItem.getSubTotal();
		}
		order.setOrderItemList(orderItemList);
		if (LocalDate.now().isEqual(LocalDate.of(2020, 7, 21))) {
			totalPrice = (int)(totalPrice * 0.6);
		}
		order.setTotalPrice(totalPrice);
		return order;
	}

	/**
	 * 乱数のユーザIDを生成し返す.
	 * 
	 * @return ユーザID
	 */
	synchronized public Integer makeRandomUserId() {
		List<Order> orderList = new ArrayList<>();
		int newUserId = 0;
		while (orderList != null) {
			newUserId = (int) ((Integer.MAX_VALUE - 1_000_000_000) * Math.random() + 1_000_000_000);
			orderList = orderRepository.findByUserIdAndStatus(newUserId, Status.BEFOREORDER.getStatusId());
		}
		return newUserId;
	}

	/**
	 * ユーザIDからオーダを作成します.
	 * 
	 * @param userId ユーザID
	 */
	synchronized public void createCart(int userId) {
		orderRepository.insert(userId);
	}

	/**
	 * ユーザ情報からオーダを作成します.
	 * 
	 * @param user ユーザ情報
	 */
	synchronized public void createCart(User user) {
		orderRepository.insert(user);
	}

}
