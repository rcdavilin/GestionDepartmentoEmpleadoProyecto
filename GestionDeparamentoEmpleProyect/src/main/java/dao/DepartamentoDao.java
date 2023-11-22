package dao;

import controller.GestionController;
import io.IO;
import model.Departamento;
import model.Empleado;
/**
 * Clase para crear los metodos que necesitemos para manejar los departamentos
 */
public class DepartamentoDao {

	/**
	 * Metodo para añadir departamentos
	 * @param controller
	 */
	public static void addDepartamento(GestionController controller) {
		IO.print("\nIntroduzca el nombre: ");
		String nombre = IO.readString();
		IO.println(controller.createDepartamento(Departamento.builder().nombre(nombre).jefe(null).build()) != null
				? "\nDepartamento añadido con éxito."
				: "\nNo se ha podido añadir el departamento.");
	}

	/**
	 * Metodo para eliminar departamentos
	 * @param controller
	 */
	public static void deleteDepartamento(GestionController controller) {
		Departamento dep = null;
		try {
			if (existenDepartamentos(controller)) {
				showDepartamentos(controller);
				IO.print("\nIntroduzca el ID del departamento que quiere eliminar: ");
				Integer id = IO.readInt();
				dep = controller.getDepartamentoById(id);

				if (dep.getMisEmpleados().size() != 0) {
					for (Empleado empleado : dep.getMisEmpleados()) {
						empleado.setDepartamento(null);
						controller.updateEmpleado(empleado);
					}
				}

				IO.println(controller.deleteDepartamento(dep) ? "\nDepartamento eliminado con éxito."
						: "\nNo se ha podido eliminar el departamento.");
			} else {
				IO.println("\n¡No existe ningún departamento!");
			}
		} catch (NullPointerException e) {
			if (dep == null) {
				IO.println("\n¡El departamento indicado no existe!");
			}
		}
	}

	/**
	 * Metodo para mostrar los departamentos
	 * @param controller
	 */
	public static void showDepartamentos(GestionController controller) {
		if (existenDepartamentos(controller)) {
			IO.println("\n");
			IO.println(controller.getDepartamento().toString());
		} else {
			IO.println("\n¡No existe ningún departamento!");
		}
	}
	/**
	 * Metodo para mostrar los departamentos por ID
	 * @param controller
	 */
	public static void showDepartamentoporID(GestionController controller) {
		try {
			if (existenDepartamentos(controller)) {
				IO.print("\nIntroduzca el ID del departamento que quiera mostrar: ");
				Integer id = IO.readInt();
				IO.println("\n" + controller.getDepartamentoById(id).toString());
			} else {
				IO.println("\n¡No existe ningún departamento!");
			}
		} catch (NullPointerException e) {
			IO.println("\n¡El departamento indicado no existe!");
		}
	}

