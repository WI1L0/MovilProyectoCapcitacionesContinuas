package com.example.experimental.Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MCursos implements Serializable {

    private int idCurso;
    private String nombreCurso;
    private String fotoCurso;
    private int duracionCurso;
    private String fechaInicioCurso;
    private String fechaFinalizacionCurso;
    private String observacionCurso;
    private String descripcionCurso;
    private int numeroCuposCurso;
    private String objetivoGeneralesCurso;
    private Boolean estadoCurso;
    private Boolean estadoAprovacionCurso;
    private Boolean estadoPublicasionCurso;
    private String nombreEspecialidad;
    private String nombreArea;
    private String nombreTipoCurso;
    private String nombreModalidadCurso;
    private String horaInicio;
    private String horaFin;


    private List<MPrerequisitos> mPrerequisitosList;
    private List<MUsuario> mUsuarioList;

    public MCursos() {
    }

    public MCursos(int idCurso, String nombreCurso, String fotoCurso, int duracionCurso, String fechaInicioCurso, String fechaFinalizacionCurso, String observacionCurso, String descripcionCurso, int numeroCuposCurso, String objetivoGeneralesCurso, Boolean estadoCurso, Boolean estadoAprovacionCurso, Boolean estadoPublicasionCurso, String nombreEspecialidad, String nombreArea, String nombreTipoCurso, String nombreModalidadCurso, String horaInicio, String horaFin, List<MPrerequisitos> mPrerequisitosList, List<MUsuario> mUsuarioList) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.fotoCurso = fotoCurso;
        this.duracionCurso = duracionCurso;
        this.fechaInicioCurso = fechaInicioCurso;
        this.fechaFinalizacionCurso = fechaFinalizacionCurso;
        this.observacionCurso = observacionCurso;
        this.descripcionCurso = descripcionCurso;
        this.numeroCuposCurso = numeroCuposCurso;
        this.objetivoGeneralesCurso = objetivoGeneralesCurso;
        this.estadoCurso = estadoCurso;
        this.estadoAprovacionCurso = estadoAprovacionCurso;
        this.estadoPublicasionCurso = estadoPublicasionCurso;
        this.nombreEspecialidad = nombreEspecialidad;
        this.nombreArea = nombreArea;
        this.nombreTipoCurso = nombreTipoCurso;
        this.nombreModalidadCurso = nombreModalidadCurso;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.mPrerequisitosList = mPrerequisitosList;
        this.mUsuarioList = mUsuarioList;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getFotoCurso() {
        return fotoCurso;
    }

    public void setFotoCurso(String fotoCurso) {
        this.fotoCurso = fotoCurso;
    }

    public int getDuracionCurso() {
        return duracionCurso;
    }

    public void setDuracionCurso(int duracionCurso) {
        this.duracionCurso = duracionCurso;
    }

    public String getFechaInicioCurso() {
        return fechaInicioCurso;
    }

    public void setFechaInicioCurso(String fechaInicioCurso) {
        this.fechaInicioCurso = fechaInicioCurso;
    }

    public String getFechaFinalizacionCurso() {
        return fechaFinalizacionCurso;
    }

    public void setFechaFinalizacionCurso(String fechaFinalizacionCurso) {
        this.fechaFinalizacionCurso = fechaFinalizacionCurso;
    }

    public String getObservacionCurso() {
        return observacionCurso;
    }

    public void setObservacionCurso(String observacionCurso) {
        this.observacionCurso = observacionCurso;
    }

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }

    public int getNumeroCuposCurso() {
        return numeroCuposCurso;
    }

    public void setNumeroCuposCurso(int numeroCuposCurso) {
        this.numeroCuposCurso = numeroCuposCurso;
    }

    public String getObjetivoGeneralesCurso() {
        return objetivoGeneralesCurso;
    }

    public void setObjetivoGeneralesCurso(String objetivoGeneralesCurso) {
        this.objetivoGeneralesCurso = objetivoGeneralesCurso;
    }

    public Boolean getEstadoCurso() {
        return estadoCurso;
    }

    public void setEstadoCurso(Boolean estadoCurso) {
        this.estadoCurso = estadoCurso;
    }

    public Boolean getEstadoAprovacionCurso() {
        return estadoAprovacionCurso;
    }

    public void setEstadoAprovacionCurso(Boolean estadoAprovacionCurso) {
        this.estadoAprovacionCurso = estadoAprovacionCurso;
    }

    public Boolean getEstadoPublicasionCurso() {
        return estadoPublicasionCurso;
    }

    public void setEstadoPublicasionCurso(Boolean estadoPublicasionCurso) {
        this.estadoPublicasionCurso = estadoPublicasionCurso;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getNombreTipoCurso() {
        return nombreTipoCurso;
    }

    public void setNombreTipoCurso(String nombreTipoCurso) {
        this.nombreTipoCurso = nombreTipoCurso;
    }

    public String getNombreModalidadCurso() {
        return nombreModalidadCurso;
    }

    public void setNombreModalidadCurso(String nombreModalidadCurso) {
        this.nombreModalidadCurso = nombreModalidadCurso;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public List<MPrerequisitos> getmPrerequisitosList() {
        return mPrerequisitosList;
    }

    public void setmPrerequisitosList(List<MPrerequisitos> mPrerequisitosList) {
        this.mPrerequisitosList = mPrerequisitosList;
    }

    public List<MUsuario> getmUsuarioList() {
        return mUsuarioList;
    }

    public void setmUsuarioList(List<MUsuario> mUsuarioList) {
        this.mUsuarioList = mUsuarioList;
    }
}
