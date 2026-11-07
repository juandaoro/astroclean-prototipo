/* =========================================================== 
 * Estructura     : Pila de Eliminaciones para Destino
 * Programado por : Juan David Orozco Carmona
 * =========================================================== */

public class PilaDestino {
    private Destino[] elementos;
    private int cima;

    public PilaDestino(int capacidad) {
        this.elementos = new Destino[capacidad];
        this.cima = -1;
    }

    // RF-005 — Apila un destino eliminado para permitir deshacer
    public void apilar(Destino d) {
        if (cima < elementos.length - 1) {
            cima++;
            elementos[cima] = d;
        }
    }

    // RF-005 — Desapila y devuelve el último destino eliminado
    public Destino desapilar() {
        if (estaVacia()) {
            return null;
        }
        Destino d = elementos[cima];
        elementos[cima] = null;
        cima--;
        return d;
    }

    public Destino cima() {
        if (estaVacia()) return null;
        return elementos[cima];
    }

    public boolean estaVacia() {
        return cima == -1;
    }

    public int tamano() {
        return cima + 1;
    }
}