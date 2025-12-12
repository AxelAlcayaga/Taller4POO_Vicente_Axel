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
		JPanel panel = new JPanel(new BorderLayout());

		JPanel arriba = new JPanel(new GridLayout(2, 2));

		arriba.add(new JLabel("Estudiante (RUT - usuario):"));
		JComboBox<String> comboEstudiantes = new JComboBox<>();

		for (Estudiante e : sistema.listarEstudiantes()) {
			comboEstudiantes.addItem(e.getRut() + " - " + e.getNombreUsuario());
		}

		arriba.add(comboEstudiantes);

		arriba.add(new JLabel("Certificación:"));
		JComboBox<String> comboCertificaciones = new JComboBox<>();

		for (Certificacion c : sistema.listarCertificaciones()) {
			comboCertificaciones.addItem(c.getIdCertificacion() + " - " + c.getNombreCertificacion());
		}

		arriba.add(comboCertificaciones);

		panel.add(arriba, BorderLayout.NORTH);

		JButton inscribir = new JButton("Inscribir estudiante en certificación");

		inscribir.addActionListener(e -> {

			if (comboEstudiantes.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (comboCertificaciones.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar una certificación", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			String rut = comboEstudiantes.getSelectedItem().toString().split(" - ")[0];
			String idCert = comboCertificaciones.getSelectedItem().toString().split(" - ")[0];

			sistema.inscribirEstudianteEnCertificacion(rut, idCert);

			JOptionPane.showMessageDialog(this, "Estudiante inscrito correctamente en " + idCert);
		});

		panel.add(inscribir, BorderLayout.SOUTH);

		return panel;
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
