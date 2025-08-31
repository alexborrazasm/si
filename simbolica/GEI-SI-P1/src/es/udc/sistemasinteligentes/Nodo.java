package es.udc.sistemasinteligentes;

import java.util.ArrayList;

public class Nodo implements Comparable<Nodo> {
    private final Estado e;
    private final Nodo padre;
    private final Accion accion;
    private float coste;
    private float funcion;

    public Nodo(Estado e, Nodo padre, Accion accion) {
        this.e = e;
        this.padre = padre;
        this.accion = accion;
    }

    public Estado getEstado() {
        return e;
    }

    public Nodo getPadre() {
        return padre;
    }

    public Accion getAccion() {
        return accion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estado anterior:\n").append(padre != null ? padre.getEstado().toString() : "null").append('\n');
        sb.append("Acción: ").append(accion != null ? accion.toString() : "null").append('\n');
        sb.append("Estado:\n").append(e != null ? e.toString() : "null").append('\n');
        sb.append("---------------------------------------------\n");
        return sb.toString();
    }

    public static Nodo[] reconstruyeSol(Nodo nodo) {
        ArrayList<Nodo> sol = new ArrayList<>();
        Nodo actual = nodo;

        while (actual != null) {
            sol.add(actual);
            actual = actual.getPadre();
        }

        Nodo[] arraySol = new Nodo[sol.size()];

        sol.toArray(arraySol);
        return arraySol;
    }

    public static Nodo[] sucesores(Estado e, Nodo p, Accion[] accionesDisponibles) {
        ArrayList<Nodo> sucesores = new ArrayList<Nodo>();

        for (Accion acc : accionesDisponibles) {
            sucesores.add(new Nodo(acc.aplicaA(e), p, acc));
        }
        Nodo[] arraySol = new Nodo[sucesores.size()];

        sucesores.toArray(arraySol);
        return arraySol;
    }

    public void setCoste(float coste) {
        this.coste = coste;
    }

    public float getCoste() {
        return coste;
    }

    public void setFuncion(float funcion) {
        this.funcion = funcion;
    }

    public float getFuncion() {
        return funcion;
    }

    @Override
    public int compareTo(Nodo o) {
        return (int) (this.funcion - o.funcion);
    }
}
