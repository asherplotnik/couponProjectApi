package app.core.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.couponProjectExceptions.DaoException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class LoginManager {	
	@Autowired
	ApplicationContext ctx;
	public ClientService login(String email, String password, int userType) throws DaoException {
		switch (userType) {
		case 0:
			AdminService adminService = (AdminService)ctx.getBean(AdminService.class);
				if (adminService.login(email, password))
					return adminService;
				break;
		case 1:
			CompanyService companyService = (CompanyService)ctx.getBean(CompanyService.class);
			if (companyService.login(email, password))
				return companyService;
			break;
		case 2:
			CustomerService customerService = (CustomerService)ctx.getBean(CustomerService.class);
			if (customerService.login(email, password))
				return customerService;
			break;
		default:
			throw new DaoException("Login Failed");
		}
		throw new DaoException("Login Failed");
	}

}
