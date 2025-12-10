package dominio;

public class Nota {

    private String rutEstudiante;
    private String codigoAsignatura;
    private String semestre;
    private double calificacion;
    private String estado;

    public Nota(String rutEstudiante, String codigoAsignatura,
                String semestre, double calificacion, String estado) {
        this.rutEstudiante = rutEstudiante;
        this.codigoAsignatura = codigoAsignatura;
        this.semestre = semestre;
        this.calificacion = calificacion;
        this.estado = estado;
    }

    public String getRutEstudiante() { return rutEstudiante; }
    public String getCodigoAsignatura() { return codigoAsignatura; }
    public String getSemestre() { return semestre; }
    public double getCalificacion() { return calificacion; }
    public String getEstado() { return estado; }
}

