package com.example.experimental.Modelos;

import java.io.Serializable;
import java.util.List;

public class MUsuario implements Serializable {

    private int idUsuario;
    private String username;
    private String password;
    private String fotoPerfil;
    private Boolean estadoUsuarioActivo;
    private Boolean estadoActivoCapacitador;
    private String tituloCapacitador;
    private String nombreRol;


    private MPersona mPersona;
    private List<MCursos> mCursosList;

    public MUsuario() {
    }

    public MUsuario(int idUsuario, String username, String password, String fotoPerfil, Boolean estadoUsuarioActivo, Boolean estadoActivoCapacitador, String tituloCapacitador, String nombreRol, MPersona mPersona, List<MCursos> mCursosList) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.fotoPerfil = fotoPerfil;
        this.estadoUsuarioActivo = estadoUsuarioActivo;
        this.estadoActivoCapacitador = estadoActivoCapacitador;
        this.tituloCapacitador = tituloCapacitador;
        this.nombreRol = nombreRol;
        this.mPersona = mPersona;
        this.mCursosList = mCursosList;
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

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
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
}