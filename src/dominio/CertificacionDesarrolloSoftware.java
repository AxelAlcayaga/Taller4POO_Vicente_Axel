package dominio;

import logica.CertificacionVisitor;

public class CertificacionDesarrolloSoftware extends Certificacion {

	public CertificacionDesarrolloSoftware(String id, String nombre, String descripcion, int creditosMinimos,
			int añosValidez) {
		super(id, nombre, descripcion, creditosMinimos, añosValidez);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(CertificacionVisitor visitor) {
		visitor.visitar(this);
	}

}
