package dominio;

public class Usuario {

	private String nombreUsuario;
	private String contraseña;
	private String rolUsuario;

	public Usuario(String nombreUsuario, String contraseña, String rolUsuario) {
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.rolUsuario = rolUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public boolean esAdministrador() {
		return "Administrador".equalsIgnoreCase(rolUsuario) || "Admin".equalsIgnoreCase(rolUsuario);
	}

	public boolean esCoordinador() {
		return "Coordinador".equalsIgnoreCase(rolUsuario);
	}

	public boolean esEstudiante() {
		return "Estudiante".equalsIgnoreCase(rolUsuario);
	}
}
