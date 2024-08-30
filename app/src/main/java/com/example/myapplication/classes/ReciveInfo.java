package com.example.myapplication.classes;

public class ReciveInfo {
    private String message;
    private long tiempoTarea;
    private boolean validoReporte;
    private long tiempoEjecucionServicio;

    public ReciveInfo(String message, long tiempoTarea, boolean validoReporte, long tiempoEjecucionServicio) {
        this.message = message;
        this.tiempoTarea = tiempoTarea;
        this.validoReporte = validoReporte;
        this.tiempoEjecucionServicio = tiempoEjecucionServicio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTiempoTarea() {
        return tiempoTarea;
    }

    public void setTiempoTarea(long tiempoTarea) {
        this.tiempoTarea = tiempoTarea;
    }

    public boolean isValidoReporte() {
        return validoReporte;
    }

    public void setValidoReporte(boolean validoReporte) {
        this.validoReporte = validoReporte;
    }

    public long getTiempoEjecucionServicio() {
        return tiempoEjecucionServicio;
    }

    public void setTiempoEjecucionServicio(long tiempoEjecucionServicio) {
        this.tiempoEjecucionServicio = tiempoEjecucionServicio;
    }

    @Override
    public String toString() {
        return "ReciveInfo{" +
                "message='" + message + '\'' +
                ", tiempoTarea=" + tiempoTarea +
                ", validoReporte=" + validoReporte +
                ", tiempoEjecucionServicio=" + tiempoEjecucionServicio +
                '}';
    }
}
