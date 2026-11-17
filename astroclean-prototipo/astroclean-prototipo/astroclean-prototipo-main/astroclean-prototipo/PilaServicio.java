/**
 * Pila utilizada para guardar temporalmente los servicios que se eliminan.
 * Permite recuperar el último registro eliminado mediante la operación de deshacer.
 *
 * @author Jean Paul Ospina Medrano
 * @version 1.0
 */
public class PilaServicio {

    // Arreglo donde se almacenan los servicios que se van guardando en la pila.
    private ServicioDestino[] elementos;

    // Indica la posición del último elemento agregado. En -1 la pila está vacía.
    private int cima;

    /**
     * Crea la pila con la capacidad indicada e inicia la cima en -1.
     *
     * @param capacidad Cantidad máxima de servicios que puede almacenar.
     */
    public PilaServicio(int capacidad) {
        this.elementos = new ServicioDestino[capacidad];
        this.cima = -1;
    }

    /**
     * Agrega un servicio en la parte superior de la pila si todavía hay espacio.
     *
     * @param s Objeto ServicioDestino que se desea guardar.
     */
    public void apilar(ServicioDestino s) {
        // Se comprueba que la cima no haya llegado a la última posición del arreglo.
        if (cima < elementos.length - 1) {
            cima++;
            elementos[cima] = s;
        }
    }

    /**
     * Retira y devuelve el último servicio agregado a la pila.
     * Si no hay elementos, devuelve null.
     *
     * @return Servicio retirado o null si la pila está vacía.
     */
    public ServicioDestino desapilar() {
        if (estaVacia()) {
            return null;
        }

        // Se conserva el objeto, se libera su posición y se baja la cima.
        ServicioDestino s = elementos[cima];
        elementos[cima] = null;
        cima--;
        return s;
    }

    /**
     * Consulta el último servicio agregado sin retirarlo de la pila.
     *
     * @return Servicio ubicado en la cima o null si la pila está vacía.
     */
    public ServicioDestino cima() {
        if (estaVacia()) return null;
        return elementos[cima];
    }

    /**
     * Indica si la pila no contiene elementos.
     *
     * @return true cuando la cima vale -1; de lo contrario, false.
     */
    public boolean estaVacia() {
        return cima == -1;
    }

    /**
     * Calcula cuántos servicios hay actualmente en la pila.
     *
     * @return Cantidad de elementos almacenados.
     */
    public int tamano() {
        // Como los índices empiezan en 0, se suma 1 a la posición de la cima.
        return cima + 1;
    }
}
