package presentacion;

import javax.swing.*;
import java.awt.*;

import dominio.Coordinador;
import logica.ISistema;
import logica.Sistema;

public class MenuCoordinadorFrame extends JFrame {

	private Coordinador coordinador;
	private ISistema sistema;

	public MenuCoordinadorFrame(Coordinador coordinador) {
		super("AcademiCore - Coordinador");
		this.coordinador = coordinador;
		this.sistema = Sistema.getInstancia();

		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Certificaciones", crearPanelCertificaciones());
		tabs.addTab("Métricas", crearPanelMetricas());
		tabs.addTab("Inscripción", crearPanelInscripcion());
		tabs.addTab("Progreso", crearPanelProgreso());

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelCertificaciones() {
		JPanel p = new JPanel(new BorderLayout());
		JTextArea area = new JTextArea("Aquí puedes:\n" + "- Modificar línea de certificación\n"
				+ "- Generar certificados para estudiantes completados\n\n"
				+ "(GUI lista, falta conectar lógica real en Sistema)");
		area.setEditable(false);
		p.add(new JScrollPane(area), BorderLayout.CENTER);
		return p;
	}

	private JPanel crearPanelMetricas() {
		JPanel p = new JPanel(new BorderLayout());
		JTextArea area = new JTextArea("Panel de Métricas y Análisis:\n"
				+ "- Aquí podrías mostrar cantidad de estudiantes por certificación,\n"
				+ "  tasas de aprobación, etc.\n\n" + "(TODO: llenar usando datos reales de Sistema)");
		area.setEditable(false);
		p.add(new JScrollPane(area), BorderLayout.CENTER);
		return p;
	}

	private JPanel crearPanelInscripcion() {
		JPanel p = new JPanel(new BorderLayout());

		JTextArea info = new JTextArea("Inscripción a certificaciones:\n" + "- Listar líneas disponibles\n"
				+ "- Mostrar requisitos y descripción\n" + "- Inscribir estudiante validando prerrequisitos\n");
		info.setEditable(false);

		JPanel abajo = new JPanel(new GridLayout(3, 2, 5, 5));
		JTextField txtRut = new JTextField();
		JTextField txtIdCert = new JTextField();
		JButton btnInscribir = new JButton("Inscribir");

		abajo.add(new JLabel("RUT estudiante:"));
		abajo.add(txtRut);
		abajo.add(new JLabel("ID certificación:"));
		abajo.add(txtIdCert);
		abajo.add(new JLabel());
		abajo.add(btnInscribir);

		btnInscribir.addActionListener(e -> {
			String rut = txtRut.getText();
			String id = txtIdCert.getText();
			sistema.inscribirEstudianteEnCertitifacion(rut, id);
			JOptionPane.showMessageDialog(this, "Inscripción registrada (falta validar prerrequisitos en la lógica).");
		});

		p.add(new JScrollPane(info), BorderLayout.CENTER);
		p.add(abajo, BorderLayout.SOUTH);
		return p;
	}

	private JPanel crearPanelProgreso() {
		JPanel p = new JPanel(new BorderLayout());
		JTextArea area = new JTextArea("Seguimiento de Progreso:\n"
				+ "- Aquí se puede usar Visitor para distintas acciones según tipo de certificación\n"
				+ "- Mostrar progreso y asignaturas pendientes\n\n"
				+ "(GUI lista; Visitor/logic en Sistema todavía por implementar.)");
		area.setEditable(false);
		p.add(new JScrollPane(area), BorderLayout.CENTER);
		return p;
	}
}