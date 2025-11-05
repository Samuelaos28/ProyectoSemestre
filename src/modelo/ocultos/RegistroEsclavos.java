package modelo.ocultos;

import modelo.usuarios.Duena;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/* Gestiona el registro de trabajadores esclavizados.
  Solo accesible con clave maestra.
 */
public class RegistroEsclavos {
    private static RegistroEsclavos instancia;
    private List<TrabajadorEsclavizado> trabajadores;
    private LocalDateTime ultimoAcceso;
    private int nivelCifrado;
    private Duena controlador;
    private List<String> logAccesos;


    private RegistroEsclavos(Duena duena) {
        this.trabajadores = new ArrayList<>();
        this.ultimoAcceso = LocalDateTime.now();
        this.nivelCifrado = 10; // Máximo nivel
        this.controlador = duena;
        this.logAccesos = new ArrayList<>();
        registrarAcceso("Registro inicializado");
    }

    // Obtiene la instancia única del registro

    public static RegistroEsclavos obtenerInstancia(Duena duena, String claveMaestra) {
        if (instancia == null) {
            if (duena != null && duena.verificarClaveMaestra(claveMaestra)) {
                instancia = new RegistroEsclavos(duena);
                System.err.println("REGISTRO DE ESCLAVOS INICIALIZADO");
            } else {
                System.err.println("ACCESO DENEGADO - Credenciales inválidas");
                return null;
            }
        }
        return instancia;
    }

    /**
     * Verifica acceso antes de cualquier operación
     */
    private boolean verificarAcceso(Duena duena, String claveMaestra) {
        if (duena == null || !duena.equals(controlador)) {
            registrarAcceso("INTENTO DE ACCESO NO AUTORIZADO");
            System.err.println(" ALERTA DE SEGURIDAD: Acceso no autorizado detectado");
            return false;
        }

        if (!duena.verificarClaveMaestra(claveMaestra)) {
            registrarAcceso("INTENTO DE ACCESO CON CLAVE INCORRECTA");
            System.err.println(" Clave maestra incorrecta");
            return false;
        }

        ultimoAcceso = LocalDateTime.now();
        registrarAcceso("Acceso autorizado");
        return true;
    }

    /**
     * Registra un acceso en el log
     */
    private void registrarAcceso(String accion) {
        String entrada = String.format("[%s] %s", LocalDateTime.now(), accion);
        logAccesos.add(entrada);
    }

    /**
     * Agrega un nuevo trabajador esclavizado
     */
    public boolean agregarTrabajador(TrabajadorEsclavizado trabajador,
                                     Duena duena, String claveMaestra) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return false;
        }

        trabajadores.add(trabajador);
        registrarAcceso("Trabajador agregado: " + trabajador.getId());
        System.err.println("⚠ Nuevo registro: " + trabajador.getId());
        return true;
    }

    /**
     * Busca un trabajador por ID
     */
    public TrabajadorEsclavizado buscarPorId(String id, Duena duena, String claveMaestra) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return null;
        }

        registrarAcceso("Búsqueda por ID: " + id);
        return trabajadores.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Lista todos los trabajadores
     */
    public List<TrabajadorEsclavizado> listarTodos(Duena duena, String claveMaestra) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return new ArrayList<>();
        }

        registrarAcceso("Listado completo accedido");
        return new ArrayList<>(trabajadores);
    }

    /**
     * Filtra trabajadores por fábrica
     */
    public List<TrabajadorEsclavizado> filtrarPorFabrica(String fabricaId,
                                                         Duena duena, String claveMaestra) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return new ArrayList<>();
        }

        registrarAcceso("Filtrado por fábrica: " + fabricaId);
        List<TrabajadorEsclavizado> resultado = new ArrayList<>();

        for (TrabajadorEsclavizado t : trabajadores) {
            if (t.getAsignadoAFabrica().equals(fabricaId)) {
                resultado.add(t);
            }
        }

        return resultado;
    }

    /**
     * Elimina un trabajador del registro (liberación o... peor)
     */
    public boolean eliminarTrabajador(String id, String motivo,
                                      Duena duena, String claveMaestra) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return false;
        }

        boolean eliminado = trabajadores.removeIf(t -> t.getId().equals(id));

        if (eliminado) {
            registrarAcceso("Trabajador eliminado: " + id + ". Motivo: " + motivo);
            System.err.println("⚠ Registro eliminado: " + id);
        }

        return eliminado;
    }

    /**
     * Muestra estadísticas del registro
     */
    public void mostrarEstadisticas(Duena duena, String claveMaestra) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return;
        }

        System.out.println("ESTADISTICAS DEL REGISTRO DE ESCLAVOS");
        System.out.println("Total de trabajadores esclavizados: " + trabajadores.size());
        System.out.println("Último acceso: " + ultimoAcceso);
        System.out.println("Nivel de cifrado: " + nivelCifrado);
        System.out.println("Total de accesos registrados: " + logAccesos.size());

        // Estadísticas por país
        System.out.println("\nDistribución por país de origen:");
        trabajadores.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        TrabajadorEsclavizado::getPaisOrigen,
                        java.util.stream.Collectors.counting()))
                .forEach((pais, cantidad) ->
                        System.out.println("  " + pais + ": " + cantidad));

        // Estadísticas por estado de salud
        System.out.println("\nEstado de salud:");
        trabajadores.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        TrabajadorEsclavizado::getSalud,
                        java.util.stream.Collectors.counting()))
                .forEach((estado, cantidad) ->
                        System.out.println("  " + estado.getDescripcion() + ": " + cantidad));

        System.out.println("\n⚠ Esta información constituye evidencia de crímenes graves");
        registrarAcceso("Estadísticas consultadas");
    }

    /**
     * Muestra el log de accesos
     */
    public void mostrarLogAccesos(Duena duena, String claveMaestra) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return;
        }
        System.out.println("\nLog de accesos registrados");
        int limite = Math.min(20, logAccesos.size());
        System.out.println("Mostrando últimos " + limite + " accesos:");

        for (int i = logAccesos.size() - limite; i < logAccesos.size(); i++) {
            System.out.println(logAccesos.get(i));
        }

    }

    /**
     * Destruye el registro (PELIGROSO - elimina toda evidencia)
     */
    public boolean destruirRegistro(Duena duena, String claveMaestra, String confirmacion) {
        if (!verificarAcceso(duena, claveMaestra)) {
            return false;
        }

        if (!"DESTRUIR_EVIDENCIA".equals(confirmacion)) {
            System.err.println("Confirmación incorrecta");
            return false;
        }

        registrarAcceso("Registro destruido - Evidencia eliminada");
        trabajadores.clear();
        System.err.println(" Las Evidencias fueron destruidas ");
        return true;
    }

    // Getters
    public int getCantidadTrabajadores() { return trabajadores.size(); }
    public LocalDateTime getUltimoAcceso() { return ultimoAcceso; }
    public int getNivelCifrado() { return nivelCifrado; }
}