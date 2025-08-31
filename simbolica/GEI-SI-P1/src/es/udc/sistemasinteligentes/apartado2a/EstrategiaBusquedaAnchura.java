package es.udc.sistemasinteligentes.apartado2a;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class EstrategiaBusquedaAnchura implements EstrategiaBusqueda {

    /*
     * Implementa búsqueda en anchura
     */
    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {
        Queue<Nodo> frontera = new ArrayDeque<>(); // cola FIFO para búsqueda en anchura
        ArrayList<Estado> explorados = new ArrayList<>(); // Lista de estados ya explorados
        Nodo nodoActual = new Nodo(p.getEstadoInicial(), null, null);

        // Contadores para estadísticas
        int nodosExpandidos = 0;
        int nodosCreados = 1; // El nodo inicial ya está creado

        // Comprobamos si el estado inicial es meta
        if (p.esMeta(nodoActual.getEstado())) {
            System.out.println("Nodos expandidos: " + nodosExpandidos);
            System.out.println("Nodos creados: " + nodosCreados);
            return new Nodo[]{nodoActual};
        }

        frontera.add(nodoActual);
        int i = 1;

        while (!frontera.isEmpty()) {
            // Sacamos el primer elemento de la frontera (el más antiguo en BFS)
            nodoActual = frontera.poll();
            nodosExpandidos++;
            System.out.println((i++) + " - Estado actual cambiado a:\n" + nodoActual.getEstado());

            // Añadimos a explorados después de sacarlo de la frontera
            explorados.add(nodoActual.getEstado());

            // Generamos los sucesores
            Nodo[] sucesores = Nodo.sucesores(nodoActual.getEstado(), nodoActual, p.acciones(nodoActual.getEstado()));
            nodosCreados += sucesores.length;

            for (Nodo sucesor : sucesores) {
                // Comprobamos si este estado ya está explorado o en la frontera
                if (!explorados.contains(sucesor.getEstado()) && !contieneFrontera(frontera, sucesor.getEstado())) {
                    // Comprobamos si es meta antes de añadirlo a la frontera
                    if (p.esMeta(sucesor.getEstado())) {
                        System.out.println((i++) + " - FIN:\n" + sucesor.getEstado() + '\n');
                        System.out.println("Nodos expandidos: " + nodosExpandidos);
                        System.out.println("Nodos creados: " + nodosCreados);
                        return Nodo.reconstruyeSol(sucesor);
                    }

                    System.out.println((i++) + " - NO explorado:\n" + sucesor.getEstado());
                    frontera.add(sucesor);
                } else {
                    System.out.println((i++) + " - YA explorado o en frontera:\n" + sucesor.getEstado());
                }
            }
        }
        System.out.println("Nodos expandidos: " + nodosExpandidos);
        System.out.println("Nodos creados: " + nodosCreados);

        throw new Exception("No se ha podido encontrar solución");
    }

    // Función auxiliar para comprobar si un estado está en la frontera
    private boolean contieneFrontera(Queue<Nodo> frontera, Estado estado) {
        for (Nodo nodo : frontera) {
            if (nodo.getEstado().equals(estado)) {
                return true;
            }
        }
        return false;
    }
}