package presentacion;

import javax.swing.*;
import java.awt.*;

import dominio.Coordinador;
import dominio.Certificacion;
import dominio.Estudiante;
import logica.ISistema;
import logica.Sistema;

public class MenuCoordinadorFrame extends JFrame {

	private Coordinador coordinador;
	private ISistema sistema;

	public MenuCoordinadorFrame(Coordinador coordinador) {
		super("AcademiCore - Coordinador");
		this.coordinador = coordinador;
		this.sistema = Sistema.getInstancia();

		setSize(700, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Inscribir", crearPanelInscribir());
		tabs.addTab("Resumen certificaciones", crearPanelResumen());
		tabs.addTab("Certificados", crearPanelCertificados());

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelInscribir() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JComboBox<String> cbEst = new JComboBox<>();
		JComboBox<String> cbCert = new JComboBox<>();

		for (Estudiante e : sistema.listarEstudiantes()) {
			cbEst.addItem(e.getRut() + " - " + e.getNombreUsuario());
		}

		for (Certificacion c : sistema.listarCertificaciones()) {
			cbCert.addItem(c.getIdCertificacion() + " - " + c.getNombreCertificacion());
		}

		JPanel top = new JPanel(new GridLayout(2, 2, 5, 5));
		top.add(new JLabel("Estudiante:"));
		top.add(cbEst);
		top.add(new JLabel("Certificación:"));
		top.add(cbCert);

		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btn = new JButton("Inscribir");

		btn.addActionListener(e -> {
			if (cbEst.getSelectedItem() == null || cbCert.getSelectedItem() == null)
				return;

			String rut = cbEst.getSelectedItem().toString().split(" - ")[0].trim();
			String idCert = cbCert.getSelectedItem().toString().split(" - ")[0].trim();

			String msg = sistema.inscribirEstudianteEnCertificacion(rut, idCert);
			area.setText(msg);
			JOptionPane.showMessageDialog(this, msg);
		});

		p.add(top, BorderLayout.NORTH);
		p.add(new JScrollPane(area), BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);
		return p;
	}

	private JPanel crearPanelResumen() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btn = new JButton("Ver resumen");

		btn.addActionListener(e -> area.setText(sistema.generarResumenCertificaciones()));

		p.add(new JScrollPane(area), BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);
		return p;
	}

	private JPanel crearPanelCertificados() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btn = new JButton("Ver certificados");

		btn.addActionListener(e -> area.setText(sistema.generarListadoCertificados()));

		p.add(new JScrollPane(area), BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);
		return p;
	}
}
