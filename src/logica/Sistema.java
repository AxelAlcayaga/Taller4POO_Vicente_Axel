package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import dominio.AsignaturaCertificacion;
import dominio.Certificacion;
import dominio.Curso;
import dominio.Estudiante;
import dominio.Nota;
import dominio.RegistroCertificacion;
import dominio.Usuario;

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
		estrategiaPromedio = new EstrategiaPromedioGeneral();
	}

	private static Sistema instancia;

	public static Sistema getInstancia() {
		if (instancia == null) {
			instancia = new Sistema();
		}
		return instancia;
	}

	@Override
	public void cargarDatos() throws FileNotFoundException {
		cargarUsuarios();
		cargarEstudiantes();
		cargarCursos();
		cargarCertificaciones();
		cargarNotas();
		cargarRegistros();
		cargarAsignaturasCertificacion();

	}

	private void cargarEstudiantes() throws FileNotFoundException {
		File f = new File("estudiantes.txt");
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
			String rut = partes[0];
			// String nombre = partes[1];
			String carrera = partes[2];
			int semestre = Integer.parseInt(partes[3]);
			String correo = partes[4];
			String contraseña = partes[5];
			String nombreUsuario = rut;
			Estudiante e = new Estudiante(nombreUsuario, contraseña, rut, carrera, semestre, correo);
			usuarios.add(e);

		}
		s.close();

	}

	private void cargarAsignaturasCertificacion() {

	}

	private void cargarRegistros() {
		// TODO Auto-generated method stub

	}

	private void cargarCursos() {
		// TODO Auto-generated method stub

	}

	private void cargarNotas() throws FileNotFoundException {
	    File f = new File("notas.txt");
	    Scanner s = new Scanner(f);

	    while (s.hasNextLine()) {
	        String linea = s.nextLine().trim();
	        if (linea.isEmpty()) continue;

	        String[] partes = linea.split(";");

	        String rut = partes[0];
	        String codigoAsignatura = partes[1];
	        String semestre = partes[2];
	        double notaObtenida = Double.parseDouble(partes[3]);

	        String estado = partes[4];

	        Nota n = new Nota(rut, codigoAsignatura, semestre, notaObtenida, estado);
	        notas.add(n);
	    }

	    s.close();
	}


	private void cargarCertificaciones() {
		// TODO Auto-generated method stub

	}

	private void cargarUsuarios() throws FileNotFoundException {
		File f = new File("usuarios.txt");
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
			String nombreUsuario = partes[0];
			String contraseña = partes[1];
			String rol = partes[2];
			String adicional = "";
			if (partes.length >= 4) {
				adicional = partes[3];
			}
			Usuario u = UsuarioFactory.crearUsuario(nombreUsuario, contraseña, rol, adicional);
			usuarios.add(u);
		}
		s.close();
	}

	@Override
	public Usuario iniciarSesion(String nombreUsuario, String contraseña) {
		for (Usuario u : usuarios) {
			if (u.getNombreUsuario().equals(nombreUsuario) && u.getContraseña().equals(contraseña)) {
				return u;
			}
		}
		return null; // si no lo encuentra
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
		ArrayList<Nota> resultado = new ArrayList<>();

		for (Nota n : notas) {
			if (n.getRutEstudiante().equals(rut)) {
				resultado.add(n);
			}
		}
		return resultado;
	}

	@Override
	public double calcularPromedioGeneral(String rut) {
		ArrayList<Nota> notasEstudiante = getNotasPorEstudiante(rut);
		estrategiaPromedio = new EstrategiaPromedioGeneral();
		return estrategiaPromedio.calcular(notasEstudiante);
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
