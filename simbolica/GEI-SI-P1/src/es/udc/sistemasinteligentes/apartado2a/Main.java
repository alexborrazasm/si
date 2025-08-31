package es.udc.sistemasinteligentes.apartado2a;
import es.udc.sistemasinteligentes.*;

public class Main {

    public static void main(String[] args) throws Exception {
        int[][] estado0 = {{4, 9, 2},
                           {3, 5, 0},
                           {0, 1, 0}},
                estado1 = {{2, 9, 0},
                           {7, 5, 3},
                           {0, 1, 0}};


        // Definimos el problema inicial
        ProblemaCuadradoMagico.EstadoCuadrado estadoInicial =
                new ProblemaCuadradoMagico.EstadoCuadrado(estado0);

        // Creamos un nuevo problema
        ProblemaBusqueda cuadradoMagico = new ProblemaCuadradoMagico(estadoInicial);

        // Búsqueda en anchura
        //EstrategiaBusqueda buscador = new EstrategiaBusquedaAnchura();
        // Búsqueda en profundidad
        EstrategiaBusqueda buscador = new EstrategiaBusquedaProfundidad();

        Nodo[] sol = buscador.soluciona(cuadradoMagico);

        int ind = 0;
        System.out.println("################## SOLUCIÓN ##################");
        for (int i = sol.length - 1; i >= 0; i--) {
            Nodo n = sol[i];
            System.out.println("Nodo: " + ind++);
            System.out.print(n.toString());
        }
    }
}