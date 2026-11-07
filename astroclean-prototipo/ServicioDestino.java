/* =========================================================== 
 * Proyecto       : AstroClean - PWA Observación Astronómica
 * Entidad        : Servicio_Destino
 * Programado por : Jean Paul Ospina Medrano
 * Fecha          : 31/10/2026
 * ----------------------------------------------------------- 
 * HU-09, HU-10, HU-25
 * RF-025  Filtrar destinos con disponibilidad de camping
 * RF-028  Filtrar destinos por servicios (baños, parqueadero)
 * RF-073  Mostrar servicios registrados para un sitio
 * RN-10  Servicios solo se muestran disponibles si fueron registrados
 * RN-20  Dato no registrado se marca como 'sin información'
 * ----------------------------------------------------------- 
 * Criterios  : CA-09.1 (bueno), CA-10.2 (falla), CA-25.1 (bueno)
 * Estructuras: Arreglo con contador . Pila propia
 * =========================================================== */

public class ServicioDestino {
    private String idServicio;
    private String idDestino; // Punto de contacto: Debe existir en Destino
    private Boolean tieneCamping;
    private Boolean tieneBanos;
    private Boolean tieneParqueadero;

    public ServicioDestino(String idServicio, String idDestino, Boolean tieneCamping, Boolean tieneBanos, Boolean tieneParqueadero) {
        this.idServicio = idServicio;
        this.idDestino = idDestino;
        this.tieneCamping = tieneCamping;
        this.tieneBanos = tieneBanos;
        this.tieneParqueadero = tieneParqueadero;
    }

    public String getIdServicio() { return idServicio; }
    public void setIdServicio(String idServicio) { this.idServicio = idServicio; }

    public String getIdDestino() { return idDestino; }
    public void setIdDestino(String idDestino) { this.idDestino = idDestino; }

    public Boolean getTieneCamping() { return tieneCamping; }
    public void setTieneCamping(Boolean tieneCamping) { this.tieneCamping = tieneCamping; }

    public Boolean getTieneBanos() { return tieneBanos; }
    public void setTieneBanos(Boolean tieneBanos) { this.tieneBanos = tieneBanos; }

    public Boolean getTieneParqueadero() { return tieneParqueadero; }
    public void setTieneParqueadero(Boolean tieneParqueadero) { this.tieneParqueadero = tieneParqueadero; }

    public String aFormatoArchivo() {
        return idServicio + ";" + idDestino + ";" + tieneCamping + ";" + tieneBanos + ";" + tieneParqueadero;
    }

    @Override
    public String toString() {
        String campingStr = (tieneCamping == null) ? "Sin info" : (tieneCamping ? "Disponible" : "No disponible");
        String banosStr = (tieneBanos == null) ? "Sin info" : (tieneBanos ? "Disponible" : "No disponible");
        String parqueaderoStr = (tieneParqueadero == null) ? "Sin info" : (tieneParqueadero ? "Disponible" : "No disponible");
        return "Servicio [ID=" + idServicio + ", Destino=" + idDestino + ", Camping=" + campingStr + 
               ", Baños=" + banosStr + ", Parqueadero=" + parqueaderoStr + "]";
    }
}