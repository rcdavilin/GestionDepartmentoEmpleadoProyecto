package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "proyectos")
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
	
	@OneToMany(mappedBy = "proyecto")
	private List<Empleado> misEmpleados = new ArrayList<Empleado>();
}
