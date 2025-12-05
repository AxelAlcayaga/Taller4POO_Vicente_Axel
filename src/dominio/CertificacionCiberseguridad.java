package dominio;

import logica.CertificacionVisitor;

public class CertificacionCiberseguridad extends Certificacion{

	public CertificacionCiberseguridad(String id, String nombre, String descripcion, int creditosMinimos,
			int añosValidez) {
		super(id, nombre, descripcion, creditosMinimos, añosValidez);
	}

	@Override
	public void accept(CertificacionVisitor visitor) {
		visitor.visitar(this);
	}

}
