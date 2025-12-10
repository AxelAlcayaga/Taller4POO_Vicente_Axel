package dominio;

public class RegistroCertificacion {

	private String rutEstudiante;
	private String idCertificacion;
	private String fecha;
	private String estado;
	private int avance;

	public RegistroCertificacion(String rutEstudiante, String idCertificacion, String fecha, String estado,
			int avance) {
		super();
		this.rutEstudiante = rutEstudiante;
		this.idCertificacion = idCertificacion;
		this.fecha = fecha;
		this.estado = estado;
		this.avance = avance;
	}

	public String getRutEstudiante() {
		return rutEstudiante;
	}

	public void setRutEstudiante(String rutEstudiante) {
		this.rutEstudiante = rutEstudiante;
	}

	public String getIdCertificacion() {
		return idCertificacion;
	}

	public void setIdCertificacion(String idCertificacion) {
		this.idCertificacion = idCertificacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getAvance() {
		return avance;
	}

	public void setAvance(int avance) {
		this.avance = avance;
	}

}