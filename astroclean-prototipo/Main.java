import java.io.*;
import java.util.Scanner;

public class Main {
    // Estructuras de Juan David (Destino)
    static Destino[] arrDestinos = new Destino[50];
    static int contadorDestinos = 0;
    static PilaDestino pilaDestinos = new PilaDestino(50);

    // Estructuras de Jean Paul (Servicio_Destino)
    static ServicioDestino[] arrServicios = new ServicioDestino[50];
    static int contadorServicios = 0;
    static PilaServicio pilaServicios = new PilaServicio(50);

    public static void main(String[] args) {
        cargarArchivos();
        
        // Precarga automática de datos de prueba si el archivo está vacío
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
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

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
                        System.out.println("Opción inválida.");
                }
            } else {
                System.out.println("Por favor ingrese un número válido.");
                scanner.nextLine();
            }
        } while (opcion != 0);

        scanner.close();
    }

    // ==========================================
    // DATOS DE PRUEBA INICIALES
    // ==========================================
    static void precargarDatosDePrueba() {
        arrDestinos[contadorDestinos++] = new Destino("DEST-001", "Reserva Natural El Retiro", "El Retiro", "Oriente", 6.0592, -75.5034, "Activo");
        arrDestinos[contadorDestinos++] = new Destino("DEST-002", "Parque Ecológico Piedras Blancas", "Medellín", "Valle de Aburrá", 6.2913, -75.5042, "Activo");
        arrDestinos[contadorDestinos++] = new Destino("DEST-003", "Alto del Capiro", "La Ceja", "Oriente", 5.9781, -75.4265, "Activo");

        arrServicios[contadorServicios++] = new ServicioDestino("SERV-001", "DEST-001", true, true, false);
        arrServicios[contadorServicios++] = new ServicioDestino("SERV-002", "DEST-002", true, true, true);
        
        System.out.println("Se han precargado 3 destinos y 2 registros de servicio de prueba.");
    }

    // ==========================================
    // MENÚ Y OPERACIONES DE DESTINO (Juan David)
    // ==========================================
    static void menuDestinos(Scanner scanner) {
        int op = -1;
        do {
            System.out.println("\n--- GESTIÓN DE DESTINOS ---");
            System.out.println("1. Crear Destino");
            System.out.println("2. Consultar Destino por ID");
            System.out.println("3. Buscar Destino por Texto (Nombre/Municipio)");
            System.out.println("4. Actualizar Destino");
            System.out.println("5. Eliminar Destino");
            System.out.println("6. Listar Destinos y Totalizar");
            System.out.println("7. Deshacer última eliminación de Destino");
            System.out.println("0. Volver al menú principal");
            System.out.print("Opción: ");

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
                    default: System.out.println("Opción no válida.");
                }
            } else {
                scanner.nextLine();
            }
        } while (op != 0);
    }

    // C - Crear Destino con validación de ID repetido
    static boolean crearDestino(Destino d) {
        if (buscarDestinoPorId(d.getIdDestino()) != null) {
            System.out.println("Error: El ID del destino ya existe.");
            return false;
        }
        if (contadorDestinos >= arrDestinos.length) {
            System.out.println("Error: Catálogo lleno.");
            return false;
        }
        arrDestinos[contadorDestinos] = d;
        contadorDestinos++;
        System.out.println("Destino registrado con éxito.");
        return true;
    }

    // R - Consultar por ID
    static Destino buscarDestinoPorId(String id) {
        for (int i = 0; i < contadorDestinos; i++) {
            if (arrDestinos[i].getIdDestino().equalsIgnoreCase(id)) {
                return arrDestinos[i];
            }
        }
        return null;
    }

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

    static void crearDestinoUI(Scanner sc) {
        System.out.print("ID (ej. DEST-004): "); String id = sc.nextLine();
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Municipio: "); String mun = sc.nextLine();
        System.out.print("Subregión: "); String sub = sc.nextLine();
        System.out.print("Latitud: "); double lat = sc.nextDouble();
        System.out.print("Longitud: "); double lon = sc.nextDouble();
        sc.nextLine();
        System.out.print("Estado (Activo/Desactivado): "); String est = sc.nextLine();
        crearDestino(new Destino(id, nom, mun, sub, lat, lon, est));
    }

    // U - Actualizar Destino
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

    // D - Eliminar con desplazamiento a la izquierda (sin huecos) y apilado
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

        // Desplazar a la izquierda
        for (int i = index; i < contadorDestinos - 1; i++) {
            arrDestinos[i] = arrDestinos[i + 1];
        }
        arrDestinos[contadorDestinos - 1] = null;
        contadorDestinos--;
        System.out.println("Destino eliminado y respaldado en pila para deshacer.");
    }

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
    // MENÚ Y OPERACIONES DE SERVICIOS (Jean Paul)
    // ==========================================
    static void menuServicios(Scanner scanner) {
        int op = -1;
        do {
            System.out.println("\n--- GESTIÓN DE SERVICIOS DE DESTINO ---");
            System.out.println("1. Registrar Servicios (Valida contra Destino)");
            System.out.println("2. Consultar Servicios por ID");
            System.out.println("3. Listar Servicios");
            System.out.println("4. Eliminar Registro de Servicios");
            System.out.println("5. Deshacer eliminación de Servicios");
            System.out.println("0. Volver al menú principal");
            System.out.print("Opción: ");

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
                    default: System.out.println("Opción no válida.");
                }
            } else {
                scanner.nextLine();
            }
        } while (op != 0);
    }

    // PUNTO DE CONTACTO OBLIGATORIO: Valida que el ID del destino exista en el módulo de Juan David
    static void crearServicioUI(Scanner sc) {
        System.out.print("ID del Registro de Servicios (ej. SERV-003): "); String idS = sc.nextLine();
        System.out.print("ID del Destino asociado: "); String idD = sc.nextLine();

        // VALIDACIÓN CRUZADA OBLIGATORIA
        Destino destinoAsociado = buscarDestinoPorId(idD);
        if (destinoAsociado == null) {
            System.out.println("Error (RN-10): No se puede registrar servicios. El ID de destino no existe en el catálogo.");
            return;
        }

        System.out.print("¿Tiene camping? (true/false): "); boolean camp = sc.nextBoolean();
        System.out.print("¿Tiene baños? (true/false): "); boolean ban = sc.nextBoolean();
        System.out.print("¿Tiene parqueadero? (true/false): "); boolean par = sc.nextBoolean();
        sc.nextLine();

        if (contadorServicios < arrServicios.length) {
            arrServicios[contadorServicios] = new ServicioDestino(idS, idD, camp, ban, par);
            contadorServicios++;
            System.out.println("Servicios registrados exitosamente para el destino: " + destinoAsociado.getNombre());
        }
    }

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