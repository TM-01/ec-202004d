package jp.co.example.ecommerce_d;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.example.ecommerce_d.domain.PayDto;
import jp.co.example.ecommerce_d.form.PayForm;

/**
 * クレジットカード認証のAPIに情報送信.
 * @author tatsuro.miyazaki
 *
 */
@Service
public class PayService {
	@Autowired
    @Qualifier("PayRestTemplate")
    private RestTemplate restTemplate;

    /** 郵便番号検索API リクエストURL */
    private static final String URL = "http://192.168.15.44:8080/sample-credit-card-web-api/credit-card/payment";
    
    
    public PayDto service(PayForm payinfo) {
        return restTemplate.postForObject(URL, payinfo, PayDto.class);
    }

}
