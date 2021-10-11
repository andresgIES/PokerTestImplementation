package exceptions;

public class ExceptionValidationPoker extends Exception {

	private static final long serialVersionUID = 5875555006895843062L;

	public static final String HAND_INVALID = "mano invalida";
	
	public ExceptionValidationPoker(String s) {
		super(s);
	}

}
