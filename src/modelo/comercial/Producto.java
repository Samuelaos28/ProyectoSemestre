package modelo.comercial;

import java.time.LocalDate;
import java.util.UUID;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private LocalDate fechaLanzamiento;
    private Categoria categoria;
    private boolean activo; // Para no eliminar, solo ocultar productos

    public Producto() {
    }

    public Producto(String nombre, String descripcion, double precio,
                    int stock, Categoria categoria) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.fechaLanzamiento = LocalDate.now();
        this.categoria = categoria;
        this.activo = true;
    }

    /**
     * Verifica si hay stock disponible
     */
    public boolean hayStock(int cantidad) {
        return stock >= cantidad && activo;
    }

    /**
     * Reduce el stock al realizar una venta
     */
    public boolean reducirStock(int cantidad) {
        if (hayStock(cantidad)) {
            this.stock -= cantidad;
            return true;
        }
        return false;
    }

    /**
     * Aumenta el stock (reabastecimiento)
     */
    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.stock += cantidad;
            System.out.println("Stock actualizado. Nuevo stock: " + this.stock);
        }
    }

    /**
     * Desactiva el producto (no se elimina, se oculta)
     */
    public void desactivar() {
        this.activo = false;
        System.out.println("Producto '" + nombre + "' desactivado");
    }


    public void activar() {
        this.activo = true;
        System.out.println("Producto '" + nombre + "' activado");
    }

    /**
     * Calcula el precio con descuento
     */
    public double calcularPrecioConDescuento(double porcentajeDescuento) {
        return precio * (1 - porcentajeDescuento / 100);
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return String.format("Producto[ID=%s, Nombre=%s, Precio=$%.2f, Stock=%d, Categoría=%s, Activo=%s]",
                id.substring(0, 8), nombre, precio, stock,
                categoria != null ? categoria.getNombre() : "Sin categoría", activo);
    }

    /**
     * Formato para mostrar en catálogo
     */
    public String formatoCatalogo() {
        return String.format("%-30s | $%-10.2f | Stock: %-5d | %s",
                nombre, precio, stock, categoria != null ? categoria.getNombre() : "N/A");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return id.equals(producto.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}