package modelo.enums;

public enum EstadoCompra {
    PENDIENTE("Pendiente de pago"),
    PAGADA("Pagada"),
    PROCESANDO("En procesamiento"),
    ENVIADA("Enviada"),
    ENTREGADA("Entregada"),
    CANCELADA("Cancelada");

    private String descripcion;

    EstadoCompra(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
