package jp.co.example.ecommerce_d.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.form.OrderExcuteForm;
import jp.co.example.ecommerce_d.repository.OrderRepository;

/**
 * 注文処理を行うサービス.
 * 
 * @author yu.konishi
 *
 */
@Service
@Transactional
public class OrderExcuteService {

	/**
	 * ordersを操作するリポジトリ
	 */
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 注文処理を行う.
	 * 
	 * @param form リクエストスコープ
	 * @param userId ユーザID
	 */
	public void orderExcute(OrderExcuteForm form, Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		Order order = orderList.get(0);
		BeanUtils.copyProperties(form, order);
		order.setOrderDate(new Date());
		order.setDestinationZipcode(form.getDestinationZipcode().replace("-", ""));
		order.setDestinationTel(form.getDestinationTel().replace("-", ""));
		String strDate = form.getDate() + " " + form.getTime() + ":00:00";		
		order.setDeliveryTime(Timestamp.valueOf(strDate));
		order.setPaymentMethod(Integer.parseInt(form.getPaymentMethod()));
		orderRepository.updateDestinationAndPaymentMethod(order, 0);
	}
	
	/**
	 * オーダを更新する.
	 * 
	 * @param userId ユーザID
	 */
	public void updateOrder(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId,0);
		Order order = orderList.get(0);
		if (order.getPaymentMethod() == 1) {
			orderRepository.updateDestinationAndPaymentMethod(order, 1);
		} else if (order.getPaymentMethod() == 2) {
			orderRepository.updateDestinationAndPaymentMethod(order, 2);
		}
	}

}
