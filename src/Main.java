import modelo.usuarios.*;
import modelo.comercial.*;
import modelo.produccion.*;
import modelo.ocultos.*;
import modelo.enums.*;

import java.time.LocalDate;
import java.util.*;

public class Main {
    // Variables globales del sistema
    private static Scanner entrada = new Scanner(System.in);
    private static Map<String, Usuario> listaUsuarios = new HashMap<>();
    private static List<Producto> listaProductos = new ArrayList<>();
    private static List<Categoria> listaCategorias = new ArrayList<>();
    private static List<Compra> listaCompras = new ArrayList<>();
    private static List<Fabrica> listaFabricas = new ArrayList<>();
    private static Usuario usuarioConectado = null;
    private static Duena duenaPrincipal = null;
    private static RegistroEsclavos registroTrabajadores = null;
    private static ConsejoSombrio consejoSecreto = null;

    public static void main(String[] args) {
        cargarDatosIniciales();
        mostrarPantallaBienvenida();

        // Bucle principal del programa
        while (true) {
            if (usuarioConectado == null) {
                mostrarMenuLogin();
            } else {
                mostrarMenuUsuario();
            }
        }
    }

    // Metodo para cargar datos de prueba al iniciar
    private static void cargarDatosIniciales() {
        // Crear categorías
        listaCategorias.add(new Categoria("Iluminadores", "Iluminadores en polvo y líquido"));
        listaCategorias.add(new Categoria("Rubores", "Rubores compactos y en crema"));
        listaCategorias.add(new Categoria("Delineadores", "Delineadores líquidos y en gel"));

        // Crear productos
        listaProductos.add(new Producto("Glow Star", "Iluminador líquido efecto radiante",
                52000, 40, listaCategorias.get(0)));
        listaProductos.add(new Producto("Blush Rosé", "Rubor en crema tono rosado natural",
                38000, 35, listaCategorias.get(1)));
        listaProductos.add(new Producto("Golden Beam", "Iluminador en polvo tono dorado",
                60000, 25, listaCategorias.get(0)));
        listaProductos.add(new Producto("Line Pro Black", "Delineador líquido punta fina resistente al agua",
                45000, 50, listaCategorias.get(2)));

        // Crear duena del sistema
        duenaPrincipal = new Duena("Cabrita Sakura", "sakura@glowup.com", "admin123", "MASTER2024");
        listaUsuarios.put(duenaPrincipal.getEmail(), duenaPrincipal);

        // Crear administradores
        AdministradorContenido adminContenido = new AdministradorContenido("Manuela Rivera",
                "manuela@glowup.com", "Manu.2025");
        listaUsuarios.put(adminContenido.getEmail(), adminContenido);

        AdministradorUsuario adminUsuarios = new AdministradorUsuario("Samuel Acosta",
                "samuel@glowup.com", "samuel321", 3);
        listaUsuarios.put(adminUsuarios.getEmail(), adminUsuarios);

        // Crear cliente de prueba
        Cliente clientePrueba = new Cliente("Camila Mejia", "camila@email.com", "cami2025",
                "Cra 15 #45-21", "3109876543");
        listaUsuarios.put(clientePrueba.getEmail(), clientePrueba);

        // Crear desarrollador
        DesarrolladorProducto desarrollador = new DesarrolladorProducto("Diego Mendez",
                "Diego@glowup.com", "godie123", "Labiales", 5);
        listaUsuarios.put(desarrollador.getEmail(), desarrollador);

        // Crear fabricas
        listaFabricas.add(new Fabrica("Brasil", "Sao Paulo", 1000, 60));
        listaFabricas.add(new Fabrica("Colombia", "Itagui", 800, 50));

        // Crear consejo sombrio
        consejoSecreto = new ConsejoSombrio("OPERACION ELIXIR");
    }

    private static void mostrarPantallaBienvenida() {
        System.out.println("\n==========================================");
        System.out.println("          SISTEMA GLOW UP");
        System.out.println("    Luxury Cosmetics Management System");
        System.out.println("==========================================\n");
    }

