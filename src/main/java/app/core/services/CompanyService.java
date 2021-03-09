package app.core.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import app.core.entities.CategoryEnum;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.couponProjectExceptions.DaoException;

@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService{
	private int companyId;
	
	public CompanyService() {
	}
	
	public boolean login(String email, String password) {
		Company comp = companyRepository.getByEmail(email);
		if (comp!=null && comp.getPassword().equals(password)) {
			this.companyId = comp.getId();
			return true;
		} 
		return false;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	
	public Coupon addCoupon(Coupon coupon) throws DaoException{	
		if(couponRepository.getFirstByTitleAndCompanyId(coupon.getTitle(),companyId).isPresent()) {
			throw new DaoException("Add coupon failed. Duplicate title for same company!!!");
		}
		if (coupon.getCategoryId() <= 0 || coupon.getCategoryId() > CategoryEnum.values().length) {
			System.out.println(coupon.getCategoryId());
			throw new DaoException("Add coupon failed. Category id out of range!!! (" + coupon.getCategoryId()+")");
		}
		if(coupon.getStartDate().isAfter(coupon.getEndDate())) {
			throw new DaoException("Add coupon failed. Coupon end date before coupon start date!!!");				
		}
		Company company =  getCompanyDetails();
		company.addCoupon(coupon);
		coupon.setId(couponRepository.getCouponByTitleAndCompanyId(coupon.getTitle(), companyId).getId());
		return coupon;
	}
	
	public Coupon deleteCoupon(int id) throws DaoException {
		//database foreign key restrictions on delete cascade will automatically delete all purchases
		//with permission of Eldar
		Optional<Coupon> chkCoupon = couponRepository.findById(id);
		if (chkCoupon.isPresent() && chkCoupon.get().getCompany().getId() == companyId) {
			couponRepository.deleteById(id);
			System.out.println("Coupon deleted successfuly!");
			return chkCoupon.get();
		} else {
			throw new DaoException("Delete coupon Failed - Coupon not found for this company");
		}
	}
	
	public Coupon updateCoupon(Coupon coupon) throws DaoException {
		Coupon couponDb;
		Optional<Coupon> co = couponRepository.findById(coupon.getId());
		if (co.isEmpty()) {
			throw new DaoException("failed to update - Coupon dont exist");
		} else {
			couponDb = co.get();
			if(couponDb.getCompany().getId() != companyId) {
				throw new DaoException("failed to update - Coupon belong to different company");
			}
		}
		Optional<Coupon> duplicate = couponRepository.getFirstByTitleAndCompanyId(coupon.getTitle(),companyId);
		if(duplicate.isPresent()&& duplicate.get().getId()> coupon.getId()) {
			throw new DaoException("Update coupon failed. Duplicate title for same company!!!");
		}
		
		if (coupon.getCategoryId() <= 0 || coupon.getCategoryId() > CategoryEnum.values().length) {
			throw new DaoException("Update coupon failed - Category id out of range!!!");
		}
		System.out.println(coupon.getStartDate()+"--"+coupon.getEndDate());
		if(coupon.getStartDate().isAfter(coupon.getEndDate())) {
			throw new DaoException("Update coupon failed - Coupon end date before coupon start date!!!");				
		}
		couponDb.setCategoryId(coupon.getCategoryId());
		couponDb.setTitle(coupon.getTitle());
		couponDb.setDescription(coupon.getDescription());
		couponDb.setStartDate(coupon.getStartDate());
		couponDb.setEndDate(coupon.getEndDate());
		couponDb.setPrice(coupon.getPrice());
		couponDb.setAmount(coupon.getAmount());
		couponDb.setImage(coupon.getImage());
		return couponDb;
	}
	
	public List<Coupon> getCompanyCoupons(){
		return couponRepository.getCouponsByCompanyId(companyId);		
	}
	public Coupon getCompanyCouponById(int couponId){
		Coupon coupon = couponRepository.getCouponByIdAndCompanyId(couponId,companyId);
		return coupon;	
	}
		
	public List<Coupon> getCompanyCouponsByCategory(int id){
		return couponRepository.getCouponsByCompanyIdAndCategoryId(companyId, id);		
	}
	
	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice){
		return couponRepository.getCouponsByCompanyIdAndPriceLessThan(companyId, maxPrice);	
	}
	
	public Company getCompanyDetails() throws DaoException{
		Optional<Company> opt =  companyRepository.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new DaoException("Company not found");
	}
	
	
}
