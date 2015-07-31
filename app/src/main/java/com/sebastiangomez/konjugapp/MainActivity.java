package com.sebastiangomez.konjugapp;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    UserLocalStore userLocalStore;
    Button activar, desactivar, jugar;
    Spinner time;
    String delay;
    GifView gifView;

    final String [] Hours = new String [] {"1 Uhr","3 Uhr","5 Uhr","7 Uhr"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Hours);

        userLocalStore = new UserLocalStore(this);

        activar = (Button) findViewById(R.id.bActivar);
        desactivar = (Button) findViewById(R.id.bDesactivar);
        jugar = (Button)findViewById(R.id.bJugar);
        time = (Spinner) findViewById(R.id.spinner);
        gifView=(GifView)findViewById(R.id.gif);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(adapter);

        time.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        delay = time.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Spielen = new Intent(MainActivity.this, Spielen.class);
                startActivity(Spielen);
            }
        });


        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent ServiceIntent = new Intent(MainActivity.this, MyService.class);
               ServiceIntent.putExtra("titleKey",delay);
               startService(ServiceIntent);

            }
        });

        desactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()==true){

        }else{
            startActivity(new Intent(this,Login.class));
        }
    }

    private boolean authenticate(){
       return userLocalStore.getUserLoggedIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            userLocalStore.ClearUserData();
            userLocalStore.SetUserLoggedIn(false);
            startActivity(new Intent(this,Login.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
