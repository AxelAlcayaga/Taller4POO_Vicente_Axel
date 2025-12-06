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

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lbl = new JLabel("Área: " + coordinador.getAreaAcademica(), SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        add(lbl, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));

        JButton btnCert = new JButton("Ver certificaciones");
        JButton btnEst = new JButton("Ver estudiantes");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnCert);
        panel.add(btnEst);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        btnCert.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Aquí se mostrarán las certificaciones."));

        btnEst.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Aquí se mostrarán los estudiantes."));

        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
