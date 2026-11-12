import java.io.*;
import java.util.Scanner;

/**
 * Clase principal del sistema AstroClean. Administra la ejecucion por consola,
 * el flujo de navegacion entre los modulos de Destinos y Servicios de Destino,
 * la persistencia en archivos de texto plano y la precarga de datos iniciales.
 * 
 * @author Juan David Orozco Carmona
 * @author Jean Paul Ospina Medrano
 * @version 1.0
 */
public class Main {
    // Estructuras de Juan David (Destino)
    static Destino[] arrDestinos = new Destino[50];
    static int contadorDestinos = 0;
    static PilaDestino pilaDestinos = new PilaDestino(50);

    // Estructuras de Jean Paul (Servicio_Destino)
    static ServicioDestino[] arrServicios = new ServicioDestino[50];
    static int contadorServicios = 0;
    static PilaServicio pilaServicios = new PilaServicio(50);

    /**
     * Metodo principal (entry point) que inicializa la carga de archivos,
     * verifica la precarga de datos y controla el ciclo de vida del menu en consola.
     * 
     * @param args Argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        cargarArchivos();
        
        // Precarga automatica de datos de prueba si el archivo esta vacio
        if (contadorDestinos == 0) {
            precargarDatosDePrueba();
        }

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA ASTROCLEAN - MENU PRINCIPAL");
            System.out.println("========================================");
            System.out.println("1. Gestionar Destinos (Juan David)");
            System.out.println("2. Gestionar Servicios de Destino (Jean Paul)");
            System.out.println("0. Guardar y Salir");
            System.out.print("Seleccione una opcion: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        menuDestinos(scanner);
                        break;
                    case 2:
                        menuServicios(scanner);
                        break;
                    case 0:
                        guardarArchivos();
                        System.out.println("Datos guardados correctamente. Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } else {
                System.out.println("Por favor ingrese un numero valido.");
                scanner.nextLine();
            }
        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Precarga automaticamente datos de ejemplo en Antioquia para asegurar que
     * la aplicacion cuente con informacion inicial disponible al ejecutarse.
     */
    static void precargarDatosDePrueba() {
        arrDestinos[contadorDestinos++] = new Destino("DEST-001", "Reserva Natural El Retiro", "El Retiro", "Oriente", 6.0592, -75.5034, "Activo");
        arrDestinos[contadorDestinos++] = new Destino("DEST-002", "Parque Ecologico Piedras Blancas", "Medellin", "Valle de Aburra", 6.2913, -75.5042, "Activo");
        arrDestinos[contadorDestinos++] = new Destino("DEST-003", "Alto del Capiro", "La Ceja", "Oriente", 5.9781, -75.4265, "Activo");

        arrServicios[contadorServicios++] = new ServicioDestino("SERV-001", "DEST-001", true, true, false);
        arrServicios[contadorServicios++] = new ServicioDestino("SERV-002", "DEST-002", true, true, true);
        
        System.out.println("Se han precargado 3 destinos y 2 registros de servicio de prueba.");
    }

