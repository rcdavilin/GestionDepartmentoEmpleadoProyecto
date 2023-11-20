package dao;

import controller.GestionController;
import io.IO;
import model.Empleado;
import model.Proyecto;

public class ProyectoDao {

	public static void addProyectos(GestionController controller) {
		BD.getProyectosInit().forEach(controller::createProyecto);
		IO.println("\n5 proyectos creados con éxito.");
	}

	public static void deleteProyecto(GestionController controller) {
		Proyecto p = null;
		try {
			if (existenProyectos(controller)) {
				showProyecto(controller);
				IO.print("\nIntroduzca el ID del proyecto que quiere eliminar: ");
				Integer id = IO.readInt();
				p = controller.getProyectoById(id);

				for (Empleado empleado : p.getMisEmpleados()) {
					empleado.getMisProyectos().remove(p);
					controller.updateProyecto(p);
				}
				IO.println(controller.deleteProyecto(p) ? "\nProyecto eliminado con éxito."
						: "\nNo se ha podido eliminar el proyecto.");
			} else {
				IO.println("\n¡No existe ningún proyecto!");
			}
		} catch (NullPointerException e) {
			if (p == null) {
				IO.println("\n¡El proyecto indicado no existe!");
			}
		}
	}

	public static void showProyecto(GestionController controller) {
		if (existenProyectos(controller)) {
			IO.println("\n");
			IO.println(controller.getProyecto().toString());
		} else {
			IO.println("\n¡No existe ningún proyecto!");
		}

	}

	public static void showProyectoporID(GestionController controller) {
		try {
			if (existenProyectos(controller)) {
				IO.print("\nIntroduzca el ID del proyecto que quiera mostrar: ");
				Integer id = IO.readInt();
				IO.println("\n" + controller.getProyectoById(id).toString() + "\n");
			} else {
				IO.println("\n¡No existe ningún proyecto!");
			}
		} catch (NullPointerException e) {
			IO.println("\n¡El proyecto indicado no existe!");
		}
	}

	public static void addProyectoToEmple(GestionController controller) {
		Proyecto proy = null;
		Empleado emp = null;
		try {
			if (existenProyectos(controller)) {
				showProyecto(controller);
				IO.print("\nIntroduzca el ID del proyecto al que quieras añadir a un empleado: ");
				Integer idProy = IO.readInt();
				proy = controller.getProyectoById(idProy);

				if (EmpleadoDao.existenEmpleados(controller)) {
					EmpleadoDao.showEmpleado(controller);
					IO.print("\nIntroduzca el ID del empleado que quieras añadir al proyecto con ID: " + idProy + ": ");
					Integer id = IO.readInt();
					emp = controller.getEmpleadoById(id);

					for (Empleado empleado : proy.getMisEmpleados()) {
						if (empleado.getId().equals(id)) {
							IO.println("\nEl proyecto con Id: " + idProy + " ya está asignado al empleado con Id: " + id
									+ ".");
							return;
						}
					}
					emp.getMisProyectos().add(proy);
					proy.getMisEmpleados().add(emp);
					controller.updateEmpleado(emp);
					controller.updateProyecto(proy);

					IO.println("\nProyecto añadido a empleado con éxito.");
				} else {
					IO.println("\n¡No existe ningún empleado al que asignarle un proyecto!");
				}
			} else {
				IO.println("\n¡No existe ningún proyecto!");
			}
		} catch (NullPointerException e) {
			if (proy == null) {
				IO.println("\n¡El proyecto indicado no existe!");
			}
			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
		}
	}

	public static void deleteProyectoFromEmple(GestionController controller) {
		Proyecto proy = null;
		Empleado emp = null;

		try {
			if (existenProyectos(controller)) {
				showProyecto(controller);
				IO.print("\nIntroduce el ID del proyecto que quieras retirar de un empleado: ");
				Integer id = IO.readInt();
				proy = controller.getProyectoById(id);

				if (proy.getMisEmpleados().size() != 0) {
					proy.getMisEmpleados();
					IO.print("\nIntroduce el id del empleado de donde quieras retirar el proyecto: ");
					Integer idEmp = IO.readInt();
					emp = controller.getEmpleadoById(idEmp);

					for (Proyecto proyecto : emp.getMisProyectos()) {
						if (proyecto.getId().equals(id)) {
							emp.getMisProyectos().remove(proyecto);
							proyecto.getMisEmpleados().remove(emp);
							controller.updateEmpleado(emp);
							controller.updateProyecto(proyecto);
							IO.println("\nProyecto retirado del empleado con éxito.");
							return;
						}
					}
					IO.println("\nEl proyecto con Id: " + id + " no está asociado al empleado con Id: " + idEmp + ".");
				} else {
					IO.println("\nEl proyecto con Id: " + id + " no está asociado a ningún empleado.");
				}
			} else {
				IO.println("\nNo existe ningún proyecto.");
			}

		} catch (NullPointerException e) {
			if (proy == null) {
				IO.println("\n¡El proyecto indicado no existe!");
				return;
			}
			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
		}
	}

	public static boolean existenProyectos(GestionController controller) {
		return controller.getProyecto().size() != 0;
	}

	// Menu
	public static void menuProyecto() {
		IO.println("\n1. Crear proyectos");
		IO.println("2. Eliminar proyecto");
		IO.println("3. Mostrar proyectos");
		IO.println("4. Mostrar proyecto por ID");
		IO.println("5. Añadir proyecto a empleado");
		IO.println("6. Eliminar proyecto de empleado");
		IO.println("0. Salir\n");
	}
}