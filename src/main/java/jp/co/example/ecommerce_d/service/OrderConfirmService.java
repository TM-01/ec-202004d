package jp.co.example.ecommerce_d.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * 注文確認のサービス
 * 
 * @author yu.konishi
 *
 */
@Service
@Transactional
public class OrderConfirmService {
	
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
	 * 注文確認画面を表示する.
	 * @param userId ユーザID
	 * @return オーダ(ない場合はnullを返す)
	 */
	public Order showConfirmList(int userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		if (orderList == null) {
			return null;
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
		orderRepository.updateTotalPrice(order.getId(), totalPrice);
		User user = userRepository.load(order.getUserId());
		StringBuilder br = new StringBuilder();
		br.append(user.getZipcode());
		br.insert(3, "-");
		user.setZipcode(new String(br));
		order.setUser(user);
		return order;
	}
	
}
