package app.core.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import app.core.entities.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	List<Coupon> getCouponsByCustomersId(Integer customerId);
	
	List<Coupon> getCouponsByCustomersIdAndCategoryId(Integer customerId, int categoryId);

	List<Coupon> getCouponsByCustomersIdAndPriceLessThan(Integer customerId, double maxPrice);
	
	List<Coupon> getCouponsByCompanyId(Integer companyId);

	Coupon getCouponByIdAndCompanyId(int couponId,Integer companyId);
	
	List<Coupon> getCouponsByCompanyIdAndCategoryId(Integer companyId, int categoryId);

	Optional<Coupon> getFirstByTitleAndCompanyId(String title, Integer companyId);
	
	List<Coupon> getCouponsByCompanyIdAndPriceLessThan(Integer customerId, double maxPrice);
	
	List<Coupon> getCouponsByEndDateBefore(LocalDate date);
		
	Coupon getCouponByTitleAndCompanyId(String title, int companyId);
	
	List<Coupon> findByCustomersIdIsNullOrCustomersIdNot(int id);
	
}
