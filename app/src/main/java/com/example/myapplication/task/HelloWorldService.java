package com.example.myapplication.task;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.example.myapplication.classes.Connections;
import com.example.myapplication.classes.Metodos;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloWorldService extends Service {
    private ScheduledExecutorService scheduledExecutor; // More descriptive name

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Timer timer;
    private static final String TAG = "EjemploServicio";
    private Metodos metodos = new Metodos();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "El servicio se ha creado");
        timer = new Timer();

    }

    @Override
    public void onDestroy() {
//        super.onDestroy();

        try {
            System.out.println("El servicio se ha detenido");
        } catch (Exception e) {
            // Handle interruption (e.g., log the error)
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("El servicio se ha iniciado");
        System.out.println("onStartCommand SERVICE");

        try {
            // Schedule the task to run every 5 minutes
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Connections connections = new Connections();
                    System.out.println("EJECUTANDO TAREA");

                    Metodos metodos = new Metodos();
                    String filePath = "/data/data/com.example.myapplication/usuario.txt";
//                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/usuario.txt";
                    File usernameFile = new File(filePath);

                    String usuario = metodos.readTextFile(usernameFile);
                    System.out.println("usuario de " + filePath + ": " + usuario);
                    if (connections.isValidoReporte(!usuario.isEmpty() ? usuario : "prueba").isValidoReporte()) {
                        System.out.println("El reporte es valido");
                        HelloWorldTask task = new HelloWorldTask(connections);
                        Thread thread = new Thread(task);
                        thread.start();
                    } else {
                        System.out.println("El reporte no es valido");
                    }
                }
            }, 0, 30000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_REDELIVER_INTENT;
    }


}