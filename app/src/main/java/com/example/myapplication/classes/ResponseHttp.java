package com.example.myapplication.classes;

public class ResponseHttp {
        private String mensaje;
        private long codigo;
        private String data;
        private boolean isValidoReporte;
    public ResponseHttp() {
    }

    public ResponseHttp(String mensaje, long codigo, String data) {
            this.mensaje = mensaje;
            this.codigo = codigo;
            this.data = data;
        }

        // Getters y setters

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isValidoReporte() {
        return isValidoReporte;
    }

    public void setValidoReporte(boolean validoReporte) {
        isValidoReporte = validoReporte;
    }

    //To String


    @Override
    public String toString() {
        return "ResponseHttp{" +
                "mensaje='" + mensaje + '\'' +
                ", codigo=" + codigo +
                ", data='" + data + '\'' +
                ", isValidoReporte=" + isValidoReporte +
                '}';
    }
}
