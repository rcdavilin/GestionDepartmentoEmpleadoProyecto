package exceptions;

/**
 * Clase para controlar las excepciones de la clase empleado
 */
public class EmpleadoExceptions extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmpleadoExceptions(String message) {
		super(message);
	}
}
