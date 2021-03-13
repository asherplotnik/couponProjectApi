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
public class CustomerService extends ClientService{
	private int customerId;
	
	public CustomerService() {
	}
	
	public boolean login(String email, String password) {
		Customer cust = customerRepository.getByEmail(email);
		if (cust != null && cust.getPassword().equals(password)) {
			this.customerId = cust.getId();
			return true;
		} 
		return false;
	}

	public int getCustomerId() {
		return customerId;
	}
	
	public Coupon purchaseCoupon(int id) throws DaoException{
		Coupon temp=null;
		Optional<Coupon> opt = couponRepository.findById(id);
		if (opt.isPresent()) {
			temp = opt.get();
		} else {				
			throw new DaoException("Cant puchase this coupon  - Coupon don't exists!!!");
		}
		if (temp.getAmount() == 0) {
			throw new DaoException("Cant puchase this coupon  - out of stock!!!");
		}
		if	( temp.getEndDate().isBefore(LocalDate.now()) ) {	
			throw new DaoException("Cant puchase this coupon  - Coupon expiration date passed already.");
		}
		
		if (getCustomerCoupons().contains(temp)) {
			throw new DaoException("Cant puchase this coupon more than once - Coupon already purchased.");
		}
		Customer customer = getCustomerDetails(); 
		temp.setAmount(temp.getAmount()-1);
		customer.addCoupon(temp);
		System.out.println("Purchased coupon successfully !");
		return temp;
	}
	
	public Coupon getCouponById(int id){
		Optional<Coupon> opt = couponRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	public List<Coupon> getCustomerCoupons(){
		return couponRepository.getCouponsByCustomersId(customerId);
	}
	
	public List<Coupon> getCustomerCouponsByCategory(int categoryId){
		return couponRepository.getCouponsByCustomersIdAndCategoryId(customerId, categoryId);
	
	}
	
	public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice ){
		return couponRepository.getCouponsByCustomersIdAndPriceLessThan(customerId, maxPrice);
	}
	
	public Customer getCustomerDetails() throws DaoException{
		Optional<Customer> opt =  customerRepository.findById(customerId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new DaoException("error");
		}
	}
	
	public List<Coupon> getAvailableCouponsforCustomer() {
		return couponRepository.getAvailableCoupons(customerId);
	}
	
	
}
