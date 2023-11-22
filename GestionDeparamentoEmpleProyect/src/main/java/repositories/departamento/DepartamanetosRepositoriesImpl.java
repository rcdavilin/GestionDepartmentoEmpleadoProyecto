package repositories.departamento;

import java.util.List;
import java.util.logging.Logger;

import dao.HibernateManager;
import exceptions.DepartamentoExceptions;
import jakarta.persistence.TypedQuery;
import model.Departamento;

/**
 * Clase donde implementamos las interfaz departamentosRepositories y implementamos los metodos 
 * de la interfaz CrudRepository y los creamos para la clase departamentos de la manera que queramos
 */
public class DepartamanetosRepositoriesImpl implements DepartamentosRepositories {
	private final Logger logger = Logger.getLogger(DepartamanetosRepositoriesImpl.class.getName());

	/**
	 * Metodo para buscar todos los departamentos
	 */
	@Override
	public List<Departamento> findAll() {
		logger.info("findAll()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findAll", Departamento.class);
		List<Departamento> list = query.getResultList();
		hb.close();
		return list;
	}

	/**
	 * Metodo para buscar un departamento por ID
	 */
	@Override
	public Departamento findById(Integer id) {
		logger.info("findById()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			Departamento depatartamento = hb.getManager().find(Departamento.class, id);
			hb.close();
			return depatartamento;
		} catch (NullPointerException e) {
			return null;
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	/**
	 * Metodo para crear salvar los departamentos
	 */
	@Override
	public Departamento save(Departamento entity) {
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
					"Error al salvar el departamento con id: " + entity.getId() + "\n" + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	/**
	 * Metodo para borrar los departamentos
	 */
	@Override
	public Boolean delete(Departamento entity) {
		logger.info("delete()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();
			// Ojo que borrar implica que estemos en la misma sesión y nos puede dar
			// problemas, por eso lo recuperamos otra vez
			entity = hb.getManager().find(Departamento.class, entity.getId());
			hb.getManager().remove(entity);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			throw new DepartamentoExceptions(
					"Error al eliminar departamento con id: " + entity.getId() + " - " + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}
}