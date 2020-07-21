package jp.co.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.repository.OrderItemRepository;
import jp.co.example.ecommerce_d.repository.OrderRepository;
import jp.co.example.ecommerce_d.repository.OrderToppingRepository;

/**
 * ショッピングカートの削除のサービス
 * 
 * @author yu.konishi
 *
 */
@Service
@Transactional
public class DeleteCartService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderToppingRepository orderToppingRepository;

	/**
	 * ショッピングカート内の注文商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @param userId      ユーザID
	 */
	public void deleteCartItem(Integer orderItemId, Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		if (orderList == null) {
			return;
		}
		List<OrderItem> orderItemList = orderItemRepository.findByOrderIdAndStatus(orderList.get(0).getId(),0);

		if (orderItemList.isEmpty()) {
			return;
		}
		orderItemRepository.deleteOrderItem(orderItemId, orderList.get(0).getId());
		orderToppingRepository.deleteOrderTopping(orderItemId);
	}

}
