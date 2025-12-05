package dominio;

public class Coordinador extends Usuario{
	
	private String areaAcademica;

	public Coordinador(String nombreUsuario, String contraseña, String areaAcademica) {
		super(nombreUsuario, contraseña, "COORDINADOR");
		this.areaAcademica = areaAcademica;
		}

	public String getAreaAcademica() {
		return areaAcademica;
	}

	public void setAreaAcademica(String areaAcademica) {
		this.areaAcademica = areaAcademica;
	} 
	
	public boolean esCoordinador() {return true;}
	
}
