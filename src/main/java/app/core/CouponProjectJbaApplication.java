package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import app.core.couponProjectExceptions.DaoException;
import app.core.filters.AdminFilter;
import app.core.filters.CompanyFilter;
import app.core.filters.CustomerFilter;
import app.core.filters.LoginFilter;
import app.core.sessions.SessionContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class CouponProjectJbaApplication {
	public static void main(String[] args) throws DaoException {
		SpringApplication.run(CouponProjectJbaApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilterRegistration(SessionContext sessionContext){
		FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<LoginFilter>();
		LoginFilter loginFilter = new LoginFilter(sessionContext);
		filterRegistrationBean.setFilter(loginFilter);
		filterRegistrationBean.addUrlPatterns("/api/*");
		return filterRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<AdminFilter> adminFilterRegistration(SessionContext sessionContext){
		FilterRegistrationBean<AdminFilter> filterRegistrationBean = new FilterRegistrationBean<AdminFilter>();
		AdminFilter adminFilter = new AdminFilter(sessionContext);
		filterRegistrationBean.setFilter(adminFilter);
		filterRegistrationBean.addUrlPatterns("/api/admin/*");
		return filterRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<CompanyFilter> companyFilterRegistration(SessionContext sessionContext){
		FilterRegistrationBean<CompanyFilter> filterRegistrationBean = new FilterRegistrationBean<CompanyFilter>();
		CompanyFilter companyFilter = new CompanyFilter(sessionContext);
		filterRegistrationBean.setFilter(companyFilter);
		filterRegistrationBean.addUrlPatterns("/api/company/*");
		return filterRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<CustomerFilter> customerFilterRegistration(SessionContext sessionContext){
		FilterRegistrationBean<CustomerFilter> filterRegistrationBean = new FilterRegistrationBean<CustomerFilter>();
		CustomerFilter customerFilter = new CustomerFilter(sessionContext);
		filterRegistrationBean.setFilter(customerFilter);
		filterRegistrationBean.addUrlPatterns("/api/customer/*");
		return filterRegistrationBean;
	}

}