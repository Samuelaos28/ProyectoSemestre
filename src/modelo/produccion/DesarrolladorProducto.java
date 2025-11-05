package modelo.produccion;


import modelo.usuarios.Usuario;
import modelo.comercial.Producto;
import java.util.ArrayList;
import java.util.List;


public class DesarrolladorProducto extends Usuario {
    private String especialidad;
    private List<Producto> productosDesarrollados;
    private int añosExperiencia;


    public DesarrolladorProducto(String nombre, String email, String password, String especialidad) {
        super(nombre, email, password, "DESARROLLADOR");
        this.especialidad = especialidad;
        this.productosDesarrollados = new ArrayList<>();
        this.añosExperiencia = 0;
    }

    public DesarrolladorProducto(String nombre, String email, String password,
                                 String especialidad, int añosExperiencia) {
        this(nombre, email, password, especialidad);
        this.añosExperiencia = añosExperiencia;
    }

    @Override
    public void mostrarPermisos() {
        System.out.println();
        System.out.println("----PERMISOS DE DESARROLLADOR DE PRODUCTO----");
        System.out.println();
        System.out.println("✓ Diseñar nuevos productos");
        System.out.println("✓ Proponer fórmulas");
        System.out.println("✓ Participar en desarrollo de productos");
        System.out.println("✓ Ver productos en desarrollo");
        System.out.println("\nEspecialidad: " + especialidad);
        System.out.println("Años de experiencia: " + añosExperiencia);
        System.out.println("Productos desarrollados: " + productosDesarrollados.size());
        System.out.println();
    }

    //Registar un producto de este especialista
    public void agregarProductoDesarrollado(Producto producto) {
        if (producto != null && !productosDesarrollados.contains(producto)) {
            productosDesarrollados.add(producto);
            System.out.println("✓ Producto '" + producto.getNombre() +
                    "' registrado como desarrollado por " + nombre);
        }
    }

    /**
     * Calcula el nivel del desarrollador basado en experiencia y productos
     */
    public String calcularNivel() {
        int puntaje = añosExperiencia + productosDesarrollados.size();

        if (puntaje < 5) return "Junior";
        else if (puntaje < 15) return "Semi-Senior";
        else if (puntaje < 30) return "Senior";
        else return "Expert";
    }

    /**
     * Muestra el portafolio del desarrollador
     */
    public void mostrarPortafolio() {
        System.out.println();
        System.out.println("    PORTAFOLIO DE DESARROLLADOR     ");
        System.out.println();
        System.out.println("Nombre: " + nombre);
        System.out.println("Especialidad: " + especialidad);
        System.out.println("Nivel: " + calcularNivel());
        System.out.println("Años de experiencia: " + añosExperiencia);
        System.out.println("\nProductos Desarrollados (" + productosDesarrollados.size() + "):");

        if (productosDesarrollados.isEmpty()) {
            System.out.println("  - Aún no ha desarrollado productos");
        } else {
            for (int i = 0; i < productosDesarrollados.size(); i++) {
                System.out.println("  " + (i + 1) + ". " +
                        productosDesarrollados.get(i).getNombre());
            }
        }
    }

    // Getters y Setters
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public List<Producto> getProductosDesarrollados() {
        return new ArrayList<>(productosDesarrollados);
    }

    public int getAñosExperiencia() { return añosExperiencia; }
    public void setAñosExperiencia(int años) { this.añosExperiencia = años; }

    @Override
    public String toString() {
        return String.format("DesarrolladorProducto[%s, Especialidad=%s, Nivel=%s, Productos=%d]",
                super.toString(), especialidad, calcularNivel(), productosDesarrollados.size());
    }
}
