package se.uu.ub.cora.storage;

public class ResourceConflictException extends RuntimeException {

	private static final long serialVersionUID = 2241064467145940402L;

	public static ResourceConflictException withMessage(String message) {
		return new ResourceConflictException(message);
	}

	public static ResourceConflictException withMessageAndException(String message,
			Exception exception) {
		return new ResourceConflictException(message, exception);
	}

	private ResourceConflictException(String message) {
		super(message);
	}

	private ResourceConflictException(String message, Exception exception) {
		super(message, exception);
	}
}
