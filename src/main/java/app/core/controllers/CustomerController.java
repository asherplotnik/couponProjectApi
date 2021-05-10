package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.couponProjectExceptions.DaoException;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.security.JwtUtil;
import app.core.services.CustomerService;
//import app.core.sessions.Session;
//import app.core.sessions.SessionContext;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	private CustomerService customerService;

	private CustomerService getService(String token) throws DaoException {
		try {
			int id = jwtUtil.extractId(token);
			customerService.setCustomerId(id);
			return customerService;
		} catch (Exception e) {
			throw new DaoException("You are not logged in !!!");
		}
	}

	@GetMapping("/getCustomerDetails")
	public Customer getCustomerDetails(@RequestHeader String token) {
		try {
			return getService(token).getCustomerDetails();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PostMapping("/purchaseCoupon/{couponId}")
	public Coupon purchaseCoupon(@RequestHeader String token, @PathVariable int couponId) {
		try {
			return getService(token).purchaseCoupon(couponId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCustomerCoupons")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token) {
		try {
			return getService(token).getCustomerCoupons();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCustomerCouponsByCategory/{id}")
	public List<Coupon> getCustomerCouponsByCategory(@RequestHeader String token, @PathVariable int id) {
		try {
			return getService(token).getCustomerCouponsByCategory(id);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCustomerCouponsByMaxPrice/{max}")
	public List<Coupon> getCustomerCouponsByMaxPrice(@RequestHeader String token, @PathVariable double max) {
		try {
			return getService(token).getCustomerCouponsByMaxPrice(max);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getAvailableCouponsforCustomer")
	public List<Coupon> getAvailableCouponsforCustomer(@RequestHeader String token) {
		try {
			return getService(token).getAvailableCouponsforCustomer();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCustomerCoupon/{id}")
	public Coupon getCustomerCoupon(@RequestHeader String token, @PathVariable int id) {
		try {
			return getService(token).getCustomerCouponById(id);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

}
