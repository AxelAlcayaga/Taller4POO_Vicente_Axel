package presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import dominio.Estudiante;
import dominio.Nota;
import logica.ISistema;
import logica.Sistema;

public class MenuEstudianteFrame extends JFrame {

	private Estudiante estudiante;
	private ISistema sistema;

	public MenuEstudianteFrame(Estudiante estudiante) {
		super("AcademiCore - Estudiante");
		this.estudiante = estudiante;
		this.sistema = Sistema.getInstancia();

		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Datos", crearPanelDatos());
		tabs.addTab("Notas", crearPanelNotas());
		tabs.addTab("Promedio", crearPanelPromedio());
		tabs.addTab("Certificaciones", crearPanelCertificaciones());
		add(tabs);

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelDatos() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel lbl1 = new JLabel("Usuario: " + estudiante.getNombreUsuario());
		JLabel lbl2 = new JLabel("RUT: " + estudiante.getRut());
		JLabel lbl3 = new JLabel("Carrera: " + estudiante.getCarrera() + " | Semestre: " + estudiante.getSemestre());

		lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl2.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl3.setAlignmentX(Component.LEFT_ALIGNMENT);

		p.add(lbl1);
		p.add(Box.createVerticalStrut(10));
		p.add(lbl2);
		p.add(Box.createVerticalStrut(10));
		p.add(lbl3);

		return p;
	}

	private JPanel crearPanelNotas() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);
		p.add(new JScrollPane(area), BorderLayout.CENTER);

		JButton btnCargar = new JButton("Cargar notas");
		p.add(btnCargar, BorderLayout.SOUTH);

		btnCargar.addActionListener(e -> {
			ArrayList<Nota> notas = sistema.getNotasPorEstudiante(estudiante.getRut());
			if (notas == null || notas.isEmpty()) {
				area.setText("No hay notas registradas para tu RUT.");
				return;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("Notas del estudiante ").append(estudiante.getRut()).append(":\n\n");

			for (Nota n : notas) {
				sb.append("Asignatura: ").append(n.getCodigoAsignatura()).append(" | Semestre: ")
						.append(n.getSemestre()).append(" | Nota: ").append(n.getCalificacion()).append(" | Estado: ")
						.append(n.getEstado()).append("\n");
			}

			area.setText(sb.toString());
		});

		return p;
	}

	private JPanel crearPanelPromedio() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel lbl = new JLabel("Promedio general: -", SwingConstants.CENTER);
		lbl.setFont(new Font("Arial", Font.BOLD, 18));
		p.add(lbl, BorderLayout.CENTER);

		JButton btnCalcular = new JButton("Calcular promedio");
		p.add(btnCalcular, BorderLayout.SOUTH);

		btnCalcular.addActionListener(e -> {
			double prom = sistema.calcularPromedioGeneral(estudiante.getRut());
			lbl.setText("Promedio general: " + prom);
		});

		return p;
	}

	private JPanel crearPanelCertificaciones() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);
		p.add(new JScrollPane(area), BorderLayout.CENTER);

		JButton btnVer = new JButton("Ver progreso en certificaciones");
		p.add(btnVer, BorderLayout.SOUTH);

		btnVer.addActionListener(e -> {
			String reporte = sistema.generarReporteProgresoEstudiante(estudiante.getRut());

			if (reporte == null || reporte.isBlank()) {
				area.setText("No hay certificaciones registradas para tu RUT.");
			} else {
				area.setText(reporte);
			}
		});

		return p;
	}

}
