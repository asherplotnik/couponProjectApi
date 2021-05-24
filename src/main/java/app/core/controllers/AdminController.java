package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("/addCompany")
	public Company addCompany(@RequestHeader String token, @RequestBody Company company) {
		try {
			return adminService.addCompany(company);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PutMapping("/updateCompany")
	public Company updateCompany(@RequestHeader String token, @RequestBody Company company) {
		try {
			return adminService.updateCompany(company);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PutMapping("/updateCustomer")
	public Customer updateCustomer(@RequestHeader String token, @RequestBody Customer customer) {
		try {
			return adminService.updateCustomer(customer);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/deleteCompany/{companyId}")
	public Company deleteCompany(@RequestHeader String token, @PathVariable int companyId) {
		try {
			return adminService.deleteCompany(companyId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getAllCompanies")
	public List<Company> getAllCompanies(@RequestHeader String token) {
		try {
			return adminService.getAllCompanies();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCompany/{companyId}")
	public Company getCompany(@RequestHeader String token, @PathVariable int companyId) {
		try {
			Company company = adminService.getOneCompany(companyId);
			return company;
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCompanyCoupons/{companyId}")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token, @PathVariable int companyId) {
		try {
			return adminService.getCompanyCoupons(companyId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PostMapping("/addCustomer")
	public Customer addCustomer(@RequestHeader String token, @RequestBody Customer customer) {
		try {
			return adminService.addCustomer(customer);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/deleteCustomer/{customerId}")
	public Customer deleteCustomer(@RequestHeader String token, @PathVariable int customerId) {
		try {
			return adminService.deleteCustomer(customerId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getAllCustomers")
	public List<Customer> getAllCustomers(@RequestHeader String token) {
		try {
			return adminService.getAllCustomers();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCustomer/{customerId}")
	public Customer getCustomer(@RequestHeader String token, @PathVariable int customerId) {
		try {
			Customer customer = adminService.getOneCustomer(customerId);
			return customer;
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("getCustomerCoupons/{customerId}")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token, @PathVariable int customerId) {
		try {
			return adminService.getCustomerCoupons(customerId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

}
