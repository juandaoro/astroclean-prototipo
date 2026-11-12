/**
 * Clase que implementa una estructura de Pila propia basada en arreglos estaticos
 * para la gestion de eliminaciones en la entidad ServicioDestino, sin utilizar 
 * java.util.Stack ni colecciones avanzadas de la biblioteca de Java.
 * 
 * @author Jean Paul Ospina Medrano
 * @version 1.0
 */
public class PilaServicio {
    private ServicioDestino[] elementos;
    private int cima;

    /**
     * Constructor que inicializa la pila con una capacidad maxima fija.
     * 
     * @param capacidad El tamano maximo del arreglo interno de la pila.
     */
    public PilaServicio(int capacidad) {
        this.elementos = new ServicioDestino[capacidad];
        this.cima = -1;
    }

    /**
     * Apila un registro de servicio eliminado para permitir la operacion de deshacer.
     * 
     * @param s El objeto ServicioDestino que se desea almacenar en la cima.
     */
    public void apilar(ServicioDestino s) {
        if (cima < elementos.length - 1) {
            cima++;
            elementos[cima] = s;
        }
    }

    /**
     * Desapila y devuelve el ultimo registro de servicio eliminado.
     * Si la pila esta vacia, devuelve null en lugar de generar excepciones.
     * 
     * @return El objeto ServicioDestino removido de la cima, o null si esta vacia.
     */
    public ServicioDestino desapilar() {
        if (estaVacia()) {
            return null;
        }
        ServicioDestino s = elementos[cima];
        elementos[cima] = null;
        cima--;
        return s;
    }

    /**
     * Devuelve el elemento ubicado en la cima sin removerlo de la pila.
     * 
     * @return El objeto ServicioDestino en la cima, o null si esta vacia.
     */
    public ServicioDestino cima() {
        if (estaVacia()) return null;
        return elementos[cima];
    }

    /**
     * Verifica si la pila se encuentra vacia evaluando el indice de la cima.
     * 
     * @return true si la cima es -1, false en caso contrario.
     */
    public boolean estaVacia() {
        return cima == -1;
    }

    /**
     * Retorna el numero actual de elementos almacenados en la pila.
     * 
     * @return El tamano actual basado en la posicion de la cima.
     */
    public int tamano() {
        return cima + 1;
    }
}