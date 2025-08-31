package es.udc.sistemasinteligentes.apartado2a;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;
import java.util.Stack;

public class EstrategiaBusquedaProfundidad implements EstrategiaBusqueda {

    /*
     * Implementa búsqueda en profundidad
     */
    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {
        Stack<Nodo> frontera = new Stack<>(); // Pila LIFO para búsqueda en profundidad
        ArrayList<Estado> explorados = new ArrayList<>(); // Lista de estados ya explorados

        int i = 0;
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

        while (!frontera.isEmpty()) {
            // Sacamos el primer elemento de la frontera (último añadido en DFS)
            nodoActual = frontera.pop();
            nodosExpandidos++;
            Estado estadoActual = nodoActual.getEstado();
            System.out.println((i++) + " - Estado actual cambiado a:\n" + estadoActual);

            // Comprobamos si es meta antes de añadirlo a explorados
            if (p.esMeta(estadoActual)) {
                System.out.println((i++) + " - FIN:\n" + estadoActual + '\n');
                System.out.println("Nodos expandidos: " + nodosExpandidos);
                System.out.println("Nodos creados: " + nodosCreados);
                return Nodo.reconstruyeSol(nodoActual);
            }

            // Añadimos a explorados después de comprobar si es meta
            explorados.add(estadoActual);
            System.out.println((i++) + " - NO es META:\n" + estadoActual);

            // Generamos los sucesores
            Accion[] acciones = p.acciones(estadoActual);
            Nodo[] sucesores = Nodo.sucesores(estadoActual, nodoActual, acciones);
            nodosCreados += sucesores.length;

            // Añadimos los sucesores a la frontera
            for (Nodo sucesor : sucesores) {
                // Comprobamos si este estado ya está explorado o en la frontera
                if (!explorados.contains(sucesor.getEstado()) && !contieneFrontera(frontera, sucesor.getEstado())) {
                    System.out.println((i++) + " - NO explorado:\n" + sucesor.getEstado());
                    frontera.push(sucesor);
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
    private boolean contieneFrontera(Stack<Nodo> frontera, Estado estado) {
        for (Nodo nodo : frontera) {
            if (nodo.getEstado().equals(estado)) {
                return true;
            }
        }
        return false;
    }
}
