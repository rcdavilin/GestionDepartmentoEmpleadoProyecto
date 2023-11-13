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
	private List<Empleado> misEmpleados = new ArrayList<Empleado>();

	public String toString() {

		if (jefe == null) {
			return "Departamento(id=" + getId() + ", nombre=" + getNombre() + ",jefe= " + jefe + ", empleados="
					+ getJefe() + ")\n";
		} else {
			return "Departamento(id=" + getId() + ", nombre=" + getNombre() + ",jefe= " + jefe.getId() + "-"
					+ jefe.getNombre() + ", empleados= " + getJefe() + ")\n";
		}

	}
}