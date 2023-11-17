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
							addEmpleToDepartamento(controller);
							break;
						case 6:
							addEmpleToProyecto(controller);

							break;
						case 7:
							deleteEmpleFromDepartamento(controller);

							break;
						case 8:
							deleteEmpleFromProyecto(controller);
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
						case 6:
							deleteJefe(controller);
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
							showProyectoporID(controller);
							break;
						case 5:
							addProyectoToEmple(controller);
							break;
						case 6:
							deleteProyectoFromEmple(controller);
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

	private static void deleteProyectoFromEmple(GestionController controller) {
		showEmpleado(controller);
		IO.print("\nIntroduzca el ID del empleado que quieras retirar de un proyecto: ");
		Integer id = IO.readInt();
		Empleado emp = controller.getEmpleadoById(id);
		try {
			if (emp.getMisProyectos().size() > 0) {
				IO.println(emp.getMisProyectos());
				IO.print("\nIntroduzca el ID del proyecto de donde quieras retirar al empleado con ID: " + id + ": ");
				Integer idProy = IO.readInt();
				Proyecto proy = controller.getProyectoById(idProy);
				Proyecto proyecto = Proyecto.builder().id(proy.getId()).nombre(proy.getNombre()).build();

				for (int i = 0; i < emp.getMisProyectos().size(); i++) {
					if (emp.getMisProyectos().get(i).getId() == idProy) {
						emp.getMisProyectos().set(i, null);
						controller.updateEmpleado(emp);
					
						break;

					}
				}
				IO.println((!emp.getMisProyectos().contains(proyecto)) ? "\nEmpleado retirado del proyecto con exito"
						: "\nNo se ha podido retirar al empleado del proyecto");

			} else {
				IO.println("\nEl empleado con ID: " + id + " no tiene ningún proyecto.");
			}
		} catch (NullPointerException e) {
			IO.println("No existe el empleado con id " + id);
			IO.println("");
		}
	}

	private static void addProyectoToEmple(GestionController controller) {
		showProyecto(controller);
		IO.print("\nIntroduzca el ID del proyecto que quieras añadir  un empleado.");
		Integer idProy = IO.readInt();
		Proyecto pro = controller.getProyectoById(idProy);

		showEmpleado(controller);
		IO.print("\nIntroduzca el ID del empleado al que quieras añadir al proyecto con ID: " + idProy + ": ");
		Integer id = IO.readInt();
		Empleado emple = controller.getEmpleadoById(id);

		for (Empleado empleado : pro.getMisEmpleados()) {
			if (empleado.getId() == idProy) {
				IO.println("Ya existe el empleado con id " + id + " en el proyecto " + idProy);
				IO.println("");
				return;
			}
		}
		try {
			emple.getMisProyectos().add(pro);
			pro.getMisEmpleados().add(emple);

			controller.updateEmpleado(emple);
			controller.updateProyecto(pro);

		} catch (NullPointerException e) {
			IO.println("No se ha podido añadir el empleado con ID " + id + " al proyecto con ID " + idProy);
			IO.println("");
		}
	}

	private static void deleteJefe(GestionController controller) {
		showEmpleado(controller);
		IO.print("\nIntroduzca el ID del jefe que quieras retirar de su departamento: ");
		Integer id = IO.readInt();
		Empleado emp = controller.getEmpleadoById(id);

		if (emp.getDepartamento() != null) {
			Integer idDep = emp.getDepartamento().getId();
			Departamento dep = controller.getDepartamentoPorId(idDep);

			dep.setJefe(emp);

			dep.setJefe(null);

			controller.updateEmpleado(emp);
			controller.updateDepartamento(dep);

		} else {
			IO.println("\nEl empleado con ID: " + id + " no tiene ningún departamento.");
		}
	}

	private static void addEmpleToProyecto(GestionController controller) {

		showEmpleado(controller);
		IO.print("\nIntroduzca el ID del empleado que quieras añadir a un proyecto.");
		Integer id = IO.readInt();
		Empleado emp = controller.getEmpleadoById(id);

		showProyecto(controller);
		IO.print("\nIntroduzca el ID del proyecto al que quieras añadir el empleado con ID: " + id + ": ");
		Integer idProy = IO.readInt();
		Proyecto proy = controller.getProyectoById(idProy);

		for (Proyecto proyecto : emp.getMisProyectos()) {
			if (proyecto.getId() == idProy) {
				IO.println("Ya existe el empleado con id " + id + " en el proyecto " + idProy);
				IO.println("");
				return;
			}
		}
		try {
			emp.getMisProyectos().add(proy);
			proy.getMisEmpleados().add(emp);

			controller.updateEmpleado(emp);
			controller.updateProyecto(proy);

		} catch (NullPointerException e) {
			IO.println("No se ha podido añadir el empleado con ID " + id + " al proyecto con ID " + idProy);
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
			IO.println("No se ha podido añadir el jefe con ID " + id + " al departamento con ID " + idDep);
			IO.println("");
		}

	}

	private static void addEmpleToDepartamento(GestionController controller) {
		showEmpleado(controller);
		IO.print("\nIntroduzca el ID del empleado que quieras añadir a un departamento: ");
		Integer id = IO.readInt();
		Empleado emp = controller.getEmpleadoById(id);

		showDepartamento(controller);
		IO.print("\nIntroduzca el ID del departamento al que quieras añadir el empleado con ID: " + id + ": ");
		Integer idDep = IO.readInt();
		Departamento dep = controller.getDepartamentoPorId(idDep);
		try {
			emp.setDepartamento(dep);
			dep.getMisEmpleados().add(emp);

			controller.updateDepartamento(dep);
			controller.updateEmpleado(emp);

		} catch (NullPointerException e) {
			IO.println("No se ha podido añadir el empleado con ID " + id + " al departamento con ID " + idDep);
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
		showEmpleado(controller);
		IO.println("\nIntroduzca el ID del empleado que quiere eliminar");
		Integer id = IO.readInt();
		try {
			IO.println(controller.deleteEmpleado(Empleado.builder().id(id).build()) ? "\nEmpleado eliminado con éxito."
					: "\nNo se ha podido eliminar el empleado.");
		} catch (NullPointerException e) {
			IO.println("No existe el empleado con el ID " + id);
			IO.println("");
		}
	}

	private static void deleteEmpleFromDepartamento(GestionController controller) {
		showEmpleado(controller);
		IO.print("\nIntroduzca el ID del empleado que quieras retirar de su departamento: ");
		Integer id = IO.readInt();
		Empleado emp = controller.getEmpleadoById(id);

		if (emp.getDepartamento() != null) {
			Integer idDep = emp.getDepartamento().getId();
			Departamento dep = controller.getDepartamentoPorId(idDep);

			emp.setDepartamento(null);
			if (dep.getJefe() != null) {
				dep.setJefe(null);
			}
			dep.getMisEmpleados().remove(emp);
			controller.updateEmpleado(emp);
			controller.updateDepartamento(dep);

			IO.println((emp.getDepartamento() == null && !dep.getMisEmpleados().contains(emp))
					? "\nEmpleado retirado de su departamento con éxito."
					: "\nNo se ha podido retirar al empleado de su departamento.");
		} else {
			IO.println("\nEl empleado con ID: " + id + " no tiene ningún departamento.");
		}

	}

	private static void deleteEmpleFromProyecto(GestionController controller) {
		showEmpleado(controller);
		IO.print("\nIntroduzca el ID del empleado que quieras retirar de un proyecto: ");
		Integer id = IO.readInt();
		Empleado emp = controller.getEmpleadoById(id);
		try {
			if (emp.getMisProyectos().size() > 0) {
				IO.println(emp.getMisProyectos());
				IO.print("\nIntroduzca el ID del proyecto de donde quieras retirar al empleado con ID: " + id + ": ");
				Integer idProy = IO.readInt();
				Proyecto proy = controller.getProyectoById(idProy);
				Proyecto proyecto = Proyecto.builder().id(proy.getId()).nombre(proy.getNombre()).build();

				for (int i = 0; i < emp.getMisProyectos().size(); i++) {
					if (emp.getMisProyectos().get(i).getId() == idProy) {
						emp.getMisProyectos().set(i, null);
						controller.updateEmpleado(emp);
					
						break;

					}
				}
				IO.println((!emp.getMisProyectos().contains(proyecto)) ? "\nEmpleado retirado del proyecto con exito"
						: "\nNo se ha podido retirar al empleado del proyecto");

			} else {
				IO.println("\nEl empleado con ID: " + id + " no tiene ningún proyecto.");
			}
		} catch (NullPointerException e) {
			IO.println("No existe el empleado con id " + id);
			IO.println("");
		}
	}

	private static void deleteProyecto(GestionController controller) {
		showProyecto(controller);
		IO.print("\nIntroduzca el ID del proyecto que quiere eliminar: ");
		Integer id = IO.readInt();
		try {
			IO.println(controller.deleteProyecto(Proyecto.builder().id(id).build()) ? "\nProyecto eliminado con éxito."
					: "\nNo se ha podido eliminar el proyecto.");
		} catch (NullPointerException e) {
			IO.println("No existe el proyecto con ID " + id);
			IO.println("");
		}
	}

	private static void deleteDepartamento(GestionController controller) {
		showDepartamento(controller);
		IO.print("\nIntroduzca el ID del departamento que quiere eliminar: ");
		Integer id = IO.readInt();
		try {
			IO.println(controller.deleteDepartamento(Departamento.builder().id(id).build())
					? "\nDepartamento eliminado con éxito."
					: "\nNo se ha podido eliminar el departamento.");
		} catch (NullPointerException e) {
			IO.println("No existe el departamento con ID " + id);
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
		IO.println("0. Salir\n");
	}

	public static void menuEmpleado() {
		IO.println("\n1. Crear empleado");
		IO.println("2. Eliminar empleado ");
		IO.println("3. Mostrar empleados");
		IO.println("4. Mostrar empleado por ID");
		IO.println("5. Añadir empleado a departamento");
		IO.println("6. Añadir empleado a proyecto");
		IO.println("7. Eliminar empleado de departamento");
		IO.println("8. Eliminar empleado de proyecto");
		IO.println("0. Salir\n");
	}

	public static void menuDepartamento() {
		IO.println("\n1. Crear departamento");
		IO.println("2. Eliminar departamento");
		IO.println("3. Mostrar departamentos");
		IO.println("4. Mostrar departamento por ID");
		IO.println("5. Añadir jefe a departamento");
		IO.println("6. Eliminar jefe de departamento");
		IO.println("0. Salir\n");
	}

	public static void menuProyecto() {
		IO.println("\n1. Crear proyectos");
		IO.println("2. Eliminar proyecto");
		IO.println("3. Mostrar proyectos");
		IO.println("4. Mostrar proyecto por ID");
		IO.println("5. Añadir proyecto a empleado");
		IO.println("6. Eliminar empleado de proyecto");
		IO.println("0. Salir\n");
	}

}