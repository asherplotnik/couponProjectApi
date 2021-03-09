package app.core.dailyJob;

import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import app.core.entities.Coupon;
import app.core.repositories.CouponRepository;

@Component
public class CouponExpirationDailyJob {
	@Value("${daily.job.expried.coupon.period:1440}")
	int dailyJobExpriedCouponPeriod;
	@Autowired
	CouponRepository couponRepository;
	private Timer timer = new Timer();
	/**
	 * clear expired coupons and wait 24 hours.
	 */
	
	public void clearExpiredCoupons() {
		List<Coupon> coupons = couponRepository.getCouponsByEndDateBefore(LocalDate.now());
		for (Coupon coupon : coupons) {
			couponRepository.deleteById(coupon.getId());
		}
	}
	
	@PostConstruct
	private void init() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				clearExpiredCoupons();
				System.out.println();
				System.out.println("cleared daily expired coupons");
				System.out.println();
			}
		};
		timer.schedule(task,3000, TimeUnit.MINUTES.toMillis(dailyJobExpriedCouponPeriod));
		System.out.println("expired coupon removal thread started");
	}
	
	@PreDestroy
	private void destroy() {
		timer.cancel();
		System.out.println("expired coupon removal thread stopped");
	}
}
