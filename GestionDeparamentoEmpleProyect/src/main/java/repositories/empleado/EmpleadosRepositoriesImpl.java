package repositories.empleado;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import dao.HibernateManager;
import exceptions.DepartamentoExceptions;
import exceptions.EmpleadoExceptions;
import jakarta.persistence.TypedQuery;
import model.Empleado;
import repositories.departamento.DepartamanetosRepositoriesImpl;

public class EmpleadosRepositoriesImpl implements EmpleadosRepository {
	private final Logger logger = Logger.getLogger(DepartamanetosRepositoriesImpl.class.getName());

	@Override
	public List<Empleado> findAll() {
		logger.info("findAll()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class);
		List<Empleado> list = query.getResultList();
		hb.close();
		return list;
	}


	@Override
	public Empleado save(Empleado entity) {
		logger.info("save()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.getTransaction().begin();

		try {
			hb.getManager().merge(entity);
			hb.getTransaction().commit();
			hb.close();
			return entity;

		} catch (Exception e) {
			throw new DepartamentoExceptions(
					"Error al salvar el empleado con id: " + entity.getId() + "\n" + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	@Override
	public Boolean delete(Empleado entity) {
		logger.info("delete()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();
			// Ojo que borrar implica que estemos en la misma sesión y nos puede dar
			// problemas, por eso lo recuperamos otra vez
			entity = hb.getManager().find(Empleado.class, entity.getId());
			hb.getManager().remove(entity);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			throw new EmpleadoExceptions(
					"Error al eliminar empleado con id: " + entity.getId() + " - " + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	@Override
	public Empleado findById(Integer id) {
		logger.info("findById()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		Empleado emp = hb.getManager().find(Empleado.class, id);
		hb.close();
		return emp;
	}

}