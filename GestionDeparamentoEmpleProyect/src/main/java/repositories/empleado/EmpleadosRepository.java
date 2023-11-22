package repositories.empleado;

import model.Empleado;
import repositories.CrudRepository;
/**
 * Interfaz donde extendemos la interfaz CrudRepositories 
 */
public interface EmpleadosRepository extends CrudRepository<Empleado, Integer> {

}
