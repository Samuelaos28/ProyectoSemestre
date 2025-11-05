package modelo.enums;

public enum TipoPago {
    TARJETA_CREDITO("Tarjeta de Crédito"),
    TARJETA_DEBITO("Tarjeta de Débito"),
    PAYPAL("PayPal"),
    TRANSFERENCIA("Transferencia Bancaria"),
    EFECTIVO("Efectivo contra entrega");

    private String descripcion;

    TipoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
