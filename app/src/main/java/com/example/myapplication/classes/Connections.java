package com.example.myapplication.classes;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

public class Connections {
    ResponseHttp responseHttp = new ResponseHttp();
    ReciveInfo reciveInfo;
    String host = "http://186.53.1.168:3000";

    public ReciveInfo getReciveInfo() {
        return reciveInfo;
    }

    public void setReciveInfo(ReciveInfo reciveInfo) {
        this.reciveInfo = reciveInfo;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ResponseHttp isValidoReporte(String usuario) {

        System.out.println("isValidoReporte");
        try{
            URL url = new URL(host + "/reportar");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("username", usuario);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());

            outputStream.flush();
            outputStream.close();
            int responseCode = conn.getResponseCode();
            String responseMensaje = conn.getResponseMessage();
            System.out.println("responseCode: " + responseCode);

            Gson gson = new Gson();
            System.out.println("responseMensaje: " + conn.getHeaderField("json"));
            reciveInfo =  gson.fromJson(conn.getHeaderField("json"), ReciveInfo.class);
            System.out.println("reciveInfo: " + reciveInfo);
//            ReciveInfo persona = gson.fromJson(responseMensaje, ReciveInfo.class);

            responseHttp.setValidoReporte(responseCode == 200 && reciveInfo != null && reciveInfo.isValidoReporte());
            responseHttp.setCodigo(responseCode);
            responseHttp.setMensaje(responseMensaje + " " + reciveInfo.getMessage());

        }catch (Exception e){
            System.out.println(e);
            responseHttp.setValidoReporte(false);
        }

        System.out.println(responseHttp);
        return responseHttp;
    };


    public void sendPostRequest(String audioPath) {
        try {
            URL url = new URL(host + "/enviaraudio");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String boundary = "*****";
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("type-data", "MENSAJE_VOZ");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());

            // Escribe el contenido del archivo
            FileInputStream fileInputStream = new FileInputStream(new File(audioPath));
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();

            outputStream.flush();
            outputStream.close();
            System.out.println("Se ha enviado el archivo");

            int responseCode = conn.getResponseCode();
            String responseMensaje = conn.getResponseMessage();
            System.out.println("responseCode: " + responseCode);


            // ... (Manejar la respuesta del servidor)
            responseHttp.setValidoReporte(responseCode == 200);
            responseHttp.setCodigo(responseCode);
            responseHttp.setMensaje(responseMensaje);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
