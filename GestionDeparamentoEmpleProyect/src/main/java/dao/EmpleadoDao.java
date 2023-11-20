package dao;

import controller.GestionController;
import io.IO;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class EmpleadoDao {

	public static void addEmpleado(GestionController controller) {
		IO.print("\nIntroduzca el nombre: ");
		String nombre = IO.readString();
		IO.print("Introduzca su salario: ");
		Double salario = IO.readDouble();
		IO.println(controller
				.createEmpleado(Empleado.builder().nombre(nombre).salario(salario).departamento(null).build()) != null
						? "\nEmpleado añadido con éxito."
						: "\nNo se ha podido añadir el departamento.");
	}

	public static void deleteEmpleado(GestionController controller) {
		try {
			if (existenEmpleados(controller)) {
				showEmpleado(controller);
				IO.print("\nIntroduzca el ID del empleado que quiere eliminar: ");
				Integer id = IO.readInt();
				Empleado emp = controller.getEmpleadoById(id);

				if (emp.getDepartamento() != null) {
					Departamento dep = emp.getDepartamento();
					if (dep.getJefe() != null && dep.getJefe().equals(emp)) {
						dep.setJefe(null);
					}
					dep.getMisEmpleados().remove(emp);
					controller.updateDepartamento(dep);
				}
				if (emp.getMisProyectos().size() != 0) {
					for (Proyecto proyecto : emp.getMisProyectos()) {
						if (proyecto.getMisEmpleados().contains(emp)) {
							proyecto.getMisEmpleados().remove(emp);
							controller.updateProyecto(proyecto);
						}
					}
				}
				IO.println(controller.deleteEmpleado(emp) ? "\nEmpleado eliminado con éxito."
						: "\nNo se ha podido eliminar el empleado.");
			} else {
				IO.println("\n¡No existe ningún empleado!");
			}
		} catch (NullPointerException e) {
			IO.println("\n¡El empleado indicado no existe!");
		}
	}

	public static void showEmpleado(GestionController controller) {
		if (existenEmpleados(controller)) {
			IO.println("\n");
			IO.println(controller.getEmpleado().toString());
		} else {
			IO.println("\n¡No existe ningún empleado!");
		}

	}

	public static void showEmpleadoporID(GestionController controller) {
		try {
			if (existenEmpleados(controller)) {
				IO.print("\nIntroduzca el ID del empleado que quiera mostrar: ");
				Integer id = IO.readInt();
				IO.println("\n" + controller.getEmpleadoById(id).toString());
			} else {
				IO.println("\n¡No existe ningún empleado!");
			}
		} catch (NullPointerException e) {
			IO.println("\n¡El empleado indicado no existe!");
		}

	}

	public static void addEmpleToDepartamento(GestionController controller) {
		Empleado emp = null;
		Departamento dep = null;
		try {
			if (existenEmpleados(controller)) {
				showEmpleado(controller);
				IO.print("\nIntroduzca el ID del empleado que quieras añadir a un departamento: ");
				Integer id = IO.readInt();
				emp = controller.getEmpleadoById(id);

				if (DepartamentoDao.existenDepartamentos(controller)) {
					DepartamentoDao.showDepartamentos(controller);
					IO.print("\nIntroduzca el ID del departamento al que quieras añadir el empleado con ID: " + id
							+ ": ");
					Integer idDep = IO.readInt();
					dep = controller.getDepartamentoById(idDep);

					if (emp.getDepartamento() != null) {
						Departamento d = emp.getDepartamento();
						if (dep.getId().equals(d.getId())) {
							IO.println("\nEl empleado con Id: " + id + " ya se encuentra en el departamento con Id: "
									+ idDep);
							return;
						}
						d.getMisEmpleados().remove(emp);
						controller.updateDepartamento(d);
					}
					emp.setDepartamento(dep);
					dep.getMisEmpleados().add(emp);
					controller.updateDepartamento(dep);
					controller.updateEmpleado(emp);

					IO.println(dep.getMisEmpleados().contains(emp) && (emp.getDepartamento() == dep)
							? "\nEmpleado añadido a departamento con éxito."
							: "\nNo se ha podido añadir el empleado al departamento.");
				} else {
					IO.println("\n¡No existe ningún departamento!");
				}
			} else {
				IO.println("\n¡No existe ningún empleado!");
			}
		} catch (NullPointerException e) {
			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
			if (dep == null) {
				IO.println("\n¡El departamento indicado no existe!");
			}
		}

	}

	public static void deleteEmpleFromDepartamento(GestionController controller) {
		Empleado emp = null;
		Departamento dep = null;
		try {
			if (existenEmpleados(controller)) {
				showEmpleado(controller);
				IO.print("\nIntroduzca el ID del empleado que quieras retirar de su departamento: ");
				Integer id = IO.readInt();
				emp = controller.getEmpleadoById(id);

				if (emp.getDepartamento() != null) {
					dep = emp.getDepartamento();

					emp.setDepartamento(null);
					dep.getMisEmpleados().remove(emp);
					controller.updateEmpleado(emp);
					controller.updateDepartamento(dep);

					IO.println("\nEmpleado retirado de su departamento con éxito.");
				} else {
					IO.println("\nEl empleado con ID: " + id + " no tiene ningún departamento.");
				}
			} else {
				IO.println("\n¡No existe ningún empleado!");
			}
		} catch (NullPointerException e) {
			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
		}
	}

	public static void addEmpleToProyecto(GestionController controller) {
		Empleado emp = null;
		Proyecto p = null;
		try {
			if (existenEmpleados(controller)) {
				showEmpleado(controller);
				IO.print("\nIntroduzca el ID del empleado que quieras añadir a un proyecto: ");
				Integer id = IO.readInt();
				emp = controller.getEmpleadoById(id);

				if (ProyectoDao.existenProyectos(controller)) {
					ProyectoDao.showProyecto(controller);
					IO.print("\nIntroduzca el ID del proyecto al que quieras añadir el empleado con ID: " + id + ": ");
					Integer idProy = IO.readInt();
					p = controller.getProyectoById(idProy);

					for (Proyecto proyecto : emp.getMisProyectos()) {
						if (proyecto.getId().equals(idProy)) {
							IO.println("\nEl empleado con Id: " + id + " ya existe en el proyecto con Id: " + idProy);
							return;
						}
					}
					emp.getMisProyectos().add(p);
					controller.updateEmpleado(emp);

					IO.println("\nEmpleado añadido a proyecto con éxito.");
				} else {
					IO.println("\n¡No existe ningún proyecto!");
				}
			} else {
				IO.println("\n¡No existe ningún empleado!");
			}
		} catch (NullPointerException e) {
			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
			if (p == null) {
				IO.println("\n¡El proyecto indicado no existe!");
			}
		}
	}

	public static void deleteEmpleFromProyecto(GestionController controller) {
		Empleado emp = null;
		Proyecto p = null;
		try {
			if (existenEmpleados(controller)) {
				showEmpleado(controller);
				IO.print("\nIntroduzca el ID del empleado que quieras retirar de un proyecto: ");
				Integer id = IO.readInt();
				emp = controller.getEmpleadoById(id);

				if (emp.getMisProyectos().size() > 0) {
					IO.println(emp.getMisProyectos());
					IO.print("\nIntroduzca el ID del proyecto de donde quieras retirar al empleado con ID: " + id
							+ ": ");
					Integer idProy = IO.readInt();

					for (Proyecto proyecto : emp.getMisProyectos()) {
						if (proyecto.getId().equals(idProy)) {
							proyecto.getMisEmpleados().remove(emp);
							emp.getMisProyectos().remove(proyecto);
							controller.updateProyecto(proyecto);
							controller.updateEmpleado(emp);
							IO.println("\nEmpleado retirado del proyecto con éxito.");
							return;
						}
					}
					IO.println("\n¡El empleado no pertenece al proyecto indicado!");
				} else {
					IO.println("\nEl empleado con ID: " + id + " no tiene ningún proyecto.");
				}
			} else {
				IO.println("\n¡No existe ningún empleado!");
			}
		} catch (NullPointerException e) {
			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
			if (p == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
		}
	}

	public static boolean existenEmpleados(GestionController controller) {
		return controller.getEmpleado().size() != 0;
	}

	// Empleado
	public static void menuEmpleado() {
		IO.println("\n1. Crear empleado");
		IO.println("2. Eliminar empleado ");
		IO.println("3. Mostrar empleados");
		IO.println("4. Mostrar empleado por ID");
		IO.println("5. Añadir empleado a departamento");
		IO.println("6. Eliminar empleado de departamento");
		IO.println("7. Añadir empleado a proyecto");
		IO.println("8. Eliminar empleado de proyecto");
		IO.println("0. Salir\n");
	}

}