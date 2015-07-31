package com.sebastiangomez.konjugapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {
    public MyService() {
    }

    MyTask myTask;
    String data;
    int time_delay;
    String wort;
    String bild;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Servicio creado!", Toast.LENGTH_SHORT).show();
        myTask = new MyTask();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data=(String) intent.getExtras().get("titleKey");
        Toast.makeText(this,data, Toast.LENGTH_SHORT).show();
        switch(data){
            case "1 Uhr" :{
                time_delay=3600000;
                break;
            }
            case "3 Uhr":{
                time_delay=10800000;
                break;
            }
            case "5 Uhr":{
                time_delay=18000000;
                break;
            }
            case "7 Uhr":{
                time_delay=25200000;
                break;
            }
        }
        myTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido!", Toast.LENGTH_SHORT).show();
        myTask.cancel(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private class MyTask extends AsyncTask<String, String, String> {

        private DateFormat dateFormat;
        private String date;
        private boolean cent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dateFormat = new SimpleDateFormat("HH:mm:ss");
            cent=true;
        }

        @Override
        protected String doInBackground(String... params) {
            while(cent){
                date = dateFormat.format(new Date());
                try{
                    publishProgress(date);
                    Thread.sleep(time_delay);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            doNotification();
            super.onProgressUpdate(values);
        }

        private void doNotification() {
            String title = "KonjugApp", content ="Neues Wort", ticker = "";

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

            wort="die Sonne";
            bild="sonne";

            builder.setContentTitle(title)
                    .setContentText(content)
                    .setTicker(ticker)
                    .setSmallIcon(R.drawable.ic_deutsch)
                    .setContentInfo("Wortschatz")
                    .setAutoCancel(true);

            Intent notIntent = new Intent(getApplicationContext(), ResultActivity.class);
            notIntent.putExtra("Wort",wort);
            notIntent.putExtra("Bild",bild);



            PendingIntent contIntent = PendingIntent.getActivity(getApplicationContext(), 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(contIntent);

            NotificationManager nm =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            nm.notify(1,builder.build());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cent=false;
        }

    }
}
