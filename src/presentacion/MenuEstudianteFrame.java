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

        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lbl = new JLabel("Bienvenido " + estudiante.getNombreUsuario(), SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        add(lbl, BorderLayout.NORTH);

        JPanel info = new JPanel(new GridLayout(3, 1));
        info.add(new JLabel("RUT: " + estudiante.getRut()));
        info.add(new JLabel("Carrera: " + estudiante.getCarrera()));
        info.add(new JLabel("Semestre: " + estudiante.getSemestre()));
        add(info, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout());

        JButton btnNotas = new JButton("Ver notas");
        JButton btnProm = new JButton("Ver promedio");
        JButton btnSalir = new JButton("Salir");

        botones.add(btnNotas);
        botones.add(btnProm);
        botones.add(btnSalir);

        add(botones, BorderLayout.SOUTH);

        btnNotas.addActionListener(e -> mostrarNotas());

        btnProm.addActionListener(e -> {
            double promedio = sistema.calcularPromedioGeneral(estudiante.getRut());
            JOptionPane.showMessageDialog(this, "Tu promedio es: " + promedio);
        });

        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void mostrarNotas() {
        ArrayList<Nota> notas = sistema.getNotasPorEstudiante(estudiante.getRut());

        if (notas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes notas registradas.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Nota n : notas) {
            sb.append("Asignatura: ").append(n.getCodigoAsignatura())
              .append(" | Nota: ").append(n.getCalificacion())
              .append(" | Estado: ").append(n.getEstado()).append("\n");
        }

        JTextArea txt = new JTextArea(sb.toString());
        txt.setEditable(false);

        JScrollPane panel = new JScrollPane(txt);
        JOptionPane.showMessageDialog(this, panel, "Notas", JOptionPane.INFORMATION_MESSAGE);
    }
}
