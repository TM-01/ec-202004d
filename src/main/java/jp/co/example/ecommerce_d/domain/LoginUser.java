package jp.co.example.ecommerce_d.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * ログインユーザの権限格納.
 * @author tatsuro.miyazaki
 *
 */
public class LoginUser  extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = 1L;
	/** ユーザ情報 */
	private final User userInfo;
	
	/**
	 * ユーザに権限を追加.
	 * @param userInfo
	 * @param authorityList
	 */
	public LoginUser(User userInfo, Collection<GrantedAuthority> authorityList) {
		super(userInfo.getEmail(), userInfo.getPassword(), authorityList);
		this.userInfo = userInfo;
	}

	public User getUserInfo() {
		return userInfo;
	}
	
}
