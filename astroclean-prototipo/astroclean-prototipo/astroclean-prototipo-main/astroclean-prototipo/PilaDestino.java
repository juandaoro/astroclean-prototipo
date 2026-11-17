/**
 * Pila de objetos Destino implementada sobre un arreglo.
 * Se utiliza para guardar los destinos que se eliminan y poder recuperarlos
 * cuando el usuario selecciona la opcion de deshacer.
 *
 * La pila trabaja con el principio LIFO: el ultimo elemento que entra es
 * el primero que sale.
 *
 * Autor: Juan David Orozco Carmona
 */
public class PilaDestino {

    // Arreglo donde se guardan los destinos y posicion del elemento superior.
    private Destino[] elementos;
    private int cima;

    /**
     * Crea una pila con la capacidad indicada.
     * La cima comienza en -1 porque todavia no hay elementos guardados.
     *
     * @param capacidad Cantidad maxima de elementos que puede almacenar la pila.
     */
    public PilaDestino(int capacidad) {
        this.elementos = new Destino[capacidad];
        this.cima = -1;
    }

    /**
     * Agrega un destino en la parte superior de la pila, siempre que haya espacio.
     * Primero se aumenta la posicion de cima y luego se guarda el objeto alli.
     *
     * @param d Destino que se quiere guardar en la pila.
     */
    public void apilar(Destino d) {
        // Se comprueba que cima no haya llegado a la ultima posicion del arreglo.
        if (cima < elementos.length - 1) {
            cima++;
            elementos[cima] = d;
        }
    }

    /**
     * Retira y devuelve el destino que esta en la cima de la pila.
     * Si no hay elementos, devuelve null. Al retirar uno, libera su posicion
     * y mueve la cima una casilla hacia abajo.
     *
     * @return Destino retirado o null cuando la pila esta vacia.
     */
    public Destino desapilar() {
        if (estaVacia()) {
            return null;
        }
        Destino d = elementos[cima];
        elementos[cima] = null;
        cima--;
        return d;
    }

    /**
     * Comprueba si la pila esta vacia. Cuando cima vale -1 no hay elementos.
     *
     * @return true si esta vacia; false si contiene al menos un destino.
     */
    public boolean estaVacia() {
        return cima == -1;
    }
}
