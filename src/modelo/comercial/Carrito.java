package modelo.comercial;

import modelo.usuarios.Cliente;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Carrito {
    private String id;
    private Cliente cliente;
    private LocalDateTime fechaCreacion;
    private List<LineaCarrito> lineas;

    /**
     * Constructor que crea un carrito para un cliente
     */
    public Carrito(Cliente cliente) {
        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.fechaCreacion = LocalDateTime.now();
        this.lineas = new ArrayList<>();
    }

    /**
     * Agrega un producto al carrito
     */
    public boolean agregarProducto(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) {
            System.out.println("❌ Producto o cantidad inválidos");
            return false;
        }

        if (!producto.hayStock(cantidad)) {
            System.out.println("❌ Stock insuficiente. Disponible: " + producto.getStock());
            return false;
        }

        // Verifica si el producto ya está en el carrito
        for (LineaCarrito linea : lineas) {
            if (linea.getProducto().getId().equals(producto.getId())) {
                // Si ya existe, actualiza la cantidad
                int nuevaCantidad = linea.getCantidad() + cantidad;
                if (producto.hayStock(nuevaCantidad)) {
                    linea.setCantidad(nuevaCantidad);
                    System.out.println("✓ Cantidad actualizada en el carrito");
                    return true;
                } else {
                    System.out.println("❌ No hay suficiente stock para esa cantidad");
                    return false;
                }
            }
        }

        // Si no existe, crea una nueva línea
        LineaCarrito nuevaLinea = new LineaCarrito(producto, cantidad);
        lineas.add(nuevaLinea);
        System.out.println("✓ Producto agregado al carrito");
        return true;
    }

    /**
     * Elimina un producto del carrito
     */
    public boolean eliminarProducto(String productoId) {
        boolean eliminado = lineas.removeIf(linea ->
                linea.getProducto().getId().equals(productoId));

        if (eliminado) {
            System.out.println("✓ Producto eliminado del carrito");
        } else {
            System.out.println("❌ Producto no encontrado en el carrito");
        }
        return eliminado;
    }

    /**
     * Actualiza la cantidad de un producto en el carrito
     */
    public boolean actualizarCantidad(String productoId, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return eliminarProducto(productoId);
        }

        for (LineaCarrito linea : lineas) {
            if (linea.getProducto().getId().equals(productoId)) {
                if (linea.getProducto().hayStock(nuevaCantidad)) {
                    linea.setCantidad(nuevaCantidad);
                    System.out.println("✓ Cantidad actualizada");
                    return true;
                } else {
                    System.out.println("❌ Stock insuficiente");
                    return false;
                }
            }
        }
        System.out.println("❌ Producto no encontrado en el carrito");
        return false;
    }

    /**
     * Calcula el total del carrito
     */
    public double calcularTotal() {
        return lineas.stream()
                .mapToDouble(LineaCarrito::getSubtotal)
                .sum();
    }

    /**
     * Vacía el carrito
     */
    public void vaciar() {
        lineas.clear();
        System.out.println("✓ Carrito vaciado");
    }

    /**
     * Verifica si el carrito está vacío
     */
    public boolean estaVacio() {
        return lineas.isEmpty();
    }

    /**
     * Muestra el contenido del carrito
     */
    public void mostrarContenido() {
        if (estaVacio()) {
            System.out.println("🛒 El carrito está vacío");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    CARRITO DE COMPRAS                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println(String.format("%-35s %10s %12s %15s",
                "Producto", "Cantidad", "P. Unit.", "Subtotal"));
        System.out.println("─────────────────────────────────────────────────────────────────");

        for (LineaCarrito linea : lineas) {
            System.out.println(linea.formatoDetallado());
        }

        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println(String.format("%58s $%,15.2f", "TOTAL:", calcularTotal()));
        System.out.println("═══════════════════════════════════════════════════════════════════\n");
    }

    // Getters
    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public List<LineaCarrito> getLineas() { return new ArrayList<>(lineas); }
    public int getCantidadItems() { return lineas.size(); }

    @Override
    public String toString() {
        return String.format("Carrito[ID=%s, Cliente=%s, Items=%d, Total=$%.2f]",
                id.substring(0, 8), cliente.getNombre(), lineas.size(), calcularTotal());
    }
}