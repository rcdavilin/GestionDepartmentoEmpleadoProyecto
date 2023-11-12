package view;

import java.util.InputMismatchException;
import java.util.logging.Logger;

import controller.GestionController;
import dao.BD;
import dao.HibernateManager;
import io.IO;
import model.Departamento;
import model.Empleado;
import model.Proyecto;
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

		var controller = new GestionController(
				new DepartamanetosRepositoriesImpl(), 
				new EmpleadosRepositoriesImpl(),
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
						menuEmpleado();
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
						case 5:

							anadirDepartamento(controller);
							

							break;
						case 6:
							anadirProyecto(controller);
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;
				case 2:
					do {
						menuDepartamento();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							addDepartamento(controller);
							break;
						case 2:
							deleteDepartamento(controller);
							break;
						case 3:
							showDepartamento(controller);
							break;
						case 4:
							showDepartamentoPorID(controller);
							break;
						default:
							break;
						}
					} while (opc1 != 0);
					break;
				case 3:
					do {
						menuProyecto();
						opc1 = IO.readInt();
						switch (opc1) {
						case 1:
							addProyectos(controller);
							break;
						case 2:
							deleteProyecto(controller);
							break;
						case 3:
							showProyecto(controller);

							break;
						case 4:
							showProyectoPorId(controller);
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

	private static void anadirProyecto(GestionController controller) {
		IO.println("Eliga el empleado que quiera modificar");
		IO.println(controller.getEmpleado());
		int id = IO.readInt();
		
		var empleado = controller.getEmpleadoById(id);
		IO.println("Eliga el proyecto al que quiera añadir a ese empleado");
		IO.println(controller.getProyecto());
		int id_pro = IO.readInt();

		Proyecto dep = Proyecto.builder().id(id_pro).build();
		empleado.ifPresent(e -> e.setProyecto(dep));
		empleado.ifPresent(controller::updateEmpleado);
	}

	private static void anadirDepartamento(GestionController controller) {
		IO.println("Eliga el empleado que quiera modificar");
		IO.println(controller.getEmpleado());
		int id = IO.readInt();
		
		var empleado = controller.getEmpleadoById(id);
		IO.println("Eliga el deparatamento al que quiera añadir a ese empleado");
		IO.println(controller.getDepartamento());
		int id_dep = IO.readInt();

		Departamento dep = Departamento.builder().id(id_dep).build();
		empleado.ifPresent(e -> e.setDepartamento(dep));
		empleado.ifPresent(controller::updateEmpleado);
	}

	private static void showDepartamentoPorID(GestionController controller) {
		IO.println("Introduzca el ID del departamento que quiera mostrar");
		Integer id = IO.readInt();
		IO.println(controller.getDepartamentoById(id).toString());
		IO.println("");
	}

	private static void showDepartamento(GestionController controller) {
		IO.print(controller.getDepartamento().toString());
		IO.println("");
	}

	private static void deleteDepartamento(GestionController controller) {
		IO.print(controller.getDepartamento());
		IO.println("\nIntroduzca el ID del departamento que quiere eliminar");
		Integer id = IO.readInt();
		controller.deleteDepartamento(Departamento.builder().id(id).build());
		IO.println("");
	}

	private static void addDepartamento(GestionController controller) {
		IO.println("\nIntroduzca el nombre: ");
		String nombre = IO.readString();

		IO.println(controller.createDepartamento(Departamento.builder().nombre(nombre).build()) != null ? "\nAñadido"
				: "\nNo se ha añadido");
	}

	private static void showProyectoPorId(GestionController controller) {
		IO.println("Introduzca el ID del proyecto que quiera mostrar");
		Integer id = IO.readInt();
		IO.println(controller.getProyectoById(id).toString());
		IO.println("");
	}

	private static void deleteProyecto(GestionController controller) {
		IO.print(controller.getProyecto());
		IO.println("\nIntroduzca el ID del proyecto que quiere eliminar");
		Integer id = IO.readInt();
		controller.deleteProyecto(Proyecto.builder().id(id).build());
		IO.println("");
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
		IO.println(controller.getEmpleado().toString());
		IO.println("");
	}

	private static void showProyecto(GestionController controller) {
		IO.println(controller.getProyecto().toString());
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

	public static void menuEmpleado() {
		IO.println("\n1. Crear empleado");
		IO.println("2. Eliminar empleado ");
		IO.println("3. Mostrar empleado");
		IO.println("4. Mostrar empleado por ID ");
		IO.println("5. Añadir departamento");
		IO.println("6. Añadir proyecto");
		IO.println("0. Salir");
	}

	public static void menuDepartamento() {
		IO.println("\n1. Crear departamento");
		IO.println("2. Eliminar departamento");
		IO.println("3. Mostrar departamento");
		IO.println("4. Mostrar departamento por ID");
		IO.println("5. Añadir jefe");
		IO.println("0. Salir");
	}

	public static void menuProyecto() {
		IO.println("\n1. Crear proyecto");
		IO.println("2. Eliminar proyecto");
		IO.println("3. Mostrar proyecto");
		IO.println("4. Mostrar proyecto por ID");

		IO.println("0. Salir");
	}
}
