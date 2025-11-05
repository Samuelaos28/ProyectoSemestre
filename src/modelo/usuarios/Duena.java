package modelo.usuarios;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

//Representa a Sakura quien es la dueña en nuestro proyecto
public class Duena extends Usuario {
    private String claveMaestra;
    private LocalDateTime fechaCoronacion;
    private String historiaOculta;

    //Constructor
    public Duena(String nombre, String email, String password, String claveMaestra) {
        super(nombre, email, password, "DUEÑA");
        this.claveMaestra = hashClaveMaestra(claveMaestra);
        this.fechaCoronacion = LocalDateTime.now();
        this.historiaOculta = "Desaparición misteriosa de Isis la de ED - Montañas de Aso, Japón";
    }

    //Hash para la clave maestra
    private String hashClaveMaestra(String clave) {
        return "MASTER_" + Integer.toHexString(clave.hashCode());
    }

    //Verificación de la clave maestra
    public boolean verificarClaveMaestra(String clave) {
        return this.claveMaestra.equals(hashClaveMaestra(clave));
    }


    //Se muestra acceso total al ser la dueña
    @Override
    public void mostrarPermisos() {
        System.out.println();
        System.out.println("CABRITA SAKURA");
        System.out.println("ACCESO TOTAL");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("✓ Control total de usuarios");
        System.out.println("✓ Gestión completa de productos");
        System.out.println("✓ Acceso a registros financieros");
        System.out.println("✓ Administración del Consejo Sombrío");
        System.out.println("✓ ACCESO AL REGISTRO DE ESCLAVOS");
        System.out.println("✓ Control de fábricas clandestinas");
        System.out.println("✓ Planes de biotecnología cosmética");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Lema: \"El maquillaje cubre imperfecciones;");
        System.out.println("       el poder borra obstáculos.\"");
    }

    //Historia de como llego al poder
    public void revelarHistoriaOculta(String claveMaestra) {
        if (verificarClaveMaestra(claveMaestra)) {
            System.out.println(" ARCHIVO CONFIDENCIAL ");
            System.out.println("\n" + historiaOculta);
            System.out.println("\nFecha del incidente: " +
                    fechaCoronacion.minusDays(30).toLocalDate()); //Restamos 30 dias a la fecha original
            System.out.println("Acciones mayoritarias transferidas: " +
                    fechaCoronacion.minusDays(5).toLocalDate());
            System.out.println("Coronación oficial: " +
                    fechaCoronacion.toLocalDate());
            System.out.println("\n Este documento no existe oficialmente.");
        } else {
            System.out.println(" ACCESO DENEGADO - Clave maestra incorrecta");
        }
    }

    //Asciende admin al consejo
    public void agregarAlConsejoSombrio(Usuario admin, String claveMaestra) {
        if (!verificarClaveMaestra(claveMaestra)) {
            System.out.println("Clave maestra incorrecta");
            return;
        }
        if (admin instanceof AdministradorContenido) {
            ((AdministradorContenido) admin).ascenderAConsejoSombrio(claveMaestra);
        } else if (admin instanceof AdministradorUsuario) {
            ((AdministradorUsuario) admin).ascenderAConsejoSombrio(claveMaestra);
        } else {
            System.out.println("Solo administradores pueden unirse al Consejo Sombrío");
        }
    }


    public void iniciarProyectoBiotecnologia(String claveMaestra) {
        if (verificarClaveMaestra(claveMaestra)) {
            System.out.println("\n🧬 Iniciando Proyecto: Elixir de Juventud");
            System.out.println("Estado: EN DESARROLLO");
            System.out.println("Inversión asignada: $50,000,000");
            System.out.println("Patente en trámite: BIOTECH-GLW-2025");
        } else {
            System.out.println("Autorización denegada");
        }
    }


    public LocalDateTime getFechaCoronacion() {
        return fechaCoronacion;
    }

    public String getHistoriaResumida() {
        return "Nueva dueña desde " + fechaCoronacion.toLocalDate() +
                " tras misteriosas circunstancias";
    }


    @Override
    public String toString() {
        return String.format("DUEÑA[%s, Coronación=%s]",
                super.toString(), fechaCoronacion.toLocalDate());
    }


}