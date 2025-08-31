package es.udc.sistemasinteligentes.apartado2b;

import es.udc.sistemasinteligentes.*;
import es.udc.sistemasinteligentes.apartado2a.ProblemaCuadradoMagico.*;

public class HeuristicaCuadradoMagico extends Heuristica {

    @Override
    public float evalua(Estado e) {
        EstadoCuadrado estadoCuadrado = (EstadoCuadrado) e;
        int[][] matriz = estadoCuadrado.getEstado();
        int n = matriz.length;
        int sumaObjetivo = (n * (n*n + 1)) / 2;
        int casillasVacias = 0;

        // Comprobar diagonales
        int sumaDiagonalPrincipal = 0;
        int sumaDiagonalSecundaria = 0;
        boolean diagonalPrincipalIncompleta = false;
        boolean diagonalSecundariaIncompleta = false;

        for (int i = 0; i < n; i++) {
            sumaDiagonalPrincipal += matriz[i][i];
            if (matriz[i][i] == 0) {
                diagonalPrincipalIncompleta = true;
            }

            sumaDiagonalSecundaria += matriz[i][n-1-i];
            if (matriz[i][n-1-i] == 0) {
                diagonalSecundariaIncompleta = true;
            }
        }

        // Si alguna diagonal está completa pero no suma el valor objetivo, el estado es inválido
        if (sumaDiagonalPrincipal != sumaObjetivo && !diagonalPrincipalIncompleta) {
            return 100000;
        }
        if (sumaDiagonalSecundaria != sumaObjetivo && !diagonalSecundariaIncompleta) {
            return 100000;
        }

        // Comprobar filas y columnas
        for (int i = 0; i < n; i++) {
            int sumaFila = 0;
            int sumaColumna = 0;
            boolean filaIncompleta = false;
            boolean columnaIncompleta = false;

            for (int j = 0; j < n; j++) {
                // Contar casillas vacías
                if (matriz[i][j] == 0) {
                    casillasVacias++;
                    filaIncompleta = true;
                }
                if (matriz[j][i] == 0) {
                    columnaIncompleta = true;
                }

                sumaFila += matriz[i][j];
                sumaColumna += matriz[j][i];
            }

            // Si alguna fila o columna está completa pero no suma el valor objetivo, el estado es inválido
            if (sumaFila != sumaObjetivo && !filaIncompleta) {
                return 100000;
            }
            if (sumaColumna != sumaObjetivo && !columnaIncompleta) {
                return 100000;
            }
        }

        return casillasVacias;
    }
}