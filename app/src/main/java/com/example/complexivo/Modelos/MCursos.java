package com.example.complexivo.Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MCursos implements Serializable {

    private int idCurso;
    private String nombreCurso;
    private String fotoCurso;
    private int duracionCurso;
    private String observacionCurso;
    private Boolean estadoCurso;
    private String estadoAprovacionCurso;
    private Boolean estadoPublicasionCurso;
    private String descripcionCurso;
    private String objetivoGeneralesCurso;
    private int numeroCuposCurso;
    private String fechaInicioCurso;
    private String fechaFinalizacionCurso;
    private String nombreEspecialidad;
    private String nombreArea;
    private String nombreTipoCurso;
    private String nombreModalidadCurso;
    private String horaInicio;
    private String horaFin;

    private MCapacitador mCapacitador;
    private  MProgramas mProgramas;
    private List<MPrerequisitos> mPrerequisitosList;


    public MCursos() {
    }

    public MCursos(int idCurso, String nombreCurso, String fotoCurso, int duracionCurso, String observacionCurso, Boolean estadoCurso, String estadoAprovacionCurso, Boolean estadoPublicasionCurso, String descripcionCurso, String objetivoGeneralesCurso, int numeroCuposCurso, String fechaInicioCurso, String fechaFinalizacionCurso, String nombreEspecialidad, String nombreArea, String nombreTipoCurso, String nombreModalidadCurso, String horaInicio, String horaFin, MCapacitador mCapacitador, MProgramas mProgramas, List<MPrerequisitos> mPrerequisitosList) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.fotoCurso = fotoCurso;
        this.duracionCurso = duracionCurso;
        this.observacionCurso = observacionCurso;
        this.estadoCurso = estadoCurso;
        this.estadoAprovacionCurso = estadoAprovacionCurso;
        this.estadoPublicasionCurso = estadoPublicasionCurso;
        this.descripcionCurso = descripcionCurso;
        this.objetivoGeneralesCurso = objetivoGeneralesCurso;
        this.numeroCuposCurso = numeroCuposCurso;
        this.fechaInicioCurso = fechaInicioCurso;
        this.fechaFinalizacionCurso = fechaFinalizacionCurso;
        this.nombreEspecialidad = nombreEspecialidad;
        this.nombreArea = nombreArea;
        this.nombreTipoCurso = nombreTipoCurso;
        this.nombreModalidadCurso = nombreModalidadCurso;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.mCapacitador = mCapacitador;
        this.mProgramas = mProgramas;
        this.mPrerequisitosList = mPrerequisitosList;
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

    public String getObservacionCurso() {
        return observacionCurso;
    }

    public void setObservacionCurso(String observacionCurso) {
        this.observacionCurso = observacionCurso;
    }

    public Boolean getEstadoCurso() {
        return estadoCurso;
    }

    public void setEstadoCurso(Boolean estadoCurso) {
        this.estadoCurso = estadoCurso;
    }

    public String getEstadoAprovacionCurso() {
        return estadoAprovacionCurso;
    }

    public void setEstadoAprovacionCurso(String estadoAprovacionCurso) {
        this.estadoAprovacionCurso = estadoAprovacionCurso;
    }

    public Boolean getEstadoPublicasionCurso() {
        return estadoPublicasionCurso;
    }

    public void setEstadoPublicasionCurso(Boolean estadoPublicasionCurso) {
        this.estadoPublicasionCurso = estadoPublicasionCurso;
    }

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }

    public String getObjetivoGeneralesCurso() {
        return objetivoGeneralesCurso;
    }

    public void setObjetivoGeneralesCurso(String objetivoGeneralesCurso) {
        this.objetivoGeneralesCurso = objetivoGeneralesCurso;
    }

    public int getNumeroCuposCurso() {
        return numeroCuposCurso;
    }

    public void setNumeroCuposCurso(int numeroCuposCurso) {
        this.numeroCuposCurso = numeroCuposCurso;
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

    public MCapacitador getmCapacitador() {
        return mCapacitador;
    }

    public void setmCapacitador(MCapacitador mCapacitador) {
        this.mCapacitador = mCapacitador;
    }

    public MProgramas getmProgramas() {
        return mProgramas;
    }

    public void setmProgramas(MProgramas mProgramas) {
        this.mProgramas = mProgramas;
    }

    public List<MPrerequisitos> getmPrerequisitosList() {
        return mPrerequisitosList;
    }

    public void setmPrerequisitosList(List<MPrerequisitos> mPrerequisitosList) {
        this.mPrerequisitosList = mPrerequisitosList;
    }
}