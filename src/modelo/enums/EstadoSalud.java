package modelo.enums;

public enum EstadoSalud {
    CRITICO("Crítico"),
    MALO("Malo"),
    REGULAR("Regular"),
    ESTABLE("Estable");

    private String descripcion;

    EstadoSalud(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
