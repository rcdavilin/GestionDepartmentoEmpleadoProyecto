package repositories.proyecto;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import dao.HibernateManager;
import exceptions.DepartamentoExceptions;
import exceptions.ProyectoExceptions;
import jakarta.persistence.TypedQuery;
import model.Proyecto;
import repositories.departamento.DepartamanetosRepositoriesImpl;

public class ProyectosRepositoriesImpl implements ProyectosRepositories{
	 private final Logger logger = Logger.getLogger(DepartamanetosRepositoriesImpl.class.getName());
	 @Override
	    public List<Proyecto> findAll() {
	        logger.info("findAll()");
	        HibernateManager hb = HibernateManager.getInstance();
	        hb.open();
	        TypedQuery<Proyecto> query = hb.getManager().createNamedQuery("Proyecto.findAll", Proyecto.class);
	        List<Proyecto> list = query.getResultList();
	        hb.close();
	        return list;
	    }

	    @Override
	    public Optional<Proyecto> findById(Integer id) {
	        logger.info("findById()");
	        HibernateManager hb = HibernateManager.getInstance();
	        hb.open();
	        Optional<Proyecto> proyecto = Optional.ofNullable(hb.getManager().find(Proyecto.class, id));
	        hb.close();
	        return proyecto;
	    }

	    @Override
	    public Proyecto save(Proyecto entity) {
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
	            throw new ProyectoExceptions("Error al salvar el proyecto con id: " + entity.getId() + "\n" + e.getMessage());
	        } finally {
	            if (hb.getTransaction().isActive()) {
	                hb.getTransaction().rollback();
	            }
	        }
	    }

	    @Override
	    public Boolean delete(Proyecto entity) {
	        logger.info("delete()");
	        HibernateManager hb = HibernateManager.getInstance();
	        hb.open();
	        try {
	            hb.getTransaction().begin();
	            // Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
	            entity = hb.getManager().find(Proyecto.class, entity.getId());
	            hb.getManager().remove(entity);
	            hb.getTransaction().commit();
	            hb.close();
	            return true;
	        } catch (Exception e) {
	            throw new DepartamentoExceptions("Error al eliminar proyecto con id: " + entity.getId() + " - " + e.getMessage());
	        } finally {
	            if (hb.getTransaction().isActive()) {
	                hb.getTransaction().rollback();
	            }
	        }
	    }

}