    // Menu cuando no hay usuario conectado
    private static void mostrarMenuLogin() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Registrar nuevo cliente");
        System.out.println("3. Salir del sistema");
        System.out.print("Seleccione una opcion: ");

        int opcionElegida = leerNumeroEntero();

        if (opcionElegida == 1) {
            iniciarSesion();
        } else if (opcionElegida == 2) {
            registrarNuevoCliente();
        } else if (opcionElegida == 3) {
            System.out.println("\nGracias por usar Glow Up. Hasta pronto!");
            System.exit(0);
        } else {
            System.out.println("ERROR: Opcion no valida");
        }
    }

    private static void iniciarSesion() {
        System.out.print("\nIngrese su email: ");
        String emailIngresado = entrada.nextLine();
        System.out.print("Ingrese su password: ");
        String passwordIngresado = entrada.nextLine();

        Usuario usuarioEncontrado = listaUsuarios.get(emailIngresado);

        if (usuarioEncontrado != null && usuarioEncontrado.verificarPassword(passwordIngresado)) {
            usuarioConectado = usuarioEncontrado;
            System.out.println("\n¡Bienvenido, " + usuarioEncontrado.getNombre() + "!");
            usuarioEncontrado.mostrarPermisos();
        } else {
            System.out.println("ERROR: Email o password incorrectos");
        }
    }

    private static void registrarNuevoCliente() {
        System.out.println("\n--- REGISTRO DE NUEVO CLIENTE ---");

        System.out.print("Nombre completo: ");
        String nombre = entrada.nextLine();

        System.out.print("Email: ");
        String email = entrada.nextLine();

        System.out.print("Password: ");
        String password = entrada.nextLine();

        System.out.print("Direccion: ");
        String direccion = entrada.nextLine();

        System.out.print("Telefono: ");
        String telefono = entrada.nextLine();

        Cliente nuevoCliente = new Cliente(nombre, email, password, direccion, telefono);
        listaUsuarios.put(email, nuevoCliente);

        System.out.println("\n¡Cliente registrado exitosamente!");
    }

    // Menu principal segun el tipo de usuario
    private static void mostrarMenuUsuario() {
        System.out.println("\n==========================================");
        System.out.println("Usuario: " + usuarioConectado.getNombre() + " [" + usuarioConectado.getRol() + "]");
        System.out.println("==========================================");

        // Mostrar opciones segun el tipo de usuario
        if (usuarioConectado instanceof Cliente) {
            mostrarOpcionesCliente();
        } else if (usuarioConectado instanceof AdministradorContenido) {
            mostrarOpcionesAdminContenido();
        } else if (usuarioConectado instanceof AdministradorUsuario) {
            mostrarOpcionesAdminUsuarios();
        } else if (usuarioConectado instanceof Duena) {
            mostrarOpcionesDuena();
        } else if (usuarioConectado instanceof DesarrolladorProducto) {
            mostrarOpcionesDesarrollador();
        }

        System.out.println("0. Cerrar sesion");
        System.out.print("Seleccione una opcion: ");

        int opcionSeleccionada = leerNumeroEntero();
        ejecutarAccion(opcionSeleccionada);
    }

    private static void mostrarOpcionesCliente() {
        System.out.println("1. Ver catalogo de productos");
        System.out.println("2. Ver mi carrito de compras");
        System.out.println("3. Agregar producto al carrito");
        System.out.println("4. Realizar compra");
        System.out.println("5. Ver historial de compras");
        System.out.println("6. Agregar metodo de pago");
    }

    private static void mostrarOpcionesAdminContenido() {
        System.out.println("1. Ver todos los productos");
        System.out.println("2. Crear nuevo producto");
        System.out.println("3. Editar producto existente");
        System.out.println("4. Ver todas las categorias");
        System.out.println("5. Crear nueva categoria");
    }

    private static void mostrarOpcionesAdminUsuarios() {
        System.out.println("1. Listar todos los usuarios");
        System.out.println("2. Suspender usuario");
        System.out.println("3. Reactivar usuario");
        System.out.println("4. Ver estadisticas del sistema");
    }

    private static void mostrarOpcionesDesarrollador() {
        System.out.println("1. Ver mi portafolio de productos");
        System.out.println("2. Registrar producto desarrollado");
        System.out.println("3. Ver productos disponibles");
    }

    private static void mostrarOpcionesDuena() {
        System.out.println("1. Ver todos los productos");
        System.out.println("2. Ver todos los usuarios");
        System.out.println("3. Ver todas las compras realizadas");
        System.out.println("4. Gestionar Consejo Sombrio");
        System.out.println("5. Ver informacion de fabricas");
        System.out.println("6. Acceder a Registro de Esclavos");
        System.out.println("7. Revelar historia oculta");
        System.out.println("8. Iniciar proyecto biotecnologia");
    }

    private static void ejecutarAccion(int opcion) {
        // Opcion 0 siempre es cerrar sesion
        if (opcion == 0) {
            usuarioConectado = null;
            System.out.println("\nSesion cerrada exitosamente");
            return;
        }

        // Ejecutar segun tipo de usuario
        if (usuarioConectado instanceof Cliente) {
            ejecutarAccionCliente(opcion);
        } else if (usuarioConectado instanceof AdministradorContenido) {
            ejecutarAccionAdminContenido(opcion);
        } else if (usuarioConectado instanceof AdministradorUsuario) {
            ejecutarAccionAdminUsuarios(opcion);
        } else if (usuarioConectado instanceof Duena) {
            ejecutarAccionDuena(opcion);
        } else if (usuarioConectado instanceof DesarrolladorProducto) {
            ejecutarAccionDesarrollador(opcion);
        }
    }

    // Acciones para Cliente
    private static void ejecutarAccionCliente(int opcion) {
        Cliente clienteActual = (Cliente) usuarioConectado;

        switch (opcion) {
            case 1:
                verCatalogoProductos();
                break;
            case 2:
                clienteActual.obtenerCarrito().mostrarContenido();
                break;
            case 3:
                agregarProductoAlCarrito(clienteActual);
                break;
            case 4:
                procesarCompra(clienteActual);
                break;
            case 5:
                verHistorialCompras(clienteActual);
                break;
            case 6:
                agregarMetodoPagoCliente(clienteActual);
                break;
            default:
                System.out.println("ERROR: Opcion no valida");
        }
    }

    // Acciones para Administrador de Contenido
    private static void ejecutarAccionAdminContenido(int opcion) {
        AdministradorContenido admin = (AdministradorContenido) usuarioConectado;

        switch (opcion) {
            case 1:
                verCatalogoProductos();
                break;
            case 2:
                crearNuevoProducto();
                break;
            case 3:
                editarProductoExistente();
                break;
            case 4:
                verListaCategorias();
                break;
            case 5:
                crearNuevaCategoria();
                break;
            default:
                System.out.println("ERROR: Opcion no valida");
        }
    }

    // Acciones para Administrador de Usuarios
    private static void ejecutarAccionAdminUsuarios(int opcion) {
        AdministradorUsuario admin = (AdministradorUsuario) usuarioConectado;

        switch (opcion) {
            case 1:
                verListaUsuarios();
                break;
            case 2:
                suspenderUsuarioDelSistema(admin);
                break;
            case 3:
                reactivarUsuarioDelSistema(admin);
                break;
            case 4:
                verEstadisticasSistema();
                break;
            default:
                System.out.println("ERROR: Opcion no valida");
        }
    }

    // Acciones para Desarrollador
    private static void ejecutarAccionDesarrollador(int opcion) {
        DesarrolladorProducto desarrollador = (DesarrolladorProducto) usuarioConectado;

        switch (opcion) {
            case 1:
                desarrollador.mostrarPortafolio();
                break;
            case 2:
                registrarProductoDelDesarrollador(desarrollador);
                break;
            case 3:
                verCatalogoProductos();
                break;
            default:
                System.out.println("ERROR: Opcion no valida");
        }
    }

    // Acciones para Duena
    private static void ejecutarAccionDuena(int opcion) {
        Duena duena = (Duena) usuarioConectado;

        switch (opcion) {
            case 1:
                verCatalogoProductos();
                break;
            case 2:
                verListaUsuarios();
                break;
            case 3:
                verTodasLasCompras();
                break;
            case 4:
                gestionarConsejoSecreto();
                break;
            case 5:
                verListaFabricas();
                break;
            case 6:
                accederARegistroTrabajadores();
                break;
            case 7:
                System.out.print("\nIngrese la clave maestra: ");
                String clave1 = entrada.nextLine();
                duena.revelarHistoriaOculta(clave1);
                break;
            case 8:
                System.out.print("\nIngrese la clave maestra: ");
                String clave2 = entrada.nextLine();
                duena.iniciarProyectoBiotecnologia(clave2);
                break;
            default:
                System.out.println("ERROR: Opcion no valida");
        }
    }

    // Funciones para ver informacion
    private static void verCatalogoProductos() {
        System.out.println("\n==========================================");
        System.out.println("           CATALOGO DE PRODUCTOS");
        System.out.println("==========================================");

        int contador = 1;
        for (Producto producto : listaProductos) {
            if (producto.isActivo()) {
                System.out.println(contador + ". " + producto.formatoCatalogo());
                contador++;
            }
        }

        System.out.println("==========================================\n");
    }

    private static void agregarProductoAlCarrito(Cliente cliente) {
        verCatalogoProductos();

        System.out.print("Ingrese el numero del producto: ");
        int numeroProducto = leerNumeroEntero() - 1;

        if (numeroProducto >= 0 && numeroProducto < listaProductos.size()) {
            Producto productoSeleccionado = listaProductos.get(numeroProducto);

            System.out.print("Ingrese la cantidad: ");
            int cantidadDeseada = leerNumeroEntero();

            cliente.obtenerCarrito().agregarProducto(productoSeleccionado, cantidadDeseada);
        } else {
            System.out.println("ERROR: Numero de producto invalido");
        }
    }

    private static void procesarCompra(Cliente cliente) {
        Carrito carritoCliente = cliente.obtenerCarrito();

        if (carritoCliente.estaVacio()) {
            System.out.println("ERROR: El carrito esta vacio");
            return;
        }

        carritoCliente.mostrarContenido();

        if (cliente.getMetodosPago().isEmpty()) {
            System.out.println("ERROR: Debe agregar un metodo de pago primero");
            return;
        }

        System.out.println("\n--- METODOS DE PAGO DISPONIBLES ---");
        List<MetodoPago> metodosPago = cliente.getMetodosPago();
        for (int i = 0; i < metodosPago.size(); i++) {
            System.out.println((i + 1) + ". " + metodosPago.get(i).formatoSeleccion());
        }

        System.out.print("\nSeleccione metodo de pago: ");
        int indiceMetodo = leerNumeroEntero() - 1;

        if (indiceMetodo >= 0 && indiceMetodo < metodosPago.size()) {
            Compra nuevaCompra = new Compra(cliente, carritoCliente, metodosPago.get(indiceMetodo));

            if (nuevaCompra.procesarPago()) {
                listaCompras.add(nuevaCompra);
                nuevaCompra.mostrarDetalle();
                cliente.nuevoCarrito();
            }
        } else {
            System.out.println("ERROR: Metodo de pago invalido");
        }
    }

    private static void agregarMetodoPagoCliente(Cliente cliente) {
        System.out.println("\n--- AGREGAR METODO DE PAGO ---");
        System.out.println("1. Tarjeta de Credito");
        System.out.println("2. Tarjeta de Debito");
        System.out.println("3. PayPal");
        System.out.print("Seleccione el tipo: ");

        int tipoSeleccionado = leerNumeroEntero();

        TipoPago tipoPagoElegido;
        if (tipoSeleccionado == 1) {
            tipoPagoElegido = TipoPago.TARJETA_CREDITO;
        } else if (tipoSeleccionado == 2) {
            tipoPagoElegido = TipoPago.TARJETA_DEBITO;
        } else if (tipoSeleccionado == 3) {
            tipoPagoElegido = TipoPago.PAYPAL;
        } else {
            System.out.println("ERROR: Tipo de pago invalido");
            return;
        }

        System.out.print("Nombre del titular: ");
        String nombreTitular = entrada.nextLine();

        System.out.print("Numero (16 digitos): ");
        String numeroTarjeta = entrada.nextLine();

        MetodoPago nuevoMetodo = new MetodoPago(tipoPagoElegido, nombreTitular, numeroTarjeta);
        cliente.agregarMetodoPago(nuevoMetodo);

        System.out.println("Metodo de pago agregado exitosamente!");
    }

    private static void verHistorialCompras(Cliente cliente) {
        System.out.println("\n--- MI HISTORIAL DE COMPRAS ---");

        List<Compra> comprasDelCliente = new ArrayList<>();
        for (Compra compra : listaCompras) {
            if (compra.getCliente().equals(cliente)) {
                comprasDelCliente.add(compra);
            }
        }

        if (comprasDelCliente.isEmpty()) {
            System.out.println("No tiene compras realizadas todavia");
        } else {
            for (Compra compra : comprasDelCliente) {
                System.out.println(compra.formatoResumido());
            }
        }
    }

    private static void crearNuevoProducto() {
        System.out.println("\n--- CREAR NUEVO PRODUCTO ---");

        System.out.print("Nombre del producto: ");
        String nombreProducto = entrada.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = entrada.nextLine();

        System.out.print("Precio: ");
        double precioProducto = leerNumeroDecimal();

        System.out.print("Stock inicial: ");
        int stockInicial = leerNumeroEntero();

        verListaCategorias();
        System.out.print("Numero de categoria: ");
        int indiceCategoria = leerNumeroEntero() - 1;

        if (indiceCategoria >= 0 && indiceCategoria < listaCategorias.size()) {
            Producto productoNuevo = new Producto(nombreProducto, descripcion, precioProducto,
                    stockInicial, listaCategorias.get(indiceCategoria));
            listaProductos.add(productoNuevo);
            System.out.println("\n¡Producto creado exitosamente!");
        } else {
            System.out.println("ERROR: Categoria invalida");
        }
    }

    private static void editarProductoExistente() {
        verCatalogoProductos();

        System.out.print("Numero del producto a editar: ");
        int numeroProducto = leerNumeroEntero() - 1;

        if (numeroProducto >= 0 && numeroProducto < listaProductos.size()) {
            Producto productoAEditar = listaProductos.get(numeroProducto);

            System.out.print("Nuevo precio (actual: " + productoAEditar.getPrecio() + "): ");
            double precioNuevo = leerNumeroDecimal();
            productoAEditar.setPrecio(precioNuevo);

            System.out.print("Nuevo stock (actual: " + productoAEditar.getStock() + "): ");
            int stockNuevo = leerNumeroEntero();
            productoAEditar.setStock(stockNuevo);

            System.out.println("\n¡Producto actualizado exitosamente!");
        } else {
            System.out.println("ERROR: Numero de producto invalido");
        }
    }

    private static void verListaCategorias() {
        System.out.println("\n--- CATEGORIAS DISPONIBLES ---");
        for (int i = 0; i < listaCategorias.size(); i++) {
            System.out.println((i + 1) + ". " + listaCategorias.get(i).getNombre());
        }
    }

    private static void crearNuevaCategoria() {
        System.out.print("\nNombre de la categoria: ");
        String nombreCategoria = entrada.nextLine();

        System.out.print("Descripcion: ");
        String descripcionCategoria = entrada.nextLine();

        listaCategorias.add(new Categoria(nombreCategoria, descripcionCategoria));
        System.out.println("\n¡Categoria creada exitosamente!");
    }

    private static void verListaUsuarios() {
        System.out.println("\n--- USUARIOS DEL SISTEMA ---");
        for (Usuario usuario : listaUsuarios.values()) {
            System.out.println(usuario);
        }
    }

    private static void suspenderUsuarioDelSistema(AdministradorUsuario admin) {
        System.out.print("\nEmail del usuario a suspender: ");
        String emailUsuario = entrada.nextLine();

        Usuario usuarioASuspender = listaUsuarios.get(emailUsuario);

        if (usuarioASuspender != null) {
            admin.suspenderUsuario(usuarioASuspender);
        } else {
            System.out.println("ERROR: Usuario no encontrado");
        }
    }

    private static void reactivarUsuarioDelSistema(AdministradorUsuario admin) {
        System.out.print("\nEmail del usuario a reactivar: ");
        String emailUsuario = entrada.nextLine();

        Usuario usuarioAReactivar = listaUsuarios.get(emailUsuario);

        if (usuarioAReactivar != null) {
            admin.reactivarUsuario(usuarioAReactivar);
        } else {
            System.out.println("ERROR: Usuario no encontrado");
        }
    }

    private static void verEstadisticasSistema() {
        System.out.println("\n--- ESTADISTICAS DEL SISTEMA ---");
        System.out.println("Total de usuarios: " + listaUsuarios.size());

        int contadorClientes = 0;
        int contadorAdmins = 0;

        for (Usuario usuario : listaUsuarios.values()) {
            if (usuario instanceof Cliente) {
                contadorClientes++;
            } else if (usuario instanceof AdministradorContenido ||
                    usuario instanceof AdministradorUsuario) {
                contadorAdmins++;
            }
        }

        System.out.println("Clientes: " + contadorClientes);
        System.out.println("Administradores: " + contadorAdmins);
        System.out.println("Total de compras: " + listaCompras.size());
    }

    private static void registrarProductoDelDesarrollador(DesarrolladorProducto desarrollador) {
        verCatalogoProductos();

        System.out.print("Numero del producto: ");
        int numeroProducto = leerNumeroEntero() - 1;

        if (numeroProducto >= 0 && numeroProducto < listaProductos.size()) {
            desarrollador.agregarProductoDesarrollado(listaProductos.get(numeroProducto));
        } else {
            System.out.println("ERROR: Numero de producto invalido");
        }
    }

    private static void verTodasLasCompras() {
        System.out.println("\n--- TODAS LAS COMPRAS REALIZADAS ---");

        if (listaCompras.isEmpty()) {
            System.out.println("No hay compras registradas en el sistema");
        } else {
            for (Compra compra : listaCompras) {
                System.out.println(compra.formatoResumido());
            }
        }
    }

    private static void gestionarConsejoSecreto() {
        System.out.println("\n--- CONSEJO SOMBRIO ---");
        System.out.println("1. Ver informacion del consejo");
        System.out.println("2. Agregar nuevo miembro");
        System.out.println("3. Realizar reunion");
        System.out.print("Seleccione opcion: ");

        int opcionConsejo = leerNumeroEntero();

        if (opcionConsejo == 1) {
            consejoSecreto.mostrarInformacion();
        } else if (opcionConsejo == 2) {
            System.out.print("Email del administrador: ");
            String emailAdmin = entrada.nextLine();
            Usuario usuarioAdmin = listaUsuarios.get(emailAdmin);

            if (usuarioAdmin != null) {
                System.out.print("Ingrese clave maestra: ");
                String claveMaestra = entrada.nextLine();
                duenaPrincipal.agregarAlConsejoSombrio(usuarioAdmin, claveMaestra);
            } else {
                System.out.println("ERROR: Usuario no encontrado");
            }
        } else if (opcionConsejo == 3) {
            System.out.print("Agenda de la reunion: ");
            String agendaReunion = entrada.nextLine();
            consejoSecreto.realizarReunion(agendaReunion);
        } else {
            System.out.println("ERROR: Opcion invalida");
        }
    }

    private static void verListaFabricas() {
        System.out.println("\n--- FABRICAS REGISTRADAS ---");
        for (Fabrica fabrica : listaFabricas) {
            fabrica.mostrarInformacion(true);
        }
    }

    private static void accederARegistroTrabajadores() {
        System.out.print("\nIngrese la clave maestra: ");
        String claveMaestra = entrada.nextLine();

        if (registroTrabajadores == null) {
            registroTrabajadores = RegistroEsclavos.obtenerInstancia(duenaPrincipal, claveMaestra);
            if (registroTrabajadores == null) {
                return;
            }
        }

        System.out.println("\n--- REGISTRO DE ESCLAVOS ---");
        System.out.println("1. Agregar trabajador");
        System.out.println("2. Listar todos los trabajadores");
        System.out.println("3. Ver estadisticas");
        System.out.print("Seleccione opcion: ");

        int opcionRegistro = leerNumeroEntero();

        if (opcionRegistro == 1) {
            System.out.print("Nombre del trabajador: ");
            String nombreTrabajador = entrada.nextLine();

            System.out.print("Pais de origen: ");
            String paisOrigen = entrada.nextLine();

            System.out.print("Edad: ");
            int edadTrabajador = leerNumeroEntero();

            System.out.println("\n--- Fabricas disponibles ---");
            for (int i = 0; i < listaFabricas.size(); i++) {
                System.out.println((i + 1) + ". " + listaFabricas.get(i).getId());
            }

            System.out.print("Numero de fabrica: ");
            int indiceFabrica = leerNumeroEntero() - 1;

            if (indiceFabrica >= 0 && indiceFabrica < listaFabricas.size()) {
                TrabajadorEsclavizado nuevoTrabajador = new TrabajadorEsclavizado(
                        nombreTrabajador, paisOrigen, edadTrabajador,
                        listaFabricas.get(indiceFabrica).getId()
                );
                registroTrabajadores.agregarTrabajador(nuevoTrabajador, duenaPrincipal, claveMaestra);
                listaFabricas.get(indiceFabrica).asignarTrabajador(nuevoTrabajador.getId(), true);
                System.out.println("\nTrabajador agregado exitosamente!");
            } else {
                System.out.println("ERROR: Numero de fabrica invalido");
            }

        } else if (opcionRegistro == 2) {
            List<TrabajadorEsclavizado> listaTrabajadores = registroTrabajadores.listarTodos(
                    duenaPrincipal, claveMaestra
            );
            System.out.println("\nTotal de trabajadores: " + listaTrabajadores.size());
            for (TrabajadorEsclavizado trabajador : listaTrabajadores) {
                System.out.println(trabajador.formatoResumido());
            }

        } else if (opcionRegistro == 3) {
            registroTrabajadores.mostrarEstadisticas(duenaPrincipal, claveMaestra);

        } else {
            System.out.println("ERROR: Opcion invalida");
        }
    }

    // Funciones auxiliares para leer datos
    private static int leerNumeroEntero() {
        try {
            String textoIngresado = entrada.nextLine();
            return Integer.parseInt(textoIngresado);
        } catch (NumberFormatException error) {
            System.out.println("ERROR: Debe ingresar un numero entero valido");
            return -1;
        }
    }

    private static double leerNumeroDecimal() {
        try {
            String textoIngresado = entrada.nextLine();
            return Double.parseDouble(textoIngresado);
        } catch (NumberFormatException error) {
            System.out.println("ERROR: Debe ingresar un numero decimal valido");
            return 0.0;
        }
    }
}