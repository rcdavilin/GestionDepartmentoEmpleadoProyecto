package controller;

import java.util.List;
import java.util.logging.Logger;

import model.Departamento;
import model.Empleado;
import model.Proyecto;
import repositories.departamento.DepartamentosRepositories;
import repositories.empleado.EmpleadosRepository;
import repositories.proyecto.ProyectosRepositories;

public class GestionController {
    private final Logger logger = Logger.getLogger(GestionController.class.getName());

    // Mis dependencias
    private final DepartamentosRepositories depRepository;
    private final EmpleadosRepository empleRepository;
    private final ProyectosRepositories proyectoRepository;

    public GestionController(DepartamentosRepositories deparatamentosRepository, 
    		EmpleadosRepository empleRepository, ProyectosRepositories proyRepository) {
        this.depRepository = deparatamentosRepository;
        this.empleRepository = empleRepository;
        this.proyectoRepository = proyRepository;
    }

    // Departamentos
    public List<Departamento> getDepartamento() {
        logger.info("Obteniendo Departamentos");
        return depRepository.findAll();
    }

    public Departamento createDepartamento(Departamento departamento) {
        logger.info("Creando Departamento");
        return depRepository.save(departamento);
    }

    public Departamento getDepartamentoPorId(Integer id) {
        logger.info("Obteniendo Departamento con uuid: " + id);
        return depRepository.findById(id);
    }

    public Departamento updateDepartamento(Departamento departamento) {
        logger.info("Actualizando Departamento con uuid: " + departamento.getId());
        return depRepository.save(departamento);
    }

    public Boolean deleteDepartamento(Departamento dep) {
        logger.info("Eliminando Departamento con uuid: " + dep.getId());
        return depRepository.delete(dep);
    }

    // Empleado
    public List<Empleado> getEmpleado() {
        logger.info("Obteniendo Empleados");
        return empleRepository.findAll();
    }

    public Empleado createEmpleado(Empleado empleado) {
        logger.info("Creando Empleado");
        return empleRepository.save(empleado);
    }

    public Empleado getEmpleadoById(Integer id) {
        logger.info("Obteniendo Empleado con uuid: " + id);
        return empleRepository.findById(id);
    }
    
  
 

    public Empleado updateEmpleado(Empleado empleado) {
        logger.info("Actualizando Empleado con uuid: " + empleado.getId());
        return empleRepository.save(empleado);
    }

    public Boolean deleteEmpleado(Empleado empleado) {
        logger.info("Eliminando Empleado con uuid: " + empleado.getId());
        return empleRepository.delete(empleado);
    }
    
 // Proyecto
    public List<Proyecto> getProyecto() {
        logger.info("Obteniendo Proyectos");
        return proyectoRepository.findAll();
    }

    public Proyecto createProyecto(Proyecto proyecto) {
        logger.info("Creando Proyecto");
        return proyectoRepository.save(proyecto);
    }

    public Proyecto getProyectoById(Integer id) {
        logger.info("Obteniendo Proyecto con uuid: " + id);
        return proyectoRepository.findById(id);
    }

    public Proyecto updateProyecto(Proyecto proyecto) {
        logger.info("Actualizando Proyecto con uuid: " + proyecto.getId());
        return proyectoRepository.save(proyecto);
    }

    public Boolean deleteProyecto(Proyecto proyecto) {
        logger.info("Eliminando Proyecto con uuid: " + proyecto.getId());
        return proyectoRepository.delete(proyecto);
    }
}