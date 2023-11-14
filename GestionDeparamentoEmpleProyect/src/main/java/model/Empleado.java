package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	@ManyToOne
	@JoinColumn(name = "proyectoId")
	private Proyecto proyecto;

	public String toString() {
		if (departamento == null && proyecto == null) {
			return "Empleado(id = " + getId() + ", nombre=" + getNombre() + ", salario=" + getSalario()
					+ ", departamento=" + departamento + ", proyectos= " + proyecto + ")\n";
		} else {
			return "Empleado(id = " + getId() + ", nombre=" + getNombre() + ", salario=" + getSalario()
					+ ", departamento=" + departamento + ", proyectos=  " + proyecto + ")\n";
		}

	}
}