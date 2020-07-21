package jp.co.example.ecommerce_d;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * springsecurityを利用したログイン.
 * @author tatsuro.miyazaki
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService memberDetailsService;
	
	/**
	 * 静的ファイルの許可関係.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers( "/css/**"
					, "/img_aloha/**"
					, "/js/**"
					, "/fonts/**");
	}
	
	/**
	 * メソッドの許可関係.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated();

		http.formLogin()
			.loginPage("/loginUser")
			.loginProcessingUrl("/login")
			.failureUrl("/loginUser/faile")
			.defaultSuccessUrl("/loginUser/succes", false)
			.usernameParameter("email")
			.passwordParameter("password");
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
			.logoutSuccessUrl("/loginUser/exit")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true);
		
	}

	/**
	 * パスワードのエンコーダにBcriptを使用
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * パスワードの認証をハッシュで行う.
	 * @return
	 */
    @Bean
    public PasswordEncoder passwordEncoder() {
    		return new BCryptPasswordEncoder();
    }
}
