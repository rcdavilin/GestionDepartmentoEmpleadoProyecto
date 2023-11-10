package dao;

import java.util.List;

import model.Proyecto;

public final class BD {
	public static List<Proyecto> getProyectosInit() {
		return List.of(
				Proyecto.builder().nombre("Proyecto 1").build(), 
				Proyecto.builder().nombre("Proyecto 2").build(),
				Proyecto.builder().nombre("Proyecto 3").build(), 
				Proyecto.builder().nombre("Proyecto 4").build(),
				Proyecto.builder().nombre("Proyecto 5").build());
	}
}