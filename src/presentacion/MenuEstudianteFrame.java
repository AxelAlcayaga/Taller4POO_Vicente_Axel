//Vicente Andres Rojas Lillo - 22.141.463-2 - ICCI
//Axel Ignacio Alcayaga Flores -  20.832.945-6 - ICCI
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

		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Datos", crearPanelDatos());
		tabs.addTab("Notas", crearPanelNotas());
		tabs.addTab("Promedios", crearPanelPromedios());
		tabs.addTab("Certificaciones", crearPanelCertificaciones());

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelDatos() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel lbl1 = new JLabel("Usuario: " + estudiante.getNombreUsuario());
		JLabel lbl2 = new JLabel("RUT: " + estudiante.getRut());
		JLabel lbl3 = new JLabel(
				"Carrera: " + estudiante.getCarrera() + " | Semestre actual: " + estudiante.getSemestre());
		JLabel lbl4 = new JLabel("Correo: " + estudiante.getCorreo());

		lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl2.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl3.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl4.setAlignmentX(Component.LEFT_ALIGNMENT);

		p.add(lbl1);
		p.add(Box.createVerticalStrut(8));
		p.add(lbl2);
		p.add(Box.createVerticalStrut(8));
		p.add(lbl3);
		p.add(Box.createVerticalStrut(8));
		p.add(lbl4);

		return p;
	}

	private JPanel crearPanelNotas() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btn = new JButton("Cargar notas");

		btn.addActionListener(e -> {
			ArrayList<Nota> notas = sistema.getNotasPorEstudiante(estudiante.getRut());
			if (notas == null || notas.isEmpty()) {
				area.setText("No hay notas registradas.");
				return;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("Notas de ").append(estudiante.getRut()).append("\n\n");

			for (Nota n : notas) {
				sb.append(n.getCodigoAsignatura()).append(" | Sem: ").append(n.getSemestre()).append(" | Nota: ")
						.append(n.getCalificacion()).append(" | Estado: ").append(n.getEstado()).append("\n");
			}

			area.setText(sb.toString());
		});

		p.add(new JScrollPane(area), BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);
		return p;
	}

	private JPanel crearPanelPromedios() {
		JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel lblGeneral = new JLabel("Promedio general: -", SwingConstants.CENTER);
		lblGeneral.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel lblSemestre = new JLabel("Promedio último semestre: -", SwingConstants.CENTER);
		lblSemestre.setFont(new Font("Arial", Font.BOLD, 18));

		JButton btn = new JButton("Calcular");

		btn.addActionListener(e -> {
			double pg = sistema.calcularPromedioGeneral(estudiante.getRut());
			double ps = sistema.calcularPromedioPorSemestre(estudiante.getRut());
			lblGeneral.setText("Promedio general: " + String.format("%.2f", pg));
			lblSemestre.setText("Promedio último semestre: " + String.format("%.2f", ps));
		});

		p.add(lblGeneral);
		p.add(lblSemestre);
		p.add(btn);
		return p;
	}

	private JPanel crearPanelCertificaciones() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btn = new JButton("Ver progreso");

		btn.addActionListener(e -> {
			String rep = sistema.generarReporteProgresoEstudiante(estudiante.getRut());
			if (rep == null || rep.isBlank()) {
				area.setText("No hay certificaciones para este estudiante.");
			} else {
				area.setText(rep);
			}
		});

		p.add(new JScrollPane(area), BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);
		return p;
	}
}
