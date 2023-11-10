package view;

import java.util.InputMismatchException;
import java.util.logging.Logger;

import controller.GestionController;
import dao.BD;
import dao.HibernateManager;
import io.IO;
import model.Empleado;
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
						menu1();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							addEmpleado(controller);
							break;
						case 2:
							deleteEmpleado(controller);
							break;
						case 3:
							showEmpleado(controller);
							break;
						case 4:
							showEmpleadoporID(controller);
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;
				case 3:
					do {
						menu1();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							addProyectos(controller);
							break;
						case 2:
							showProyecto(controller);
							break;
						case 3:
							
							break;
						case 4:
							
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

	private static void addProyectos(GestionController controller) {
		BD.getProyectosInit().forEach(controller::createProyecto);
	}

	private static void showEmpleadoporID(GestionController controller) {
		IO.println("Introduzca el ID del empleado que quiera mostrar");
		Integer id = IO.readInt();
		IO.println(controller.getEmpleadoById(id).toString());
		IO.println("");
	}

	private static void showEmpleado(GestionController controller) {
		IO.print(controller.getEmpleado().toString());
		IO.println("");
	}
	
	private static void showProyecto(GestionController controller) {
		IO.print(controller.getProyecto().toString());
		IO.println("");
	}

	private static void deleteEmpleado(GestionController controller) {
		IO.print(controller.getEmpleado());
		IO.println("\nIntroduzca el ID del empleado que quiere eliminar");
		Integer id = IO.readInt();
		controller.deleteEmpleado(Empleado.builder().id(id).build());
		IO.println("");
	}

	private static void addEmpleado(GestionController controller) {
		IO.println("\nIntroduzca el nombre: ");
		String nombre = IO.readString();
		IO.println("Introduzca su salario");
		Double salario = IO.readDouble();
		IO.println(controller.createEmpleado(Empleado.builder().nombre(nombre).salario(salario).build()) != null
				? "\nAñadido"
				: "\nNo se ha añadido");
	}

	private static void initDataBase() {
		// Leemos el fichero de configuración

		ApplicationProperties properties = new ApplicationProperties();
		logger.info("Leyendo fichero de configuración..." + properties.readProperty("app.title"));
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.close();
	}

	public static void menu() {
		IO.println("\n1. Empleado");
		IO.println("2. Departamento");
		IO.println("3. Proyecto");
		IO.println("0. Salir");
	}

	public static void menu1() {
		IO.println("\n1. Crear");
		IO.println("2. Eliminar");
		IO.println("3. Mostrar");
		IO.println("4. Mostrar por ID");
		IO.println("0. Salir");
	}
}
