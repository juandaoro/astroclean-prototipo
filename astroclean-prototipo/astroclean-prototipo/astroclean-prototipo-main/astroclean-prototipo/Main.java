import java.io.*;              // Importa las clases necesarias para leer y escribir archivos.
import java.util.Scanner;      // Importa Scanner para recibir datos escritos por el usuario.

/**
 * Clase principal del sistema AstroClean.
 *
 * Se encarga de iniciar la aplicación, mostrar los menús, dirigir las operaciones
 * de destinos y servicios, y guardar o recuperar la información mediante archivos
 * de texto plano.
 *
 * También mantiene los arreglos, los contadores y las pilas utilizados por ambos
 * módulos.
 *
 * @author Juan David Orozco Carmona
 * @author Jean Paul Ospina Medrano
 * @version 1.0
 */
public class Main {

    // ================================================================
    // ESTRUCTURAS DEL MÓDULO DE DESTINOS
    // ================================================================

    /**
     * Arreglo que almacena los objetos Destino.
     * Tiene una capacidad máxima de 50 posiciones.
     * Al principio, sus posiciones contienen null porque aún no hay objetos.
     */
    static Destino[] arrDestinos = new Destino[50];

    /**
     * Cuenta cuántos destinos están registrados actualmente en arrDestinos.
     * No representa la capacidad del arreglo, sino la cantidad de posiciones ocupadas.
     */
    static int contadorDestinos = 0;

    /**
     * Pila que respalda los destinos eliminados para permitir recuperar
     * el último destino eliminado.
     */
    static PilaDestino pilaDestinos = new PilaDestino(50);


    // ================================================================
    // ESTRUCTURAS DEL MÓDULO DE SERVICIOS
    // ================================================================

    /**
     * Arreglo que almacena los objetos ServicioDestino.
     * Tiene capacidad para 50 registros de servicios.
     */
    static ServicioDestino[] arrServicios = new ServicioDestino[50];

    /**
     * Cuenta cuántos servicios están registrados actualmente en arrServicios.
     */
    static int contadorServicios = 0;

    /**
     * Pila que conserva los servicios eliminados para permitir deshacer
     * la última eliminación realizada.
     */
    static PilaServicio pilaServicios = new PilaServicio(50);


    // ================================================================
    // MÉTODO PRINCIPAL Y MENÚ PRINCIPAL
    // ================================================================

