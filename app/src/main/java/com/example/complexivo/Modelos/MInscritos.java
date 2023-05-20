package com.example.complexivo.Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MInscritos implements Serializable {

    private int idInscrito;
    private String fechaInscrito;
    private Boolean estadoInscrito;
    private Boolean estadoInscritoActivo;
    private String estadoParticipanteAprobacion;
    private Boolean estadoParticipanteActivo;


    private List<MParticipante> mParticipanteList;
    private MUsuario mUsuario;
    private MCursos mCursos;

    public MInscritos() {
    }

    public MInscritos(int idInscrito, String fechaInscrito, Boolean estadoInscrito, Boolean estadoInscritoActivo, String estadoParticipanteAprobacion, Boolean estadoParticipanteActivo, List<MParticipante> mParticipanteList, MUsuario mUsuario, MCursos mCursos) {
        this.idInscrito = idInscrito;
        this.fechaInscrito = fechaInscrito;
        this.estadoInscrito = estadoInscrito;
        this.estadoInscritoActivo = estadoInscritoActivo;
        this.estadoParticipanteAprobacion = estadoParticipanteAprobacion;
        this.estadoParticipanteActivo = estadoParticipanteActivo;
        this.mParticipanteList = mParticipanteList;
        this.mUsuario = mUsuario;
        this.mCursos = mCursos;
    }

    public int getIdInscrito() {
        return idInscrito;
    }

    public void setIdInscrito(int idInscrito) {
        this.idInscrito = idInscrito;
    }

    public String getFechaInscrito() {
        return fechaInscrito;
    }

    public void setFechaInscrito(String fechaInscrito) {
        this.fechaInscrito = fechaInscrito;
    }

    public Boolean getEstadoInscrito() {
        return estadoInscrito;
    }

    public void setEstadoInscrito(Boolean estadoInscrito) {
        this.estadoInscrito = estadoInscrito;
    }

    public Boolean getEstadoInscritoActivo() {
        return estadoInscritoActivo;
    }

    public void setEstadoInscritoActivo(Boolean estadoInscritoActivo) {
        this.estadoInscritoActivo = estadoInscritoActivo;
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

    public List<MParticipante> getmParticipanteList() {
        return mParticipanteList;
    }

    public void setmParticipanteList(List<MParticipante> mParticipanteList) {
        this.mParticipanteList = mParticipanteList;
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