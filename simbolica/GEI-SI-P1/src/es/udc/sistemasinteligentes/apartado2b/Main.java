package es.udc.sistemasinteligentes.apartado2b;

import es.udc.sistemasinteligentes.*;
import es.udc.sistemasinteligentes.apartado2a.ProblemaCuadradoMagico;

public class Main {

    public static void main(String[] args) throws Exception {
        int[][] estado0 = {{4, 9, 2},
                           {3, 5, 0},
                           {0, 1, 0}},
                estado1 = {{2, 0, 0},
                           {0, 0, 0},
                           {0, 0, 0}},
                estado2 = {{2, 0, 15, 0},
                           {0, 0, 5, 3},
                           {0, 0, 0, 0},
                           {0, 1, 0, 16}};

        // Definimos el problema inicial
        ProblemaCuadradoMagico.EstadoCuadrado estadoInicial =
                new ProblemaCuadradoMagico.EstadoCuadrado(estado1);

        // Creamos la heuristica
        Heuristica heuristica = new HeuristicaCuadradoMagico();

        // Creamos un nuevo problema
        ProblemaBusqueda cuadradoMagico = new ProblemaCuadradoMagico(estadoInicial);

        // Búsqueda en profundidad
        EstrategiaBusquedaInformada buscador = new EstretegiaBusquedaA();

        Nodo[] sol = buscador.soluciona(cuadradoMagico, heuristica);

        int ind = 0;
        System.out.println("################## SOLUCIÓN ##################");
        for (int i = sol.length - 1; i >= 0; i--) {
            Nodo n = sol[i];
            System.out.println("Nodo: " + ind++);
            System.out.print(n.toString());
        }
    }
}