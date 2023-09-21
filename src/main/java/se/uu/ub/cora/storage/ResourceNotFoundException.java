package se.uu.ub.cora.storage;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2241064467145940402L;

	public static ResourceNotFoundException withMessage(String message) {
		return new ResourceNotFoundException(message);
	}

	public static ResourceNotFoundException withMessageAndException(String message,
			Exception exception) {
		return new ResourceNotFoundException(message, exception);
	}

	private ResourceNotFoundException(String message) {
		super(message);
	}

	private ResourceNotFoundException(String message, Exception exception) {
		super(message, exception);
	}
}
