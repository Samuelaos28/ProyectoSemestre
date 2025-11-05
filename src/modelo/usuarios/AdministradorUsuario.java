package modelo.usuarios;

import modelo.enums.EstadoCuenta;

public class AdministradorUsuario extends Usuario {
    private int nivelAcceso; // 1-5, donde 5 es máximo
    private boolean miembroConsejoSombrio;


    public AdministradorUsuario(String nombre, String email, String password) {
        super(nombre, email, password, "ADMIN_USUARIO");
        this.nivelAcceso = 3; // Nivel medio por defecto
        this.miembroConsejoSombrio = false;
    }

    /**
     * Constructor con nivel de acceso personalizado
     */
    public AdministradorUsuario(String nombre, String email, String password, int nivelAcceso) {
        this(nombre, email, password);
        setNivelAcceso(nivelAcceso);
    }

    @Override
    public void mostrarPermisos() {
        System.out.println();
        System.out.println("  PERMISOS DE ADMINISTRADOR DE USUARIOS");
        System.out.println("  Nivel de Acceso: " + nivelAcceso + "/5");
        System.out.println("✓ Ver lista de usuarios");
        System.out.println("✓ Suspender/Reactivar usuarios");

        if (nivelAcceso >= 3) {
            System.out.println("✓ Modificar datos de usuarios");
        }
        if (nivelAcceso >= 4) {
            System.out.println("✓ Eliminar usuarios");
            System.out.println("✓ Crear administradores");
        }
        if (nivelAcceso >= 5 || miembroConsejoSombrio) {
            System.out.println("✓ Acceso a logs del sistema");
            System.out.println("✓ ACCESO AL CONSEJO SOMBRÍO");
        }
        System.out.println("═══════════════════════════════════════");
    }

    /**
     * Suspende la cuenta de un usuario
     */
    public boolean suspenderUsuario(Usuario usuario) {
        if (usuario.getEstadoCuenta() == EstadoCuenta.ACTIVA) {
            usuario.setEstadoCuenta(EstadoCuenta.SUSPENDIDA);
            System.out.println("Usuario " + usuario.getNombre() + " ha sido suspendido");
            return true;
        }
        System.out.println("El usuario ya está suspendido o inactivo");
        return false;
    }

    /**
     * Reactiva la cuenta de un usuario
     */
    public boolean reactivarUsuario(Usuario usuario) {
        if (usuario.getEstadoCuenta() != EstadoCuenta.ACTIVA) {
            usuario.setEstadoCuenta(EstadoCuenta.ACTIVA);
            System.out.println("Usuario " + usuario.getNombre() + " ha sido reactivado");
            return true;
        }
        System.out.println("El usuario ya está activo");
        return false;
    }

    /**
     * Verifica si puede realizar una acción según su nivel
     */
    public boolean puedeRealizarAccion(String accion) {
        switch (accion) {
            case "SUSPENDER":
            case "REACTIVAR":
                return nivelAcceso >= 2;
            case "MODIFICAR":
                return nivelAcceso >= 3;
            case "ELIMINAR":
            case "CREAR_ADMIN":
                return nivelAcceso >= 4;
            case "VER_LOGS":
                return nivelAcceso >= 5 || miembroConsejoSombrio;
            default:
                return false;
        }
    }

    /**
     * Convierte al administrador en miembro del Consejo Sombrío
     */
    public void ascenderAConsejoSombrio(String claveMaestra) {
        this.miembroConsejoSombrio = true;
        this.nivelAcceso = 5;
        System.out.println(" Miembro ascendido al Consejo Sombrío");
    }


    public boolean esMiembroConsejoSombrio() {
        return miembroConsejoSombrio;
    }

    // Getters y Setters
    public int getNivelAcceso() { return nivelAcceso; }

    public void setNivelAcceso(int nivel) {
        if (nivel >= 1 && nivel <= 5) {
            this.nivelAcceso = nivel;
        } else {
            System.out.println("Nivel inválido. Debe estar entre 1 y 5");
        }
    }

    @Override
    public String toString() {
        return String.format("AdministradorUsuario[%s, Nivel=%d, Consejo Sombrío=%s]",
                super.toString(), nivelAcceso, miembroConsejoSombrio);
    }
}
