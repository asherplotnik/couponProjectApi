package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import app.core.couponProjectExceptions.DaoException;
import app.core.entities.Coupon;
import app.core.entities.Customer;

@Service
@Transactional
@Scope("prototype")
public class CustomerService extends ClientService {
	private int customerId;

	public CustomerService() {
	}

	@Override
	public boolean login(String email, String password) throws DaoException {
		try {
			Customer cust = customerRepository.getByEmail(email);
			if (cust != null && cust.getPassword().equals(password)) {
				this.customerId = cust.getId();
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new DaoException("Login customer failed !!!");
		}
	}

	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Coupon purchaseCoupon(int id) throws DaoException {
		try {
			Coupon temp = null;
			Optional<Coupon> opt = couponRepository.findById(id);
			if (opt.isPresent()) {
				temp = opt.get();
			} else {
				throw new DaoException("Cant puchase this coupon  - Coupon don't exists!!!");
			}
			if (temp.getAmount() == 0) {
				throw new DaoException("Cant puchase this coupon  - out of stock!!!");
			}
			if (temp.getEndDate().isBefore(LocalDate.now())) {
				throw new DaoException("Cant puchase this coupon  - Coupon expiration date passed already.");
			}

			if (getCustomerCoupons().contains(temp)) {
				throw new DaoException("Cant puchase this coupon more than once - Coupon already purchased.");
			}
			Customer customer = getCustomerDetails();
			temp.setAmount(temp.getAmount() - 1);
			customer.addCoupon(temp);
			System.out.println("Purchased coupon successfully !");
			return temp;
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public Coupon getCustomerCouponById(int id) throws DaoException {
		try {
			Optional<Coupon> opt = couponRepository.findByIdAndCustomersId(id,getCustomerDetails().getId());
			if (opt.isEmpty()) {
				throw new DaoException("Coupon not found.");
			}
			return opt.get();
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Coupon> getCustomerCoupons() throws DaoException {
		try {
			return couponRepository.getCouponsByCustomersId(customerId);
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Coupon> getCustomerCouponsByCategory(int categoryId) throws DaoException {
		try {
			return couponRepository.getCouponsByCustomersIdAndCategoryId(customerId, categoryId);
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws DaoException {
		try {
			return couponRepository.getCouponsByCustomersIdAndPriceLessThan(customerId, maxPrice);
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public Customer getCustomerDetails() throws DaoException {
		try {
			Optional<Customer> opt = customerRepository.findById(customerId);
			if (opt.isPresent()) {
				return opt.get();
			} else {
				throw new DaoException("error");
			}
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Coupon> getAvailableCouponsforCustomer() throws DaoException {
		try {
			return couponRepository.getAvailableCoupons(customerId);
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

}
