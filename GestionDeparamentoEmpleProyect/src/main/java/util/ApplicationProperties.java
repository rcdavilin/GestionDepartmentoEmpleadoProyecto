package util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para crear las propiedades de la aplicacion 
 */
public class ApplicationProperties {

	private final Properties properties;

	/**
	 * Constructor para declarar una nueva propiedad para leer el fichero application.properties
	 */
	public ApplicationProperties() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

		} catch (IOException ex) {
			System.err.println("IOException Ocurrido al leer el fichero de propiedades: " + ex.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.ALL,
					"IOException Ocurrido al leer el fichero de propiedades: " + ex.getMessage());
		}
	}

	/**
	 * Metodo para leer las propiedades del fichero
	 * @param keyName
	 * @return
	 */
	public String readProperty(String keyName) {
		// Logger.getLogger(getClass().getName()).log(Level.INFO, "Leyendo propiedad " +
		// keyName);
		return properties.getProperty(keyName, "No existe esa clave en el fichero de propiedades");
	}

}
