package com.example.complexivo.Modelos;

public class MRolUsuario {

    private int idRolUsuario;
    private int idUsuario;
    private int idRol;

    private MRol mRol;
    private MUsuario mUsuario;

    public MRolUsuario() {
    }

    public MRolUsuario(int idRolUsuario, int idUsuario, int idRol, MRol mRol, MUsuario mUsuario) {
        this.idRolUsuario = idRolUsuario;
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.mRol = mRol;
        this.mUsuario = mUsuario;
    }

    public int getIdRolUsuario() {
        return idRolUsuario;
    }

    public void setIdRolUsuario(int idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public MRol getmRol() {
        return mRol;
    }

    public void setmRol(MRol mRol) {
        this.mRol = mRol;
    }

    public MUsuario getmUsuario() {
        return mUsuario;
    }

    public void setmUsuario(MUsuario mUsuario) {
        this.mUsuario = mUsuario;
    }
}