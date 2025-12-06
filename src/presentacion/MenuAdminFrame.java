package presentacion;

import javax.swing.*;
import java.awt.*;

import dominio.Administrador;
import logica.ISistema;
import logica.Sistema;

public class MenuAdminFrame extends JFrame {

    private Administrador admin;
    private ISistema sistema;

    public MenuAdminFrame(Administrador admin) {
        super("AcademiCore - Administrador");

        this.admin = admin;
        this.sistema = Sistema.getInstancia();

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lbl = new JLabel("Menú Administrador", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        add(lbl, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));

        JButton btnUsuarios = new JButton("Gestionar usuarios");
        JButton btnCursos = new JButton("Ver cursos");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnUsuarios);
        panel.add(btnCursos);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        btnUsuarios.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Aquí irá la gestión de usuarios."));

        btnCursos.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Aquí se mostrarán los cursos."));

        btnSalir.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}

