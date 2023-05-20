package com.example.complexivo.Modelos;

import java.io.Serializable;
import java.util.List;

public class MUsuario implements Serializable {

    private int idUsuario;
    private String username;
    private String password;
    private String fotoPerfil;
    private Boolean estadoUsuarioActivo;

    private MPersona mPersona;
    private List<MCursos> mCursosList;
    private List<MRolUsuario> mRolUsuarioList;
    private List<MCapacitador> mCapacitadorList;

    public MUsuario() {
    }

    public MUsuario(int idUsuario, String username, String password, String fotoPerfil, Boolean estadoUsuarioActivo, MPersona mPersona, List<MCursos> mCursosList, List<MRolUsuario> mRolUsuarioList, List<MCapacitador> mCapacitadorList) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.fotoPerfil = fotoPerfil;
        this.estadoUsuarioActivo = estadoUsuarioActivo;
        this.mPersona = mPersona;
        this.mCursosList = mCursosList;
        this.mRolUsuarioList = mRolUsuarioList;
        this.mCapacitadorList = mCapacitadorList;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Boolean getEstadoUsuarioActivo() {
        return estadoUsuarioActivo;
    }

    public void setEstadoUsuarioActivo(Boolean estadoUsuarioActivo) {
        this.estadoUsuarioActivo = estadoUsuarioActivo;
    }

    public MPersona getmPersona() {
        return mPersona;
    }

    public void setmPersona(MPersona mPersona) {
        this.mPersona = mPersona;
    }

    public List<MCursos> getmCursosList() {
        return mCursosList;
    }

    public void setmCursosList(List<MCursos> mCursosList) {
        this.mCursosList = mCursosList;
    }

    public List<MRolUsuario> getmRolUsuarioList() {
        return mRolUsuarioList;
    }

    public void setmRolUsuarioList(List<MRolUsuario> mRolUsuarioList) {
        this.mRolUsuarioList = mRolUsuarioList;
    }

    public List<MCapacitador> getmCapacitadorList() {
        return mCapacitadorList;
    }

    public void setmCapacitadorList(List<MCapacitador> mCapacitadorList) {
        this.mCapacitadorList = mCapacitadorList;
    }
}