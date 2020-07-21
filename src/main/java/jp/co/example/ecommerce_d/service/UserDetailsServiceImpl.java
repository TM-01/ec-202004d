package jp.co.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jp.co.example.ecommerce_d.domain.LoginUser;
import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * DBを検索して情報を返す.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User userinfo = userRepository.findByEmail(email);
		if (userinfo == null) {
			throw new UsernameNotFoundException("そのEmailは登録されていません。");
		}
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (passwordEncoder.matches("administrator",userinfo.getPassword())) {
			if ("admin@test.co.jp".equals(userinfo.getEmail())) {
				authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
		}


		return new LoginUser(userinfo, authorityList);
	}
}
