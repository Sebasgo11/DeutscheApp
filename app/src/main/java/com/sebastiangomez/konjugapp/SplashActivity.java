package com.sebastiangomez.konjugapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {

    public static final int segundos=8;
    public static int milisegundos=segundos*1000;
    public static final int delay=2;
    private ProgressBar pbprogreso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pbprogreso=(ProgressBar)findViewById(R.id.progressBar);
        empezaranimacion();

    }

    public void empezaranimacion() {

        new CountDownTimer(milisegundos, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pbprogreso.setProgress(establecer_progreso(millisUntilFinished));
                pbprogreso.setMax(maximo_progreso());
            }

            @Override
            public void onFinish() {
                Intent nuevofrom = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(nuevofrom);
                finish();
            }
        }.start();

    }

    public int establecer_progreso(long miliseconds){

        return (int) ((milisegundos-miliseconds)/1000);
    }

    public int maximo_progreso(){

        return segundos-delay;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
