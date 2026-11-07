/* =========================================================== 
 * Proyecto       : AstroClean - PWA Observación Astronómica
 * Entidad        : Destino
 * Programado por : Juan David Orozco Carmona
 * Fecha          : 31/10/2026
 * ----------------------------------------------------------- 
 * HU-01, HU-27, HU-29 
 * RF-001  Mostrar listado de destinos activos
 * RF-079  Registrar nuevo destino en el catálogo
 * RF-085  Actualizar o eliminar/desactivar destino
 * RF-005  Deshacer la última eliminación mediante Pila
 * RN-01  Cobertura inicial entre 15 y 20 sitios en Antioquia
 * RN-02  Todo destino publicado debe tener latitud y longitud válidas
 * ----------------------------------------------------------- 
 * Criterios  : CA-01.1 (bueno), CA-27.2 (falla), CA-29.1 (bueno)
 * Estructuras: Arreglo con contador . Pila propia
 * =========================================================== */

public class Destino {
    private String idDestino;
    private String nombre;
    private String municipio;
    private String subregion;
    private double latitud;
    private double longitud;
    private String estadoPublicacion; // "Activo", "Desactivado"

    public Destino(String idDestino, String nombre, String municipio, String subregion, double latitud, double longitud, String estadoPublicacion) {
        this.idDestino = idDestino;
        this.nombre = nombre;
        this.municipio = municipio;
        this.subregion = subregion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estadoPublicacion = estadoPublicacion;
    }

    public String getIdDestino() { return idDestino; }
    public void setIdDestino(String idDestino) { this.idDestino = idDestino; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getSubregion() { return subregion; }
    public void setSubregion(String subregion) { this.subregion = subregion; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public String getEstadoPublicacion() { return estadoPublicacion; }
    public void setEstadoPublicacion(String estadoPublicacion) { this.estadoPublicacion = estadoPublicacion; }

    public String aFormatoArchivo() {
        return idDestino + ";" + nombre + ";" + municipio + ";" + subregion + ";" + latitud + ";" + longitud + ";" + estadoPublicacion;
    }

    @Override
    public String toString() {
        return "Destino [ID=" + idDestino + ", Nombre=" + nombre + ", Municipio=" + municipio + 
               ", Subregión=" + subregion + ", Lat=" + latitud + ", Lon=" + longitud + ", Estado=" + estadoPublicacion + "]";
    }
}