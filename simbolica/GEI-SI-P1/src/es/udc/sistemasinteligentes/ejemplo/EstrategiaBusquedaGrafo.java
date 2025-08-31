package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class EstrategiaBusquedaGrafo implements EstrategiaBusqueda {

    public EstrategiaBusquedaGrafo() {
    }

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {
        Queue<Nodo> frontera = new ArrayDeque<>();
        ArrayList<Estado> explorados = new ArrayList<>();
        Nodo nodoActual = new Nodo(p.getEstadoInicial(), null, null);
        Nodo[] H;
        int i = 1;

        frontera.add(nodoActual);

        System.out.println((i++) + " - Empezando búsqueda en " + nodoActual.getEstado());

        while (!frontera.isEmpty()) {
            nodoActual = frontera.poll();
            System.out.println((i++) + " - Estado actual cambiado a " + nodoActual.getEstado());
            explorados.add(nodoActual.getEstado());
            boolean modificado = false;
            H = Nodo.sucesores(nodoActual.getEstado(), nodoActual, p.acciones(nodoActual.getEstado()));

            for (Nodo h : H) {  // añadimos cada uno de los sucesores a la solución
                boolean pertenece = false;
                if(p.esMeta(h.getEstado())) {
                    System.out.println((i++) + " - FIN - " + h.getEstado());
                    return Nodo.reconstruyeSol(h);
                } else {
                    System.out.println((i++) + " - " + nodoActual.getEstado() + " no es meta");

                    if (explorados.contains(h.getEstado())) {
                        System.out.println((i++) + " - " + h.getEstado() + " ya explorado");
                        pertenece = true;
                    }

                    if (!pertenece) {
                        for (Nodo n : frontera) {
                            if (h.getEstado().equals(n.getEstado())) {
                                pertenece = true;
                                break;
                            }
                        }
                    }
                    if (!pertenece) {
                        System.out.println((i++) + " - " + h.getEstado() + " NO explorado");
                        frontera.add(h);
                    }
                }
            }
        }

        throw new Exception("No se ha podido encontrar una solución");
    }
}