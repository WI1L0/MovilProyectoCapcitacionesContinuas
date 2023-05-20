package com.example.complexivo.Utilidades;

public class Atributos {

    //Persona
    public static final String table_persona = "personas";
    public static final String atr_per_id = "idPersona";
    public static final String atr_per_identificador = "identificacion";
    public static final String atr_per_nombre1 = "nombre1";
    public static final String atr_per_nombre2 = "nombre2";
    public static final String atr_per_apellido1 = "apellido1";
    public static final String atr_per_apellido2 = "apellido2";
    public static final String atr_per_correo = "correo";
    public static final String atr_per_telefono = "telefono";
    public static final String atr_per_celular = "celular";
    public static final String atr_per_genero = "genero";
    public static final String atr_per_etnia = "etnia";

    //Pre_requisitos
    public static final String table_prerequisitos = "prerequisitos";
    public static final String atr_pre_id = "idPrerequisitoCurso";
    public static final String atr_pre_estado = "estadoPrerequisitoCurso";
    public static final String atr_pre_nombre = "nombrePrerequisitoCurso";

    //Programas
    public static final String table_programas = "programas";
    public static final String atr_pro_id = "idPrograma";
    public static final String atr_pro_nombre = "nombrePrograma";
    public static final String atr_pro_estado_activo = "estadoProgramaActivo";
    public static final String atr_pro_estado = "estadoPeriodoPrograma";
    public static final String atr_pro_f_inicio = "fechaInicioPeriodoPrograma";
    public static final String atr_pro_f_fin = "fechaFinPeriodoPrograma";
    public static final String atr_pro_periodo = "nombrePeriodoPrograma";

    public static final String table_capacitador = "capacitador";
    public static final String atr_cap_id = "idCapacitador";
    public static final String atr_cap_estado_capacitador = "estadoActivoCapacitador";
    public static final String atr_cap_titulo = "tituloCapacitador";

    public static final String table_rol_usu = "rolusuario";
    public static final String atr_rolusuario_id = "idRolUsuario";

    public static final String table_rol = "roles";
    public static final String atr_rol_id = "idRol";
    public static final String atr_rol_nombre = "nombreRol";
    public static final String atr_rol_est = "estadoRolActivo";

    public static final String table_usuarios = "usuarios";
    public static final String atr_usu_id = "idUsuario";
    public static final String atr_usu_user = "username";
    public static final String atr_usu_paswork = "password";
    public static final String atr_usu_foto = "fotoPerfil";
    public static final String atr_usu_estado_activo = "estadoUsuarioActivo";

    public static final String table_cursos = "cursos";
    public static final String atr_cur_id = "idCurso";
    public static final String atr_cur_nombre = "nombreCurso";
    public static final String atr_cur_foto = "fotoCurso";
    public static final String atr_cur_duracion = "duracionCurso";
    public static final String atr_cur_observaciones = "observacionCurso";
    public static final String atr_cur_estado = "estadoCurso";
    public static final String atr_cur_estado_aprobacion = "estadoAprovacionCurso";
    public static final String atr_cur_estado_publicacion = "estadoPublicasionCurso";
    public static final String atr_cur_descripcion = "descripcionCurso";
    public static final String atr_cur_objetivos = "objetivoGeneralesCurso";
    public static final String atr_cur_cupos = "numeroCuposCurso";
    public static final String atr_cur_f_inicio = "fechaInicioCurso";
    public static final String atr_cur_f_fin = "fechaFinalizacionCurso";
    public static final String atr_cur_especialidad = "nombreEspecialidad";
    public static final String atr_cur_area = "nombreArea";
    public static final String atr_cur_tipo = "nombreTipoCurso";
    public static final String atr_cur_modalidad = "nombreModalidadCurso";
    public static final String atr_cur_horario_inicio = "horaInicio";
    public static final String atr_cur_horario_fin = "horaFin";

    public static final String table_inscritos = "inscritos";
    public static final String atr_ins_id = "idInscrito";
    public static final String atr_ins_fecha= "fechaInscrito";
    public static final String atr_ins_estado = "estadoInscrito";
    public static final String atr_ins_estado_activo = "estadoInscritoActivo";

