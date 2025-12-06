package logica;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import dominio.Certificacion;
import dominio.Curso;
import dominio.Estudiante;
import dominio.Nota;
import dominio.RegistroCertificacion;
import dominio.Usuario;

public interface ISistema {
	
	Usuario iniciarSesion(String nombreUsuario, String contraseña);
	
	void cargarDatos() throws FileNotFoundException;
	ArrayList<Curso> listarCurso();
	ArrayList<Certificacion> listarCertificaciones(); 
	ArrayList<RegistroCertificacion> getRegistrosPorEstudiante(String rut);
	ArrayList<Nota> getNotasPorEstudiante(String rut);
	ArrayList<Estudiante> getEstudiantes();

	
	double calcularPromedioGeneral(String rut);
	double calcularPromedioPorSemestre(String rut);
	void inscribirEstudianteEnCertitifacion(String rut, String idCertificacion);
	
	void agregarUsuario(Usuario u);
	void eliminarUsuario(String nombreUsuario);
	void cambiarContraseña(String nombreUsuario, String nuevaContraseña);
}