    /**
     * Punto de entrada del programa.
     *
     * Primero carga los datos que existan en los archivos de texto. Si no hay
     * destinos registrados, precarga información de prueba. Después muestra
     * el menú principal hasta que el usuario seleccione la opción 0.
     *
     * @param args argumentos que se podrían recibir desde la línea de comandos.
     */
    public static void main(String[] args) {

        // Recupera los destinos y servicios guardados en ejecuciones anteriores.
        cargarArchivos();

        // Si no se cargó ningún destino, agrega registros de ejemplo para probar el sistema.
        if (contadorDestinos == 0) {
            precargarDatosDePrueba();
        }

        // Crea el lector de entradas que utilizarán los menús.
        Scanner scanner = new Scanner(System.in);

        // Valor inicial de la opción. El ciclo se ejecutará al menos una vez.
        int opcion = -1;

        // El menú se repite mientras el usuario no seleccione la opción 0.
        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA ASTROCLEAN - MENU PRINCIPAL");
            System.out.println("========================================");
            System.out.println("1. Gestionar Destinos");
            System.out.println("2. Gestionar Servicios de Destino");
            System.out.println("0. Guardar y Salir");
            System.out.print("Seleccione una opcion: ");

            // Verifica que el dato ingresado sea un número entero antes de leerlo.
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();  // Lee el entero y lo guarda en opcion.
                scanner.nextLine();          // Consume el salto de línea que deja Enter.

                // Ejecuta la operación que corresponde al número seleccionado.
                switch (opcion) {
                    case 1:
                        // Abre el submenú para administrar destinos.
                        menuDestinos(scanner);
                        break; // Sale del switch.
                    case 2:
                        // Abre el submenú para administrar servicios.
                        menuServicios(scanner);
                        break; // Sale del switch.
                    case 0:
                        // Guarda los datos antes de finalizar la aplicación.
                        guardarArchivos();
                        System.out.println("Datos guardados correctamente. Saliendo del sistema...");
                        break;
                    default:
                        // Se ejecuta cuando el entero no coincide con 0, 1 o 2.
                        System.out.println("Opcion invalida.");
                }
            } else {
                // Informa que el dato no fue un entero y descarta esa entrada.
                System.out.println("Por favor ingrese un numero valido.");
                scanner.nextLine();
            }
        } while (opcion != 0);

        // Cierra el Scanner al terminar el programa.
        scanner.close();
    }


    // ================================================================
    // PRECARGA DE INFORMACIÓN DE PRUEBA
    // ================================================================

    /**
     * Agrega automáticamente tres destinos y dos registros de servicios de ejemplo.
     *
     * Se utiliza para que el programa tenga información inicial cuando no se han
     * cargado destinos desde el archivo destinos.txt.
     */
    static void precargarDatosDePrueba() {

        // Crea un objeto Destino y lo guarda en la posición actual del arreglo.
        // contadorDestinos++ utiliza primero el valor actual y después lo incrementa.
        arrDestinos[contadorDestinos++] = new Destino("DEST-001", "Reserva Natural El Retiro", "El Retiro", "Oriente", 6.0592, -75.5034, "Activo");
        arrDestinos[contadorDestinos++] = new Destino("DEST-002", "Parque Ecologico Piedras Blancas", "Medellin", "Valle de Aburra", 6.2913, -75.5042, "Activo");
        arrDestinos[contadorDestinos++] = new Destino("DEST-003", "Alto del Capiro", "La Ceja", "Oriente", 5.9781, -75.4265, "Activo");

        // Crea dos objetos ServicioDestino y los almacena en el arreglo de servicios.
        // Cada servicio contiene su propio ID y el ID del destino al que está asociado.
        arrServicios[contadorServicios++] = new ServicioDestino("SERV-001", "DEST-001", true, true, false);
        arrServicios[contadorServicios++] = new ServicioDestino("SERV-002", "DEST-002", true, true, true);

        // Confirma en la consola que se cargaron los registros de ejemplo.
        System.out.println("Se han precargado 3 destinos y 2 registros de servicio de prueba.");
    }


    // ================================================================
    // MENÚ DEL MÓDULO DE DESTINOS
    // ================================================================

    /**
     * Muestra el submenú de gestión de destinos y ejecuta la opción seleccionada.
     *
     * @param scanner objeto Scanner compartido con el método main para leer entradas.
     */
    static void menuDestinos(Scanner scanner) {

        // Guarda la opción elegida dentro de este submenú.
        int op = -1;

        // Repite el menú hasta que el usuario seleccione 0 para volver.
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

            // Comprueba que el usuario haya escrito un número entero.
            if (scanner.hasNextInt()) {
                op = scanner.nextInt(); // Lee la opción seleccionada.
                scanner.nextLine();     // Consume el Enter pendiente.

                // Dirige el programa al método correspondiente a la opción.
                switch (op) {
                    case 1: crearDestinoUI(scanner); break;       // Registra un destino.
                    case 2: consultarDestinoUI(scanner); break;   // Consulta por ID.
                    case 3: buscarDestinoUI(scanner); break;      // Busca por nombre o municipio.
                    case 4: actualizarDestinoUI(scanner); break;  // Modifica datos permitidos.
                    case 5: eliminarDestinoUI(scanner); break;    // Elimina un destino.
                    case 6: listarDestinosUI(); break;            // Muestra todos los destinos.
                    case 7: deshacerDestinoUI(); break;            // Recupera el último eliminado.
                    case 0: break;                                // Sale del switch y permite terminar el ciclo.
                    default: System.out.println("Opcion no valida."); // Opción entera no contemplada.
                }
            } else {
                // Descarta la entrada cuando no es un entero.
                scanner.nextLine();
            }
        } while (op != 0);
    }


    // ================================================================
    // OPERACIONES INTERNAS DE DESTINOS
    // ================================================================

    /**
     * Registra un objeto Destino en el arreglo.
     *
     * Antes de agregarlo, comprueba que su ID no esté repetido y que el arreglo
     * tenga espacio disponible.
     *
     * @param d objeto Destino que se desea registrar.
     * @return true si se registra correctamente; false si alguna validación falla.
     */
    static boolean crearDestino(Destino d) {

        // Busca si ya existe un destino con el mismo identificador.
        // Si la búsqueda devuelve un objeto distinto de null, el ID está repetido.
        if (buscarDestinoPorId(d.getIdDestino()) != null) {
            System.out.println("Error: El ID del destino ya existe.");
            return false; // Detiene el método e informa que no se registró.
        }

        // Comprueba que el contador no haya llegado a la capacidad del arreglo.
        if (contadorDestinos >= arrDestinos.length) {
            System.out.println("Error: Catalogo lleno.");
            return false; // No hay una posición disponible.
        }

        // Guarda el objeto en la siguiente posición libre del arreglo.
        arrDestinos[contadorDestinos] = d;

        // Actualiza la cantidad de destinos registrados.
        contadorDestinos++;

        System.out.println("Destino registrado con exito.");
        return true; // Informa que el registro fue exitoso.
    }


    /**
     * Busca un destino mediante su identificador único.
     *
     * @param id identificador que se desea localizar.
     * @return el objeto Destino encontrado o null si no existe.
     */
    static Destino buscarDestinoPorId(String id) {

        // Recorre únicamente las posiciones ocupadas del arreglo.
        for (int i = 0; i < contadorDestinos; i++) {

            // Compara el ID guardado en la posición actual con el ID solicitado.
            // equalsIgnoreCase ignora diferencias entre mayúsculas y minúsculas.
            if (arrDestinos[i].getIdDestino().equalsIgnoreCase(id)) {
                return arrDestinos[i]; // Devuelve el destino encontrado y termina la búsqueda.
            }
        }

        // Si termina el recorrido sin coincidencias, indica que no existe.
        return null;
    }


    /**
     * Solicita un ID por consola y muestra el destino correspondiente.
     *
     * @param sc Scanner utilizado para leer el ID escrito por el usuario.
     */
    static void consultarDestinoUI(Scanner sc) {
        System.out.print("Ingrese el ID del destino: ");
        String id = sc.nextLine(); // Lee el identificador ingresado.

        // Llama al método de búsqueda y guarda el resultado.
        Destino d = buscarDestinoPorId(id);

        // Si d no es null, significa que se encontró el destino.
        if (d != null) {
            System.out.println(d); // Imprime el objeto; Java utiliza su representación toString().
        } else {
            System.out.println("Destino no encontrado.");
        }
    }


    /**
     * Busca destinos usando una parte del nombre o del municipio.
     *
     * @param sc Scanner utilizado para leer el texto que se desea buscar.
     */
    static void buscarDestinoUI(Scanner sc) {
        System.out.print("Texto a buscar (nombre o municipio): ");

        // Convierte el texto a minúsculas para facilitar la comparación.
        String texto = sc.nextLine().toLowerCase();

        // Controla si se encontró al menos un destino que coincida.
        boolean encontrado = false;

        // Recorre los destinos que están registrados.
        for (int i = 0; i < contadorDestinos; i++) {

            // contains permite encontrar coincidencias parciales en nombre o municipio.
            if (arrDestinos[i].getNombre().toLowerCase().contains(texto) ||
                arrDestinos[i].getMunicipio().toLowerCase().contains(texto)) {

                System.out.println(arrDestinos[i]); // Muestra el destino coincidente.
                encontrado = true;                  // Registra que hubo una coincidencia.
            }
        }

        // Si ningún elemento coincidió, informa al usuario.
        if (!encontrado) System.out.println("No se encontraron coincidencias.");
    }


    /**
     * Solicita los datos de un destino, crea el objeto y llama al método que lo registra.
     *
     * @param sc Scanner utilizado para capturar los datos.
     */
    static void crearDestinoUI(Scanner sc) {
        // Solicita los datos de tipo texto.
        System.out.print("ID (ej. DEST-004): "); String id = sc.nextLine();
        System.out.print("Nombre: "); String nom = sc.nextLine();
        System.out.print("Municipio: "); String mun = sc.nextLine();
        System.out.print("Subregion/Zona: "); String sub = sc.nextLine();

        // Solicita las coordenadas numéricas del destino.
        System.out.print("Latitud: "); double lat = sc.nextDouble();
        System.out.print("Longitud: "); double lon = sc.nextDouble();

        // Consume el salto de línea que queda después de leer los números.
        sc.nextLine();

        // Solicita el estado de publicación.
        System.out.print("Estado (Activo/Desactivado): "); String est = sc.nextLine();

        // Construye un objeto Destino con los datos capturados y lo envía a crearDestino.
        crearDestino(new Destino(id, nom, mun, sub, lat, lon, est));
    }


    /**
     * Busca un destino existente y permite actualizar su nombre y municipio.
     * Si el usuario deja un campo vacío, conserva el valor anterior.
     *
     * @param sc Scanner utilizado para leer el ID y los nuevos valores.
     */
    static void actualizarDestinoUI(Scanner sc) {
        System.out.print("Ingrese ID del destino a actualizar: ");
        String id = sc.nextLine();

        // Busca el destino que se desea modificar.
        Destino d = buscarDestinoPorId(id);

        // Si no se encontró, informa y termina el método.
        if (d == null) {
            System.out.println("Destino no encontrado.");
            return;
        }

        // Solicita un nuevo nombre. Solo lo cambia si el usuario escribió algo.
        System.out.print("Nuevo Nombre (" + d.getNombre() + "): ");
        String nom = sc.nextLine();
        if(!nom.isEmpty()) d.setNombre(nom);

        // Solicita un nuevo municipio. Si se deja vacío, conserva el anterior.
        System.out.print("Nuevo Municipio (" + d.getMunicipio() + "): ");
        String mun = sc.nextLine();
        if(!mun.isEmpty()) d.setMunicipio(mun);

        System.out.println("Destino actualizado correctamente.");
    }


    /**
     * Elimina un destino del arreglo y lo guarda previamente en la pila.
     *
     * Primero localiza el índice del destino. Si existe, lo apila para poder
     * deshacer la eliminación, desplaza los elementos siguientes hacia la izquierda,
     * limpia la última posición ocupada y disminuye el contador.
     *
     * @param sc Scanner utilizado para leer el ID del destino que se eliminará.
     */
    static void eliminarDestinoUI(Scanner sc) {
        System.out.print("Ingrese ID del destino a eliminar: ");
        String id = sc.nextLine();

        // -1 representa que todavía no se ha encontrado el destino.
        int index = -1;

        // Busca el ID y guarda la posición donde aparece.
        for (int i = 0; i < contadorDestinos; i++) {
            if (arrDestinos[i].getIdDestino().equalsIgnoreCase(id)) {
                index = i;
                break; // Ya se encontró; no hace falta seguir recorriendo.
            }
        }

        // Si index sigue en -1, no se encontró el registro.
        if (index == -1) {
            System.out.println("Destino no encontrado.");
            return;
        }

        // Guarda una referencia al destino que se va a eliminar.
        Destino eliminado = arrDestinos[index];

        // Respalda el objeto en la pila para que pueda recuperarse después.
        pilaDestinos.apilar(eliminado);

        // Desplaza una posición a la izquierda los elementos que estaban después del eliminado.
        for (int i = index; i < contadorDestinos - 1; i++) {
            arrDestinos[i] = arrDestinos[i + 1];
        }

        // Limpia la última posición que quedó duplicada por el desplazamiento.
        arrDestinos[contadorDestinos - 1] = null;

        // Disminuye la cantidad actual de destinos registrados.
        contadorDestinos--;

        System.out.println("Destino eliminado y respaldado en pila para deshacer.");
    }


    /**
     * Muestra todos los destinos registrados y la cantidad total.
     */
    static void listarDestinosUI() {

        // Si el contador es cero, no hay elementos que listar.
        if (contadorDestinos == 0) {
            System.out.println("No hay destinos registrados.");
            return;
        }

        System.out.println("\n--- LISTADO DE DESTINOS ---");

        // Recorre e imprime todos los destinos desde la posición 0 hasta contadorDestinos - 1.
        for (int i = 0; i < contadorDestinos; i++) {
            System.out.println(arrDestinos[i]);
        }

        // Muestra el número de destinos que están registrados en ese momento.
        System.out.println("Total de destinos: " + contadorDestinos);
    }


    /**
     * Recupera el último destino eliminado y lo vuelve a insertar en el arreglo.
     */
    static void deshacerDestinoUI() {

        // Verifica que exista por lo menos un objeto en la pila.
        if (pilaDestinos.estaVacia()) {
            System.out.println("No hay eliminaciones recientes para deshacer.");
            return;
        }

        // Extrae de la pila el último destino que se había eliminado.
        Destino recuperado = pilaDestinos.desapilar();

        // Comprueba que el arreglo tenga espacio antes de reinsertar el objeto.
        if (contadorDestinos < arrDestinos.length) {
            arrDestinos[contadorDestinos] = recuperado; // Lo guarda al final del arreglo.
            contadorDestinos++;                         // Actualiza la cantidad de destinos.
            System.out.println("Se ha restaurado el destino: " + recuperado.getNombre());
        }
    }


    // ================================================================
    // MENÚ DEL MÓDULO DE SERVICIOS
    // ================================================================

    /**
     * Muestra el submenú de servicios y ejecuta la operación seleccionada.
     *
     * @param scanner objeto Scanner compartido para leer la opción del usuario.
     */
    static void menuServicios(Scanner scanner) {
        int op = -1; // Almacena la opción elegida en este submenú.

        // Repite el menú hasta que el usuario seleccione 0.
        do {
            System.out.println("\n--- GESTION DE SERVICIOS DE DESTINO ---");
            System.out.println("1. Registrar Servicios (Valida contra Destino)");
            System.out.println("2. Consultar Servicios por ID");
            System.out.println("3. Listar Servicios");
            System.out.println("4. Eliminar Registro de Servicios");
            System.out.println("5. Deshacer eliminacion de Servicios");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            // Verifica que la opción ingresada sea un entero.
            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine(); // Consume el salto de línea pendiente.

                // Selecciona el método que corresponde a la opción.
                switch (op) {
                    case 1: crearServicioUI(scanner); break; // Registra servicios.
                    case 2: consultarServicioUI(scanner); break; // Consulta por ID.
                    case 3: listarServiciosUI(); break; // Lista los servicios.
                    case 4: eliminarServicioUI(scanner); break; // Elimina un servicio.
                    case 5: deshacerServicioUI(); break; // Recupera el último eliminado.
                    case 0: break; // Permite salir del submenú.
                    default: System.out.println("Opcion no valida.");
                }
            } else {
                // Descarta la entrada cuando no corresponde a un número entero.
                scanner.nextLine();
            }
        } while (op != 0);
    }


    // ================================================================
    // OPERACIONES INTERNAS DE SERVICIOS
    // ================================================================

    /**
     * Registra los servicios asociados a un destino existente.
     *
     * Solicita los identificadores y las características del servicio. Antes de
     * registrarlo, verifica que el ID del destino exista en el catálogo.
     *
     * @param sc Scanner utilizado para leer los datos ingresados por el usuario.
     */
    static void crearServicioUI(Scanner sc) {
        // Solicita el identificador propio del registro de servicios.
        System.out.print("ID del Registro de Servicios (ej. SERV-003): "); String idS = sc.nextLine();

        // Solicita el identificador del destino al que estará asociado el servicio.
        System.out.print("ID del Destino asociado: "); String idD = sc.nextLine();

        // Busca el destino para validar que el servicio no quede asociado a un ID inexistente.
        Destino destinoAsociado = buscarDestinoPorId(idD);

        // Si la búsqueda devuelve null, el destino no existe y se cancela el registro.
        if (destinoAsociado == null) {
            System.out.println("Error (RN-10): No se puede registrar servicios. El ID de destino no existe en el catalogo.");
            return; // Termina este método sin crear el servicio.
        }

        // Pregunta si el destino tiene camping y guarda la respuesta true o false.
        System.out.print("¿Tiene camping? (true/false): "); boolean camp = sc.nextBoolean();

        // Pregunta si el destino tiene baños y guarda la respuesta.
        System.out.print("¿Tiene banos? (true/false): "); boolean ban = sc.nextBoolean();

        // Pregunta si el destino tiene parqueadero y guarda la respuesta.
        System.out.print("¿Tiene parqueadero? (true/false): "); boolean par = sc.nextBoolean();

        // Consume el Enter que queda pendiente después de leer el último boolean.
        sc.nextLine();

        // Comprueba que todavía haya espacio en el arreglo de servicios.
        if (contadorServicios < arrServicios.length) {

            // Crea el objeto ServicioDestino y lo guarda en la siguiente posición libre.
            arrServicios[contadorServicios] = new ServicioDestino(idS, idD, camp, ban, par);

            // Aumenta el total de servicios registrados.
            contadorServicios++;

            // Confirma el registro mostrando el nombre del destino asociado.
            System.out.println("Servicios registrados exitosamente para el destino: " + destinoAsociado.getNombre());
        }
    }


    /**
     * Busca un servicio por su identificador y muestra el registro cuando lo encuentra.
     *
     * @param sc Scanner utilizado para leer el identificador del servicio.
     */
    static void consultarServicioUI(Scanner sc) {
        System.out.print("Ingrese ID de servicio: ");
        String id = sc.nextLine(); // Guarda el ID que desea consultar.

        // Recorre los servicios registrados para buscar el identificador solicitado.
        for (int i = 0; i < contadorServicios; i++) {

            // Compara el ID del servicio actual con el ingresado, ignorando mayúsculas/minúsculas.
            if (arrServicios[i].getIdServicio().equalsIgnoreCase(id)) {
                System.out.println(arrServicios[i]); // Muestra el objeto encontrado.
                return; // Termina porque ya encontró el servicio.
            }
        }

        // Solo llega aquí si terminó el recorrido y no encontró coincidencias.
        System.out.println("Registro de servicios no encontrado.");
    }


    /**
     * Muestra todos los servicios que están registrados actualmente.
     */
    static void listarServiciosUI() {

        // Evita recorrer el arreglo cuando no hay servicios registrados.
        if (contadorServicios == 0) {
            System.out.println("No hay servicios registrados.");
            return;
        }

        System.out.println("\n--- LISTADO DE SERVICIOS ---");

        // Recorre las posiciones ocupadas e imprime cada objeto ServicioDestino.
        for (int i = 0; i < contadorServicios; i++) {
            System.out.println(arrServicios[i]);
        }
    }


    /**
     * Elimina un servicio por su ID, lo respalda en la pila y reorganiza el arreglo.
     *
     * @param sc Scanner utilizado para leer el identificador del servicio.
     */
    static void eliminarServicioUI(Scanner sc) {
        System.out.print("Ingrese ID de servicio a eliminar: ");
        String id = sc.nextLine(); // ID del servicio que se desea eliminar.

        // -1 significa que todavía no se ha encontrado el servicio.
        int index = -1;

        // Busca el servicio y conserva el índice de la posición donde aparece.
        for (int i = 0; i < contadorServicios; i++) {
            if (arrServicios[i].getIdServicio().equalsIgnoreCase(id)) {
                index = i;
                break; // Detiene la búsqueda al encontrar el registro.
            }
        }

        // Si index no cambió de -1, el servicio no existe en el arreglo.
        if (index == -1) {
            System.out.println("Registro no encontrado.");
            return;
        }

        // Guarda el servicio eliminado en la pila antes de quitarlo del arreglo.
        pilaServicios.apilar(arrServicios[index]);

        // Desplaza los servicios posteriores una posición hacia la izquierda.
        for (int i = index; i < contadorServicios - 1; i++) {
            arrServicios[i] = arrServicios[i + 1];
        }

        // Limpia la última casilla ocupada para evitar conservar una referencia duplicada.
        arrServicios[contadorServicios - 1] = null;

        // Actualiza la cantidad de servicios registrados.
        contadorServicios--;

        System.out.println("Servicio eliminado y respaldado en pila.");
    }


    /**
     * Recupera el último servicio eliminado y lo vuelve a agregar al arreglo.
     */
    static void deshacerServicioUI() {

        // Comprueba si hay algún servicio disponible para recuperar.
        if (pilaServicios.estaVacia()) {
            System.out.println("No hay eliminaciones de servicios para deshacer.");
            return;
        }

        // Extrae de la pila el último servicio eliminado.
        ServicioDestino recuperado = pilaServicios.desapilar();

        // Comprueba que el arreglo todavía tenga una posición libre.
        if (contadorServicios < arrServicios.length) {
            arrServicios[contadorServicios] = recuperado; // Lo agrega al final del arreglo.
            contadorServicios++;                          // Actualiza la cantidad registrada.
            System.out.println("Servicio restaurado correctamente.");
        }
    }


    // ================================================================
    // GUARDADO DE INFORMACIÓN EN ARCHIVOS TXT
    // ================================================================

    /**
     * Guarda los destinos y servicios registrados en archivos de texto.
     *
     * Cada objeto se convierte a una línea de texto mediante aFormatoArchivo().
     * Los campos se separan con punto y coma para poder recuperarlos posteriormente.
     */
    static void guardarArchivos() {

        // Abre destinos.txt para escritura. PrintWriter permite escribir líneas.
        // try-with-resources cierra automáticamente el archivo al salir del bloque.
        try (PrintWriter pw = new PrintWriter(new FileWriter("destinos.txt"))) {

            // Recorre los destinos registrados y escribe cada uno en una línea.
            for (int i = 0; i < contadorDestinos; i++) {
                pw.println(arrDestinos[i].aFormatoArchivo());
            }
        } catch (IOException e) {
            // Maneja errores que puedan ocurrir al crear o escribir destinos.txt.
            System.out.println("Error al guardar destinos: " + e.getMessage());
        }

        // Realiza el mismo proceso para el archivo de servicios.
        try (PrintWriter pw = new PrintWriter(new FileWriter("servicios.txt"))) {

            // Escribe únicamente las posiciones ocupadas del arreglo de servicios.
            for (int i = 0; i < contadorServicios; i++) {
                pw.println(arrServicios[i].aFormatoArchivo());
            }
        } catch (IOException e) {
            // Informa si ocurrió un problema al guardar los servicios.
            System.out.println("Error al guardar servicios: " + e.getMessage());
        }
    }


    // ================================================================
    // CARGA DE INFORMACIÓN DESDE ARCHIVOS TXT
    // ================================================================

    /**
     * Lee los archivos destinos.txt y servicios.txt si existen.
     *
     * Cada línea se separa por punto y coma. Con los campos recuperados se crean
     * nuevamente los objetos y se almacenan en sus arreglos correspondientes.
     * También se actualizan los contadores.
     */
    static void cargarArchivos() {

        // Crea un objeto File que representa el archivo de destinos.
        File f1 = new File("destinos.txt");

        // Solo intenta leerlo cuando el archivo existe.
        if (f1.exists()) {

            // Abre el archivo para lectura. Los recursos se cierran automáticamente.
            try (BufferedReader br = new BufferedReader(new FileReader(f1))) {
                String linea; // Guardará temporalmente cada línea leída.

                // Lee una línea en cada vuelta hasta llegar al final del archivo (null).
                while ((linea = br.readLine()) != null) {

                    // Divide la línea en campos utilizando el punto y coma.
                    String[] p = linea.split(";");

                    // Un destino debe contener siete campos para reconstruir el objeto.
                    if (p.length == 7) {

                        // Crea el objeto Destino con los datos recuperados.
                        // parseDouble convierte los textos de latitud y longitud a double.
                        // Después lo guarda en el arreglo y aumenta el contador.
                        arrDestinos[contadorDestinos++] = new Destino(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]), Double.parseDouble(p[5]), p[6]);
                    }
                }
            } catch (Exception e) {
                // Informa que hubo un problema durante la lectura o conversión de destinos.
                System.out.println("Error al cargar destinos.");
            }
        }

        // Crea un objeto File que representa el archivo de servicios.
        File f2 = new File("servicios.txt");

        // Comprueba que el archivo de servicios exista antes de leerlo.
        if (f2.exists()) {

            // Abre servicios.txt para leerlo línea por línea.
            try (BufferedReader br = new BufferedReader(new FileReader(f2))) {
                String linea; // Almacena temporalmente cada registro de texto.

                // Continúa leyendo mientras existan líneas.
                while ((linea = br.readLine()) != null) {

                    // Separa los campos del servicio utilizando el punto y coma.
                    String[] p = linea.split(";");

                    // Un servicio debe contener cinco campos.
                    if (p.length == 5) {

                        // Convierte los textos true/false a valores Boolean y crea el objeto.
                        // El objeto se almacena en el arreglo y el contador se incrementa.
                        arrServicios[contadorServicios++] = new ServicioDestino(p[0], p[1], Boolean.parseBoolean(p[2]), Boolean.parseBoolean(p[3]), Boolean.parseBoolean(p[4]));
                    }
                }
            } catch (Exception e) {
                // Informa que hubo un problema durante la lectura o conversión de servicios.
                System.out.println("Error al cargar servicios.");
            }
        }
    }
}