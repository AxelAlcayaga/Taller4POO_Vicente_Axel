package dominio;

public abstract class Usuario {
	
	private String nombreUsuario;
	private String contraseña;
	private String rolUsuario;
	public Usuario(String nombreUsuario, String contraseña, String rolUsuario) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.rolUsuario = rolUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getRolUsuario() {
		return rolUsuario;
	}
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	
	public boolean esAdministrador() { return false;}
	public boolean esCoordinador() { return false;}
	public boolean esEstudiante() { return false;}


}
