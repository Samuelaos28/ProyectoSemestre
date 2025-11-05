package modelo.usuarios;

import java.time.LocalDateTime;
import java.util.UUID;
import modelo.enums.EstadoCuenta;


/*Será una abstract class ya que será la super clase para nuestras clases pertenecientes a
* la carpéta de usuarios */

public abstract class Usuario {
    // Atributos protegidos para que las subclases puedan acceder
    protected String id;
    protected String nombre;
    protected String email;
    protected String passwordHash;
    protected String rol;
    protected LocalDateTime fechaRegistro;
    protected EstadoCuenta estadoCuenta;


    //Constructor del usuario
    public Usuario(String nombre, String email, String password, String rol) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = hashPassword(password);
        this.rol = rol;
        this.fechaRegistro = LocalDateTime.now();
        this.estadoCuenta = EstadoCuenta.ACTIVA;
    }



    // Este metodo convertira la contraseña ingresada en hash
    private String hashPassword(String password) {
        //Se calcula el hashCode
        // Se convierte el número a String
        //Se returna el passwordHash
        return Integer.toString(password.hashCode());
    }


    public boolean verificarPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    //Metodo para definir las acciones específicas de cada usuario
    public abstract void mostrarPermisos();


    //Getters y setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    @Override
    public String toString() {
        return String.format("Usuario[ID=%s, Nombre=%s, Email=%s, Rol=%s, Estado=%s]",
                id, nombre, email, rol, estadoCuenta);
    }
}