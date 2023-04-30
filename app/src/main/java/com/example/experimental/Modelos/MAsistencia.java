package com.example.experimental.Modelos;

import java.io.Serializable;
import java.time.LocalDate;

public class MAsistencia implements Serializable {

    private int idAsistencia;
    private String fechaAsistencia;
    private Boolean estadoAsistencia;
    private String observacionAsistencia;

    public MAsistencia() {
    }

    public MAsistencia(int idAsistencia, String fechaAsistencia, Boolean estadoAsistencia, String observacionAsistencia) {
        this.idAsistencia = idAsistencia;
        this.fechaAsistencia = fechaAsistencia;
        this.estadoAsistencia = estadoAsistencia;
        this.observacionAsistencia = observacionAsistencia;
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
}
