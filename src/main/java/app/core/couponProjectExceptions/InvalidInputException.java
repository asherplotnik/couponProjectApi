package app.core.couponProjectExceptions;
/**
 * this exception manage connection to storage errors.
 * @author ASHER
 * 
 * 
 */
public class InvalidInputException extends CouponProjectException {
	private static final long serialVersionUID = 1L;

	public InvalidInputException() {
		super();
	}

	public InvalidInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}
}
