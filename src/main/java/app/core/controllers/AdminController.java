package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.couponProjectExceptions.DaoException;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.security.JwtUtil;
import app.core.services.AdminService;
//import app.core.sessions.Session;
//import app.core.sessions.SessionContext;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	JwtUtil jwtUtil;

	private AdminService getService(String token) throws DaoException {
		try {
			if (!jwtUtil.isTokenExpired(token)) {
				if (jwtUtil.extractUserType(token) == 0) {
					return adminService;
				}
			}
			throw new DaoException("You are not logged in !!!");
		} catch (Exception e) {
			throw new DaoException("You are not logged in !!!");
		}
	}

	@PostMapping("/addCompany")
	public Company addCompany(@RequestHeader String token, @RequestBody Company company) {
		try {
			return getService(token).addCompany(company);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PostMapping("/updateCompany")
	public Company updateCompany(@RequestHeader String token, @RequestBody Company company) {
		try {
			return getService(token).updateCompany(company);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PostMapping("/updateCustomer")
	public Customer updateCustomer(@RequestHeader String token, @RequestBody Customer customer) {
		try {
			return getService(token).updateCustomer(customer);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/deleteCompany/{companyId}")
	public Company deleteCompany(@RequestHeader String token, @PathVariable int companyId) {
		try {
			return getService(token).deleteCompany(companyId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getAllCompanies")
	public List<Company> getAllCompanies(@RequestHeader String token) {
		try {
			return getService(token).getAllCompanies();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCompany/{companyId}")
	public Company getCompany(@RequestHeader String token, @PathVariable int companyId) {
		try {
			Company company = getService(token).getOneCompany(companyId);
			return company;
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCompanyCoupons/{companyId}")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token, @PathVariable int companyId) {
		try {
			return getService(token).getCompanyCoupons(companyId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PostMapping("/addCustomer")
	public Customer addCustomer(@RequestHeader String token, @RequestBody Customer customer) {
		try {
			return getService(token).addCustomer(customer);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/deleteCustomer/{customerId}")
	public Customer deleteCustomer(@RequestHeader String token, @PathVariable int customerId) {
		try {
			return getService(token).deleteCustomer(customerId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getAllCustomers")
	public List<Customer> getAllCustomers(@RequestHeader String token) {
		try {
			return getService(token).getAllCustomers();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCustomer/{customerId}")
	public Customer getCustomer(@RequestHeader String token, @PathVariable int customerId) {
		try {
			Customer customer = getService(token).getOneCustomer(customerId);
			return customer;
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCustomerCoupons/{customerId}")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token, @PathVariable int customerId) {
		try {
			return getService(token).getCustomerCoupons(customerId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

}
