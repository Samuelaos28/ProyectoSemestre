package modelo.comercial;

import modelo.usuarios.Cliente;
import java.time.LocalDateTime;
import modelo.enums.EstadoCompra;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Compra {
    private String id;
    private Cliente cliente;
    private LocalDateTime fecha;
    private double total;
    private EstadoCompra estado;
    private List<LineaCompra> lineas;
    private MetodoPago metodoPago;
    private String direccionEnvio;

    /**
     * Constructor que crea una compra desde un carrito
     */
    public Compra(Cliente cliente, Carrito carrito, MetodoPago metodoPago) {
        this.id = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoCompra.PENDIENTE;
        this.metodoPago = metodoPago;
        this.direccionEnvio = cliente.getDireccionEnvio();
        this.lineas = new ArrayList<>();

        // Convierte las líneas del carrito en líneas de compra
        for (LineaCarrito lineaCarrito : carrito.getLineas()) {
            LineaCompra lineaCompra = new LineaCompra(
                    lineaCarrito.getProducto(),
                    lineaCarrito.getCantidad(),
                    lineaCarrito.getProducto().getPrecio()
            );
            this.lineas.add(lineaCompra);
        }

        calcularTotal();
    }

    /**
     * Calcula el total de la compra
     */
    private void calcularTotal() {
        this.total = lineas.stream()
                .mapToDouble(LineaCompra::getSubtotal)
                .sum();
    }

    /**
     * Procesa el pago de la compra
     */
    public boolean procesarPago() {
        if (estado != EstadoCompra.PENDIENTE) {
            System.out.println("❌ La compra ya fue procesada");
            return false;
        }

        // Reduce el stock de los productos
        for (LineaCompra linea : lineas) {
            if (!linea.getProducto().reducirStock(linea.getCantidad())) {
                System.out.println("❌ Error al reducir stock del producto: " +
                        linea.getProducto().getNombre());
                return false;
            }
        }

        this.estado = EstadoCompra.PAGADA;
        System.out.println("✓ Pago procesado exitosamente");
        return true;
    }

    /**
     * Actualiza el estado de la compra
     */
    public void actualizarEstado(EstadoCompra nuevoEstado) {
        EstadoCompra estadoAnterior = this.estado;
        this.estado = nuevoEstado;
        System.out.println(String.format("Estado actualizado: %s → %s",
                estadoAnterior.getDescripcion(), nuevoEstado.getDescripcion()));
    }

    /**
     * Cancela la compra (solo si no ha sido enviada)
     */
    public boolean cancelar() {
        if (estado == EstadoCompra.ENVIADA || estado == EstadoCompra.ENTREGADA) {
            System.out.println("❌ No se puede cancelar una compra enviada o entregada");
            return false;
        }

        // Devuelve el stock si ya estaba pagada
        if (estado == EstadoCompra.PAGADA || estado == EstadoCompra.PROCESANDO) {
            for (LineaCompra linea : lineas) {
                linea.getProducto().agregarStock(linea.getCantidad());
            }
        }

        this.estado = EstadoCompra.CANCELADA;
        System.out.println("✓ Compra cancelada");
        return true;
    }

    /**
     * Muestra el detalle completo de la compra
     */
    public void mostrarDetalle() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DETALLE DE COMPRA                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("Orden ID: " + id);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Fecha: " + fecha.toLocalDate() + " " + fecha.toLocalTime());
        System.out.println("Estado: " + estado.getDescripcion());
        System.out.println("Dirección de envío: " + (direccionEnvio != null ? direccionEnvio : "No especificada"));
        System.out.println("Método de pago: " + (metodoPago != null ? metodoPago.getTipo() : "No especificado"));
        System.out.println("\n─────────────────────────────────────────────────────────────────");
        System.out.println(String.format("%-35s %10s %12s %15s",
                "Producto", "Cantidad", "P. Unit.", "Subtotal"));
        System.out.println("─────────────────────────────────────────────────────────────────");

        for (LineaCompra linea : lineas) {
            System.out.println(linea.formatoDetallado());
        }

        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println(String.format("%58s $%,15.2f", "TOTAL:", total));
        System.out.println("═══════════════════════════════════════════════════════════════════\n");
    }

    /**
     * Formato resumido para listados
     */
    public String formatoResumido() {
        return String.format("%-12s | %s | %-20s | $%,10.2f | %s",
                id, fecha.toLocalDate(), cliente.getNombre(), total, estado.getDescripcion());
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public List<LineaCompra> getLineas() {
        return new ArrayList<>(lineas);
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    @Override
    public String toString() {
        return String.format("Compra[ID=%s, Cliente=%s, Total=$%.2f, Estado=%s]",
                id, cliente.getNombre(), total, estado.getDescripcion());
    }
}