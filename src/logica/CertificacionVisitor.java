package logica;

import dominio.CertificacionCiberseguridad;
import dominio.CertificacionDesarrolloSoftware;
import dominio.CertificacionSistemasInteligentes;

public interface CertificacionVisitor {
	
	void visitar(CertificacionDesarrolloSoftware c);
	void visitar(CertificacionSistemasInteligentes c);
	void visitar(CertificacionCiberseguridad c);

}
