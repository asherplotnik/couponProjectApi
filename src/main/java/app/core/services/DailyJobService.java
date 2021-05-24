package app.core.services;

import java.time.LocalDate;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.core.couponProjectExceptions.DaoException;
import app.core.repositories.CouponRepository;

@Service
@Transactional
public class DailyJobService {
	@Autowired
	CouponRepository couponRepository;

	public DailyJobService() {
	}

	public void clearExpiredCoupons() throws DaoException {
		try {
			couponRepository.deleteByEndDateBefore(LocalDate.now());
		} catch (Exception e) {
			throw new DaoException("Clear coupons failed!!!");
		}
	}
}
