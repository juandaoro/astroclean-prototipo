/**
 * Representa el registro de servicios prácticos (camping, baños, parqueadero) 
 * asociados de manera lógica a un destino existente.
 * 
 * <p><b>Proyecto:</b> AstroClean</p>
 * <p><b>Autor:</b> Jean Paul Ospina Medrano</p>
 * 
 * @version 1.0
 * @since 2026-10-31
 */
public class ServicioDestino {
    private String idServicio;
    private String idDestino; // Punto de contacto obligatorio con Destino
    private Boolean tieneCamping;
    private Boolean tieneBanos;
    private Boolean tieneParqueadero;

    /**
     * Constructor para inicializar el registro de servicios de un destino.
     * 
     * @param idServicio Identificador único del registro de servicios.
     * @param idDestino Identificador del destino al que se vincula (Llave foránea lógica).
     * @param tieneCamping Disponibilidad de zona de camping (true, false o null).
     * @param tieneBanos Disponibilidad de servicios sanitarios.
     * @param tieneParqueadero Disponibilidad de zona de estacionamiento.
     */
    public ServicioDestino(String idServicio, String idDestino, Boolean tieneCamping, Boolean tieneBanos, Boolean tieneParqueadero) {
        this.idServicio = idServicio;
        this.idDestino = idDestino;
        this.tieneCamping = tieneCamping;
        this.tieneBanos = tieneBanos;
        this.tieneParqueadero = tieneParqueadero;
    }

    /**
     * Obtiene el código del servicio.
     * @return ID del servicio.
     */
    public String getIdServicio() { return idServicio; }

    /**
     * Obtiene el código del destino vinculado.
     * @return ID del destino asociado.
     */
    public String getIdDestino() { return idDestino; }

    /**
     * Genera la cadena en formato plano delimitada por punto y coma para el archivo persistente.
     * @return Texto formateado del servicio.
     */
    public String aFormatoArchivo() {
        return idServicio + ";" + idDestino + ";" + tieneCamping + ";" + tieneBanos + ";" + tieneParqueadero;
    }
}
