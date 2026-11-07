/* =========================================================== 
 * Estructura     : Pila de Eliminaciones para Servicio_Destino
 * Programado por : Jean Paul Ospina Medrano
 * =========================================================== */

public class PilaServicio {
    private ServicioDestino[] elementos;
    private int cima;

    public PilaServicio(int capacidad) {
        this.elementos = new ServicioDestino[capacidad];
        this.cima = -1;
    }

    // RF-025 — Apila un servicio eliminado para permitir deshacer
    public void apilar(ServicioDestino s) {
        if (cima < elementos.length - 1) {
            cima++;
            elementos[cima] = s;
        }
    }

    // RF-025 — Desapila y devuelve el último servicio eliminado
    public ServicioDestino desapilar() {
        if (estaVacia()) {
            return null;
        }
        ServicioDestino s = elementos[cima];
        elementos[cima] = null;
        cima--;
        return s;
    }

    public ServicioDestino cima() {
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