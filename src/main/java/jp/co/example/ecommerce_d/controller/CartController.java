package jp.co.example.ecommerce_d.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.common.Status;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.domain.OrderTopping;
import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.form.AddOrderForm;
import jp.co.example.ecommerce_d.service.CartService;

@Controller
@RequestMapping("")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/addOrder")
	public String addOrder(Model model) {
		System.out.println("/addOrder: "+(Integer)session.getAttribute("userId"));
		Integer userId = cartService.checkUserId();
		List<Order> orderList = cartService.findByUserIdAndStatus(userId, Status.BEFOREORDER.getStatusId());
		if (orderList == null) {
			User user = cartService.loadUser(userId);
			if (user == null) {
				cartService.createCart(userId);
			} else {
				cartService.createCart(user);
			}
		}
		orderList = new ArrayList<>();
		orderList = cartService.findByUserIdAndStatus(userId, Status.BEFOREORDER.getStatusId());
		OrderItem orderItem = new OrderItem();
		AddOrderForm addOrder = (AddOrderForm) model.getAttribute("addOrder");
		if (addOrder != null) {
			int itemOrderId = -1;
			try {
				orderItem.setItemId(Integer.parseInt(addOrder.getItemId()));
				orderItem.setOrderId(orderList.get(0).getId());
				orderItem.setQuantity(Integer.parseInt(addOrder.getQuantity()));
				if ('M' == addOrder.getSize() || 'L' == addOrder.getSize()) {
					orderItem.setSize(addOrder.getSize());
				} else {
					throw new IllegalArgumentException("不正な操作が行われました");
				}
				itemOrderId = cartService.addOrderItem(orderItem);
				List<OrderTopping> orderToppingList = new ArrayList<>();
				OrderTopping orderTopping;
				for (String toppingId : addOrder.getToppings()) {
					orderTopping = new OrderTopping();
					orderTopping.setOrderItemId(itemOrderId);
					orderTopping.setToppingId(Integer.parseInt(toppingId));
					orderToppingList.add(orderTopping);
				}
				if (orderToppingList.size() != 0) {
					cartService.addOrderTopping(orderToppingList);
				}
			} catch (Exception e) {
				return "redirect:/";
			}
		}
		return "redirect:/showCartList";
	}

}
