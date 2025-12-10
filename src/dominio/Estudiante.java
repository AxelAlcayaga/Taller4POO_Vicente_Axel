package dominio;

public class Estudiante extends Usuario {

	private String rut;
	private String carrera;
	private int semestre;
	private String correo;

	public Estudiante(String nombreUsuario, String contraseña, String rut, String carrera, int semestre,
			String correo) {

		super(nombreUsuario, contraseña, "Estudiante");
		this.rut = rut;
		this.carrera = carrera;
		this.semestre = semestre;
		this.correo = correo;
	}

	public String getRut() {
		return rut;
	}

	public String getCarrera() {
		return carrera;
	}

	public int getSemestre() {
		return semestre;
	}

	public String getCorreo() {
		return correo;
	}
}
