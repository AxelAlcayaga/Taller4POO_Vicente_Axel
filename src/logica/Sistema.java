package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import dominio.AsignaturaCertificacion;
import dominio.Certificacion;
import dominio.CertificacionCiberseguridad;
import dominio.CertificacionDesarrolloSoftware;
import dominio.CertificacionSistemasInteligentes;
import dominio.Curso;
import dominio.Estudiante;
import dominio.Nota;
import dominio.RegistroCertificacion;
import dominio.Usuario;

public class Sistema implements ISistema {

	private ArrayList<Usuario> usuarios;
	private ArrayList<Curso> cursos;
	private ArrayList<Certificacion> certificaciones;
	private ArrayList<RegistroCertificacion> registros;
	private ArrayList<Nota> notas;
	private ArrayList<AsignaturaCertificacion> asignaturasCertificacion;
	private EstrategiaCalculoPromedio estrategiaPromedio;
	private ArrayList<Estudiante> estudiantes;

	private Sistema() {
		usuarios = new ArrayList<>();
		cursos = new ArrayList<>();
		certificaciones = new ArrayList<>();
		registros = new ArrayList<>();
		notas = new ArrayList<>();
		asignaturasCertificacion = new ArrayList<>();
		estrategiaPromedio = new EstrategiaPromedioGeneral();
		estudiantes = new ArrayList<>();
	}

	private static Sistema instancia;

	public static Sistema getInstancia() {
		if (instancia == null) {
			instancia = new Sistema();
		}
		return instancia;
	}

	@Override
	public void cargarDatos() throws FileNotFoundException {
		cargarUsuarios();
		cargarEstudiantes();
		cargarCursos();
		cargarCertificaciones();
		cargarNotas();
		cargarRegistros();
		cargarAsignaturasCertificacion();
	}

