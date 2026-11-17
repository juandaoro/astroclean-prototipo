/**
 * Representa un destino de observacion astronomica y camping ubicado en Antioquia.
 * Cada objeto de esta clase guarda la informacion de un lugar, como su nombre,
 * ubicacion, coordenadas y estado de publicacion.
 *
 * Main utiliza esta clase para crear y administrar los destinos del sistema.
 * Tambien permite consultar o modificar algunos datos y preparar la informacion
 * para guardarla en un archivo de texto.
 *
 * Proyecto: AstroClean
 * Autor: Juan David Orozco Carmona
 * Version: 1.0
 */
public class Destino {

    // Datos que identifican y describen cada destino.
    // Se mantienen privados para controlar el acceso desde otras clases.
    private String idDestino;          // Codigo unico del destino, por ejemplo DEST-001.
    private String nombre;             // Nombre del lugar.
    private String municipio;          // Municipio donde se encuentra.
    private String subregion;          // Subregion geografica a la que pertenece.
    private double latitud;            // Coordenada geografica de latitud.
    private double longitud;           // Coordenada geografica de longitud.
    private String estadoPublicacion;  // Estado del destino: Activo, Desactivado, etc.

    /**
     * Crea un destino con los datos recibidos. Se ejecuta cuando se utiliza
     * new Destino(...) desde Main, por ejemplo al registrar o cargar un destino.
     *
     * @param idDestino Identificador unico del destino.
     * @param nombre Nombre del lugar.
     * @param municipio Municipio donde se encuentra.
     * @param subregion Subregion geografica.
     * @param latitud Coordenada de latitud.
     * @param longitud Coordenada de longitud.
     * @param estadoPublicacion Estado actual de publicacion.
     */
    public Destino(String idDestino, String nombre, String municipio, String subregion, double latitud, double longitud, String estadoPublicacion) {
        // this distingue los atributos del objeto de los parametros recibidos.
        this.idDestino = idDestino;
        this.nombre = nombre;
        this.municipio = municipio;
        this.subregion = subregion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estadoPublicacion = estadoPublicacion;
    }

    // Getters: permiten consultar los valores de los atributos.
    public String getIdDestino() { return idDestino; }
    public String getNombre() { return nombre; }
    public String getMunicipio() { return municipio; }

    // Setters: permiten actualizar los atributos que Main necesita modificar.
    public void setIdDestino(String idDestino) { this.idDestino = idDestino; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    /**
     * Organiza los siete atributos en una sola linea, separados por punto y coma.
     * Main utiliza este formato para escribir el destino en destinos.txt y luego
     * separar nuevamente sus datos cuando carga el archivo.
     *
     * @return Datos del destino separados por punto y coma.
     */
    public String aFormatoArchivo() {
        return idDestino + ";" + nombre + ";" + municipio + ";" + subregion + ";" + latitud + ";" + longitud + ";" + estadoPublicacion;
    }

    /**
     * Define como se muestra un destino cuando se imprime en consola.
     * Por ejemplo, System.out.println(d) utiliza automaticamente este metodo.
     *
     * @return Texto con los datos principales del destino.
     */
    @Override
    public String toString() {
        return "Destino [ID=" + idDestino + ", Nombre=" + nombre + ", Municipio=" + municipio +
               ", Subregión=" + subregion + ", Lat=" + latitud + ", Lon=" + longitud + ", Estado=" + estadoPublicacion + "]";
    }
}