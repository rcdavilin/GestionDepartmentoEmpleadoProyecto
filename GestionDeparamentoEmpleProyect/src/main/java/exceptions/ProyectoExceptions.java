package exceptions;

/**
 * Clase para controlar las excepciones de la clase proyecto
 */
public class ProyectoExceptions extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProyectoExceptions(String message) {
		super(message);
	}
}
