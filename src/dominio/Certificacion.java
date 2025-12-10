package dominio;

import logica.CertificacionVisitor;

public abstract class Certificacion {

	protected String idCertificacion;
	protected String nombreCertificacion;
	protected String descripcion;
	protected int creditosMinimos;
	protected int añosValidez;

	public Certificacion(String idCertificacion, String nombreCertificacion, String descripcion, int creditosMinimos,
			int añosValidez) {
		this.idCertificacion = idCertificacion;
		this.nombreCertificacion = nombreCertificacion;
		this.descripcion = descripcion;
		this.creditosMinimos = creditosMinimos;
		this.añosValidez = añosValidez;
	}

	public String getIdCertificacion() {
		return idCertificacion;
	}

	public String getNombreCertificacion() {
		return nombreCertificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCreditosMinimos() {
		return creditosMinimos;
	}

	public int getAñosValidez() {
		return añosValidez;
	}

	public abstract void accept(CertificacionVisitor visitor);
}
