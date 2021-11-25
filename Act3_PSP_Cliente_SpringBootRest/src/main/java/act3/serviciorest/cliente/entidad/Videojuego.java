package act3.serviciorest.cliente.entidad;

import org.springframework.stereotype.Component;

// Creamos la clase Videojuego. Será exactamente igual que la clase Videojuego 
// de la parte de servidor 

// Lo declaramos como @Component, de modo se da de alta dentro del contexto Spring
//y deja de ser anónimo. Su identificador será "videojuego"
@Component
public class Videojuego{
	
	private int id;
	private String nombre;
	private String compañia;
	private int nota;

	
	//CREAMOS MÉTODOS CONSTRUCTORES
	//CREAMOS GETTER Y SETTER
	//CREAMOS MÉTODO TO STRING
	
	public Videojuego() {
		super();
	}	

	public Videojuego(int id, String nombre, String compañia, int nota) {
		this.id = id;
		this.nombre = nombre;
		this.compañia = compañia;
		this.nota = nota;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCompañia() {
		return compañia;
	}
	public void setCompañia(String compañia) {
		this.compañia = compañia;
	}

	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Videojuego [id=" + id + ", nombre=" + nombre + ", compañia=" + compañia + ", nota=" + nota + "]";
	}
	

	
}
