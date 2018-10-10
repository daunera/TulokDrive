package hu.tobias.entities.exceptions;

public class NotFoundEntityException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NotFoundEntityException() {
		super();
	}
	
	public NotFoundEntityException(String message, Throwable cause) {
		super(message, cause);
	}

}