	/**
	 * Metodo para añadir jefe a un departamento
	 * @param controller
	 */
	public static void addJefe(GestionController controller) {
		Empleado emp = null;
		Departamento dep = null;
		try {
			if (existenDepartamentos(controller)) {
				showDepartamentos(controller);
				IO.print("\nIntroduzca el ID del departamento al que le quieras añadir un jefe: ");
				Integer idDep = IO.readInt();
				dep = controller.getDepartamentoById(idDep);

				if (EmpleadoDao.existenEmpleados(controller)) {
					EmpleadoDao.showEmpleado(controller);
					IO.print("\nIntroduzca el ID del empleado al que quieras añadir como jefe: ");
					Integer id = IO.readInt();
					emp = controller.getEmpleadoById(id);

					if (emp.getDepartamento() != null) {
						Departamento d = emp.getDepartamento();
						d.getMisEmpleados().remove(emp);
						controller.updateDepartamento(d);
					}

					emp.setDepartamento(dep);
					dep.setJefe(emp);

					controller.updateDepartamento(dep);
					controller.updateEmpleado(emp);

					IO.println((emp.getDepartamento().equals(dep) && dep.getJefe().equals(emp))
							? "\nJefe añadido con éxito."
							: "\nNo se ha podido añadir un jefe.");
				} else {
					IO.println("\n¡No existe ningún empleado para poder añadir un jefe!");
				}
			} else {
				IO.println("\n¡No existe ningún departamento!");
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

	/**
	 * Metodo para eliminar un jefe de un departamento
	 * @param controller
	 */
	public static void deleteJefe(GestionController controller) {
		Departamento dep = null;
		try {
			if (existenDepartamentos(controller)) {
				showDepartamentos(controller);
				IO.print("\nIntroduzca el ID del departamento al que le quieras eliminar el jefe: ");
				Integer id = IO.readInt();
				dep = controller.getDepartamentoById(id);

				if (dep.getJefe() != null) {
					dep.setJefe(null);
					controller.updateDepartamento(dep);
					IO.println("\nJefe eliminado con éxito.");
				} else {
					IO.println("\nEl departamento con Id: " + id + " no tiene ningún jefe asociado.");
				}
			} else {
				IO.println("\n¡No existe ningún departamento!");
			}
		} catch (NullPointerException e) {
			if (dep == null) {
				IO.println("\n¡El departamento indicado no existe!");
			}
		}
	}

	/**
	 * Metodo para añadir un empleado a un departamento
	 * @param controller
	 */
	public static void addDepartamentoToEmple(GestionController controller) {
		Empleado emp = null;
		Departamento dep = null;
		try {
			if (existenDepartamentos(controller)) {
				showDepartamentos(controller);
				IO.print("\nIntroduzca el ID del departamento al que quieras añadir a un empleado: ");
				Integer idDep = IO.readInt();
				dep = controller.getDepartamentoById(idDep);

				if (EmpleadoDao.existenEmpleados(controller)) {
					EmpleadoDao.showEmpleado(controller);
					IO.print("\nIntroduzca el ID del empleado al que quieras añadir un departamento: ");
					Integer id = IO.readInt();
					emp = controller.getEmpleadoById(id);

					if (emp.getDepartamento() != null) {
						if (emp.getDepartamento().getId().equals(idDep)) {
							IO.println("\nEl departamento con Id: " + idDep + " ya está asociado al empleado con Id: "
									+ id + ".");
							return;
						}
						Departamento d = emp.getDepartamento();
						d.getMisEmpleados().remove(emp);
						controller.updateDepartamento(d);
					}

					emp.setDepartamento(dep);
					dep.getMisEmpleados().add(emp);

					controller.updateDepartamento(dep);
					controller.updateEmpleado(emp);

					IO.println("\nDepartamento añadido con éxito.");
				} else {
					IO.println("\n¡No existe ningún empleado!");
				}
			} else {
				IO.println("\n¡No existe ningún departamento!");
			}
		} catch (NullPointerException e) {
			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}

			if (dep == null) {
				IO.println("\n¡El departamento indicado no existe!");
				return;
			}
		}
	}

	/**
	 * Metodo para eliminar un empleado de un departamento
	 * @param controller
	 */
	public static void deleteDepartamentoFromEmple(GestionController controller) {
		Departamento dep = null;
		Empleado emp = null;
		try {
			if (existenDepartamentos(controller)) {
				showDepartamentos(controller);
				IO.print("\nIntroduzca el ID del departamento al que quieras retirar de un empleado: ");
				Integer id = IO.readInt();
				dep = controller.getDepartamentoById(id);

				if (dep.getMisEmpleados().size() != 0) {
					IO.println(dep.getMisEmpleados());
					IO.print("\nIntroduzca el ID del empleado del que quieras retirar el departamento con Id: " + id
							+ ": ");
					Integer idEmp = IO.readInt();
					emp = controller.getEmpleadoById(idEmp);

					if (emp.getDepartamento().getId().equals(dep.getId())) {
						dep.getMisEmpleados().remove(emp);
						emp.setDepartamento(null);
						controller.updateDepartamento(dep);
						controller.updateEmpleado(emp);
						IO.println("\nDepartamento retirado con éxito.");
						return;
					}
					IO.println("\nNo se ha podido retirar el departamento.");

				} else {
					IO.println("\nEl departamento con Id: " + id + " no está asociado a ningún empleado.");
				}
			} else {
				IO.println("\n¡No existe ningún departamento!");
			}
		} catch (NullPointerException e) {
			if (dep == null) {
				IO.println("\n¡El departamento indicado no existe!");
				return;
			}

			if (emp == null) {
				IO.println("\n¡El empleado indicado no existe!");
			}
		}
	}

	/**
	 * Metodo para comprobar que existe un departamento
	 * @param controller
	 */
	public static boolean existenDepartamentos(GestionController controller) {
		return controller.getDepartamento().size() != 0;
	}

	/**
	 * Menu para lo que queramos hacer en departamento
	 */
	public static void menuDepartamento() {
		IO.println("\n1. Crear departamento");
		IO.println("2. Eliminar departamento");
		IO.println("3. Mostrar departamentos");
		IO.println("4. Mostrar departamento por ID");
		IO.println("5. Añadir jefe a departamento");
		IO.println("6. Eliminar jefe de departamento");
		IO.println("7. Añadir departamento a empleado");
		IO.println("8. Eliminar departamento de empleado");
		IO.println("0. Salir\n");
	}
}