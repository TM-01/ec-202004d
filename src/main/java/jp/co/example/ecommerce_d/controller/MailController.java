package jp.co.example.ecommerce_d.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.service.MailService;

/**
 * メール送信を行うコントローラー.
 * @author tatsuro.miyazaki
 *
 */
@Controller
@RequestMapping("/email")
public class MailController {
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Autowired
	private HttpSession session;
	@Autowired
	private MailSender mailSender;

	@Autowired
	private MailService mailService;
	
	/**
	 * メールの送信実行.
	 * @param id ユーザのログインID
	 */
	@RequestMapping("")
	public String sendMail() {
		String id = String.valueOf((Integer)session.getAttribute("userId"));
		List<Order> orderList = mailService.findByUserIdAndStatus(id);
		List<OrderItem> orderItemList = mailService.findByOrderIdAndStatus(String.valueOf(orderList.get(0).getId()));
		SimpleMailMessage msg = new SimpleMailMessage();
		int total = 0;
		String insertMessage = "注文　" + orderList.get(0).getDestinationName() + "様" + LINE_SEPARATOR;
		for (Order order : orderList) {
			insertMessage += "注文日:" + order.getOrderDate() + LINE_SEPARATOR;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH時");
			insertMessage += "配達日:" + formatter.format(order.getDeliveryTime().toLocalDateTime()) + LINE_SEPARATOR;
			insertMessage += "注文商品:" + LINE_SEPARATOR;
			for(OrderItem orderitem : orderItemList) {
				Item item = mailService.findItemName(orderitem.getItemId());
				insertMessage += "商品名:" + item.getName() + LINE_SEPARATOR;
			}
			if(order.getPaymentMethod()==1) {
				insertMessage += "支払い方法:代金引換" + LINE_SEPARATOR;
			}else {
				insertMessage += "支払い方法:クレジットカード" + LINE_SEPARATOR;
			}
			insertMessage += LINE_SEPARATOR;
			total += order.getCalcTotalPrice();
		}
		
		insertMessage += "合計金額:" + total + "円" + LINE_SEPARATOR;
		msg.setTo(orderList.get(0).getDestinationEmail());// 宛先メールアドレス
		msg.setSubject("ラクラクアロハ ご注文詳細");// メールのタイトル
		msg.setText(insertMessage);
		mailSender.send(msg);
		return "redirect:/showConfirmList/update";
	}
}
