package com.example.complexivo.Modelos;

import java.util.List;

public class MRol {

    private int idRol;
    private Boolean estadoRolActivo;
    private String nombreRol;

    private List<MRolUsuario> mRolUsuarioList;

    public MRol() {
    }

    public MRol(int idRol, Boolean estadoRolActivo, String nombreRol, List<MRolUsuario> mRolUsuarioList) {
        this.idRol = idRol;
        this.estadoRolActivo = estadoRolActivo;
        this.nombreRol = nombreRol;
        this.mRolUsuarioList = mRolUsuarioList;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public Boolean getEstadoRolActivo() {
        return estadoRolActivo;
    }

    public void setEstadoRolActivo(Boolean estadoRolActivo) {
        this.estadoRolActivo = estadoRolActivo;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public List<MRolUsuario> getmRolUsuarioList() {
        return mRolUsuarioList;
    }

    public void setmRolUsuarioList(List<MRolUsuario> mRolUsuarioList) {
        this.mRolUsuarioList = mRolUsuarioList;
    }
}