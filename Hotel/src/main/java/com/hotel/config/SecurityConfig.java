package com.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			//HTTTP ServletRequest를 사용하는 요청들에 대한 접근 제한 설정,
		http.authorizeHttpRequests().requestMatchers("/").permitAll() // 해당 주소로 시작하는 값은 모든 이용자가 접근가능			
			.anyRequest()
			.authenticated();
		 
		http.formLogin()
				.loginPage("/users") //로그인을 실행하게될 FORM페이지로의 이동
				.loginProcessingUrl("/users/") //해당하는 url로 접근시 로그인의 기능이 작동, request되는 값을 컨트롤러가 아닌 이곳으로 넘겨받음.
				.usernameParameter("m_id") // default= Username   파라미터로 넘어오는 값의 이름을 설정
				.passwordParameter("m_pwd")// default =Password
				.defaultSuccessUrl("/index")	// 로그인 성공시 이동하는 페이지
				.failureUrl("/users")	// 로그인 실패시 이동하는 페이지
				.permitAll()	//모두 접근을 허용.
				.and()
				.logout() // 로그아웃에 대한 설정
				.permitAll();
		return http.build();
	}
	


	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() { // css,js,img등의 시큐리티 필터적용이 필요없는 자원에 대한 접근을 설정,
		return (web) -> web.ignoring().requestMatchers("/css/**","/font/**","/img/**");
		//requestMatcher.permitAll과 비슷하지만 permitAll은 Security필터에서 한번 검증을 받고 넘어가고 webignoring은 아예 필터를 거치지 않고 통과.
	}

}
