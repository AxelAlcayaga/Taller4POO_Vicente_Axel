package dominio;

import logica.CertificacionVisitor;

public class CertificacionSistemasInteligentes extends Certificacion{

	public CertificacionSistemasInteligentes(String id, String nombre, String descripcion, int creditosMinimos,
			int añosValidez) {
		super(id, nombre, descripcion, creditosMinimos, añosValidez);
	}

	@Override
	public void accept(CertificacionVisitor visitor) {
		visitor.visitar(this);
	}

		

}
