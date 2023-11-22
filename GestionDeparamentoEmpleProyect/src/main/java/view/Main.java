package view;

import java.util.InputMismatchException;
import java.util.logging.Logger;

import controller.GestionController;
import dao.DepartamentoDao;
import dao.EmpleadoDao;
import dao.HibernateManager;
import dao.ProyectoDao;
import io.IO;
import repositories.departamento.DepartamanetosRepositoriesImpl;
import repositories.empleado.EmpleadosRepositoriesImpl;
import repositories.proyecto.ProyectosRepositoriesImpl;
import util.ApplicationProperties;

public class Main {
	static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		System.out.println("Gestion Departamentos, Empleados y Proyectos");
		int opc = 0;
		int opc1 = 0;
		initDataBase();

		/**
		 * Creamos un constructor de gestionController y le pasamos como parametros
		 * nuestro repositorios implementados
		 */
		var controller = new GestionController(new DepartamanetosRepositoriesImpl(), new EmpleadosRepositoriesImpl(),
				new ProyectosRepositoriesImpl());
		/**
		 * Do while para elegir opcion mientras opc sea distinto de 0
		 */
		do {
			try {
				/**
				 * Mostramos el menu principal y eligimos opcion
				 */
				menu();
				opc = IO.readInt();
				/**
				 * Switch para ir por las distintas opciones del menu
				 */
				switch (opc) {
				/**
				 * Salimos
				 */
				case 0:
					IO.print("Saliendo");
					break;
				/**
				 * Para trabajar con empleados
				 */
				case 1:
					do {
						/**
						 * Mostramos el menu de empleados y elegimos opcion
						 */
						EmpleadoDao.menuEmpleado();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							/**
							 * Vamos al metodo añadir empleado
							 */
							EmpleadoDao.addEmpleado(controller);
							break;
						case 2:
							/**
							 * Vamos al metodo eliminar empleado
							 */
							EmpleadoDao.deleteEmpleado(controller);
							break;
						case 3:
							/**
							 * Vamos al metodo mostrar empleado
							 */
							EmpleadoDao.showEmpleado(controller);
							break;
						case 4:
							/**
							 * Vamos al metodo mostrar empleado por ID
							 */
							EmpleadoDao.showEmpleadoporID(controller);
							break;
						case 5:
							/**
							 * Vamos al metodo añadir empleado a departamento
							 */
							EmpleadoDao.addEmpleToDepartamento(controller);
							break;
						case 6:
							/**
							 * Vamos al metodo eliminar empleado de departamento
							 */
							EmpleadoDao.deleteEmpleFromDepartamento(controller);
							break;
						case 7:
							/**
							 * Vamos al metodo añadir empleado a proyecto
							 */
							EmpleadoDao.addEmpleToProyecto(controller);
							break;
						case 8:
							/**
							 * Vamos al metodo eliminar empleado de proyecto
							 */
							EmpleadoDao.deleteEmpleFromProyecto(controller);
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;
				/**
				 * Para trabajar con departamentos
				 */
				case 2:
					do {
						/**
						 * Mostramos el menu de departamentos y elegimos opcion
						 */
						DepartamentoDao.menuDepartamento();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							/**
							 * Vamos al metodo añadir departamento
							 */
							DepartamentoDao.addDepartamento(controller);
							break;
						case 2:
							/**
							 * Vamos al metodo eliminar departamento
							 */
							DepartamentoDao.deleteDepartamento(controller);
							break;
						case 3:
							/**
							 * Vamos al metodo mostrar departamento
							 */
							DepartamentoDao.showDepartamentos(controller);
							break;
						case 4:
							/**
							 * Vamos al metodo mostrar departamento por ID
							 */
							DepartamentoDao.showDepartamentoporID(controller);
							break;
						case 5:
							/**
							 * Vamos al metodo añadir jefe a departamento
							 */
							DepartamentoDao.addJefe(controller);
							break;
						case 6:
							/**
							 * Vamos al metodo eliminar jefe de departamento
							 */
							DepartamentoDao.deleteJefe(controller);
							break;
						case 7:
							/**
							 * Vamos al metodo añadir departamento a empleado
							 */
							DepartamentoDao.addDepartamentoToEmple(controller);
							break;
						case 8:
							/**
							 * Vamos al metodo eliminar departamento de empleado
 							 */
							DepartamentoDao.deleteDepartamentoFromEmple(controller);
						
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;
				/**
				 * Para trabajar con proyectos
				 */
				case 3:
					do {
						/**
						 * Mostramos el menu de proyectos y elegimos opcion
						 */
						ProyectoDao.menuProyecto();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							/**
							 * Vamos al metodo añadir proyecto
							 */
							ProyectoDao.addProyectos(controller);
							break;
						case 2:
							/**
							 * Vamos al metodo eliminar proyecto
							 */
							ProyectoDao.deleteProyecto(controller);
							break;
						case 3:
							/**
							 * Vamos al metodo mostrar proyecto
							 */
							ProyectoDao.showProyecto(controller);
							break;
						case 4:
							/**
							 * Vamos al metodo mostrar proyecto por ID
							 */
							ProyectoDao.showProyectoporID(controller);
							break;
						case 5:
							/**
							 * Vamos al metodo añadir proyecto a empleado
							 */
							ProyectoDao.addProyectoToEmple(controller);
							break;
						case 6:
							/**
							 * Vamos al metodos eliminar proyecto de empleado
							 */
							ProyectoDao.deleteProyectoFromEmple(controller);
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;

				default:
					break;
				}
			} catch (InputMismatchException e) {
				IO.println("\nSólo se permiten números");
			}

		} while (opc != 0);

	}

	// Menu principla

	public static void menu() {
		IO.println("\n1. Empleado");
		IO.println("2. Departamento");
		IO.println("3. Proyecto");
		IO.println("0. Salir\n");
	}

	// Iniciar base de datos

	private static void initDataBase() {
		// Leemos el fichero de configuración

		ApplicationProperties properties = new ApplicationProperties();
		logger.info("\nLeyendo fichero de configuración..." + properties.readProperty("app.title"));
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.close();
	}
}