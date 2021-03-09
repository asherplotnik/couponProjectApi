package app.core.couponProjectExceptions;
/**
 * this exception manage connection to storage errors.
 * @author ASHER
 *
 */
public class DaoException extends CouponProjectException {
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}
}
