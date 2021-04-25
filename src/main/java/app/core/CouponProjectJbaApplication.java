package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import app.core.couponProjectExceptions.DaoException;
import app.core.filters.SpaFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class CouponProjectJbaApplication {
	public static void main(String[] args) throws DaoException {
		SpringApplication.run(CouponProjectJbaApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<SpaFilter> loginFilterRegistration(){
		FilterRegistrationBean<SpaFilter> filterRegistrationBean = new FilterRegistrationBean<SpaFilter>();
		SpaFilter spaFilter = new SpaFilter();
		filterRegistrationBean.setFilter(spaFilter);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
	
}