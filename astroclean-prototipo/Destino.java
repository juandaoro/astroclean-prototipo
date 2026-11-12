/**
 * Modela un destino de observación astronómica y camping ubicado en el departamento de Antioquia.
 * Contiene la información geográfica, estado de publicación y métodos de exportación a archivo plano.
 * 
 * <p><b>Proyecto:</b> AstroClean</p>
 * <p><b>Autor:</b> Juan David Orozco Carmona</p>
 * 
 * @version 1.0
 * @since 2026-10-31
 */
public class Destino {
    private String idDestino;
    private String nombre;
    private String municipio;
    private String subregion;
    private double latitud;
    private double longitud;
    private String estadoPublicacion;

    /**
     * Constructor para inicializar una nueva instancia de Destino.
     * 
     * @param idDestino Identificador único del sitio (ej. DEST-001).
     * @param nombre Nombre comercial o oficial del lugar.
     * @param municipio Municipio de Antioquia donde se sitúa.
     * @param subregion Subregión geográfica de cobertura (ej. Oriente, Valle de Aburrá, Suroeste).
     * @param latitud Coordenada de latitud en formato decimal.
     * @param longitud Coordenada de longitud en formato decimal.
     * @param estadoPublicacion Estado operativo actual (Activo, Desactivado, Pendiente).
     */
    public Destino(String idDestino, String nombre, String municipio, String subregion, double latitud, double longitud, String estadoPublicacion) {
        this.idDestino = idDestino;
        this.nombre = nombre;
        this.municipio = municipio;
        this.subregion = subregion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estadoPublicacion = estadoPublicacion;
    }

    /**
     * Obtiene el identificador único del destino.
     * @return El código ID del destino.
     */
    public String getIdDestino() { return idDestino; }

    /**
     * Modifica el identificador único del destino.
     * @param idDestino Nuevo código ID.
     */
    public void setIdDestino(String idDestino) { this.idDestino = idDestino; }

    /**
     * Obtiene el nombre del destino.
     * @return Nombre del lugar.
     */
    public String getNombre() { return nombre; }

    /**
     * Modifica el nombre del destino.
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el municipio del destino.
     * @return Municipio asociado.
     */
    public String getMunicipio() { return municipio; }

    /**
     * Modifica el municipio del destino.
     * @param municipio Nuevo municipio.
     */
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    /**
     * Transforma los atributos del destino en una cadena de texto separada por punto y coma (;)
     * para su almacenamiento en disco.
     * 
     * @return Cadena formateada para persistencia en archivo.
     */
    public String aFormatoArchivo() {
        return idDestino + ";" + nombre + ";" + municipio + ";" + subregion + ";" + latitud + ";" + longitud + ";" + estadoPublicacion;
    }

    @Override
    public String toString() {
        return "Destino [ID=" + idDestino + ", Nombre=" + nombre + ", Municipio=" + municipio + 
               ", Subregión=" + subregion + ", Lat=" + latitud + ", Lon=" + longitud + ", Estado=" + estadoPublicacion + "]";
    }
}
