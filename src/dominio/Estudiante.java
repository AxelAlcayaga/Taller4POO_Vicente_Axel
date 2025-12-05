package dominio;

public class Estudiante extends Usuario{

	private String rut;
	private String carrera;
	private int semestre;
	private String correo;
	
	public Estudiante(String nombreUsuario, String contraseña, String rut, String carrera,
			int semestre, String correo) {
		super(nombreUsuario, contraseña, "ESTUDIANTE");
		this.rut = rut;
		this.carrera = carrera;
		this.semestre = semestre;
		this.correo = correo;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public boolean esEstudiante() {return true;}
	
	

}
