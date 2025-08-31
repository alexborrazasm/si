package es.udc.sistemasinteligentes.apartado2a;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ProblemaCuadradoMagico extends ProblemaBusqueda {

    public static class EstadoCuadrado extends Estado {
        private final int[][] estado; // Matrix que representa el estado

        public EstadoCuadrado(int[][] estado) {
            this.estado = estado;
        }

        public int[][] getEstado() {
            return estado;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int[] fila : estado) {
                builder.append("[");
                for (int elemento : fila) {
                    // Alinear los números para que ocupen siempre 2 espacios
                    builder.append(String.format(" %2d", elemento));
                }
                builder.append("  ]\n");
            }
            // Borrar el último \n
            builder.deleteCharAt(builder.length() - 1);

            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EstadoCuadrado that = (EstadoCuadrado) o;
            return Objects.deepEquals(estado, that.estado);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(estado);
        }
    }

    public static class AccionCuadrado extends Accion {
        private final int x, y, valor;

        public AccionCuadrado(int x, int y, int valor) {
            this.x = x;
            this.y = y;
            this.valor = valor;
        }

        @Override
        public String toString() {
            return "Casilla ["+ x + "][" + y + "] -> " + valor;
        }

        // Un estado es aplicable si la casilla selecionada vale 0
        @Override
        public Estado aplicaA(Estado es) {
            EstadoCuadrado cuadrado = (EstadoCuadrado) es;
            int[][] estado = cuadrado.getEstado();
            int[][] newEstado = new int[estado.length][estado[0].length];

            for (int i = 0; i < estado.length; i++) {
                // Copia cada fila individualmente
                newEstado[i] = estado[i].clone();
            }

            newEstado[this.x][this.y] = valor;

            return new EstadoCuadrado(newEstado);
        }

        @Override
        public boolean esAplicable(Estado es) {
            EstadoCuadrado cuadrado = (EstadoCuadrado) es;

            if (cuadrado.estado[x][y] == 0)
                return true;

            return false;
        }
    }

    public ProblemaCuadradoMagico(Estado estadoInicial) {
        super(estadoInicial);
    }

    @Override
    public boolean esMeta(Estado es) { // Comparamos si una matriz es meta
        EstadoCuadrado cuadrado = (EstadoCuadrado) es;
        int[][] matriz = cuadrado.getEstado();

        // Verificar que la matriz sea cuadrada
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            if (matriz[i].length != n) {
                return false; // No es una matriz cuadrada
            }
        }

        // Calcular la constante mágica esperada: n*[n*n +1] /2
        int constanteMagica = n * (n * n + 1) / 2;

        // Verificar sumas de filas
        for (int i = 0; i < n; i++) {
            int sumaFila = 0;
            for (int j = 0; j < n; j++) {
                sumaFila += matriz[i][j];
            }
            if (sumaFila != constanteMagica) {
                return false;
            }
        }

        // Verificar sumas de columnas
        for (int j = 0; j < n; j++) {
            int sumaColumna = 0;
            for (int i = 0; i < n; i++) {
                sumaColumna += matriz[i][j];
            }
            if (sumaColumna != constanteMagica) {
                return false;
            }
        }

        // Verificar suma de diagonal principal (de arriba-izquierda a abajo-derecha)
        int sumaDiagonalPrincipal = 0;
        for (int i = 0; i < n; i++) {
            sumaDiagonalPrincipal += matriz[i][i];
        }
        if (sumaDiagonalPrincipal != constanteMagica) {
            return false;
        }

        // Verificar suma de diagonal secundaria (de arriba-derecha a abajo-izquierda)
        int sumaDiagonalSecundaria = 0;
        for (int i = 0; i < n; i++) {
            sumaDiagonalSecundaria += matriz[i][n - 1 - i];
        }
        if (sumaDiagonalSecundaria != constanteMagica) {
            return false;
        }

        // Verificar que todos los números del 1 al n² estén presentes
        for (int i = 1; i <= (n * n); i++) {
            if (!contiene(matriz, i)) {
                return false;
            }
        }

        // Si todas las verificaciones pasan, es un cuadrado mágico
        return true;
    }

    // Función auxiliar para verificar si un número está presente en la matriz
    private boolean contiene(int[][] arr, int z) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == z) {
                    return true;
                }
            }
        }
        return false;
    }
    /*
     * Generamos las acciones posibles buscando la primera posición en blanco y
     * rellenando con todos los números de 1 a N^2
     */
    @Override
    public Accion[] acciones(Estado es) {
        int x=0, y=0;
        boolean encontrado = false;
        EstadoCuadrado cuadrado = (EstadoCuadrado) es;
        ArrayList<AccionCuadrado> acciones= new ArrayList<>();
        int [][] estado= cuadrado.estado;
        int lim = (int)Math.pow(estado.length,2);

        for(int i = 0; i < estado.length; i++){
            for(int j = 0; j< estado[i].length; j++){
                if(estado[i][j] == 0 && !encontrado){
                    x = i;
                    y = j;
                    encontrado = true;
                }
            }
        }
        if(!encontrado) {
            return new Accion[0];
        } else {
            for(int i = 1; i <= lim; i++){
                AccionCuadrado acc = new AccionCuadrado(x, y, i);
                if(acc.esAplicable(es)) {
                    acciones.add(acc);
                }
            }
        }
        Accion[] array = new Accion[acciones.size()];
        acciones.toArray(array);
        return array;
    }
}