    /**
     * Despliega el sub menu para la gestion completa de la entidad Destino.
     * 
     * @param scanner Objeto Scanner para la captura de entradas por consola.
     */
    static void menuDestinos(Scanner scanner) {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE DESTINOS ---");
            System.out.println("1. Crear Destino");
            System.out.println("2. Consultar Destino por ID");
            System.out.println("3. Buscar Destino por Texto (Nombre/Municipio)");
            System.out.println("4. Actualizar Destino");
            System.out.println("5. Eliminar Destino");
            System.out.println("6. Listar Destinos y Totalizar");
            System.out.println("7. Deshacer ultima eliminacion de Destino");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine();

                switch (op) {
                    case 1: crearDestinoUI(scanner); break;
                    case 2: consultarDestinoUI(scanner); break;
                    case 3: buscarDestinoUI(scanner); break;
                    case 4: actualizarDestinoUI(scanner); break;
                    case 5: eliminarDestinoUI(scanner); break;
                    case 6: listarDestinosUI(); break;
                    case 7: deshacerDestinoUI(); break;
                    case 0: break;
                    default: System.out.println("Opcion no valida.");
                }
            } else {
                scanner.nextLine();
            }
        } while (op != 0);
    }

    /**
     * RF-079 · CA-27.1 — Registra un nuevo destino validando que el identificador unico 
     * no se encuentre repetido y que el arreglo no rebase su capacidad maxima.
     * 
     * @param d El objeto Destino que se desea registrar.
     * @return true si se creo con exito, false si fallo la validacion.
     */
    static boolean crearDestino(Destino d) {
        if (buscarDestinoPorId(d.getIdDestino()) != null) {
            System.out.println("Error: El ID del destino ya existe.");
            return false;
        }
        if (contadorDestinos >= arrDestinos.length) {
            System.out.println("Error: Catalogo lleno.");
            return false;
        }
        arrDestinos[contadorDestinos] = d;
        contadorDestinos++;
        System.out.println("Destino registrado con exito.");
        return true;
    }

    /**
     * RF-001 · CA-01.1 — Recorre el arreglo linealmente hasta el contador actual 
     * buscando coincidencias con el identificador unico.
     * 
     * @param id Identificador del destino a buscar.
     * @return El objeto Destino encontrado, o null si no existe.
     */
    static Destino buscarDestinoPorId(String id) {
        for (int i = 0; i < contadorDestinos; i++) {
            if (arrDestinos[i].getIdDestino().equalsIgnoreCase(id)) {
                return arrDestinos[i];
            }
        }
        return null;
    }

    /**
     * Gestiona la interfaz de usuario por consola para consultar un destino por su ID.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void consultarDestinoUI(Scanner sc) {
        System.out.print("Ingrese el ID del destino: ");
        String id = sc.nextLine();
        Destino d = buscarDestinoPorId(id);
        if (d != null) {
            System.out.println(d);
        } else {
            System.out.println("Destino no encontrado.");
        }
    }

    /**
     * RF-010 · CA-04.1 — Realiza una busqueda por texto parcial sobre el nombre o 
     * municipio, informando adecuadamente si no se encuentran resultados.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void buscarDestinoUI(Scanner sc) {
        System.out.print("Texto a buscar (nombre o municipio): ");
        String texto = sc.nextLine().toLowerCase();
        boolean encontrado = false;
        for (int i = 0; i < contadorDestinos; i++) {
            if (arrDestinos[i].getNombre().toLowerCase().contains(texto) || 
                arrDestinos[i].getMunicipio().toLowerCase().contains(texto)) {
                System.out.println(arrDestinos[i]);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No se encontraron coincidencias.");
    }

    /**
     * Captura los datos ingresados por el usuario para instanciar y crear un destino.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void crearDestinoUI(Scanner sc) {
        System.out.print("ID (ej. DEST-004): "); String id = sc.nextLine();
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Municipio: "); String mun = sc.nextLine();
        System.out.print("Subregion/Zona: "); String sub = sc.nextLine();
        System.out.print("Latitud: "); double lat = sc.nextDouble();
        System.out.print("Longitud: "); double lon = sc.nextDouble();
        sc.nextLine();
        System.out.print("Estado (Activo/Desactivado): "); String est = sc.nextLine();
        crearDestino(new Destino(id, nom, mun, sub, lat, lon, est));
    }

    /**
     * RF-085 · CA-29.1 — Actualiza los campos permitidos de un destino existente 
     * reemplazando los valores directamente en su casilla correspondiente.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void actualizarDestinoUI(Scanner sc) {
        System.out.print("Ingrese ID del destino a actualizar: ");
        String id = sc.nextLine();
        Destino d = buscarDestinoPorId(id);
        if (d == null) {
            System.out.println("Destino no encontrado.");
            return;
        }
        System.out.print("Nuevo Nombre (" + d.getNombre() + "): ");
        String nom = sc.nextLine(); if(!nom.isEmpty()) d.setNombre(nom);
        System.out.print("Nuevo Municipio (" + d.getMunicipio() + "): ");
        String mun = sc.nextLine(); if(!mun.isEmpty()) d.setMunicipio(mun);
        System.out.println("Destino actualizado correctamente.");
    }

    /**
     * RF-005 · CA-01.2 — Elimina un destino localizando su indice, respaldando el objeto 
     * en la pila propia y desplazando los elementos restantes a la izquierda para evitar huecos.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void eliminarDestinoUI(Scanner sc) {
        System.out.print("Ingrese ID del destino a eliminar: ");
        String id = sc.nextLine();
        int index = -1;
        for (int i = 0; i < contadorDestinos; i++) {
            if (arrDestinos[i].getIdDestino().equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Destino no encontrado.");
            return;
        }
        Destino eliminado = arrDestinos[index];
        pilaDestinos.apilar(eliminado);

        // Desplazamiento a la izquierda (sin dejar nulos intermedios)
        for (int i = index; i < contadorDestinos - 1; i++) {
            arrDestinos[i] = arrDestinos[i + 1];
        }
        arrDestinos[contadorDestinos - 1] = null;
        contadorDestinos--;
        System.out.println("Destino eliminado y respaldado en pila para deshacer.");
    }

    /**
     * RF-001 · RNF-01 — Muestra en consola todos los destinos activos hasta el contador 
     * actual y presenta el totalizador de registros.
     */
    static void listarDestinosUI() {
        if (contadorDestinos == 0) {
            System.out.println("No hay destinos registrados.");
            return;
        }
        System.out.println("\n--- LISTADO DE DESTINOS ---");
        for (int i = 0; i < contadorDestinos; i++) {
            System.out.println(arrDestinos[i]);
        }
        System.out.println("Total de destinos: " + contadorDestinos);
    }

    /**
     * RF-005 — Extrae el ultimo elemento almacenado en la pila propia de eliminaciones 
     * y lo reinserta al final del arreglo, incrementando el contador.
     */
    static void deshacerDestinoUI() {
        if (pilaDestinos.estaVacia()) {
            System.out.println("No hay eliminaciones recientes para deshacer.");
            return;
        }
        Destino recuperado = pilaDestinos.desapilar();
        if (contadorDestinos < arrDestinos.length) {
            arrDestinos[contadorDestinos] = recuperado;
            contadorDestinos++;
            System.out.println("Se ha restaurado el destino: " + recuperado.getNombre());
        }
    }


    // ==========================================
    // MENU Y OPERACIONES DE SERVICIOS (Jean Paul)
    // ==========================================

    /**
     * Despliega el sub menu para la gestion de la entidad ServicioDestino.
     * 
     * @param scanner Objeto Scanner para la captura de entradas por consola.
     */
    static void menuServicios(Scanner scanner) {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE SERVICIOS DE DESTINO ---");
            System.out.println("1. Registrar Servicios (Valida contra Destino)");
            System.out.println("2. Consultar Servicios por ID");
            System.out.println("3. Listar Servicios");
            System.out.println("4. Eliminar Registro de Servicios");
            System.out.println("5. Deshacer eliminacion de Servicios");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine();

                switch (op) {
                    case 1: crearServicioUI(scanner); break;
                    case 2: consultarServicioUI(scanner); break;
                    case 3: listarServiciosUI(); break;
                    case 4: eliminarServicioUI(scanner); break;
                    case 5: deshacerServicioUI(); break;
                    case 0: break;
                    default: System.out.println("Opcion no valida.");
                }
            } else {
                scanner.nextLine();
            }
        } while (op != 0);
    }

    /**
     * RN-10 · RF-073 — Implementa el punto de contacto obligatorio: valida llamando 
     * a la funcion de busqueda del estudiante A que el ID de destino exista antes de permitir el registro.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void crearServicioUI(Scanner sc) {
        System.out.print("ID del Registro de Servicios (ej. SERV-003): "); String idS = sc.nextLine();
        System.out.print("ID del Destino asociado: "); String idD = sc.nextLine();

        // VALIDACION CRUZADA OBLIGATORIA (Punto de contacto)
        Destino destinoAsociado = buscarDestinoPorId(idD);
        if (destinoAsociado == null) {
            System.out.println("Error (RN-10): No se puede registrar servicios. El ID de destino no existe en el catalogo.");
            return;
        }

        System.out.print("¿Tiene camping? (true/false): "); boolean camp = sc.nextBoolean();
        System.out.print("¿Tiene banos? (true/false): "); boolean ban = sc.nextBoolean();
        System.out.print("¿Tiene parqueadero? (true/false): "); boolean par = sc.nextBoolean();
        sc.nextLine();

        if (contadorServicios < arrServicios.length) {
            arrServicios[contadorServicios] = new ServicioDestino(idS, idD, camp, ban, par);
            contadorServicios++;
            System.out.println("Servicios registrados exitosamente para el destino: " + destinoAsociado.getNombre());
        }
    }

    /**
     * RF-073 — Consulta y muestra un registro de servicios a partir de su identificador unico.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void consultarServicioUI(Scanner sc) {
        System.out.print("Ingrese ID de servicio: ");
        String id = sc.nextLine();
        for (int i = 0; i < contadorServicios; i++) {
            if (arrServicios[i].getIdServicio().equalsIgnoreCase(id)) {
                System.out.println(arrServicios[i]);
                return;
            }
        }
        System.out.println("Registro de servicios no encontrado.");
    }

    /**
     * RF-025 · RNF-01 — Muestra todos los registros de servicios activos en el arreglo estatico.
     */
    static void listarServiciosUI() {
        if (contadorServicios == 0) {
            System.out.println("No hay servicios registrados.");
            return;
        }
        System.out.println("\n--- LISTADO DE SERVICIOS ---");
        for (int i = 0; i < contadorServicios; i++) {
            System.out.println(arrServicios[i]);
        }
    }

    /**
     * RF-025 — Elimina un registro de servicios localizandolo, respaldandolo en la pila 
     * y desplazando los elementos siguientes hacia la izquierda.
     * 
     * @param sc Objeto Scanner para lectura de datos.
     */
    static void eliminarServicioUI(Scanner sc) {
        System.out.print("Ingrese ID de servicio a eliminar: ");
        String id = sc.nextLine();
        int index = -1;
        for (int i = 0; i < contadorServicios; i++) {
            if (arrServicios[i].getIdServicio().equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Registro no encontrado.");
            return;
        }
        pilaServicios.apilar(arrServicios[index]);
        for (int i = index; i < contadorServicios - 1; i++) {
            arrServicios[i] = arrServicios[i + 1];
        }
        arrServicios[contadorServicios - 1] = null;
        contadorServicios--;
        System.out.println("Servicio eliminado y respaldado en pila.");
    }

    /**
     * RF-025 — Restaura el ultimo registro de servicios eliminado empleando la pila propia.
     */
    static void deshacerServicioUI() {
        if (pilaServicios.estaVacia()) {
            System.out.println("No hay eliminaciones de servicios para deshacer.");
            return;
        }
        ServicioDestino recuperado = pilaServicios.desapilar();
        if (contadorServicios < arrServicios.length) {
            arrServicios[contadorServicios] = recuperado;
            contadorServicios++;
            System.out.println("Servicio restaurado correctamente.");
        }
    }


    // ==========================================
    // PERSISTENCIA EN ARCHIVOS DE TEXTO (.txt)
    // ==========================================

    /**
     * RNF-03 — Guarda los datos actuales en archivos de texto plano separados por punto y coma 
     * al momento de salir de la aplicacion.
     */
    static void guardarArchivos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("destinos.txt"))) {
            for (int i = 0; i < contadorDestinos; i++) {
                pw.println(arrDestinos[i].aFormatoArchivo());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar destinos: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("servicios.txt"))) {
            for (int i = 0; i < contadorServicios; i++) {
                pw.println(arrServicios[i].aFormatoArchivo());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar servicios: " + e.getMessage());
        }
    }

    /**
     * RNF-03 — Carga los archivos de texto al iniciar la aplicacion, reconstruyendo 
     * los objetos en los arreglos y posicionando los contadores respectivos.
     */
    static void cargarArchivos() {
        File f1 = new File("destinos.txt");
        if (f1.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f1))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] p = linea.split(";");
                    if (p.length == 7) {
                        arrDestinos[contadorDestinos++] = new Destino(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]), Double.parseDouble(p[5]), p[6]);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al cargar destinos.");
            }
        }

        File f2 = new File("servicios.txt");
        if (f2.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f2))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] p = linea.split(";");
                    if (p.length == 5) {
                        arrServicios[contadorServicios++] = new ServicioDestino(p[0], p[1], Boolean.parseBoolean(p[2]), Boolean.parseBoolean(p[3]), Boolean.parseBoolean(p[4]));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al cargar servicios.");
            }
        }
    }
}