    public static final String table_asistencia = "asistencia";
    public static final String atr_asi_id = "idAsistencia";
    public static final String atr_asi_fecha = "fechaAsistencia";
    public static final String atr_asi_estado = "estadoAsistencia";
    public static final String atr_asi_observaciones = "observacionAsistencia";
    public static final String atr_asi_est_sub = "estadoSubida";
    public static final String atr_asi_est_act = "estadoActual";


    public static final String table_participante = "participante";
    public static final String atr_par_id = "idParticipanteMatriculado";
    public static final String atr_par_estado_aprobado = "estadoParticipanteAprobacion";
    public static final String atr_par_estado_participante = "estadoParticipanteActivo";

    public static final String table_control = "control";
    public static final String atr_con_table = "nametable";
    public static final String atr_con_estado = "estado";




    public static final String CREAR_TABLA_PERSONAS = "CREATE TABLE " + table_persona + " (" +
            atr_per_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_per_identificador + " TEXT NOT NULL, " +
            atr_per_nombre1 + " TEXT NOT NULL, " + atr_per_nombre2 + " TEXT, " +
            atr_per_apellido1 + " TEXT NOT NULL, " + atr_per_apellido2 + " TEXT, " +
            atr_per_correo + " TEXT NOT NULL, "  + atr_per_telefono+ " TEXT, " +
            atr_per_celular + " TEXT NOT NULL, " + atr_per_genero + " TEXT NOT NULL, " +
            atr_per_etnia + " TEXT);";

    public static final String CREAR_TABLA_PROGRAMAS = "CREATE TABLE " + table_programas + " (" +
            atr_pro_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_pro_nombre + " TEXT NOT NULL, " +
            atr_pro_estado_activo + " BOOLEAN NOT NULL, " + atr_pro_estado + " BOOLEAN NOT NULL, " +
            atr_pro_f_inicio + " TEXT NOT NULL, " + atr_pro_f_fin + " TEXT NOT NULL, " +
            atr_pro_periodo + " TEXT NOT NULL);";

    public static final String CREAR_TABLA_ROLES = "CREATE TABLE " + table_rol + " (" +
            atr_rol_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_rol_est + " BOOLEAN NOT NULL, " +
            atr_rol_nombre + " TEXT NOT NULL);";

    public static final String CREAR_TABLA_ROLES_USUARIOS = "CREATE TABLE " + table_rol_usu + " (" +
            atr_rolusuario_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + atr_usu_id + " INTEGER NOT NULL, " +
            atr_rol_id + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + atr_usu_id + ") REFERENCES " + table_usuarios + " (" + atr_usu_id + "), " +
            "FOREIGN KEY(" + atr_rol_id + ") REFERENCES " + table_rol + " (" + atr_rol_id + "));";

    public static final String CREAR_TABLA_USUARIOS = "CREATE TABLE " + table_usuarios + " (" +
            atr_usu_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_per_id + " INTEGER NOT NULL, " +
            atr_usu_user + " TEXT NOT NULL, " + atr_usu_paswork + " TEXT NOT NULL, " +
            atr_usu_foto + " TEXT, " + atr_usu_estado_activo + " BOOLEAN NOT NULL, " +
            "FOREIGN KEY(" + atr_per_id + ") REFERENCES " + table_persona + " (" + atr_per_id + "));";

    public static final String CREAR_TABLA_CAPACITADOR = "CREATE TABLE " + table_capacitador + " (" +
            atr_cap_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_usu_id + " INTEGER NOT NULL, " +
            atr_cap_estado_capacitador + " BOOLEAN NOT NULL, " + atr_cap_titulo + " TEXT NOT NULL, " +
            "FOREIGN KEY(" + atr_usu_id + ") REFERENCES " + table_usuarios + " (" + atr_usu_id + "));";

