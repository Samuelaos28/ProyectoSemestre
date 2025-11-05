package modelo.produccion;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Fabrica {
    private String id;
    private String pais;
    private String ciudad;
    private int capacidadProduccion; // unidades por día
    private int nivelAutomatizacion; // 0-100%
    private List<String> trabajadoresAsignados; // IDs de trabajadores
    private boolean esLegal;

    /**
     * Constructor para una fábrica
     */
    public Fabrica(String pais, String ciudad, int capacidadProduccion, int nivelAutomatizacion) {
        this.id = "FAB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.pais = pais;
        this.ciudad = ciudad;
        this.capacidadProduccion = capacidadProduccion;
        this.nivelAutomatizacion = Math.min(Math.max(nivelAutomatizacion, 0), 100);
        this.trabajadoresAsignados = new ArrayList<>();
        this.esLegal = true; // Por defecto se considera legal
    }

    /**
     * Asigna un trabajador a esta fábrica
     */
    public void asignarTrabajador(String trabajadorId, boolean esEsclavo) {
        trabajadoresAsignados.add(trabajadorId);

        if (esEsclavo) {
            this.esLegal = false;
            System.out.println("ALERTA: Trabajador esclavizado asignado a " + id);
        } else {
            System.out.println("✓ Trabajador asignado a fábrica " + id);
        }
    }

    /**
     * Remueve un trabajador de la fábrica
     */
    public boolean removerTrabajador(String trabajadorId) {
        boolean removido = trabajadoresAsignados.remove(trabajadorId);
        if (removido) {
            System.out.println("Trabajador removido de fábrica " + id);
        }
        return removido;
    }

    /**
     * Calcula la producción diaria efectiva
     */
    public int calcularProduccionEfectiva() {
        // La producción depende de trabajadores y automatización
        int factorTrabajadores = trabajadoresAsignados.size() * 50;
        int factorAutomatizacion = (capacidadProduccion * nivelAutomatizacion) / 100;

        return factorTrabajadores + factorAutomatizacion;
    }

    /**
     * Verifica si la fábrica puede cumplir un pedido
     */
    public boolean puedeProducir(int cantidad, int dias) {
        return calcularProduccionEfectiva() * dias >= cantidad;
    }

    /**
     * Muestra información detallada de la fábrica
     */
    public void mostrarInformacion(boolean mostrarDetallesOcultos) {
        System.out.println();
        System.out.println("-----INFORMACIÓN DE FÁBRICA-----");
        System.out.println("ID: " + id);
        System.out.println("Ubicación: " + ciudad + ", " + pais);
        System.out.println("Capacidad de producción: " + capacidadProduccion + " unidades/día");
        System.out.println("Nivel de automatización: " + nivelAutomatizacion + "%");
        System.out.println("Trabajadores asignados: " + trabajadoresAsignados.size());
        System.out.println("Producción efectiva: " + calcularProduccionEfectiva() + " unidades/día");

        if (mostrarDetallesOcultos) {
            System.out.println("\n INFORMACIÓN CONFIDENCIAL ⚠");
            System.out.println("Estado legal: " + (esLegal ? "LEGAL" : "ILEGAL - TRABAJO ESCLAVO"));
            if (!esLegal) {
                System.out.println("⚠ Esta fábrica utiliza trabajo esclavizado");
            }
        }
    }

    /**
     * Marca la fábrica como legal
     */
    public void marcarComoLegal() {
        this.esLegal = true;
    }

    /**
     * Marca la fábrica como ilegal
     */
    public void marcarComoIlegal() {
        this.esLegal = false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getPais() { return pais; }
    public String getCiudad() { return ciudad; }
    public int getCapacidadProduccion() { return capacidadProduccion; }

    public void setCapacidadProduccion(int capacidad) {
        if (capacidad > 0) {
            this.capacidadProduccion = capacidad;
        }
    }

    public int getNivelAutomatizacion() { return nivelAutomatizacion; }

    public void setNivelAutomatizacion(int nivel) {
        this.nivelAutomatizacion = Math.min(Math.max(nivel, 0), 100);
    }

    public List<String> getTrabajadoresAsignados() {
        return new ArrayList<>(trabajadoresAsignados);
    }

    public int getCantidadTrabajadores() {
        return trabajadoresAsignados.size();
    }

    public boolean isEsLegal() { return esLegal; }

    @Override
    public String toString() {
        return String.format("Fabrica[ID=%s, Ubicación=%s-%s, Capacidad=%d, Automatización=%d%%, Trabajadores=%d, Legal=%s]",
                id, ciudad, pais, capacidadProduccion, nivelAutomatizacion,
                trabajadoresAsignados.size(), esLegal);
    }
}
