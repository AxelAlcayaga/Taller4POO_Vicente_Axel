package presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import dominio.Coordinador;
import dominio.Certificacion;
import dominio.Estudiante;
import logica.ISistema;
import logica.Sistema;

public class MenuCoordinadorFrame extends JFrame {

	private Coordinador coordinador;
	private ISistema sistema;

	private JComboBox<String> comboEstudiantes;
	private JComboBox<String> comboCertificaciones;
	private JTextArea areaMensajes;

	private ArrayList<Estudiante> listaEstudiantes;
	private ArrayList<Certificacion> listaCertificaciones;

	public MenuCoordinadorFrame(Coordinador coordinador) {
		super("AcademiCore - Coordinador");
		this.coordinador = coordinador;
		this.sistema = Sistema.getInstancia();

		setSize(650, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Resumen", crearPanelResumen());
		tabs.addTab("Inscribir estudiante", crearPanelInscripcion());

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelResumen() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel lbl1 = new JLabel("Usuario: " + coordinador.getNombreUsuario());
		JLabel lbl2 = new JLabel("Rol: Coordinador");

		lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl2.setAlignmentX(Component.LEFT_ALIGNMENT);

		p.add(lbl1);
		p.add(Box.createVerticalStrut(10));
		p.add(lbl2);

		return p;
	}

	private JPanel crearPanelInscripcion() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 5, 5));

		comboEstudiantes = new JComboBox<>();
		comboCertificaciones = new JComboBox<>();

		listaEstudiantes = sistema.listarEstudiantes();
		listaCertificaciones = sistema.listarCertificaciones();

		for (Estudiante e : listaEstudiantes) {
			comboEstudiantes.addItem(e.getRut() + " - " + e.getNombreUsuario());
		}

		for (Certificacion c : listaCertificaciones) {
			comboCertificaciones.addItem(c.getIdCertificacion() + " - " + c.getNombreCertificacion());
		}

		panelSuperior.add(new JLabel("Estudiante (RUT - usuario):"));
		panelSuperior.add(comboEstudiantes);
		panelSuperior.add(new JLabel("Certificación:"));
		panelSuperior.add(comboCertificaciones);

		p.add(panelSuperior, BorderLayout.NORTH);

		areaMensajes = new JTextArea();
		areaMensajes.setEditable(false);
		p.add(new JScrollPane(areaMensajes), BorderLayout.CENTER);

		JButton btnInscribir = new JButton("Inscribir estudiante en certificación");
		p.add(btnInscribir, BorderLayout.SOUTH);

		btnInscribir.addActionListener(e -> inscribirSeleccion());

		return p;
	}

	private void inscribirSeleccion() {
		int idxEst = comboEstudiantes.getSelectedIndex();
		int idxCert = comboCertificaciones.getSelectedIndex();

		if (idxEst < 0 || idxCert < 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante y una certificación.", "Mensaje",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Estudiante est = listaEstudiantes.get(idxEst);
		Certificacion cert = listaCertificaciones.get(idxCert);

		sistema.inscribirEstudianteEnCertificacion(est.getRut(), cert.getIdCertificacion());

		String msg = "Estudiante " + est.getRut() + " inscrito en " + cert.getNombreCertificacion() + "\n";

		areaMensajes.append(msg);

		JOptionPane.showMessageDialog(this, "Inscripción realizada correctamente.", "Mensaje",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
