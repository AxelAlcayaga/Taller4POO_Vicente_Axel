package dominio;

import java.util.ArrayList;

import logica.ISistema;

public class Sistema implements ISistema {

	private ArrayList<Usuario> usuarios;
	private ArrayList<Curso> cursos;
	private ArrayList<Certificacion> certificaciones;
	private ArrayList<RegistroCertificacion> registros;
	private ArrayList<Nota> notas;
	private ArrayList<AsignaturaCertificacion> asignaturasCertificacion;
	private EstrategiaCalculoPromedio estrategiaPromedio;
	private ArrayList<Estudiante> estudiantes;

	private Sistema() {
		usuarios = new ArrayList<>();
		cursos = new ArrayList<>();
		certificaciones = new ArrayList<>();
		registros = new ArrayList<>();
		notas = new ArrayList<>();
		asignaturasCertificacion = new ArrayList<>();
		estrategiaPromedio = new EstrategiaCalculoPromedio();
	}

	private static Sistema instancia;

	public static Sistema getInstancia() {
		if (instancia == null) {
			instancia = new Sistema();
		}
		return instancia;
	}

	@Override
	public void cargarDatos() {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario iniciarSesion(String nombreUsuario, String contraseña) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Curso> listarCurso() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Certificacion> listarCertificaciones() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<RegistroCertificacion> getRegistrosPorEstudiante(String rut) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Nota> getNotasPorEstudiante(String rut) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double calcularPromedioGeneral(String rut) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calcularPromedioPorSemestre(String rut) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void inscribirEstudianteEnCertitifacion(String rut, String idCertificacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarUsuario(Usuario u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarUsuario(String nombreUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambiarContraseña(String nombreUsuario, String nuevaContraseña) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Estudiante> getEstudiantes() {

		return null;
	}

}