    public static final String CREAR_TABLA_CURSOS = "CREATE TABLE " + table_cursos + " (" +
            atr_cur_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_pro_id + " INTEGER NOT NULL, " +
            atr_cap_id + " INTEGER NOT NULL, " + atr_cur_nombre + " TEXT NOT NULL, " +
            atr_cur_foto + " TEXT, " + atr_cur_duracion + " INTEGER NOT NULL, " +
            atr_cur_observaciones + " TEXT, " + atr_cur_estado + " BOOLEAN NOT NULL, " +
            atr_cur_estado_aprobacion + " TEXT, " + atr_cur_estado_publicacion + " TEXT, " +
            atr_cur_descripcion + " TEXT, " + atr_cur_objetivos + " TEXT, " +
            atr_cur_cupos + " INTEGER NOT NULL, " + atr_cur_f_inicio + " TEXT NOT NULL, " +
            atr_cur_f_fin + " TEXT NOT NULL, " + atr_cur_especialidad + " TEXT NOT NULL, " +
            atr_cur_area + " TEXT NOT NULL, " + atr_cur_tipo + " TEXT NOT NULL, " +
            atr_cur_modalidad + " TEXT NOT NULL, " + atr_cur_horario_inicio + " TEXT NOT NULL, " +
            atr_cur_horario_fin + " TEXT NOT NULL, " +
            "FOREIGN KEY(" + atr_pro_id + ") REFERENCES " + table_programas + " (" + atr_pro_id + "), " +
            "FOREIGN KEY(" + atr_cap_id + ") REFERENCES " + table_capacitador + " (" + atr_cap_id + "));";

    public static final String CREAR_TABLA_PREREQUISITOS = "CREATE TABLE " + table_prerequisitos + " (" +
            atr_pre_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_cur_id + " INTEGER NOT NULL, " +
            atr_pre_estado + " BOOLEAN NOT NULL, " + atr_pre_nombre + " TEXT NOT NULL, " +
            "FOREIGN KEY(" + atr_cur_id +") REFERENCES " + table_cursos + " (" + atr_cur_id + "));";

    public static final String CREAR_TABLA_INSCRITOS = "CREATE TABLE " + table_inscritos + " (" +
            atr_ins_id + " INTEGER PRIMARY KEY NOT NULL, " + atr_usu_id + " INTEGER NOT NULL, " +
            atr_cur_id + " INTEGER NOT NULL, " + atr_ins_fecha + " TEXT NOT NULL, " +
            atr_ins_estado + " BOOLEAN NOT NULL, " + atr_ins_estado_activo + " BOOLEAN NOT NULL, " +
            "FOREIGN KEY(" + atr_usu_id + ") REFERENCES " + table_usuarios + " (" + atr_usu_id + "));";

    public static final String CREAR_TABLA_PARTICIPANTES = "CREATE TABLE " + table_participante + " (" +
            atr_par_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + atr_ins_id + " INTEGER NOT NULL, " +
            atr_par_estado_aprobado + " TEXT NOT NULL, " + atr_par_estado_participante + " BOOLEAN NOT NULL, " +
            "FOREIGN KEY(" + atr_ins_id + ") REFERENCES " + table_inscritos + " (" + atr_ins_id + "));";

    public static final String CREAR_TABLA_ASISTENCIAS = "CREATE TABLE " + table_asistencia + " (" +
            atr_asi_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + atr_par_id + " INTEGER NOT NULL, " +
            atr_asi_fecha + " TEXT NOT NULL, " + atr_asi_estado + " BOOLEAN NOT NULL, " +
            atr_asi_observaciones + " TEXT, " + atr_asi_est_sub + " BOOLEAN NOT NULL, " +
            atr_asi_est_act + " TEXT NOT NULL, " +
            "FOREIGN KEY(" + atr_par_id + ") REFERENCES " + table_participante + " (" + atr_par_id + "));";


    public static final String CREAR_TABLA_CONTROL = "CREATE TABLE " + table_control + " (" +
            atr_con_table + " TEXT PRIMARY KEY, " + atr_con_estado + " BOOLEAN NOT NULL);";
}
