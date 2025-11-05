package modelo.usuarios;
import java.util.HashSet;
import java.util.Set;


public class AdministradorContenido extends Usuario {
    private Set<String> permisosEdicion;
    private boolean miembroConsejoSombrio;


    public AdministradorContenido(String nombre, String email, String password) {
        super(nombre, email, password, "ADMIN_CONTENIDO");
        this.permisosEdicion = new HashSet<>();
        inicializarPermisos();
        this.miembroConsejoSombrio = false;
    }

    /**
     * Inicializa los permisos por defecto
     */
    private void inicializarPermisos() {
        permisosEdicion.add("CREAR_PRODUCTO");
        permisosEdicion.add("EDITAR_PRODUCTO");
        permisosEdicion.add("ELIMINAR_PRODUCTO");
        permisosEdicion.add("VER_PRODUCTOS");
        permisosEdicion.add("GESTIONAR_CATEGORIAS");
    }

    @Override
    public void mostrarPermisos() {
        System.out.println();
        System.out.println("  PERMISOS DE ADMINISTRADOR DE CONTENIDO");
        System.out.println();
        System.out.println("✓ Crear nuevos productos");
        System.out.println("✓ Editar productos existentes");
        System.out.println("✓ Eliminar productos");
        System.out.println("✓ Gestionar categorías");
        System.out.println("✓ Ver estadísticas de ventas");
        if (miembroConsejoSombrio) {
            System.out.println("✓ ACCESO AL CONSEJO SOMBRÍO");
        }
        System.out.println();
    }

    /**
     * Verifica si tiene un permiso específico
     */
    public boolean tienePermiso(String permiso) {
        return permisosEdicion.contains(permiso);
    }

    /**
     * Agrega un nuevo permiso
     */
    public void agregarPermiso(String permiso) {
        permisosEdicion.add(permiso);
        System.out.println("Permiso '" + permiso + "' agregado");
    }

    /**
     * Remueve un permiso
     */
    public void removerPermiso(String permiso) {
        if (permisosEdicion.remove(permiso)) {
            System.out.println("Permiso '" + permiso + "' removido");
        }
    }

    /**
     * Convierte al administrador en miembro del Consejo Sombrío
     */
    public void ascenderAConsejoSombrio(String claveMaestra) {
        // Solo la Dueña puede ascender miembros
        this.miembroConsejoSombrio = true;
        agregarPermiso("ACCESO_CONSEJO_SOMBRIO");
        System.out.println("⚠ Miembro ascendido al Consejo Sombrío");
    }

    // Getters y Setters
    public Set<String> getPermisosEdicion() {
        return new HashSet<>(permisosEdicion);
    }

    public boolean esMiembroConsejoSombrio() {
        return miembroConsejoSombrio;
    }

    @Override
    public String toString() {
        return String.format("AdministradorContenido[%s, Permisos=%d, Consejo Sombrío=%s]",
                super.toString(), permisosEdicion.size(), miembroConsejoSombrio);
    }
}
