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

        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Datos", crearPanelDatos());
        tabs.addTab("Notas", crearPanelNotas());
        tabs.addTab("Promedio", crearPanelPromedio());

        add(tabs);
        setVisible(true);
    }

    private JPanel crearPanelDatos() {
        JPanel p = new JPanel(new GridLayout(3, 1));
        p.add(new JLabel("Usuario: " + estudiante.getNombreUsuario()));
        p.add(new JLabel("RUT: " + estudiante.getRut()));
        p.add(new JLabel("Carrera: " + estudiante.getCarrera()
                         + " | Semestre: " + estudiante.getSemestre()));
        return p;
    }

    private JPanel crearPanelNotas() {
        JPanel p = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        p.add(new JScrollPane(area), BorderLayout.CENTER);

        JButton btnCargar = new JButton("Cargar notas");
        p.add(btnCargar, BorderLayout.SOUTH);

        btnCargar.addActionListener(e -> {
            ArrayList<Nota> notas = sistema.getNotasPorEstudiante(estudiante.getRut());
            if (notas.isEmpty()) {
                area.setText("No hay notas registradas.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Nota n : notas) {
                sb.append("Asignatura: ").append(n.getCodigoAsignatura())
                  .append(" | Nota: ").append(n.getCalificacion())
                  .append(" | Estado: ").append(n.getEstado())
                  .append("\n");
            }
            area.setText(sb.toString());
        });

        return p;
    }

    private JPanel crearPanelPromedio() {
        JPanel p = new JPanel(new BorderLayout());

        JLabel lbl = new JLabel("Promedio general: ", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(lbl, BorderLayout.CENTER);

        JButton btnCalcular = new JButton("Calcular");
        p.add(btnCalcular, BorderLayout.SOUTH);

        btnCalcular.addActionListener(e -> {
            double prom = sistema.calcularPromedioGeneral(estudiante.getRut());
            lbl.setText("Promedio general: " + prom);
        });

        return p;
    }
}

