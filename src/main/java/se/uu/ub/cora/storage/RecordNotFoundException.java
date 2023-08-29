package se.uu.ub.cora.storage;

public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2241064467145940402L;

	public static RecordNotFoundException withMessage(String message) {
		return new RecordNotFoundException(message);
	}

	public static RecordNotFoundException withMessageAndException(String message,
			Exception exception) {
		return new RecordNotFoundException(message, exception);
	}

	private RecordNotFoundException(String message) {
		super(message);
	}

	private RecordNotFoundException(String message, Exception exception) {
		super(message, exception);
	}
}
