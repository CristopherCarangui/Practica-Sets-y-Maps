package eva2;

public class Estudiante {
    private String nombre;
    private double promedio;
    private int edad;
    public Estudiante(String nombre, double promedio, int edad){
        this.nombre = nombre;
        this.promedio = promedio;
        this.edad = edad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPromedio() {
        return promedio;
    }
    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    
}
