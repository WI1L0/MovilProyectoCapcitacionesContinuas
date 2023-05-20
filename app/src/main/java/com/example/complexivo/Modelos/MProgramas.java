package com.example.complexivo.Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MProgramas implements Serializable {

    private int idPrograma;
    private String nombrePrograma;
    private Boolean estadoProgramaActivo;
    private Boolean estadoPeriodoPrograma;
    private String fechaInicioPeriodoPrograma;
    private String fechaFinPeriodoPrograma;
    private String nombrePeriodoPrograma;


    private List<MCursos> mCursosList;

    public MProgramas() {
    }

    public MProgramas(int idPrograma, String nombrePrograma, String nombrePeriodoPrograma, String fechaInicioPeriodoPrograma, String fechaFinPeriodoPrograma, Boolean estadoProgramaActivo, Boolean estadoPeriodoPrograma, List<MCursos> mCursosList) {
        this.idPrograma = idPrograma;
        this.nombrePrograma = nombrePrograma;
        this.nombrePeriodoPrograma = nombrePeriodoPrograma;
        this.fechaInicioPeriodoPrograma = fechaInicioPeriodoPrograma;
        this.fechaFinPeriodoPrograma = fechaFinPeriodoPrograma;
        this.estadoProgramaActivo = estadoProgramaActivo;
        this.estadoPeriodoPrograma = estadoPeriodoPrograma;
        this.mCursosList = mCursosList;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getNombrePeriodoPrograma() {
        return nombrePeriodoPrograma;
    }

    public void setNombrePeriodoPrograma(String nombrePeriodoPrograma) {
        this.nombrePeriodoPrograma = nombrePeriodoPrograma;
    }

    public String getFechaInicioPeriodoPrograma() {
        return fechaInicioPeriodoPrograma;
    }

    public void setFechaInicioPeriodoPrograma(String fechaInicioPeriodoPrograma) {
        this.fechaInicioPeriodoPrograma = fechaInicioPeriodoPrograma;
    }

    public String getFechaFinPeriodoPrograma() {
        return fechaFinPeriodoPrograma;
    }

    public void setFechaFinPeriodoPrograma(String fechaFinPeriodoPrograma) {
        this.fechaFinPeriodoPrograma = fechaFinPeriodoPrograma;
    }

    public Boolean getEstadoProgramaActivo() {
        return estadoProgramaActivo;
    }

    public void setEstadoProgramaActivo(Boolean estadoProgramaActivo) {
        this.estadoProgramaActivo = estadoProgramaActivo;
    }

    public Boolean getEstadoPeriodoPrograma() {
        return estadoPeriodoPrograma;
    }

    public void setEstadoPeriodoPrograma(Boolean estadoPeriodoPrograma) {
        this.estadoPeriodoPrograma = estadoPeriodoPrograma;
    }

    public List<MCursos> getmCursosList() {
        return mCursosList;
    }

    public void setmCursosList(List<MCursos> mCursosList) {
        this.mCursosList = mCursosList;
    }
}