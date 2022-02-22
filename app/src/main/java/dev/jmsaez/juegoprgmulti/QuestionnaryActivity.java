package dev.jmsaez.juegoprgmulti;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class QuestionnaryActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Definimos los componentes del layout de la activity
    TextView tvBlue, tvRed, tvYellow, tvBlack, tvGreen, tvResult;
    Spinner spinner, spinner1, spinner2, spinner3, spinner4;
    Button btRespose, btMain, btPicture;

    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnary);


        //Definimos los mediaplayer de los sonidos junto con su volumen
        MediaPlayer mp = MediaPlayer.create(this, R.raw.win);
        mp.setVolume(1f, 1f);
        MediaPlayer mp1 = MediaPlayer.create(this, R.raw.lose);
        mp1.setVolume(1f, 1f);

        //Instanciamos las variables de la clase
        tvBlue = findViewById(R.id.tvBlue);
        tvRed = findViewById(R.id.tvRed);
        tvYellow = findViewById(R.id.tvYellow);
        tvBlack = findViewById(R.id.tvBlack);
        tvGreen = findViewById(R.id.tvGreen);
        tvResult = findViewById(R.id.tvResult);

        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner2);
        spinner2 = findViewById(R.id.spinner3);
        spinner3 = findViewById(R.id.spinner4);
        spinner4 = findViewById(R.id.spinner5);

        ivPhoto = findViewById(R.id.ivPhoto);

        btRespose = findViewById(R.id.btResponse);
        btMain = findViewById(R.id.btMain);
        btPicture = findViewById(R.id.button);
        btPicture.setVisibility(View.GONE);

        //Listener del boton para volver al menu principal
        btMain.setOnClickListener(e ->{
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });



        //Array junto con su adapter para ponerlo en los spinner de la GUI
        Integer [] nums = {1, 2, 3, 4, 5};
        ArrayAdapter adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, nums);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);

        //Bundle del que recogemos un entero que nos indica desde que dificulta provenimos para
        //cargar un layout u otro
        Bundle bundle = getIntent().getExtras();

        int difficulty = bundle.getInt("difficulty");

        switch (difficulty){
            case 1:
                hideEasy();
                break;
            case 2:
                hideMedium();
                break;
            default:
                break;
        }

        //Boton respuesta con un listener, donde en el momento de hacer click comprueba los resultados
        //y muestra un resultado junto con su musica correspondiente
        btRespose.setOnClickListener(e ->{
            switch (difficulty){
                case 1:
                    int resp1, resp2;
                    resp1 = (Integer) spinner.getSelectedItem();
                    resp2 = (Integer) spinner1.getSelectedItem();
                    if(resp1 == 3 && resp2 == 2) {
                        hideAll();
                        btPicture.setVisibility(View.VISIBLE);
                        btPicture.setOnClickListener( l->{
                            checkPermission();
                            launchCamera();
                        });
                        mp.start();
                        tvResult.setVisibility(View.VISIBLE);
                        tvResult.setText("You Win!");
                        tvResult.setTextColor(Color.GREEN);
                        btMain.setVisibility(View.VISIBLE);
                    } else {
                        mp1.start();
                        hideAll();
                        tvResult.setVisibility(View.VISIBLE);
                        tvResult.setText("You Lose!");
                        tvResult.setTextColor(Color.RED);
                        btMain.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    int blue, red, yellow;
                    blue = (Integer) spinner.getSelectedItem();
                    red = (Integer) spinner1.getSelectedItem();
                    yellow = (Integer) spinner2.getSelectedItem();
                    if(red == 3 && blue == 3 && yellow == 2) {
                        hideAll();
                        btPicture.setVisibility(View.GONE);
                        btPicture.setOnClickListener( l->{
                            checkPermission();
                            launchCamera();
                        });
                        mp.start();
                        tvResult.setVisibility(View.VISIBLE);
                        tvResult.setText("You Win!");
                        tvResult.setTextColor(Color.GREEN);
                        btMain.setVisibility(View.VISIBLE);
                    } else{
                        mp1.start();
                        hideAll();
                        tvResult.setVisibility(View.VISIBLE);
                        tvResult.setText("You Lose!");
                        tvResult.setTextColor(Color.RED);
                        btMain.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    int respBlue, respRed, respYellow, respBlack, respGreen;
                    respBlue = (Integer) spinner.getSelectedItem();
                    respRed = (Integer) spinner1.getSelectedItem();
                    respYellow = (Integer) spinner2.getSelectedItem();
                    respBlack = (Integer) spinner3.getSelectedItem();
                    respGreen = (Integer) spinner4.getSelectedItem();
                    if(respRed == 3 && respBlue == 3 && respYellow == 2 && respBlack == 1 && respGreen == 1) {
                        hideAll();
                        btPicture.setVisibility(View.GONE);
                        btPicture.setOnClickListener( l->{
                            checkPermission();
                            launchCamera();
                        });
                        mp.start();
                        tvResult.setVisibility(View.VISIBLE);
                        tvResult.setText("You Win!");
                        tvResult.setTextColor(Color.GREEN);
                        btMain.setVisibility(View.VISIBLE);
                    } else{
                        mp1.start();
                        hideAll();
                        tvResult.setVisibility(View.VISIBLE);
                        tvResult.setText("You Lose!");
                        tvResult.setTextColor(Color.RED);
                        btMain.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    //Metodos que nos permiten cargar un layout u otro
    //region Hide Methods to load layout
    void hideEasy(){
        tvYellow.setVisibility(View.GONE);
        tvBlack.setVisibility(View.GONE);
        tvGreen.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        spinner3.setVisibility(View.GONE);
        spinner4.setVisibility(View.GONE);
    }

    void hideMedium(){
        tvBlack.setVisibility(View.GONE);
        tvGreen.setVisibility(View.GONE);
        spinner3.setVisibility(View.GONE);
        spinner4.setVisibility(View.GONE);
    }

    void hideAll(){
        tvBlue.setVisibility(View.GONE);
        tvRed.setVisibility(View.GONE);
        tvYellow.setVisibility(View.GONE);
        tvBlack.setVisibility(View.GONE);
        tvGreen.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        spinner3.setVisibility(View.GONE);
        spinner4.setVisibility(View.GONE);
        btRespose.setVisibility(View.GONE);
    }
    //endregion

    void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }


    }

    void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Bitmap btmap = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(btmap);
        }
    }



}