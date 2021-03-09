package app.core.couponProjectExceptions;


/**
 * manage exceptions for this project this is the highest exception in the project exception hierarchy 
 * @author ASHER
 *
 */

public class CouponProjectException extends Exception {
	private static final long serialVersionUID = 1L;

	public CouponProjectException() {
		super();
	}

	public CouponProjectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CouponProjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponProjectException(String message) {
		super(message);
	}

	public CouponProjectException(Throwable cause) {
		super(cause);
	}
	
}
