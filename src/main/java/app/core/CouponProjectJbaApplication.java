package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import app.core.couponProjectExceptions.DaoException;
import app.core.filters.TokenFilter;
import app.core.security.JwtUtil;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class CouponProjectJbaApplication {
	public static void main(String[] args) throws DaoException {
		SpringApplication.run(CouponProjectJbaApplication.class, args);
	}
	
	
	@Bean
	public FilterRegistrationBean<TokenFilter> tokenFilterRegistration(JwtUtil jwtUtil){
		FilterRegistrationBean<TokenFilter> filterRegistrationBean = new FilterRegistrationBean<TokenFilter>();
		TokenFilter tokenFilter = new TokenFilter(jwtUtil);
		filterRegistrationBean.setFilter(tokenFilter);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}