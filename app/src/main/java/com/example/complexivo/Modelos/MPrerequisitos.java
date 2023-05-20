package com.example.complexivo.Modelos;

import java.util.List;

public class MPrerequisitos {

    private int idPrerequisitoCurso;
    private Boolean estadoPrerequisitoCurso;
    private String nombrePrerequisitoCurso;

    private MCursos mCursos;

    public MPrerequisitos() {
    }

    public MPrerequisitos(int idPrerequisitoCurso, Boolean estadoPrerequisitoCurso, String nombrePrerequisitoCurso, MCursos mCursos) {
        this.idPrerequisitoCurso = idPrerequisitoCurso;
        this.estadoPrerequisitoCurso = estadoPrerequisitoCurso;
        this.nombrePrerequisitoCurso = nombrePrerequisitoCurso;
        this.mCursos = mCursos;
    }

    public int getIdPrerequisitoCurso() {
        return idPrerequisitoCurso;
    }

    public void setIdPrerequisitoCurso(int idPrerequisitoCurso) {
        this.idPrerequisitoCurso = idPrerequisitoCurso;
    }

    public Boolean getEstadoPrerequisitoCurso() {
        return estadoPrerequisitoCurso;
    }

    public void setEstadoPrerequisitoCurso(Boolean estadoPrerequisitoCurso) {
        this.estadoPrerequisitoCurso = estadoPrerequisitoCurso;
    }

    public String getNombrePrerequisitoCurso() {
        return nombrePrerequisitoCurso;
    }

    public void setNombrePrerequisitoCurso(String nombrePrerequisitoCurso) {
        this.nombrePrerequisitoCurso = nombrePrerequisitoCurso;
    }

    public MCursos getmCursos() {
        return mCursos;
    }

    public void setmCursos(MCursos mCursos) {
        this.mCursos = mCursos;
    }
}