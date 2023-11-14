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
							addDepartamentoToEmple(controller);
							break;
						case 6:
							anadirEmpleadoProyecto(controller);
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
							showDepartamentoporID(controller);
							break;
						case 5:
							addJefe(controller);
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
							deleteProyecto(controller);
							break;
						case 3:
							showProyecto(controller);
							break;
						case 4:
							showProyectoporID(controller);
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

	private static void anadirEmpleadoProyecto(GestionController controller) {
		IO.println(controller.getEmpleado().toString());
		IO.print("\nIntroduzca el ID del empleado al que le quieras añadir un proyecto: ");
		int id = IO.readInt();

		IO.println(controller.getProyecto().toString());
		IO.print("\nIntroduzca el ID del proyecto al que quieras añadir el empleado con ID = " + id + ": ");
		int idPro = IO.readInt();
		try {
			Proyecto pro = controller.getProyectoById(idPro);

			Empleado emp = controller.getEmpleadoById(id);

			pro.setEmpleado(emp);
			emp.setProyecto(pro);

			controller.updateProyecto(pro);
			controller.updateEmpleado(emp);
		} catch (NullPointerException e) {
			IO.println("No se ha podido añadir el departamento con ID " + idPro + " al empleado con ID " + id);
			IO.println("");
		}
	}

	private static void addJefe(GestionController controller) {
		IO.println(controller.getDepartamento());
		IO.print("\nIntroduzca el ID del departamento al que le quieras añadir un jefe: ");
		int idDep = IO.readInt();


		IO.println(controller.getEmpleado().toString());
		IO.print("\nIntroduzca el ID del empleado al que quieras añadir como jefe: ");
		int id = IO.readInt();
		try {
			
			Departamento dep = controller.getDepartamentoPorId(idDep);

			Empleado emp = controller.getEmpleadoById(id);

			dep.setJefe(emp);
			emp.setDepartamento(dep);

			controller.updateDepartamento(dep);
			controller.updateEmpleado(emp);
			
		} catch (NullPointerException e) {
			IO.println("No se ha podido añadir el jefe con ID "+id+" al departamento con ID "+ idDep);
			IO.println("");
		}

	}

	private static void addDepartamentoToEmple(GestionController controller) {
		IO.println(controller.getEmpleado().toString());
		IO.print("\nIntroduzca el ID del empleado al que le quieras añadir un departamento: ");
		int id = IO.readInt();

		IO.println(controller.getDepartamento().toString());
		IO.print("\nIntroduzca el ID del departamento al que quieras añadir el empleado con ID = " + id + ": ");
		int idDep = IO.readInt();
		try {

			Empleado emp = controller.getEmpleadoById(id);

			Departamento dep = Departamento.builder().id(idDep).build();
			
			emp.setDepartamento(dep);
			
			
			controller.updateEmpleado(emp);
		} catch (NullPointerException e) {
			IO.println("No se ha podido añadir el departamento con ID " + idDep + " al empleado con ID " + id);
			IO.println("");
		}

	}

	private static void addProyectos(GestionController controller) {
		BD.getProyectosInit().forEach(controller::createProyecto);
	}

	private static void showEmpleadoporID(GestionController controller) {
		IO.println("Introduzca el ID del empleado que quiera mostrar");
		Integer id = IO.readInt();
		try {

			IO.println(controller.getEmpleadoById(id).toString());
			IO.println("");
		} catch (NullPointerException e) {
			IO.println("No se ha podido mostrar el empleado con ID " + id);
			IO.println("");
		}
	}

	private static void showDepartamentoporID(GestionController controller) {
		IO.println("Introduzca el ID del departamento que quiera mostrar");
		Integer id = IO.readInt();
		try {

			IO.println(controller.getDepartamentoPorId(id).toString());
			IO.println("");
		} catch (NullPointerException e) {
			IO.println("No se ha podido mostrar el departamento con ID " + id);
			IO.println("");
		}
	}

	private static void showProyectoporID(GestionController controller) {
		IO.println("Introduzca el ID del proyecto que quiera mostrar");
		Integer id = IO.readInt();
		try {

			IO.println(controller.getProyectoById(id).toString());
			IO.println("");
		} catch (NullPointerException e) {
			IO.println("No se ha podido mostrar el proyecto con ID " + id);
			IO.println("");
		}
	}

	private static void showEmpleado(GestionController controller) {
		IO.print(controller.getEmpleado().toString());
		IO.println("");
	}

	private static void showDepartamento(GestionController controller) {
		IO.print(controller.getDepartamento().toString());
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
		try {
			Empleado emp = controller.getEmpleadoById(id);		
			controller.deleteEmpleado(emp);
		} catch (NullPointerException e) {
			IO.println("No se ha podido eliminar el empleado con ID " + id);
			IO.println("");
		}
	}

	private static void deleteProyecto(GestionController controller) {
		IO.print(controller.getProyecto());
		IO.println("\nIntroduzca el ID del proyecto que quiere eliminar");
		Integer id = IO.readInt();
		try {

			controller.deleteProyecto(Proyecto.builder().id(id).build());
			IO.println("");
		} catch (NullPointerException e) {
			IO.println("No se ha podido eliminar el departamento con ID " + id);
			IO.println("");
		}
	}

	private static void deleteDepartamento(GestionController controller) {
		IO.print(controller.getDepartamento());
		IO.println("\nIntroduzca el ID del departamento que quiere eliminar");
		Integer id = IO.readInt();
		try {

			controller.deleteDepartamento(Departamento.builder().id(id).build());
			IO.println("");
		} catch (NullPointerException e) {
			IO.println("No se ha podido eliminar el departamento con ID " + id);
			IO.println("");
		}
	}

	private static void addDepartamento(GestionController controller) {
		IO.println("\nIntroduzca el nombre: ");
		String nombre = IO.readString();
		IO.println(controller.createDepartamento(Departamento.builder().nombre(nombre).jefe(null).build()) != null
				? "\nAñadido"
				: "\nNo se ha añadido");
	}

	private static void addEmpleado(GestionController controller) {
		IO.println("\nIntroduzca el nombre: ");
		String nombre = IO.readString();
		IO.println("Introduzca su salario");
		Double salario = IO.readDouble();
		IO.println(controller
				.createEmpleado(Empleado.builder().nombre(nombre).salario(salario).departamento(null).build()) != null
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

	public static void menuEmpleado() {
		IO.println("\n1. Crear");
		IO.println("2. Eliminar");
		IO.println("3. Mostrar");
		IO.println("4. Mostrar por ID");
		IO.println("5. Añadir departamento");
		IO.println("6. Añadir proyecto");
		IO.println("0. Salir");
	}

	public static void menuDepartamento() {
		IO.println("\n1. Crear");
		IO.println("2. Eliminar");
		IO.println("3. Mostrar");
		IO.println("4. Mostrar por ID");
		IO.println("5. Añadir jefe");
		IO.println("0. Salir");
	}
}