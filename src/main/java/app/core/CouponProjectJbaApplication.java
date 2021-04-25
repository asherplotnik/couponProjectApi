package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import app.core.couponProjectExceptions.DaoException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class CouponProjectJbaApplication {
	public static void main(String[] args) throws DaoException {
		SpringApplication.run(CouponProjectJbaApplication.class, args);
	}
	
}