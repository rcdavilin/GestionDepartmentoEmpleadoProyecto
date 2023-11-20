package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "proyectos")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
public class Proyecto {

	@Id
	@GeneratedValue
	@Column(name = "id")
	@Getter
	private Integer id;

	@Setter
	private String nombre;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "misProyectos")
	private List<Empleado> misEmpleados;

	//Metodo toString para mostrar los atributos
	public String toString() {
		return "[Proyecto(Id: " + getId() + ", Nombre: " + getNombre() + ", [Empleados: "
				+ mostrarEmpleados(misEmpleados) + "\n";
	}

	//Metodo para mostrar los empleados de un proyecto
	private List<String> mostrarEmpleados(List<Empleado> misEmpleados) {
		List<String> empleados = new ArrayList<String>();
		if (misEmpleados != null) {
			for (Empleado empleado : misEmpleados) {
				empleados.add("(Id: " + empleado.getId() + ", Nombre: " + empleado.getNombre() + ", Salario: "
						+ empleado.getSalario() + ")]");
			}
			return empleados;
		}
		return null;
	}
}