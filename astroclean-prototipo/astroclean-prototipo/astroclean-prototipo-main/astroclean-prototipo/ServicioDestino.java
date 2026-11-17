/**
 * Representa los servicios que ofrece un destino dentro de AstroClean.
 * Cada registro guarda su identificador, el destino relacionado y la disponibilidad
 * de camping, baños y parqueadero.
 *
 * <p><b>Proyecto:</b> AstroClean</p>
 * <p><b>Autor:</b> Jean Paul Ospina Medrano</p>
 *
 * @version 1.0
 * @since 2026-10-31
 */
public class ServicioDestino {

    // Identificador propio del registro de servicios.
    private String idServicio;

    // ID del destino al que pertenecen estos servicios.
    private String idDestino;

    // Indican si el destino cuenta con cada uno de estos servicios.
    // Boolean permite true, false y también null cuando no hay un valor definido.
    private Boolean tieneCamping;
    private Boolean tieneBanos;
    private Boolean tieneParqueadero;

    /**
     * Inicializa un registro de servicios con la información recibida.
     *
     * @param idServicio Identificador único del registro.
     * @param idDestino Identificador del destino relacionado.
     * @param tieneCamping Indica si hay zona de camping.
     * @param tieneBanos Indica si hay servicios sanitarios.
     * @param tieneParqueadero Indica si hay zona de estacionamiento.
     */
    public ServicioDestino(String idServicio, String idDestino, Boolean tieneCamping, Boolean tieneBanos, Boolean tieneParqueadero) {
        this.idServicio = idServicio;
        this.idDestino = idDestino;
        this.tieneCamping = tieneCamping;
        this.tieneBanos = tieneBanos;
        this.tieneParqueadero = tieneParqueadero;
    }

    /**
     * Devuelve el identificador del registro de servicios.
     * @return ID del servicio.
     */
    public String getIdServicio() { return idServicio; }

    /**
     * Devuelve el identificador del destino asociado.
     * @return ID del destino.
     */
    public String getIdDestino() { return idDestino; }

    /**
     * Convierte los datos del servicio en una sola línea separada por punto y coma,
     * formato utilizado para guardarlos en el archivo servicios.txt.
     *
     * @return Datos del servicio preparados para almacenarse en el archivo.
     */
    public String aFormatoArchivo() {
        return idServicio + ";" + idDestino + ";" + tieneCamping + ";" + tieneBanos + ";" + tieneParqueadero;
    }
}
