package com.example.complexivo.Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MPersona implements Serializable {

    private int idPersona;
    private String identificacion;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String telefono;
    private String celular;
    private String genero;
    private String etnia;


    private List<MUsuario> mUsuarioList;

    public MPersona() {
    }

    public MPersona(int idPersona, String identificacion, String nombre1, String nombre2, String apellido1, String apellido2, String correo, String telefono, String celular, String genero, String etnia, List<MUsuario> mUsuarioList) {
        this.idPersona = idPersona;
        this.identificacion = identificacion;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.correo = correo;
        this.telefono = telefono;
        this.celular = celular;
        this.genero = genero;
        this.etnia = etnia;
        this.mUsuarioList = mUsuarioList;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public List<MUsuario> getmUsuarioList() {
        return mUsuarioList;
    }

    public void setmUsuarioList(List<MUsuario> mUsuarioList) {
        this.mUsuarioList = mUsuarioList;
    }
}
