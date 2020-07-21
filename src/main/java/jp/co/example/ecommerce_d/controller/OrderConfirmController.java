package jp.co.example.ecommerce_d.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.example.ecommerce_d.PayService;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.PayDto;
import jp.co.example.ecommerce_d.form.OrderExcuteForm;
import jp.co.example.ecommerce_d.form.PayForm;
import jp.co.example.ecommerce_d.service.OrderConfirmService;
import jp.co.example.ecommerce_d.service.OrderExcuteService;

/**
 * 注文確認のコントローラ.
 * 
 * @author yu.konishi
 *
 */
@Controller
@RequestMapping("/showConfirmList")
public class OrderConfirmController {

	/**
	 * セッションスコープ
	 */
	@Autowired
	private HttpSession session;
	/**
	 * 注文確認のサービス
	 */
	@Autowired
	private OrderConfirmService orderConfirmService;
	/**
	 * 注文処理のサービス
	 */
	@Autowired
	private OrderExcuteService orderExcuteService;
	
	@Autowired
	private PayService payService;
	
	@ModelAttribute
	public PayForm setUpForm() {
		return new PayForm();
	}

	@ModelAttribute
	public OrderExcuteForm setUpExcuteForm() {
		return new OrderExcuteForm();
	}

	/**
	 * 注文確認画面を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return 注文確認画面
	 */
	@RequestMapping("")
	public String showOrderList(Model model) {
		Integer userId = (Integer) session.getAttribute("loginId");
		if (userId == null) {
			session.setAttribute("backPage", "showCartList");
			return "redirect:/loginUser";
		}
		Order order = orderConfirmService.showConfirmList(userId);
		if (order == null) {
			return "redirect:/";
		}
		model.addAttribute("order", order);
		if (order.getOrderItemList().isEmpty()) {
			return "redirect:/";
		}
		return "order_confirm";
	}

	/**
	 * 注文処理をする.
	 * 
	 * @param form   注文処理のフォーム
	 * @param result エラー一覧
	 * @param model  リクエストスコープ
	 * @param flash  フラッシュスコープ
	 * @return 注文処理
	 */
	@RequestMapping("/orderExcute")
	public String orderExcute(@Validated OrderExcuteForm form, BindingResult result, Model model) {
		if (!result.hasFieldErrors("date")) {
			String strDate = form.getDate() + " " + form.getTime() + ":00:00";
			long delivaryTime = Timestamp.valueOf(strDate).getTime();
			long now = new Date().getTime();
			long time = delivaryTime - now;
			if (time < 3 * 60 * 60 * 1000) {
				result.addError(new FieldError(result.getObjectName(), "date", "今から3時間後の日時をご入力ください"));
			}
		}
		if (result.hasErrors()) {
			return showOrderList(model);
		}
		Integer userId = (Integer)session.getAttribute("userId");
		orderExcuteService.orderExcute(form,userId);
		return "redirect:/email";
	}
	
	@RequestMapping("/update")
	public String updateOrder() {
		Integer userId = (Integer)session.getAttribute("userId");
		orderExcuteService.updateOrder(userId);
		return "redirect:/toFinish";
	}
	
	/**
	 * クレジットカード認証APIへのアクセス.
	 * @param session
	 * @param model
	 * @param form
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/pay", method=RequestMethod.POST)
    public String PayConfirm(HttpSession session, Model model, 
                                 @Validated PayForm form, BindingResult result,
                                 RedirectAttributes redirectAttributes){

        if(result.hasErrors()) {
        	return showOrderList(model);
        }
        // クレカAPIサービス呼び出し
        PayDto payDto = payService.service(form);
        // thymeleafでリストを展開して表示する
        System.out.println(payDto.getStatus());
        if("success".equals(payDto.getStatus())) {
        	redirectAttributes.addFlashAttribute("credit",true);
        	return "redirect:/showConfirmList";
        }else {
        	redirectAttributes.addFlashAttribute("statusError",true);
        	return "redirect:/showConfirmList";  
        }
    }
	
}
