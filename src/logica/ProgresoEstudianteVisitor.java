package logica;

import java.util.ArrayList;

import dominio.Certificacion;
import dominio.RegistroCertificacion;

public class ProgresoEstudianteVisitor implements RegistroCertificacionVisitor {

	private String rutObjetivo;
	private ArrayList<Certificacion> certificaciones;
	private StringBuilder reporte;

	public ProgresoEstudianteVisitor(String rutObjetivo, ArrayList<Certificacion> certificaciones) {
		this.rutObjetivo = rutObjetivo;
		this.certificaciones = certificaciones;
		this.reporte = new StringBuilder();
	}

	@Override
	public void visitar(RegistroCertificacion registro) {

		if (!registro.getRutEstudiante().equals(rutObjetivo)) {
			return;
		}

		Certificacion cert = buscarCertificacionPorId(registro.getIdCertificacion());
		String nombreCert;

		if (cert != null) {
			nombreCert = cert.getNombreCertificacion();
		} else {
			nombreCert = registro.getIdCertificacion();
		}

		int avance = registro.getAvance();

		reporte.append("Certificación: ").append(nombreCert).append(" | Estado: ").append(registro.getEstado())
				.append(" | Avance: ").append(avance).append("%").append("\n");
	}

	private Certificacion buscarCertificacionPorId(String id) {
		for (Certificacion c : certificaciones) {
			if (c.getIdCertificacion().equals(id)) {
				return c;
			}
		}
		return null;
	}

	public String getReporte() {
		if (reporte.length() == 0) {
			return "No tienes certificaciones registradas aún.";
		}
		return reporte.toString();
	}
}
