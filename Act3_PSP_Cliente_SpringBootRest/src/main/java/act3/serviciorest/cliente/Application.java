package act3.serviciorest.cliente;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import act3.serviciorest.cliente.entidad.Videojuego;
import act3.serviciorest.cliente.servicio.ServicioProxyVideojuego;

// Es una aplicación Spring, por lo que con la anotación @SpringBootApplication 
// se configura como tal, y busca anotaciones de Spring por toda la aplicación
// Hacemos que la clase implemente la interfaz CommandLineRunner para que el main pueda acceder
// a los objetos dinámicos de esta clase
@SpringBootApplication
public class Application implements CommandLineRunner {

	// Inyectamos el objeto necesario para conectar con el servidor
	// Lo creamos con la anotación @Autowired propia de Spring
	@Autowired
	private ServicioProxyVideojuego spv;
	
	// Inyectamos también el contexto que creamos, que es un objeto 
	private ApplicationContext context;
	
	// Damos de alta un objeto RestTemplate, que será el que haga las conexiones HTTP fuera de la JVM
	// Creamos el objeto con la anotación @Bean, por lo que se da de alta en el contexto Spring. 
	// El método lo llamamos también restTemplate y recibe un objeto de tipo RestTemplateBuilder
	// Este es el objeto que nos permitirá salir fuera de la JVM y hacer peticiones al servicio rest
	@Bean 
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	// Arrancamos el contexto en el método MAIN. 
	// Dado que es un método estático, para que pueda acceder a los objetos spv y spm (que nos permiten hacer conexiones(
	// hemos hecho que la clase Application implemente "CommandLineRunner" previamente
	// Para que pueda llamar al método run en cuanto cargueel contexto
	public static void main(String[] args) {
		System.out.println("CLIENTE  --> Cargando el contexto de Spring...");
		SpringApplication.run(Application.class, args);
		System.out.println("CLIENTE --> El contexto de Spring ha sido creado");
	}
	
	// Aquí ejecutaremos todo como si se tratase del main
	@Override
	public void run(String...args) throws Exception {
		System.out.println("----------ARRANCANDO EL CLIENTE REST---------");
		
		System.out.println("ELIJA UNA OPCIÓN:\n\n"
				+ "  1. Dar de alta un videojuego \n"
				+ "  2. Dar de baja un videojuego por ID \n"
				+ "  3. Modificar un videojuego por ID\n"
				+ "  4. Obtener un videojuego por ID\n"
				+ "  5. Listar todos los videojuegos \n"
				+ "  6. Salir");
		
		// Creamos un objeto Videojuego al que dentro del switch le cambiaremos los atributos para poder darlo de alta sin ID
		Videojuego vid1 = new Videojuego();
		
		// Creamos un objeto Scanner para pedir por pantalla al cliente la opcion del menú deseada
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
		
		// Creamos un único Scanner para usar a lo largo del menú
		Scanner scanner = new Scanner(System.in);
		
		switch (opcion) {
		
		case 1:
			System.out.println("----DAR DE ALTA DE UN VIDEOJUEGO----");
					
			System.out.println("Escriba el nombre del videojuego:");
			String nombreVideojuego = scanner.nextLine();
			vid1.setNombre(nombreVideojuego);
						
			System.out.println("Escriba la compañía del videojuego: ");
			String nombreCompañia = scanner.nextLine();
			vid1.setCompañia(nombreCompañia);
			
			System.out.println("Introduzca la nota :");
			int nota = scanner.nextInt();
			
			// Damos de alta el objeto videojuego llamando al método alta
			Videojuego vdjg1 = spv.alta(vid1);
			System.out.println("Se ha dado de alta el siguiente videojuego " + vdjg1);
			break;
			
		case 2:
			System.out.println("----DAR DE BAJA UN VIDEOJUEGO POR ID----");
			//Pedimos por pantalla el ID del videojuego que se quiere borrar:
			System.out.println("Escriba el ID del videojuego que desea dar de baja: ");
			int idBorrar = scanner.nextInt();
			boolean borrado = spv.borrar(idBorrar);
			// Confirmamos por pantalla que se ha borrado: deberá salir true
			System.out.println("El videojuego con id " + idBorrar + "ha sido borrado? " + borrado);
			break;
		
		case 3:
			System.out.println("----MODIFICAR UN VIDEOJUEGO POR ID----");
			
			System.out.println("Introduzca los datos nuevos que desea modificar:");
			//Pedimos por pantalla los datos nuevos del videojuego que se quiere modificar:
			System.out.println("Escriba el nombre del videojuego:");
			vid1.setId(scanner.nextInt());
			
			System.out.println("Escriba el nombre del videojuego:");
			vid1.setNombre(scanner.nextLine());
						
			System.out.println("Escriba la compañía del videojuego: ");
			vid1.setCompañia(scanner.nextLine());
			
			System.out.println("Introduzca la nota :");
			vid1.setNota(scanner.nextInt());		
			
			// Modificamos el objeto videojuego llamando al método .modificar() pasándole como argumento
			// el objeto videojuego con los datos que nos ha introducido el cliente por pantalla:
			boolean modificada = spv.modificar(vid1);
			System.out.println("Se ha modificado la persona deseada?" + modificada);
			break;
			
		case 4:
			System.out.println("----OBTENER UN VIDEOJUEGO POR ID----");
			//Pedimos por pantalla el ID del videojuego que se quiere obtener o consultar:
			System.out.println("Escriba el ID del videojuego que desea obtener: ");
			int idObtener = scanner.nextInt();
			vid1 = spv.obtener(idObtener);
			System.out.println("La persona con el id " + idObtener + "es la siguiente: " + vid1);
			break;
			
		case 5:
			System.out.println("----LISTAR VIDEOJUEGOS----");
			// Guardamos en una lista la lista de bibliotecas
			List<Videojuego> listaVideojuegos = spv.listar();
			// Imprimimos la lista de videojuegos recorrieándola con un bucle for/each:
			listaVideojuegos.forEach((v) -> System.out.println(v));
			
			break;
		
		
		case 6:
			 
			System.out.println("Cerrando la aplicación....");
			//context.close();
		
			break;
			
		}
		
		sc.close();
		scanner.close();
	}

} 

