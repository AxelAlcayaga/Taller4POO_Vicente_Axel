package logica;

import dominio.Administrador;
import dominio.Coordinador;
import dominio.Estudiante;
import dominio.Usuario;

public class UsuarioFactory {

	public static Usuario crearUsuario(String tipo, String nombreUsuario, String contraseña, String rut, String carrera,
			int semestre, String correo, String area) {

		tipo = tipo.toUpperCase();

		switch (tipo) {
		case "ADMINISTRADOR":
			return new Administrador(nombreUsuario, contraseña);
		case "COORDINADOR":
			return new Coordinador(nombreUsuario, contraseña, area);
		case "ESTUDIANTE":
			return new Estudiante(nombreUsuario, contraseña, rut, carrera, semestre, correo);
		default:
			System.out.println("Tipo de usuario desconocido" + tipo);
			return null;

		}
	}

}
