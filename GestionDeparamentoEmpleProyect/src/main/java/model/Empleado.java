package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Creamos los constructores y datos que necesitamos con el loombok
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
/**
 * Creamos la tabla con nombre empleados
 */
@Table(name = "empleados")
/**
 * Creamos una query para buscar todos los empleados
 */
@NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")
public class Empleado {
	/**
	 * Creamos los atributos con sus getters y setters y una lista de proyectos 
	 */

	@Id
	@GeneratedValue
	@Column(name = "id")
	@Getter
	private Integer id;
	@Setter
	private String nombre;
	private Double salario;

	@ManyToOne
	@JoinColumn(name = "departamentoId")
	private Departamento departamento;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "empleadosProyectos", joinColumns = @JoinColumn(name = "empleado_id"), 
	inverseJoinColumns = @JoinColumn(name = "proyecto_id"))
	private List<Proyecto> misProyectos;

	/**
	 * Metodo toString para mostrar los atributos
	 */

	public String toString() {

		return "[Empleado(Id: " + getId() + ", Nombre: " + getNombre() + ", Salario: " + getSalario()
				+ ", Departamento:  " + mostrarDepartamento(departamento) + ", [Proyectos: "
				+ mostrarProyectos(misProyectos) + "]\n";


	}

	/**
	 * Metodo para mostrar los proyectos de un empleado
	 * @param misProyectos
	 * @return
	 */
	private List<String> mostrarProyectos(List<Proyecto> misProyectos) {
		List<String> proyectos = new ArrayList<String>();
		for (Proyecto proyecto : misProyectos) {
			proyectos.add("(Id: " + proyecto.getId() + ", Nombre: " + proyecto.getNombre() + ")");
		}
		return proyectos;
	}

	//Metodo para mostrar los departamentos de un empleado
	private String mostrarDepartamento(Departamento dep) {
		if (dep != null) {
			return "(Id: " + departamento.getId() + ", Nombre: " + departamento.getNombre() + ")";
		}
		return null;
	}
}