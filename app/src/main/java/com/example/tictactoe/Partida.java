package com.example.tictactoe;

import java.util.Random;

public class Partida {
    //él lo tiene public por si falla algo probar a cambiarlo
    public final int dificultad;
    public int jugador;
    private int [] casillasMarcadas;
    private final int [][] COMBINACIONES =
            {
                    {0,1,2},
                    {3,4,5},
                    {6,7,8},
                    {0,3,6},
                    {1,4,7},
                    {2,5,8},
                    {0,4,8},
                    {2,4,6}
            };

    public Partida(int dificultad) {
        this.dificultad = dificultad;
        jugador = 1;
        casillasMarcadas = new int[9];
        for (int i = 0; i < casillasMarcadas.length; i++) {
            casillasMarcadas[i] = 0;
        }
    }

    public boolean estaMarcada(int casilla) {
        if (casillasMarcadas[casilla] != 0) return true;

        casillasMarcadas[casilla] = jugador;
        return false;
    }

    public int dosEnRaya(int jugadorEnTurno) {
        int casilla = -1; //casía que habría que marcar para conseguir 3 en raya
        int cuantas_lleva = 0;

        for (int i = 0; i < COMBINACIONES.length; i++) {
            for (int pos :
                    COMBINACIONES[i]) {
                    if (casillasMarcadas[pos] == jugadorEnTurno) cuantas_lleva++;
                    if(casillasMarcadas[pos] == 0) casilla = pos;
            }
            if(cuantas_lleva == 2 && casilla!=-1) return casilla;

            casilla = -1;
            cuantas_lleva = 0;
        }
        return -1;
    }

    public int ia() {
        int casilla;

        casilla = dosEnRaya(2);
        if (casilla!= -1) return casilla;

        if (dificultad > 0) {
            casilla = dosEnRaya(1);
            if (casilla != -1 ) return casilla;
        }

        if (dificultad == 2) {
            if (casillasMarcadas[0] == 0) return 0;
            if (casillasMarcadas[2] == 0) return 2;
            if (casillasMarcadas[6] == 0) return 6;
            if (casillasMarcadas[8] == 0) return 8;
        }
        Random randomCasilla = new Random();
        casilla = randomCasilla.nextInt(9);
        return casilla;
    }

    public int turno() {
        boolean empate = true;
        boolean ult_movimiento = true;

        for (int i = 0; i < COMBINACIONES.length; i++) {
            for (int pos :
                    COMBINACIONES[i]) {
                if (casillasMarcadas[pos] != jugador) ult_movimiento = false;

                if (casillasMarcadas[pos] == 0) empate = false;
            }
            if(ult_movimiento) return jugador;
            ult_movimiento = true;
        }

        if(empate) return 3;
        jugador++;
        if (jugador>2) jugador=1;

        return 0;
    }

}
