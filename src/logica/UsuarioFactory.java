package logica;

import dominio.Administrador;
import dominio.Coordinador;
import dominio.Usuario;

public class UsuarioFactory {

	public static Usuario crearUsuario(String nombreUsuario, String contraseña, String rol, String adicional) {

		rol = rol.toUpperCase();

		switch (rol) {
		case "ADMIN":
			return new Administrador(nombreUsuario, contraseña);
		case "COORDINADOR":
			return new Coordinador(nombreUsuario, contraseña, adicional);
		default:
			System.out.println("Tipo de usuario desconocido" + rol);
			return null;

		}
	}

}
