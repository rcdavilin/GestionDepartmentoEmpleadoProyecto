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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "empleados")
@NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")
public class Empleado {

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

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinTable(name = "empleado_proyecto", joinColumns = { @JoinColumn(name = "proyecto_id") }, inverseJoinColumns = {
			@JoinColumn(name = "empleado_id") })
	private List<Proyecto> misProyectos;

	public String toString() {
		if (departamento == null) {
			return "[Empleado(Id: " + getId() + ", Nombre: " + getNombre() + ", Salario: " + getSalario()
					+ ", Departamento: " + departamento + ", Proyectos= " + mostrarProyectos(misProyectos) + ")]\n";
		} else {
			return "[Empleado(Id: " + getId() + ", Nombre: " + getNombre() + ", Salario: " + getSalario()
					+ ", Departamento: [Id: " + departamento.getId() + ", Nombre: " + departamento.getNombre() + "]"
					+ ", Proyectos= " + mostrarProyectos(misProyectos) + ")]\n";
		}

	}

	private String mostrarProyectos(List<Proyecto> misProyectos) {

		List<String> proyectos = new ArrayList<String>();

		for (Proyecto proyecto : misProyectos) {
			proyectos.add("( ID = " + proyecto.getId() + ", nombre: " + proyecto.getNombre());

		}
		return proyectos + "";

	}
}