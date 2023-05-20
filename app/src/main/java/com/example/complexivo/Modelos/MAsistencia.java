package com.example.complexivo.Modelos;

import java.io.Serializable;
import java.time.LocalDate;

public class MAsistencia implements Serializable {

    private int idAsistencia;
    private String fechaAsistencia;
    private Boolean estadoAsistencia;
    private String observacionAsistencia;
    private Boolean estadoSubida;
    private String estadoActual;


    private MParticipante mParticipante;

    public MAsistencia() {
    }

    public MAsistencia(int idAsistencia, String fechaAsistencia, Boolean estadoAsistencia, String observacionAsistencia, Boolean estadoSubida, String estadoActual, MParticipante mParticipante) {
        this.idAsistencia = idAsistencia;
        this.fechaAsistencia = fechaAsistencia;
        this.estadoAsistencia = estadoAsistencia;
        this.observacionAsistencia = observacionAsistencia;
        this.estadoSubida = estadoSubida;
        this.estadoActual = estadoActual;
        this.mParticipante = mParticipante;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public String getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(String fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public Boolean getEstadoAsistencia() {
        return estadoAsistencia;
    }

    public void setEstadoAsistencia(Boolean estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }

    public String getObservacionAsistencia() {
        return observacionAsistencia;
    }

    public void setObservacionAsistencia(String observacionAsistencia) {
        this.observacionAsistencia = observacionAsistencia;
    }

    public Boolean getEstadoSubida() {
        return estadoSubida;
    }

    public void setEstadoSubida(Boolean estadoSubida) {
        this.estadoSubida = estadoSubida;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public MParticipante getmParticipante() {
        return mParticipante;
    }

    public void setmParticipante(MParticipante mParticipante) {
        this.mParticipante = mParticipante;
    }
}