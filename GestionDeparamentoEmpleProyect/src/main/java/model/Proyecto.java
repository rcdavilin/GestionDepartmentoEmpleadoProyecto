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
	
	@ManyToOne
	@JoinColumn(name = "empleadoId")
	private Empleado empleado;
	

	public String toString() {

		if (empleado == null) {
			return "Departamento(id=" + getId() + ", nombre=" + getNombre() + ", empleados="
					+ getEmpleado() + ")\n";
		} else {
			return "Departamento(id=" + getId() + ", nombre=" + getNombre()
					+ ", empleados= " + " ID: "+empleado.getId() + ", nombre: "+empleado.getNombre() +", salario: "+ empleado.getSalario() + ")\n";
		}

	}
}
