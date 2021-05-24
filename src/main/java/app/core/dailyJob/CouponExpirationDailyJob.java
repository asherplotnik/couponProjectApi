package app.core.dailyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import app.core.couponProjectExceptions.DaoException;
import app.core.services.DailyJobService;

@Component
@EnableScheduling
public class CouponExpirationDailyJob {
	@Autowired
	DailyJobService couponService;
	
	@Scheduled(fixedDelayString = "${daily.job.expried.coupon.period}", initialDelay = 3000)
	public void clearExpiredCoupons() throws DaoException {
		couponService.clearExpiredCoupons();
		System.out.println("Expired coupons deleted");
	}

}
