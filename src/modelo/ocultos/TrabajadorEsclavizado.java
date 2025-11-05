package modelo.ocultos;

import java.time.LocalDate;
import java.util.UUID;
import modelo.enums.EstadoSalud;

/**
 * Es una clase confidencial, solo la dueña tiene accceso (Sakura).
 */
public class TrabajadorEsclavizado {
    private String id;
    private String nombre;
    private String paisOrigen;
    private int edad;
    private LocalDate fechaCaptura;
    private EstadoSalud salud;
    private String asignadoAFabrica; // ID de fábrica
    private String notas; // Información adicional



    /**
     * Constructor para registrar un trabajador esclavizado
     */
    public TrabajadorEsclavizado(String nombre, String paisOrigen, int edad,
                                 LocalDate fechaCaptura, String fabricaId) {
        this.id = "TE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
        this.edad = edad;
        this.fechaCaptura = fechaCaptura;
        this.salud = EstadoSalud.REGULAR;
        this.asignadoAFabrica = fabricaId;
        this.notas = "";

        System.err.println("⚠ REGISTRO CONFIDENCIAL CREADO: " + id);
    }

    /**
     * Constructor simplificado
     */
    public TrabajadorEsclavizado(String nombre, String paisOrigen, int edad, String fabricaId) {
        this(nombre, paisOrigen, edad, LocalDate.now(), fabricaId);
    }

    /**
     * Calcula el tiempo en cautiverio
     */
    public long calcularDiasEnCautiverio() {
        return LocalDate.now().toEpochDay() - fechaCaptura.toEpochDay();
    }

    /**
     * Actualiza el estado de salud
     */
    public void actualizarSalud(EstadoSalud nuevoEstado, String motivo) {
        EstadoSalud anterior = this.salud;
        this.salud = nuevoEstado;
        this.notas += String.format("\n[%s] Salud: %s → %s. Motivo: %s",
                LocalDate.now(), anterior.getDescripcion(),
                nuevoEstado.getDescripcion(), motivo);

        if (nuevoEstado == EstadoSalud.CRITICO) {
            System.err.println("⚠ ALERTA CRÍTICA: Trabajador " + id + " en estado crítico");
        }
    }

    /**
     * Reasigna a otra fábrica
     */
    public void reasignarFabrica(String nuevaFabricaId) {
        String fabricaAnterior = this.asignadoAFabrica;
        this.asignadoAFabrica = nuevaFabricaId;
        this.notas += String.format("\n[%s] Transferido de %s a %s",
                LocalDate.now(), fabricaAnterior, nuevaFabricaId);
        System.err.println("⚠ Trabajador " + id + " transferido");
    }

    /**
     * Agrega una nota al registro
     */
    public void agregarNota(String nota) {
        this.notas += String.format("\n[%s] %s", LocalDate.now(), nota);
    }

    /**
     * Muestra información del trabajador (solo accesible con clave maestra)
     */
    public void mostrarInformacion() {
        System.out.println("----- ⚠ REGISTRO CONFIDENCIAL - NIVEL 10 ⚠ ------");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("País de origen: " + paisOrigen);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Fecha de captura: " + fechaCaptura);
        System.out.println("Días en cautiverio: " + calcularDiasEnCautiverio());
        System.out.println("Estado de salud: " + salud.getDescripcion());
        System.out.println("Asignado a fábrica: " + asignadoAFabrica);

        if (!notas.isEmpty()) {
            System.out.println("\nNotas del registro:" + notas);
        }

        System.out.println("\nEste documento constituye evidencia de crímenes");
    }

    /**
     * Formato resumido para listados
     */
    public String formatoResumido() {
        return String.format("%-12s | %-25s | %-15s | %3d años | %-10s | %s",
                id, nombre, paisOrigen, edad, salud.getDescripcion(), asignadoAFabrica);
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPaisOrigen() { return paisOrigen; }
    public int getEdad() { return edad; }
    public LocalDate getFechaCaptura() { return fechaCaptura; }
    public EstadoSalud getSalud() { return salud; }
    public String getAsignadoAFabrica() { return asignadoAFabrica; }
    public String getNotas() { return notas; }

    @Override
    public String toString() {
        return String.format("TrabajadorEsclavizado[ID=%s, Nombre=%s, Origen=%s, Edad=%d, Salud=%s]",
                id, nombre, paisOrigen, edad, salud.getDescripcion());
    }
}