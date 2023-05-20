package com.example.complexivo.Modelos;

import java.util.List;

public class MCapacitador {

    private int idCapacitador;
    private Boolean estadoActivoCapacitador;
    private String tituloCapacitador;

    private MUsuario mUsuario;
    private List<MCursos> mCursosList;

    public MCapacitador() {
    }

    public MCapacitador(int idCapacitador, Boolean estadoActivoCapacitador, String tituloCapacitador, MUsuario mUsuario, List<MCursos> mCursosList) {
        this.idCapacitador = idCapacitador;
        this.estadoActivoCapacitador = estadoActivoCapacitador;
        this.tituloCapacitador = tituloCapacitador;
        this.mUsuario = mUsuario;
        this.mCursosList = mCursosList;
    }

    public int getIdCapacitador() {
        return idCapacitador;
    }

    public void setIdCapacitador(int idCapacitador) {
        this.idCapacitador = idCapacitador;
    }

    public Boolean getEstadoActivoCapacitador() {
        return estadoActivoCapacitador;
    }

    public void setEstadoActivoCapacitador(Boolean estadoActivoCapacitador) {
        this.estadoActivoCapacitador = estadoActivoCapacitador;
    }

    public String getTituloCapacitador() {
        return tituloCapacitador;
    }

    public void setTituloCapacitador(String tituloCapacitador) {
        this.tituloCapacitador = tituloCapacitador;
    }

    public MUsuario getmUsuario() {
        return mUsuario;
    }

    public void setmUsuario(MUsuario mUsuario) {
        this.mUsuario = mUsuario;
    }

    public List<MCursos> getmCursosList() {
        return mCursosList;
    }

    public void setmCursosList(List<MCursos> mCursosList) {
        this.mCursosList = mCursosList;
    }
}
