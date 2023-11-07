package model;

import jakarta.persistence.*;
import lombok.*;

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
	
	@OneToMany
	private Empleado empleado;
	
	public String show() {		
		if (id == 0) {
			return "no departamento!!!";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%2d:%-20s:", id, nombre));
		if (empleado == null || empleado.getNombre() == null) {
			sb.append("sin jefe!!");
		} else {
			sb.append(String.format("jefe [%2d:%s]", empleado.getId(), empleado.getNombre()));
		}
		
		return sb.toString();
	}
}
