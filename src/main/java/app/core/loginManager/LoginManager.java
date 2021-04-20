package app.core.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.couponProjectExceptions.DaoException;
import app.core.security.JwtUtil;
import app.core.services.AdminService;
//import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class LoginManager {
	@Autowired
	ApplicationContext ctx;
	@Autowired
	JwtUtil jwtUtil;

	public String login(String email, String password, int userType) throws DaoException {
		try {
		String jwtToken=null;
		switch (userType) {
		case 0:
			AdminService adminService = (AdminService) ctx.getBean(AdminService.class);
			if (adminService.login(email, password)) {
				jwtToken = jwtUtil.generateToken(email, password, 0, 0);
				System.out.println(jwtToken);
				System.out.println(jwtUtil.extractUserType(jwtToken));
				System.out.println(jwtUtil.extractPassword(jwtToken));
				System.out.println(jwtUtil.extractUsername(jwtToken));
				System.out.println(jwtUtil.validateToken(jwtToken, email));
				return jwtToken;
			}
			break;
		case 1:
			CompanyService companyService = (CompanyService) ctx.getBean(CompanyService.class);
			//login method set company id if found email and password match
			if (companyService.login(email, password)) {
				jwtToken = jwtUtil.generateToken(email, password, 1,companyService.getCompanyId());
				return jwtToken;
			}
			break;
		case 2:
			CustomerService customerService = (CustomerService) ctx.getBean(CustomerService.class);
			if (customerService.login(email, password)) {
				jwtToken = jwtUtil.generateToken(email, password, 2,customerService.getCustomerId());
				return jwtToken;
			}
			break;
		default:
			throw new DaoException("Login Failed");
		}
		throw new DaoException("Login Failed");
		} catch (Exception e) {
			throw new DaoException("Login Failed - "+e.getLocalizedMessage());
		}
	}

}
