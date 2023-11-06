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
}
