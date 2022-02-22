package dev.jmsaez.juegoprgmulti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaramos los objetos mediaplayer para reproducir los sonidos deseados
        MediaPlayer mp = MediaPlayer.create(this, R.raw.okay_les_go);
        MediaPlayer mp2 = MediaPlayer.create(this, R.raw.main_music);
        MediaPlayer mp3 = MediaPlayer.create(this, R.raw.background_music);

        //Definimos los componentes de la interfaz
        Button btEasy, btMedium, btHard;


        btEasy = findViewById(R.id.btEasy);
        btHard = findViewById(R.id.btHard);
        btMedium = findViewById(R.id.btMedium);

        mp.setVolume(1f, 1f);
        mp2.setVolume(1f, 1f);
        mp3.setVolume(1f, 1f);

        mp2.start();

        //Creamos una lambda con cada uno de los botones

        /*
        *
        * Dentro de cada lambda, se reproduce el sonido del boton, y con un listener del mediaplayer,
        * cuando este acaba, comienza el juego y la reproduccion de la musica de fondo junto con el juego
        *
        * */
        btEasy.setOnClickListener((View v)->{
            mp2.stop();
            mp.start();
            mp.setOnCompletionListener( e->{
                mp3.start();
                GameBall game = new GameBall(this, 1);
                setContentView(game);
                btEasy.setVisibility(View.GONE);
                btHard.setVisibility(View.GONE);
                btMedium.setVisibility(View.GONE);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mp3.stop();
                    Intent i = new Intent(this, QuestionnaryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("difficulty", 1);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }, 15000);
            });
        });

        btMedium.setOnClickListener((View v)->{
            mp2.stop();
            mp.start();
            mp.setOnCompletionListener( e->{
                mp3.start();
                GameBall game = new GameBall(this, 2);
                setContentView(game);
                btEasy.setVisibility(View.GONE);
                btHard.setVisibility(View.GONE);
                btMedium.setVisibility(View.GONE);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mp3.stop();
                    Intent i = new Intent(this, QuestionnaryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("difficulty", 2);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }, 15000);
            });
        });

        btHard.setOnClickListener((View v)->{
            mp2.stop();
            mp.start();
            mp.setOnCompletionListener( e->{
                mp3.start();
                GameBall game = new GameBall(this, 3);
                setContentView(game);
                btEasy.setVisibility(View.GONE);
                btHard.setVisibility(View.GONE);
                btMedium.setVisibility(View.GONE);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mp3.stop();
                    Intent i = new Intent(this, QuestionnaryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("difficulty", 3);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }, 15000);
            });
        });


    }


    //Clase interna que se encarga de la parte de dibujar el juego segun el boton presionado
    class GameBall extends View{

        int level;
        int circleY = 100;
        int circleYBlue = 50;
        int circleYYellow = 10;
        int circleYBlack = 70;
        int circleYGreen = 30;
        int axisXRed = 50;
        int axisXBlue = 50;
        int axisXYellow = 50;
        int axisXBlack = 50;
        int axisXGreen = 50;
        Paint blue = new Paint();
        Paint red = new Paint();
        Paint yellow = new Paint();
        Paint black = new Paint();
        Paint green = new Paint();

        public GameBall(Context context, int difficulty) { //Constructor
            super(context);
            this.level = difficulty;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Random r = new Random();
            if(level == 1){ //Si el nivel es el facil, se ejecuta esta parte del codigo
                /*
                * En esta parte del codigo se dibujan 6 bolas de dos colores que caen a diferente velocidad,
                * y tras 15 segundos, se va a otra actividad donde se comprueban los resultados
                *
                */


                blue.setStyle(Paint.Style.FILL);
                blue.setColor(Color.BLUE);

                red.setStyle(Paint.Style.FILL);
                red.setColor(Color.RED);

                circleY+=10;
                if(circleY > canvas.getHeight()){
                    circleY  -=getHeight();
                    axisXRed = r.nextInt(300)+1;
                }

                circleYBlue +=20;
                if(circleYBlue > canvas.getHeight()){
                    circleYBlue -=getHeight();
                    axisXBlue = r.nextInt(500)+1;
                }
                canvas.drawCircle(axisXRed, circleY, 30, red);
                canvas.drawCircle(axisXRed + 346, circleY+10, 30, red);
                canvas.drawCircle(axisXRed + 490, circleY+5, 30, red);
                canvas.drawCircle(axisXBlue + 275, circleYBlue, 30, blue);
                canvas.drawCircle(axisXBlue + 700, circleYBlue, 30, blue);;
                invalidate();


            }

            if(level == 2){//Si el nivel seleccionado es el medio, se ejecutará esta parte de código

                /*
                 * En esta parte del codigo se dibujan 8 bolas de 3 colores que caen a diferente velocidad,
                 * y tras 15 segundos, se va a otra actividad donde se comprueban los resultados
                 *
                 */
                blue.setStyle(Paint.Style.FILL);
                blue.setColor(Color.BLUE);

                red.setStyle(Paint.Style.FILL);
                red.setColor(Color.RED);

                yellow.setStyle(Paint.Style.FILL);
                yellow.setColor(Color.YELLOW);

                circleY+=10;
                if(circleY > canvas.getHeight()){
                    circleY  -=getHeight();
                    axisXRed = r.nextInt(300)+1;
                }

                circleYBlue +=20;
                if(circleYBlue > canvas.getHeight()){
                    circleYBlue -=getHeight();
                    axisXBlue = r.nextInt(500)+1;
                }

                circleYYellow +=30;
                if(circleYYellow > canvas.getHeight()){
                    circleYYellow -=getHeight();
                    axisXYellow = r.nextInt(200)+1;
                }
                canvas.drawCircle(axisXRed, circleY, 30, red);
                canvas.drawCircle(axisXRed + 346, circleY+10, 30, red);
                canvas.drawCircle(axisXRed + 490, circleY+5, 30, red);
                canvas.drawCircle(axisXBlue + 275, circleYBlue, 30, blue);
                canvas.drawCircle(axisXBlue + 700, circleYBlue, 30, blue);
                canvas.drawCircle(axisXBlue + 790, circleYBlue, 30, blue);
                canvas.drawCircle(axisXYellow + 400, circleYYellow, 30, yellow);
                canvas.drawCircle(axisXYellow + 567, circleYYellow, 30, yellow);
                invalidate();

            }

            if(level == 3){//Si el nivel seleccionado es el dificil, se ejecutará esta parte de código

                /*
                 * En esta parte del codigo se dibujan 10 bolas de 5 colores que caen a diferente velocidad,
                 * y tras 15 segundos, se va a otra actividad donde se comprueban los resultados
                 *
                 */

                blue.setStyle(Paint.Style.FILL);
                blue.setColor(Color.BLUE);

                red.setStyle(Paint.Style.FILL);
                red.setColor(Color.RED);

                yellow.setStyle(Paint.Style.FILL);
                yellow.setColor(Color.YELLOW);

                black.setStyle(Paint.Style.FILL);
                black.setColor(Color.BLACK);

                green.setStyle(Paint.Style.FILL);
                green.setColor(Color.GREEN);

                circleY+=10;
                if(circleY > canvas.getHeight()){
                    circleY  -=getHeight();
                    axisXRed = r.nextInt(300)+1;
                }

                circleYBlue +=20;
                if(circleYBlue > canvas.getHeight()){
                    circleYBlue -=getHeight();
                    axisXBlue = r.nextInt(500)+1;
                }

                circleYYellow +=30;
                if(circleYYellow > canvas.getHeight()){
                    circleYYellow -=getHeight();
                    axisXYellow = r.nextInt(200)+1;
                }

                circleYBlack +=10;
                if(circleYBlack> canvas.getHeight()){
                    circleYBlack -=getHeight();
                    axisXBlack = r.nextInt(175)+1;
                }

                circleYGreen +=24;
                if(circleYGreen > canvas.getHeight()){
                    circleYGreen -=getHeight();
                    axisXGreen = r.nextInt(50)+1;
                }
                canvas.drawCircle(axisXRed, circleY, 30, red);
                canvas.drawCircle(axisXRed + 346, circleY+10, 30, red);
                canvas.drawCircle(axisXRed + 490, circleY+5, 30, red);
                canvas.drawCircle(axisXBlue + 275, circleYBlue, 30, blue);
                canvas.drawCircle(axisXBlue + 799, circleYBlue, 30, blue);
                canvas.drawCircle(axisXBlue + 700, circleYBlue, 30, blue);
                canvas.drawCircle(axisXYellow + 400, circleYYellow, 30, yellow);
                canvas.drawCircle(axisXYellow + 567, circleYYellow, 30, yellow);
                canvas.drawCircle(axisXBlack + 148, circleYBlack, 30, black);
                canvas.drawCircle(axisXGreen + 800, circleYGreen, 30, green);
                invalidate();

            }

        }
    }
}