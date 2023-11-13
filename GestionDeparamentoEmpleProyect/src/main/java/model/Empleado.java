package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER)
	private List<Proyecto> misProyectos = new ArrayList<Proyecto>();

	public String toString() {
		if (departamento == null) {
			return "[Empleado(id = " + getId() + ", nombre=" + getNombre() + ", salario=" + getSalario()
					+ ", departamento=" + departamento + ", proyectos" + getMisProyectos() + ")]";
		} else {
			return "[Empleado(id = " + getId() + ", nombre=" + getNombre() + ", salario=" + getSalario()
					+ ", departamento=" + departamento.getId() + " - " + departamento.getNombre() + ", proyectos"
					+ getMisProyectos() + ")]";
		}

	}
}