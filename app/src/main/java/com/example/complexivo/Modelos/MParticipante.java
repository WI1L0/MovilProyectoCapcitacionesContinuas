package com.example.complexivo.Modelos;

import java.util.List;

public class MParticipante {

    private int idParticipanteMatriculado;
    private String estadoParticipanteAprobacion;
    private Boolean estadoParticipanteActivo;


    private MInscritos mInscritos;
    private List<MAsistencia> mAsistenciaList;

    public MParticipante() {
    }

    public MParticipante(int idParticipanteMatriculado, String estadoParticipanteAprobacion, Boolean estadoParticipanteActivo, MInscritos mInscritos, List<MAsistencia> mAsistenciaList) {
        this.idParticipanteMatriculado = idParticipanteMatriculado;
        this.estadoParticipanteAprobacion = estadoParticipanteAprobacion;
        this.estadoParticipanteActivo = estadoParticipanteActivo;
        this.mInscritos = mInscritos;
        this.mAsistenciaList = mAsistenciaList;
    }

    public int getIdParticipanteMatriculado() {
        return idParticipanteMatriculado;
    }

    public void setIdParticipanteMatriculado(int idParticipanteMatriculado) {
        this.idParticipanteMatriculado = idParticipanteMatriculado;
    }

    public String getEstadoParticipanteAprobacion() {
        return estadoParticipanteAprobacion;
    }

    public void setEstadoParticipanteAprobacion(String estadoParticipanteAprobacion) {
        this.estadoParticipanteAprobacion = estadoParticipanteAprobacion;
    }

    public Boolean getEstadoParticipanteActivo() {
        return estadoParticipanteActivo;
    }

    public void setEstadoParticipanteActivo(Boolean estadoParticipanteActivo) {
        this.estadoParticipanteActivo = estadoParticipanteActivo;
    }

    public MInscritos getmInscritos() {
        return mInscritos;
    }

    public void setmInscritos(MInscritos mInscritos) {
        this.mInscritos = mInscritos;
    }

    public List<MAsistencia> getmAsistenciaList() {
        return mAsistenciaList;
    }

    public void setmAsistenciaList(List<MAsistencia> mAsistenciaList) {
        this.mAsistenciaList = mAsistenciaList;
    }
}