	private void cargarEstudiantes() throws FileNotFoundException {
		File f = new File("estudiantes.txt");
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
			String rut = partes[0];
			// String nombre = partes[1];
			String carrera = partes[2];
			int semestre = Integer.parseInt(partes[3]);
			String correo = partes[4];
			String contraseña = partes[5];
			Estudiante e = new Estudiante(correo, contraseña, rut, carrera, semestre, correo);
			usuarios.add(e);
			estudiantes.add(e);

		}
		s.close();

	}

	private void cargarAsignaturasCertificacion() throws FileNotFoundException {
		File f = new File("asignaturas_certificaciones.txt");
		Scanner s = new Scanner(f);

		while (s.hasNextLine()) {
			String linea = s.nextLine().trim();
			if (linea.isEmpty())
				continue;

			String[] partes = linea.split(";");
			String idCert = partes[0].trim();
			String nrc = partes[1].trim();

			AsignaturaCertificacion ac = new AsignaturaCertificacion(idCert, nrc);
			asignaturasCertificacion.add(ac);
		}

		s.close();
	}

	private void cargarCertificaciones() throws FileNotFoundException {
		File f = new File("certificaciones.txt");
		Scanner s = new Scanner(f, "UTF-8");

		while (s.hasNextLine()) {
			String linea = s.nextLine().trim();
			if (linea.isEmpty())
				continue;

			String[] partes = linea.split(";");

			String id = partes[0];
			String nombre = partes[1];
			String descripcion = partes[2];
			int creditosMinimos = Integer.parseInt(partes[3]);
			int añosValidez = Integer.parseInt(partes[4]);

			Certificacion c = crearCertificacionDesdeTxt(id, nombre, descripcion, creditosMinimos, añosValidez);
			certificaciones.add(c);
		}

		s.close();
	}

	private Certificacion crearCertificacionDesdeTxt(String id, String nombre, String descripcion, int creditosMinimos,
			int añosValidez) {

		switch (id) {
		case "CERT-001":
			return new CertificacionSistemasInteligentes(id, nombre, descripcion, creditosMinimos, añosValidez);

		case "CERT-002":
			return new CertificacionCiberseguridad(id, nombre, descripcion, creditosMinimos, añosValidez);

		case "CERT-003":
			return new CertificacionDesarrolloSoftware(id, nombre, descripcion, creditosMinimos, añosValidez);

		default:
			return new CertificacionSistemasInteligentes(id, nombre, descripcion, creditosMinimos, añosValidez);
		}
	}

	private void cargarRegistros() throws FileNotFoundException {
		File f = new File("registros.txt");
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			String linea = s.nextLine().trim();
			if (linea.isEmpty())
				continue;
			String[] partes = linea.split(";");
			String rut = partes[0];
			String idCert = partes[1];
			String fecha = partes[2];
			String estado = partes[3];
			int avance = Integer.parseInt(partes[4]);
			RegistroCertificacion r = new RegistroCertificacion(rut, idCert, fecha, estado, avance);
			registros.add(r);
		}
		s.close();
	}

	private void cargarCursos() throws FileNotFoundException {
		File f = new File("cursos.txt");
		Scanner sc = new Scanner(f);

		while (sc.hasNextLine()) {
			String[] p = sc.nextLine().split(";");

			String nrc = p[0];
			String nombre = p[1];
			int semestre = Integer.parseInt(p[2]);
			int creditos = Integer.parseInt(p[3]);
			String area = p[4];

			Curso c = new Curso(nrc, nombre, creditos, area, String.valueOf(semestre));

			if (p.length == 6) {
				String[] prereq = p[5].split(",");
				for (String r : prereq) {
					c.getPrerrequisitos().add(r.trim());
				}
			}

			cursos.add(c);
		}
		sc.close();
	}

	private void cargarNotas() throws FileNotFoundException {
		File f = new File("notas.txt");
		Scanner s = new Scanner(f);

		while (s.hasNextLine()) {
			String linea = s.nextLine().trim();
			if (linea.isEmpty())
				continue;

			String[] partes = linea.split(";");

			String rut = partes[0];
			String codigoAsignatura = partes[1];
			double notaObtenida = Double.parseDouble(partes[2]);
			String estado = partes[3];
			String semestre = partes[4];

			Nota n = new Nota(rut, codigoAsignatura, notaObtenida, estado, semestre);
			notas.add(n);
		}

		s.close();
	}

	private void cargarUsuarios() throws FileNotFoundException {
		File f = new File("usuarios.txt");
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			String[] partes = linea.split(";");
			String nombreUsuario = partes[0];
			String contraseña = partes[1];
			String rol = partes[2];
			String adicional = "";
			if (partes.length >= 4) {
				adicional = partes[3];
			}
			Usuario u = UsuarioFactory.crearUsuario(nombreUsuario, contraseña, rol, adicional);
			usuarios.add(u);
		}
		s.close();
	}

	@Override
	public Usuario iniciarSesion(String nombreUsuario, String contraseña) {
		for (Usuario u : usuarios) {
			if (u != null && u.getNombreUsuario().equals(nombreUsuario) && u.getContraseña().equals(contraseña)) {
				return u;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Curso> listarCurso() {
		return new ArrayList<>(cursos);
	}

	@Override
	public ArrayList<Certificacion> listarCertificaciones() {
		return new ArrayList<>(certificaciones);
	}

	@Override
	public ArrayList<RegistroCertificacion> getRegistrosPorEstudiante(String rut) {
		ArrayList<RegistroCertificacion> lista = new ArrayList<>();

		for (RegistroCertificacion r : registros) {
			if (r.getRutEstudiante().equals(rut)) {
				lista.add(r);
			}
		}

		return lista;
	}

	@Override
	public ArrayList<Nota> getNotasPorEstudiante(String rut) {
		ArrayList<Nota> resultado = new ArrayList<>();

		for (Nota n : notas) {
			if (n.getRutEstudiante().equals(rut)) {
				resultado.add(n);
			}
		}
		return resultado;
	}

	@Override
	public double calcularPromedioGeneral(String rut) {
		ArrayList<Nota> notasEstudiante = getNotasPorEstudiante(rut);
		estrategiaPromedio = new EstrategiaPromedioGeneral();
		return estrategiaPromedio.calcular(notasEstudiante);
	}

	@Override
	public double calcularPromedioPorSemestre(String rut) {
		ArrayList<Nota> notasEst = getNotasPorEstudiante(rut);
		if (notasEst == null || notasEst.isEmpty())
			return 0;

		String ultimo = null;
		int mejorKey = Integer.MIN_VALUE;

		for (Nota n : notasEst) {
			if (n == null)
				continue;
			String sem = n.getSemestre();
			int key = semestreKey(sem);
			if (key > mejorKey) {
				mejorKey = key;
				ultimo = sem;
			}
		}

		if (ultimo == null)
			return 0;

		double suma = 0;
		int cont = 0;

		for (Nota n : notasEst) {
			if (n == null)
				continue;
			if (!ultimo.equals(n.getSemestre()))
				continue;

			String estado = (n.getEstado() == null) ? "" : n.getEstado().trim().toLowerCase();
			if (estado.contains("curs"))
				continue;

			suma += n.getCalificacion();
			cont++;
		}

		return (cont == 0) ? 0 : (suma / cont);
	}

	private int semestreKey(String semestre) {
		if (semestre == null)
			return Integer.MIN_VALUE;
		String s = semestre.trim();
		if (!s.contains("-"))
			return Integer.MIN_VALUE;

		String[] p = s.split("-");
		try {
			int year = Integer.parseInt(p[0].trim());
			int sem = Integer.parseInt(p[1].trim());
			return year * 10 + sem;
		} catch (Exception e) {
			return Integer.MIN_VALUE;
		}
	}

	@Override
	public void agregarUsuario(Usuario u) {
		if (u == null)
			return;

		for (Usuario x : usuarios) {
			if (x != null && x.getNombreUsuario().equalsIgnoreCase(u.getNombreUsuario())) {
				return;
			}
		}

		usuarios.add(u);

		if (u instanceof Estudiante) {
			estudiantes.add((Estudiante) u);
		}
	}

	@Override
	public void eliminarUsuario(String nombreUsuario) {
		if (nombreUsuario == null)
			return;

		Usuario objetivo = buscarUsuarioPorNombre(nombreUsuario);
		if (objetivo == null)
			return;

		usuarios.remove(objetivo);

		if (objetivo instanceof Estudiante) {
			Estudiante est = (Estudiante) objetivo;
			estudiantes.remove(est);

			String rut = est.getRut();
			notas.removeIf(n -> n.getRutEstudiante().equals(rut));
			registros.removeIf(r -> r.getRutEstudiante().equals(rut));
		}
	}

	public int calcularProgresoCertificacion(String rut, String idCert) {

		ArrayList<String> ramosCert = new ArrayList<>();

		for (AsignaturaCertificacion ac : asignaturasCertificacion) {
			if (ac.getIdCertificacion().equals(idCert)) {
				ramosCert.add(ac.getNrcCurso());
			}
		}

		if (ramosCert.isEmpty())
			return 0;

		int aprobados = 0;

		for (Nota n : notas) {
			if (!n.getRutEstudiante().equals(rut))
				continue;
			if (!n.getEstado().equalsIgnoreCase("Aprobada"))
				continue;

			if (ramosCert.contains(n.getCodigoAsignatura())) {
				aprobados++;
			}
		}

		return (int) ((aprobados * 100.0) / ramosCert.size());
	}

	public void actualizarProgresosCertificaciones(String rut) {

		for (RegistroCertificacion r : registros) {
			if (!r.getRutEstudiante().equals(rut))
				continue;

			int avance = calcularProgresoCertificacion(rut, r.getIdCertificacion());
			r.setProgreso(avance);

			if (avance >= 100) {
				r.setEstado("Completada");
			}
		}
	}

	@Override
	public void cambiarContraseña(String nombreUsuario, String nuevaContraseña) {
		if (nombreUsuario == null || nuevaContraseña == null)
			return;

		Usuario u = buscarUsuarioPorNombre(nombreUsuario);
		if (u == null)
			return;

		u.setContraseña(nuevaContraseña);
	}

	@Override
	public ArrayList<Estudiante> getEstudiantes() {

		return null;
	}

	@Override
	public String generarReporteProgresoEstudiante(String rut) {

		if (registros == null || certificaciones == null) {
			return "No hay información de certificaciones cargada.";
		}

		ProgresoEstudianteVisitor visitor = new ProgresoEstudianteVisitor(rut, certificaciones);

		for (RegistroCertificacion r : registros) {
			if (r != null) {
				visitor.visitar(r);
			}
		}

		return visitor.getReporte();
	}

	@Override
	public ArrayList<Estudiante> listarEstudiantes() {
		return new ArrayList<>(estudiantes);
	}

	@Override
	public String inscribirEstudianteEnCertificacion(String rutEstudiante, String idCertificacion) {
		Estudiante est = null;
		for (Estudiante e : estudiantes) {
			if (e.getRut().equals(rutEstudiante)) {
				est = e;
				break;
			}
		}
		if (est == null) {
			return "No se encontró el estudiante con RUT " + rutEstudiante;
		}

		Certificacion cert = null;
		for (Certificacion c : certificaciones) {
			if (c.getIdCertificacion().equals(idCertificacion)) {
				cert = c;
				break;
			}
		}
		if (cert == null) {
			return "No se encontró la certificación con ID " + idCertificacion;
		}

		for (RegistroCertificacion r : registros) {
			if (r.getRutEstudiante().equals(rutEstudiante) && r.getIdCertificacion().equals(idCertificacion)
					&& !r.getEstado().equalsIgnoreCase("Suspendida")) {
				return "El estudiante ya tiene un registro para esta certificación (estado: " + r.getEstado() + ").";
			}
		}

		int creditosAprobados = calcularCreditosAprobados(rutEstudiante);
		int creditosMinimos = cert.getCreditosMinimos();

		if (creditosAprobados < creditosMinimos) {
			return "El estudiante tiene " + creditosAprobados + " créditos aprobados, " + "y se requieren "
					+ creditosMinimos + " para inscribir la certificación.";
		}

		String fechaHoy = java.time.LocalDate.now().toString();
		RegistroCertificacion reg = new RegistroCertificacion(rutEstudiante, idCertificacion, fechaHoy, "Activa", 0

		);

		registros.add(reg);

		return "Estudiante " + rutEstudiante + " inscrito en certificación " + idCertificacion + " correctamente.";
	}

	public int calcularCreditosAprobados(String rut) {
		int total = 0;

		for (Nota n : notas) {
			if (!n.getRutEstudiante().equals(rut))
				continue;
			if (!n.getEstado().equalsIgnoreCase("Aprobada"))
				continue;

			for (Curso c : cursos) {
				if (c.getNrc().equals(n.getCodigoAsignatura())) {
					total += c.getCreditos();
					break;
				}
			}
		}
		return total;
	}

	private Curso buscarCursoPorNrc(String nrc) {
		for (Curso c : cursos) {
			if (c.getNrc().equalsIgnoreCase(nrc)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Usuario> listarUsuarios() {
		return new ArrayList<>(usuarios);
	}

	@Override
	public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
		for (Usuario u : usuarios) {
			if (u.getNombreUsuario().equals(nombreUsuario)) {
				return u;
			}
		}
		return null;
	}

	@Override
	public boolean eliminarUsuarioYReferencias(String nombreUsuario) {

		Usuario objetivo = buscarUsuarioPorNombre(nombreUsuario);
		if (objetivo == null) {
			return false;
		}

		usuarios.remove(objetivo);

		if (objetivo instanceof Estudiante) {
			Estudiante est = (Estudiante) objetivo;

			estudiantes.remove(est);

			String rut = est.getRut();

			notas.removeIf(n -> n.getRutEstudiante().equals(rut));

			registros.removeIf(r -> r.getRutEstudiante().equals(rut));
		}

		System.out.println("Usuario " + nombreUsuario + " eliminado junto a sus referencias.");

		return true;
	}

	@Override
	public String generarResumenCertificaciones() {
		StringBuilder sb = new StringBuilder();
		sb.append("Resumen de certificaciones:\n\n");

		if (certificaciones.isEmpty()) {
			sb.append("No hay certificaciones cargadas.\n");
			return sb.toString();
		}

		for (Certificacion c : certificaciones) {
			int activos = 0;
			int completadas = 0;
			int suspendidas = 0;

			for (RegistroCertificacion r : registros) {
				if (r.getIdCertificacion().equals(c.getIdCertificacion())) {
					String estado = r.getEstado().toLowerCase();
					if (estado.contains("activa")) {
						activos++;
					} else if (estado.contains("complet")) {
						completadas++;
					} else if (estado.contains("suspend")) {
						suspendidas++;
					}
				}
			}

			sb.append(c.getIdCertificacion()).append(" - ").append(c.getNombreCertificacion()).append("\n")
					.append("  Inscritos activos: ").append(activos).append("\n").append("  Completadas: ")
					.append(completadas).append("\n").append("  Suspendidas: ").append(suspendidas).append("\n\n");
		}

		return sb.toString();
	}

	@Override
	public String generarListadoCertificados() {
		StringBuilder sb = new StringBuilder();
		sb.append("Estudiantes con certificaciones completadas:\n\n");

		boolean hayAlMenosUno = false;

		for (RegistroCertificacion r : registros) {
			boolean completadaPorEstado = r.getEstado().equalsIgnoreCase("Completada");
			boolean completadaPorAvance = r.getProgreso() >= 100;

			if (completadaPorEstado || completadaPorAvance) {
				hayAlMenosUno = true;

				Estudiante est = buscarEstudiantePorRut(r.getRutEstudiante());
				Certificacion cert = buscarCertificacion(r.getIdCertificacion());

				String nombreEst = (est != null) ? est.getNombreUsuario() : "(desconocido)";
				String nombreCert = (cert != null) ? cert.getNombreCertificacion() : r.getIdCertificacion();

				sb.append("Estudiante: ").append(nombreEst).append(" | RUT: ").append(r.getRutEstudiante())
						.append(" | Certificación: ").append(nombreCert).append(" | Avance: ").append(r.getProgreso())
						.append("%").append(" | Estado: ").append(r.getEstado()).append("\n");
			}
		}

		if (!hayAlMenosUno) {
			return "No hay certificaciones completadas todavía.";
		}

		return sb.toString();
	}

	@Override
	public Certificacion buscarCertificacion(String idCert) {
		for (Certificacion c : certificaciones) {
			if (c.getIdCertificacion().equals(idCert)) {
				return c;
			}
		}
		return null;
	}

	private Estudiante buscarEstudiantePorRut(String rut) {
		for (Usuario u : usuarios) {
			if (u instanceof Estudiante) {
				Estudiante e = (Estudiante) u;
				if (e.getRut().equals(rut)) {
					return e;
				}
			}
		}
		return null;
	}

}
