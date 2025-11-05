package modelo.comercial;

public class LineaCarrito {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    /**
     * Constructor que crea una línea de carrito
     */
    public LineaCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    /**
     * Calcula el subtotal de esta línea
     */
    private void calcularSubtotal() {
        this.subtotal = producto.getPrecio() * cantidad;
    }

    /**
     * Actualiza la cantidad y recalcula el subtotal
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    /**
     * Formato detallado para mostrar en pantalla
     */
    public String formatoDetallado() {
        return String.format("%-35s %10d $%,10.2f $%,14.2f",
                producto.getNombre().length() > 35 ?
                        producto.getNombre().substring(0, 32) + "..." : producto.getNombre(),
                cantidad,
                producto.getPrecio(),
                subtotal);
    }

    // Getters
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }

    @Override
    public String toString() {
        return String.format("LineaCarrito[Producto=%s, Cantidad=%d, Subtotal=$%.2f]",
                producto.getNombre(), cantidad, subtotal);
    }
}