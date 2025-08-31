package es.udc.sistemasinteligentes.apartado2b;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class EstretegiaBusquedaA implements EstrategiaBusquedaInformada {

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p, Heuristica h) throws Exception {
        PriorityQueue<Nodo> frontera = new PriorityQueue<>(); // Cola de prioridade
        ArrayList<Estado> explorados = new ArrayList<>();
        Nodo nodoActual= new Nodo(p.getEstadoInicial(),null,null);

        int i = 0;
        frontera.add(nodoActual);
        Estado S = null;
        Nodo[] H;
        float cn;

        while (!frontera.isEmpty()) {
            nodoActual = frontera.remove(); // Quitamos el primer nodo de la frontera
            S = nodoActual.getEstado();
            System.out.println((i++) + " - Estado actual cambiado a:\n" + nodoActual.getEstado());

            if (p.esMeta(S)) { // Miramos si el estado es meta
                System.out.println((i++) + " - FIN:\n" + nodoActual.getEstado() + '\n');
                return Nodo.reconstruyeSol(nodoActual);
            } else {
                explorados.add(S);
                H = Nodo.sucesores(S, nodoActual, p.acciones(S));
                for (Nodo nh : H) {
                    nh.setCoste(nodoActual.getCoste() + 1);
                    nh.setFuncion(nh.getCoste() + h.evalua(nh.getEstado()));
                    boolean pertenece = false;

                    if (explorados.contains(nh.getEstado())) {
                        System.out.println((i++) + " - ya explorado:\n" + nh.getEstado());
                        pertenece = true;
                    }
                    if (!pertenece) {
                        for (Nodo n : frontera) {
                            if (nh.getEstado().equals(n.getEstado())) {
                                pertenece = true;
                                if (nh.getFuncion() < n.getFuncion()) {
                                    System.out.println((i++) + " - Nodo frontera:\n" + n.getEstado()
                                            + "actualizado a \n" + nh.getEstado());
                                    frontera.remove(n);
                                    frontera.add(nh);
                                }
                            }
                        }
                    }
                    if (!pertenece) { // si el estado no esta ni en frontera ni en explorados lo añadimos a frontera
                        frontera.add(nh);
                    }
                }
            }
        }

        throw new Exception("No se ha podido encontrar una solución");
    }
}