package dominio;

public class RegistroCertificacion {

	private String rutEstudiante;
	private String idCertificacion;
	private String fechaRegistro;
	private String estado;
	private int avance;

	public RegistroCertificacion(String rutEstudiante, String idCertificacion, String fechaRegistro, String estado,
			int avance) {
		this.rutEstudiante = rutEstudiante;
		this.idCertificacion = idCertificacion;
		this.fechaRegistro = fechaRegistro;
		this.estado = estado;
		this.avance = avance;
	}

	public String getRutEstudiante() {
		return rutEstudiante;
	}

	public String getIdCertificacion() {
		return idCertificacion;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public String getEstado() {
		return estado;
	}

	public int getAvance() {
		return avance;
	}
}
