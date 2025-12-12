package presentacion;

import javax.swing.*;
import java.awt.*;
import dominio.Estudiante;
import dominio.Certificacion;
import logica.ISistema;
import logica.Sistema;

public class MenuCoordinadorFrame extends JFrame {

	private ISistema sistema;

	public MenuCoordinadorFrame() {
		super("AcademiCore - Coordinador");
		this.sistema = Sistema.getInstancia();

		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Resumen", crearPanelResumen());
		tabs.addTab("Inscribir estudiante", crearPanelInscripcion());

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelResumen() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btnCargar = new JButton("Cargar resumen");

		btnCargar.addActionListener(e -> {
			area.setText("");
			for (Estudiante est : sistema.listarEstudiantes()) {
				int creditos = sistema.calcularCreditosAprobados(est.getRut());
				area.append(est.getRut() + " | " + est.getCorreo() + " | Créditos aprobados: " + creditos + "\n");
			}
		});

		panel.add(new JScrollPane(area), BorderLayout.CENTER);
		panel.add(btnCargar, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel crearPanelInscripcion() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));

		JComboBox<String> cbEstudiantes = new JComboBox<>();
		JComboBox<String> cbCertificaciones = new JComboBox<>();

		for (Estudiante e : sistema.listarEstudiantes()) {
			cbEstudiantes.addItem(e.getRut() + " - " + e.getCorreo());
		}

		for (Certificacion c : sistema.listarCertificaciones()) {
			cbCertificaciones.addItem(c.getIdCertificacion() + " - " + c.getNombreCertificacion());
		}

		form.add(new JLabel("Estudiante (RUT - usuario):"));
		form.add(cbEstudiantes);
		form.add(new JLabel("Certificación:"));
		form.add(cbCertificaciones);

		JTextArea areaInfo = new JTextArea();
		areaInfo.setEditable(false);

		JButton btnInscribir = new JButton("Inscribir estudiante en certificación");

		btnInscribir.addActionListener(e -> {
			if (cbEstudiantes.getSelectedItem() == null || cbCertificaciones.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar estudiante y certificación");
				return;
			}

			String rut = cbEstudiantes.getSelectedItem().toString().split(" - ")[0];
			String idCert = cbCertificaciones.getSelectedItem().toString().split(" - ")[0];

			Certificacion cert = sistema.buscarCertificacion(idCert);
			int creditosAprobados = sistema.calcularCreditosAprobados(rut);

			areaInfo.setText("Estudiante: " + rut + "\n" + "Certificación: " + cert.getNombreCertificacion() + "\n"
					+ "Créditos aprobados: " + creditosAprobados + "\n" + "Créditos requeridos: "
					+ cert.getCreditosMinimos() + "\n");

			if (creditosAprobados < cert.getCreditosMinimos()) {
				JOptionPane.showMessageDialog(this, "El estudiante tiene " + creditosAprobados
						+ " créditos aprobados, y se requieren " + cert.getCreditosMinimos());
				return;
			}

			sistema.inscribirEstudianteEnCertificacion(rut, idCert);
			JOptionPane.showMessageDialog(this, "Estudiante inscrito correctamente");
		});

		panel.add(form, BorderLayout.NORTH);
		panel.add(new JScrollPane(areaInfo), BorderLayout.CENTER);
		panel.add(btnInscribir, BorderLayout.SOUTH);

		return panel;
	}
}
