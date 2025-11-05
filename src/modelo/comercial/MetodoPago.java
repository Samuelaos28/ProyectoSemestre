package modelo.comercial;

import java.util.UUID;
import modelo.enums.TipoPago;


public class MetodoPago {
    private String id;
    private TipoPago tipo;
    private String titular;
    private String numeroEnmascarado; // Solo últimos 4 dígitos
    private boolean activo;


    public MetodoPago(TipoPago tipo, String titular, String numeroCompleto) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.titular = titular;
        this.numeroEnmascarado = enmascararNumero(numeroCompleto);
        this.activo = true;
    }

    public MetodoPago(TipoPago tipo, String titular) {
        this(tipo, titular, "");
    }

    /**
     * Enmascara el número completo, dejando solo los últimos 4 dígitos
     */
    private String enmascararNumero(String numeroCompleto) {
        if (numeroCompleto == null || numeroCompleto.isEmpty()) {
            return "N/A";
        }

        // Elimina espacios y guiones
        String numeroLimpio = numeroCompleto.replaceAll("[\\s-]", "");

        if (numeroLimpio.length() < 4) {
            return "****";
        }

        String ultimosCuatro = numeroLimpio.substring(numeroLimpio.length() - 4);
        return "**** **** **** " + ultimosCuatro;
    }

    /**
     * Valida si el método de pago puede ser usado
     */
    public boolean validar() {
        if (!activo) {
            System.out.println("Este método de pago está desactivado");
            return false;
        }

        if (titular == null || titular.trim().isEmpty()) {
            System.out.println("El titular no puede estar vacío");
            return false;
        }

        System.out.println("✓ Método de pago válido");
        return true;
    }

    /**
     * Desactiva el método de pago
     */
    public void desactivar() {
        this.activo = false;
        System.out.println("Método de pago desactivado");
    }

    /**
     * Reactiva el método de pago
     */
    public void activar() {
        this.activo = true;
        System.out.println("Método de pago reactivado");
    }

    // Getters y Setters
    public String getId() { return id; }
    public TipoPago getTipo() { return tipo; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public String getNumeroEnmascarado() { return numeroEnmascarado; }
    public boolean isActivo() { return activo; }

    @Override
    public String toString() {
        return String.format("MetodoPago[Tipo=%s, Titular=%s, Número=%s, Activo=%s]",
                tipo.getDescripcion(), titular, numeroEnmascarado, activo);
    }

    /**
     * Formato para mostrar en selección
     */
    public String formatoSeleccion() {
        return String.format("%s - %s (%s)",
                tipo.getDescripcion(), numeroEnmascarado, titular);
    }
}
