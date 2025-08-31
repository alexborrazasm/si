package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.Nodo;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

public class Main {

    public static void main(String[] args) throws Exception {
        ProblemaAspiradora.EstadoAspiradora estadoInicial = new ProblemaAspiradora.EstadoAspiradora(ProblemaAspiradora.EstadoAspiradora.PosicionRobot.IZQ,
                                                                                                    ProblemaAspiradora.EstadoAspiradora.PosicionBasura.AMBAS);
        ProblemaBusqueda aspiradora = new ProblemaAspiradora(estadoInicial);

        //EstrategiaBusqueda buscador = new Estrategia4(); // 1a
        EstrategiaBusqueda buscador = new EstrategiaBusquedaGrafo(); // 1b

        Nodo[] sol = buscador.soluciona(aspiradora);

        int ind = 0;
        System.out.println("################## SOLUCIÓN ##################");
        for (int i = sol.length - 1; i >= 0; i--) {
            Nodo n = sol[i];
            System.out.println("Nodo: " + ind++);
            System.out.print(n.toString());
        }
    }
}
