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

		var controller = new GestionController(new DepartamanetosRepositoriesImpl(), new EmpleadosRepositoriesImpl(),
				new ProyectosRepositoriesImpl());

		do {
			try {
				menu();
				opc = IO.readInt();
				switch (opc) {
				case 0:
					IO.print("Saliendo");
					break;
				case 1:
					do {
						EmpleadoDao.menuEmpleado();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							EmpleadoDao.addEmpleado(controller);
							break;
						case 2:
							EmpleadoDao.deleteEmpleado(controller);
							break;
						case 3:
							EmpleadoDao.showEmpleado(controller);
							break;
						case 4:
							EmpleadoDao.showEmpleadoporID(controller);
							break;
						case 5:
							EmpleadoDao.addEmpleToDepartamento(controller);
							break;
						case 6:
							EmpleadoDao.deleteEmpleFromDepartamento(controller);
							break;
						case 7:
							EmpleadoDao.addEmpleToProyecto(controller);
							break;
						case 8:
							EmpleadoDao.deleteEmpleFromProyecto(controller);
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;
				case 2:
					do {
						DepartamentoDao.menuDepartamento();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							DepartamentoDao.addDepartamento(controller);
							break;
						case 2:
							DepartamentoDao.deleteDepartamento(controller);
							break;
						case 3:
							DepartamentoDao.showDepartamentos(controller);
							break;
						case 4:
							DepartamentoDao.showDepartamentoporID(controller);
							break;
						case 5:
							DepartamentoDao.addJefe(controller);
							break;
						case 6:
							DepartamentoDao.deleteJefe(controller);
							break;
						case 7:
							DepartamentoDao.addDepartamentoToEmple(controller);
							break;
						case 8:
							DepartamentoDao.deleteDepartamentoFromEmple(controller);
							;
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;
				case 3:
					do {
						ProyectoDao.menuProyecto();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							ProyectoDao.addProyectos(controller);
							break;
						case 2:
							ProyectoDao.deleteProyecto(controller);
							break;
						case 3:
							ProyectoDao.showProyecto(controller);
							break;
						case 4:
							ProyectoDao.showProyectoporID(controller);
							break;
						case 5:
							ProyectoDao.addProyectoToEmple(controller);
							break;
						case 6:
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

	// Menu

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