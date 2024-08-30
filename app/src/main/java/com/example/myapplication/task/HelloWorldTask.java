package com.example.myapplication.task;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.example.myapplication.classes.Connections;
import com.example.myapplication.classes.ReciveInfo;
import com.example.myapplication.classes.ResponseHttp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HelloWorldTask implements Runnable {
    private MediaRecorder recorder;
    private String audioPath;
    Connections connections ;

    public HelloWorldTask(Connections connections) {
        this.connections = connections;
    }

    @Override
    public void run() {

            System.out.println("El reporte es valido");
            startRecording();

    }



    private void startRecording() {
        ReciveInfo reciveInfo = connections.getReciveInfo();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "audio_" + "timeStamp" + ".ogg";
        audioPath = "/data/data/com.example.myapplication" + "/" + fileName;
//        filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
        if (recorder != null) {
            recorder.reset();
            recorder.release();
        }
        System.out.println("filePath: " + audioPath);
        recorder = new MediaRecorder();

        //obtener numero SIM
        String numeroSim = "";



        //if version de android mayor a 29
        if (android.os.Build.VERSION.SDK_INT > 29) {
            // Usar la API de Scoped Storage para acceder a archivos
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.OGG);
            recorder.setOutputFile(audioPath);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.OPUS);
        } else {
            // Usar la API antigua para acceder a archivos
            recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(audioPath);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        }

        try {
            File file = new File(audioPath);
            if (!file.exists()){
                System.out.println("Creando nuevo File: " + fileName);
                file.createNewFile();
            }else {
                System.out.println("Existe el File: " + fileName);
            }
            recorder.prepare();
            recorder.start();



            int segundos = reciveInfo != null ? (int) (reciveInfo.getTiempoTarea() ) * 1000 : 10 * 1000;
            System.out.println("Segundos " + segundos / 1000);
            try {
                Thread.sleep(segundos); // Grabar durante 1 minuto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (recorder != null) {
                recorder.stop();
                recorder.release();
                recorder = null;
            }
            connections.sendPostRequest(audioPath);

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void startActivity(Intent intent) {
    }

    private void stopRecording() {

    }
}