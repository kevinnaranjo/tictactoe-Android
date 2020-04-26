package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int jugadores;
    private int [] CASILLAS;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cargamos las casillas en el array
        CASILLAS = new int[9];
        CASILLAS[0]=R.id.a1;
        CASILLAS[1]=R.id.a2;
        CASILLAS[2]=R.id.a3;
        CASILLAS[3]=R.id.b1;
        CASILLAS[4]=R.id.b2;
        CASILLAS[5]=R.id.b3;
        CASILLAS[6]=R.id.c1;
        CASILLAS[7]=R.id.c2;
        CASILLAS[8]=R.id.c3;
    }

    public void comenzarJuego(View view) {
        //Limpio el tablero
        ImageView imagen;
        for (int casilla:
             CASILLAS) {
            imagen = (ImageView)findViewById(casilla);
            imagen.setImageResource(R.drawable.casilla);
        }

        //Miro que boton se pulsó para saber el num de jugadores
        jugadores = 1;
        if(view.getId() == R.id.btnDosJugadores) jugadores = 2;

        //Averiguo la dificultad
        int dificultad = calcularDificultad();

        //Comienzo la partida
        partida = new Partida(dificultad);

        //Deshabilito los botones
        deshabilitarBotonera();

    }

    public void onPulsarCasilla(View view) {
        if (partida != null) {
            int casilla = 0;
            for (int i = 0; i < 9; i++) {
                if (CASILLAS[i] == view.getId()) {
                    casilla = i;
                    break;
                }
            }

            if (partida.estaMarcada(casilla)) return;
            //pinta la casilla que pulso
            pintarCasilla(casilla);

           int resultado =partida.turno();
            decideEstadoDelJuego(resultado);
            if(resultado > 0) return;

            if (jugadores == 1) {
                //pinta la casilla la ia
                casilla = partida.ia();
                while (partida.estaMarcada(casilla)) {
                    casilla = partida.ia();
                }
                pintarCasilla(casilla);
                decideEstadoDelJuego(partida.turno());
            }

        }
    }

    private void terminarPartida(int resultado) {
        String mensaje;
        if (resultado == 1) mensaje = "Circles wins";
        else  if (resultado == 2) mensaje = "Crosses wins";
        else mensaje = "Tie";
        mostrarMensajeResultado(mensaje);
    }

    private void mostrarMensajeResultado(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        partida = null;
        habilitarBotonera();
    }

    private void decideEstadoDelJuego(int resultado) {
        if (resultado > 0) terminarPartida(resultado);
    }

    private void pintarCasilla(int casilla) {
        ImageView imagen;
        imagen = (ImageView)findViewById(CASILLAS[casilla]);
        if (partida.jugador == 1) {
            imagen.setImageResource(R.drawable.circulo);
        } else {
            imagen.setImageResource(R.drawable.aspa);
        }
    }

    private int calcularDificultad() {
        RadioGroup dificultades = (RadioGroup) findViewById(R.id.dificultadesGroup);
        int id = dificultades.getCheckedRadioButtonId();
        int dificultad = 0;
        if (id == R.id.radioNormal) dificultad = 1;
        if (id == R.id.radioDificil) dificultad = 2;

        return dificultad;
    }
    private void deshabilitarBotonera() {
        ((Button)findViewById(R.id.btnUnJugador)).setEnabled(false);
        ((Button)findViewById(R.id.btnDosJugadores)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.dificultadesGroup)).setAlpha(0);
    }

    private void habilitarBotonera() {
        ((Button)findViewById(R.id.btnUnJugador)).setEnabled(true);
        ((Button)findViewById(R.id.btnDosJugadores)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.dificultadesGroup)).setAlpha(1);
    }
}
