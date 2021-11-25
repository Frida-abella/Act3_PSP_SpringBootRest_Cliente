package act3.serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import act3.serviciorest.cliente.entidad.Videojuego;

// Usamos la anotación @Service, ya que sirve para acceder al servicio rest.
// Con esta anotación se da alta como objeto dentro del contexto Spring
@Service
public class ServicioProxyVideojuego {
	
	// COMPROBAR SI ESTÁ BIEN LA URL!!
	// Declaramos la URL base del servicio REST de Videojuegos
	public static final String URL = "http://localhost:8086/videojuegos/";
	
	// Inyectamos el objeto que nos permite salir fuera de la JVM y hacer peticiones http al sercicio rest
	@Autowired
	RestTemplate restTemplate;
	
	// Creamos el método que nos permitirá obtener/buscar un videojuego por su ID del servicio REST 
	// Este método trabaja también con objetos ResponseEntity, gracias a Spring
	// @param se le pasa un número entero como ID buscado
	// Genera un objeto ResponseEntity<Videojuego> que almacena el contenido
	// al llamar al método getEntity, que recupera el ResponseEntity del controlador del servicio rest
	
	
	public Videojuego obtener (int id) {
		try {
			ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
			// Asignamos a la variable "hs"el contenido del StatusCode que se manda desde el servidor (en el ResponseEntity guardado en "re")
			HttpStatus hs = re.getStatusCode();
			if (hs == HttpStatus.OK) {  // Si el status del HTTP recibido desde el servicio es un OK, se procede a mostrar el Body con el contenido del ResponseEntity 
				return re.getBody(); // (devolverá el videojuego buscado)
			} else {
				System.out.println("Respuesta no contemplada por el servicio");
				return null;
			}
			// Con la excepción HttpClientErrorException podremos capturar el código del Status enviado arrojado por el servidor
			// con el método .getStatusCode()
		} catch (HttpClientErrorException e){ // Capturamos las excepeciones en caso de que no encuentre el videojuego buscado y saca el status http
			System.out.println("La persona con el id " +id + " no se ha encontrdo o no existe");
			System.out.println("Código de respuesta " + e.getStatusCode());
			return null;
		}
	}
	
	public Videojuego alta (Videojuego vdjg) {
		try {
			// Creará un objeto responseEntity con el resultado de "Postear" en el servicio el videojuego
			// se usa el método .postForEntity() para dar de alta el videojuego. A este método se le pasan
			// como parámetros: la URL, el objeto videojuego (en el Body) y el objeto que nos devuelve el servicio
			ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL, vdjg, Videojuego.class);
			System.out.println("Dar de alta. El código de respuesta es --> "+ re.getStatusCode());
			return re.getBody();
		}
		catch (HttpClientErrorException e){
			System.out.println("El videojuego no se ha podido dar de alta");
			System.out.println("Código de respuesta: " + e.getStatusCode());
			return null;
		}
	}
	
	public boolean modificar (Videojuego vdjg) {
		try {
			// Para modificar un videojuego usaremos el método PUT del objeto restTemplate
			// Le pasamos como parámetros la URL, el ID del videojuego y el cast a la clase Videojuego
			// El método PUT no devuelve nada, pero si no ha dado error asumiremos que se ha dado de alta
			restTemplate.put(URL, vdjg.getId(), Videojuego.class);
			return true;
			// capturamos la excepción por si da error 
		} catch (HttpClientErrorException e) {
			// Avisamos de que no se ha modificado el videojuego
			System.out.println("No se ha podido modificar el videojuego con id " + vdjg.getId());
			// Sacamos el código de status arrojado por el servidor
			System.out.println("Código de respuesta: " +e.getStatusCode());
			return false;
		}
		
	}
	
	
	// El método borrar para eliminar uno de los videojuegos por su id llamará
	// al método delete(), al que se le pasa el id. No devuelve nada
	// Sólo devolverá un mensaje al saltar un error o excepción, porque la capturamos
	
	public boolean borrar (int id) {
		try {
			restTemplate.delete(URL, id);
			return true;
		}
		catch (HttpClientErrorException e){
			System.out.println("No se ha podido borrar el videojuego deseado, con id: " + id);
			System.out.println("Código de respuesta enviado por el server: " + e.getStatusCode());
			return false;
		}
	}
	
	public List<Videojuego> listar () {
		try {
			ResponseEntity<Videojuego[]> response = restTemplate.getForEntity(URL, Videojuego[].class);
			//Asignamos (casteamos)a la variable arrayVideojuegos el conjunto de elementos que contiene el Body 
			// de la respuesta enviada desde el server
			Videojuego [] arrayVideojuegos = response.getBody();
			// Convertimos el array en un ArrayList:
			return Arrays.asList(arrayVideojuegos);
		}
		catch (HttpClientErrorException e) {
			System.out.println("No se ha podido listar los videojuegos");
			System.out.println("Código de respuesta enviado por el server: " + e.getStatusCode());
			return null;
		}
	}
	
	
}
