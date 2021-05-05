package app.core.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.core.couponProjectExceptions.DaoException;
import app.core.security.JwtUtil;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.utils.UserPayload;

@Component
public class LoginManager {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CompanyService companyService; 
	@Autowired
	private CustomerService customerService;
	
	public UserPayload login(String email, String password) throws DaoException {
		try {
			String jwtToken = null;
			int userType = checkUser(email, password);
			switch (userType) {
			case 0:
				jwtToken = jwtUtil.generateToken(email, "Admin", 0, 0);
				return new UserPayload(jwtToken,"Admin",0);
			case 1:
				// login method set company id if found email and password match
				String compName = companyService.getCompanyDetails().getName();
				jwtToken = jwtUtil.generateToken(email, compName, 1, companyService.getCompanyId());
				return new UserPayload(jwtToken, compName,1);
			case 2:
				// login method set customer id if found email and password match
				String custName = customerService.getCustomerDetails().getFirst_name();
				jwtToken = jwtUtil.generateToken(email, custName, 2, customerService.getCustomerId());
				return new UserPayload(jwtToken,custName,2);
			default:
				throw new DaoException("Login Failed");
			}
		} catch (Exception e) {
			throw new DaoException("Login Failed - " + e.getLocalizedMessage());
		}
	}

	private int checkUser(String email, String password) throws DaoException {	
		try {
			if (adminService.login(email, password)) 
				return 0;
			if (companyService.login(email, password)) 
				return 1;
			if (customerService.login(email, password)) 
				return 2;
			return -1;
		} catch (Exception e) {
			throw new DaoException("LOGIN FAILED !!!");
		}
	}
}
