package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.couponProjectExceptions.DaoException;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.services.CompanyService;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;
import app.core.utils.Payload;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

	@Autowired
	private SessionContext sessionContext;

	private CompanyService getService(String token) {
		Session session = sessionContext.getSession(token);
		return (CompanyService) session.getAttribute("service");
	}

	@GetMapping("/getCompanyDetails")
	public Company getCompanyDetails(@RequestHeader String token) {
		try {
			return getService(token).getCompanyDetails();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

//	@PostMapping("/addCoupon")
//	public Coupon addCoupon(@RequestHeader String token, @RequestBody Coupon coupon) {
//		try {
//			return getService(token).addCoupon(coupon);
//		} catch (DaoException e) {
//			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
//		}
//	}

	@PostMapping(path = "/addCoupon", consumes = { "multipart/form-data" })
	public Coupon addCoupon(@RequestHeader String token, @ModelAttribute Payload payload) {
		try {
			return getService(token).addCoupon(payload);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/deleteCoupon/{couponId}")
	public Coupon deleteCoupon(@RequestHeader String token, @PathVariable int couponId) {
		try {
			return getService(token).deleteCoupon(couponId);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@PostMapping("/updateCoupon")
	public Coupon updateCoupon(@RequestHeader String token, @ModelAttribute Payload payload) {
		try {
			return getService(token).updateCoupon(payload);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCompanyCouponById/{id}")
	public Coupon getCompanyCouponById(@RequestHeader String token, @PathVariable int id) {
		try {
			return getService(token).getCompanyCouponById(id);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCompanyCoupons")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token) {
		try {
			return getService(token).getCompanyCoupons();
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCompanyCouponsByCategory/{id}")
	public List<Coupon> getCompanyCouponsByCategory(@RequestHeader String token, @PathVariable int id) {
		try {
			return getService(token).getCompanyCouponsByCategory(id);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCompanyCouponsByMaxPrice/{max}")
	public List<Coupon> getCompanyCouponsByMaxPrice(@RequestHeader String token, @PathVariable double max) {
		try {
			return getService(token).getCompanyCouponsByMaxPrice(max);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

	@GetMapping("/getCompanyCoupon/{id}")
	public Coupon getCompanyCoupon(@RequestHeader String token, @PathVariable int id) {
		try {
			return getService(token).getCompanyCouponById(id);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getLocalizedMessage());
		}
	}

}
