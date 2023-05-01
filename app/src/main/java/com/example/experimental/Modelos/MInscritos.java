package com.example.experimental.Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MInscritos implements Serializable {

    private int idInscrito;
    private Boolean estadoInscritoActivo;
    private String fechaInscrito;
    private Boolean estadoParticipanteAprobacion;
    private Boolean estadoParticipanteActivo;


    private List<MAsistencia> mAsistenciaList;
    private MUsuario mUsuario;
    private MCursos mCursos;

    public MInscritos() {
    }

    public MInscritos(int idInscrito, Boolean estadoInscritoActivo, String fechaInscrito, Boolean estadoParticipanteAprobacion, Boolean estadoParticipanteActivo, List<MAsistencia> mAsistenciaList, MUsuario mUsuario, MCursos mCursos) {
        this.idInscrito = idInscrito;
        this.estadoInscritoActivo = estadoInscritoActivo;
        this.fechaInscrito = fechaInscrito;
        this.estadoParticipanteAprobacion = estadoParticipanteAprobacion;
        this.estadoParticipanteActivo = estadoParticipanteActivo;
        this.mAsistenciaList = mAsistenciaList;
        this.mUsuario = mUsuario;
        this.mCursos = mCursos;
    }

    public int getIdInscrito() {
        return idInscrito;
    }

    public void setIdInscrito(int idInscrito) {
        this.idInscrito = idInscrito;
    }

    public Boolean getEstadoInscritoActivo() {
        return estadoInscritoActivo;
    }

    public void setEstadoInscritoActivo(Boolean estadoInscritoActivo) {
        this.estadoInscritoActivo = estadoInscritoActivo;
    }

    public String getFechaInscrito() {
        return fechaInscrito;
    }

    public void setFechaInscrito(String fechaInscrito) {
        this.fechaInscrito = fechaInscrito;
    }

    public Boolean getEstadoParticipanteAprobacion() {
        return estadoParticipanteAprobacion;
    }

    public void setEstadoParticipanteAprobacion(Boolean estadoParticipanteAprobacion) {
        this.estadoParticipanteAprobacion = estadoParticipanteAprobacion;
    }

    public Boolean getEstadoParticipanteActivo() {
        return estadoParticipanteActivo;
    }

    public void setEstadoParticipanteActivo(Boolean estadoParticipanteActivo) {
        this.estadoParticipanteActivo = estadoParticipanteActivo;
    }

    public List<MAsistencia> getmAsistenciaList() {
        return mAsistenciaList;
    }

    public void setmAsistenciaList(List<MAsistencia> mAsistenciaList) {
        this.mAsistenciaList = mAsistenciaList;
    }

    public MUsuario getmUsuario() {
        return mUsuario;
    }

    public void setmUsuario(MUsuario mUsuario) {
        this.mUsuario = mUsuario;
    }

    public MCursos getmCursos() {
        return mCursos;
    }

    public void setmCursos(MCursos mCursos) {
        this.mCursos = mCursos;
    }
}