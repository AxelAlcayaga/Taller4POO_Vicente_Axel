package dominio;

public class Administrador extends Usuario {

	public Administrador(String nombreUsuario, String contraseña) {
		super(nombreUsuario, contraseña, "ADMINISTRADOR");
	}
	
	public boolean esAdministrador() {return true;}

}
