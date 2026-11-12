/**
 * Estructura de datos tipo Pila (Last-In, First-Out) construida de forma personalizada
 * sobre un arreglo estático, diseñada para respaldar los elementos eliminados
 * y permitir la operación de deshacer (Undo).
 * 
 * <p><b>Autor:</b> Juan David Orozco Carmona</p>
 */
public class PilaDestino {
    private Destino[] elementos;
    private int cima;

    /**
     * Inicializa la pila con una capacidad máxima definida.
     * @param capacidad Tamaño máximo del arreglo interno.
     */
    public PilaDestino(int capacidad) {
        this.elementos = new Destino[capacidad];
        this.cima = -1;
    }

    /**
     * Inserta un elemento en la parte superior de la pila.
     * @param d Objeto de tipo Destino a apilar.
     */
    public void apilar(Destino d) {
        if (cima < elementos.length - 1) {
            cima++;
            elementos[cima] = d;
        }
    }

    /**
     * Extrae y remueve el elemento situado en la cima de la pila.
     * @return El objeto Destino removido, o null si la pila se encuentra vacía.
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
     * Comprueba si la pila no contiene elementos.
     * @return true si la pila está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return cima == -1;
    }
}