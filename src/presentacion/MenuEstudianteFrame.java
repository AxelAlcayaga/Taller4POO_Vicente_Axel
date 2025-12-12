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
        tabs.addTab("Malla", crearPanelMalla());

        add(tabs);
        setVisible(true);
    }

    // ===================== MALLA =====================

    private JPanel crearPanelMalla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelMalla = new JPanel();
        panelMalla.setLayout(new BoxLayout(panelMalla, BoxLayout.Y_AXIS));

        ArrayList<Nota> notasEst = sistema.getNotasPorEstudiante(estudiante.getRut());

        if (notasEst == null || notasEst.isEmpty()) {
            panelMalla.add(new JLabel("No hay información de malla para este estudiante."));
        } else {

            // Suponemos semestres del 1 al 10
            for (int semestre = 1; semestre <= 10; semestre++) {

                JPanel panelSemestre = new JPanel(new GridLayout(0, 1, 5, 5));
                panelSemestre.setBorder(
                        BorderFactory.createTitledBorder("Semestre " + semestre)
                );

                boolean hayAsignaturasEnSemestre = false;

                for (Nota n : notasEst) {
                    try {
                        int semNota = Integer.parseInt(n.getSemestre());
                        if (semNota == semestre) {

                            hayAsignaturasEnSemestre = true;

                            String texto = n.getCodigoAsignatura()
                                    + " | Estado: " + n.getEstado()
                                    + " | Nota: " + n.getCalificacion();

                            JLabel lbl = new JLabel(texto);
                            lbl.setOpaque(true);

                            String estado = n.getEstado().toLowerCase();

                            if (estado.contains("aprob")) {
                                lbl.setBackground(new Color(198, 239, 206));   // verde claro
                            } else if (estado.contains("reprob")) {
                                lbl.setBackground(new Color(255, 199, 206));   // rojo claro
                            } else if (estado.contains("curs") || estado.contains("inscrito")) {
                                lbl.setBackground(new Color(255, 235, 156));   // amarillo
                            } else {
                                lbl.setBackground(Color.LIGHT_GRAY);           // otros
                            }

                            panelSemestre.add(lbl);
                        }
                    } catch (NumberFormatException ex) {
                        // Si el semestre de la nota no es numérico, lo ignoramos
                    }
                }

                if (hayAsignaturasEnSemestre) {
                    panelMalla.add(panelSemestre);
                    panelMalla.add(Box.createVerticalStrut(10));
                }
            }

            if (panelMalla.getComponentCount() == 0) {
                panelMalla.add(new JLabel("No se pudo agrupar la malla por semestre."));
            }
        }

        JScrollPane scroll = new JScrollPane(panelMalla);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ===================== DATOS =====================

    private JPanel crearPanelDatos() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lbl1 = new JLabel("Usuario: " + estudiante.getNombreUsuario());
        JLabel lbl2 = new JLabel("RUT: " + estudiante.getRut());
        JLabel lbl3 = new JLabel("Carrera: " + estudiante.getCarrera()
                + " | Semestre: " + estudiante.getSemestre());

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

    // ===================== NOTAS =====================

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
                sb.append("Asignatura: ").append(n.getCodigoAsignatura())
                        .append(" | Semestre: ").append(n.getSemestre())
                        .append(" | Nota: ").append(n.getCalificacion())
                        .append(" | Estado: ").append(n.getEstado())
                        .append("\n");
            }

            area.setText(sb.toString());
        });

        return p;
    }

    // ===================== PROMEDIO =====================

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

    // ===================== CERTIFICACIONES =====================

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
