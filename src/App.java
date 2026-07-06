import eva1.Producto;
import eva1.ProductoController;
import eva2.Estudiante;
import eva2.EstudianteController;
import eva3.Libro;
import eva3.LibroController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        runeva1Pro();
        runeva2Est();
        runeva3Lib();
    }
    private static void runeva1Pro(){
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Mouse", 15.0, "Electrónica"));
        productos.add(new Producto("mouse", 15.0, "Electrónica"));  
        productos.add(new Producto("Teclado", 50.0, "Electrónica"));
        productos.add(new Producto("Manzana", 2.0, "Comida"));
        productos.add(new Producto("Pan", 3.0, "Comida"));
        productos.add(new Producto("Silla", 200.0, "Muebles")); 
        ProductoController controller = new ProductoController();
        Set<Producto> fP = controller.filtrarporPrecio(productos, 100);
        System.out.println("Filtrar por Precio");
        for(Producto p1 : fP){
            System.out.println(p1.getNombre() + "-" + p1.getCategoria() + "-" + p1.getPrecio());
        }
        Map<String,List<String>> agrupar = controller.agruparPorCategoria(productos);
        System.out.println("Agrupar por Categoria");
        for(Map.Entry<String,List<String>> entry: agrupar.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        } 


    }

    private static void runeva2Est() {
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("Pedro", 15.5, 20));
        estudiantes.add(new Estudiante("Ana", 18.0, 22));
        estudiantes.add(new Estudiante("Andres", 12.0, 19));
        estudiantes.add(new Estudiante("pedro", 15.5, 20));   // duplicado exacto de Pedro (nombre+promedio+edad igual)
        estudiantes.add(new Estudiante("Pablo", 10.0, 21));
        estudiantes.add(new Estudiante("Esteban", 14.0, 23));
        estudiantes.add(new Estudiante("elena", 16.5, 20));
        estudiantes.add(new Estudiante("Ernesto", 8.0, 25));
        EstudianteController controller = new EstudianteController();
        Set<Estudiante> filEstudiantes = controller.filtrarAprobados(estudiantes, 7.5);
        System.out.println("Filtrar por Promedio");
        for(Estudiante e : filEstudiantes){
            System.out.println(e.getNombre()+ "-" + e.getPromedio() + "-" + e.getEdad());
        }
        Map<Character,List<String>> agruparNombre = controller.agruparPorLetraInicial(estudiantes);
        for(Map.Entry<Character,List<String>> entry : agruparNombre.entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private static void runeva3Lib() {
        List<Libro> libros = new ArrayList<>();
        libros.add(new Libro("Cien Años de Soledad", "Gabriel Garcia Marquez", 1967));
        libros.add(new Libro("El Amor en los Tiempos del Colera", "Gabriel Garcia Marquez", 1985));
        libros.add(new Libro("La Casa de los Espiritus", "Isabel Allende", 1982));
        libros.add(new Libro("Rayuela", "Julio Cortazar", 1963));
        libros.add(new Libro("Norwegian Wood", "Haruki Murakami", 1987));
        libros.add(new Libro("1Q84", "Haruki Murakami", 2009));
        libros.add(new Libro("Kafka en la Orilla", "Haruki Murakami", 2002));
        libros.add(new Libro("cien años de soledad", "gabriel garcia marquez", 1967)); // duplicado exacto
        libros.add(new Libro("El Codigo Da Vinci", "Dan Brown", 2003));
        LibroController controller = new LibroController();
        Set<Libro> filLibros = controller.filtrarPorAnio(libros, 1974);
        System.out.println("Filtrar por Anio");
        for(Libro l : filLibros){
            System.out.println(l.getTitulo() + "-" + l.getAutor() + "-" + l.getAnioPublicacion());
        }
        Map<String, List<String>> agruparAP = controller.agruparPorDecada(libros);
        System.out.println("Agrupar por Decada");
        for(Map.Entry<String,List<String>> entry: agruparAP.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        } 
    }
}
