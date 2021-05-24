package app.core.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.core.entities.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	List<Coupon> getCouponsByCustomersId(Integer customerId);
	
	List<Coupon> getCouponsByCustomersIdAndCategoryId(Integer customerId, int categoryId);

	List<Coupon> getCouponsByCustomersIdAndPriceLessThan(Integer customerId, double maxPrice);
	
	List<Coupon> getCouponsByCompanyId(Integer companyId);

	Optional<Coupon> getCouponByIdAndCompanyId(int couponId,Integer companyId);
	
	Optional<Coupon> findByIdAndCustomersId(int id, int customerId);
	
	List<Coupon> getCouponsByCompanyIdAndCategoryId(Integer companyId, int categoryId);

	Optional<Coupon> getFirstByTitleAndCompanyId(String title, Integer companyId);
	
	List<Coupon> getCouponsByCompanyIdAndPriceLessThan(Integer customerId, double maxPrice);
	
	List<Coupon> getCouponsByEndDateBefore(LocalDate date);
		
	Coupon getCouponByTitleAndCompanyId(String title, int companyId);
	
	List<Coupon> findByCustomersIdNotOrCustomersIdIsNullAndAmountGreaterThan(int id,int amount);
	
	@Query(value = "select coupon.* from coupon left  join  (select c.id from coupon as c left join coupon_customer as cvc on c.id = cvc.coupon_id  where cvc.customer_id = ?1) as f on coupon.id = f.id where f.id is null and coupon.amount > 0 ", nativeQuery = true)
	List<Coupon> getAvailableCoupons(int id);
	
	int deleteByEndDateBefore(LocalDate date);
	
}
