package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "deparatamentos")
@NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d")
public class Departamento {
	@Id
	@GeneratedValue
	@Column(name = "id")
	@Getter
	private Integer id;
	@Setter
	private String nombre;

	@OneToOne
	private Empleado jefe;

	@OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
	private List<Empleado> misEmpleados;

	// Metodo toString para mostrar los atributos
	public String toString() {

		if (jefe == null) {
			return "[Departamento(Id: " + getId() + ", Nombre: " + getNombre() + ", Jefe: " + jefe + ", Empleados:"
					+ mostrarEmpleado(misEmpleados) + "\n";
		} else {
			return "[Departamento(Id: " + getId() + ", Nombre: " + getNombre() + ", Jefe: (Id: " + jefe.getId()
					+ ", Nombre: " + jefe.getNombre() + "), Empleados:" + mostrarEmpleado(misEmpleados) + "\n";
		}

	}

	// Metodo para mostrar los empleados de un departamento
	private List<String> mostrarEmpleado(List<Empleado> empleados) {
		List<String> e = new ArrayList<String>();
		for (Empleado empleado : empleados) {
			e.add("(Id: " + empleado.getId() + ", Nombre: " + empleado.getNombre() + ")");
		}
		return e;
	}
}