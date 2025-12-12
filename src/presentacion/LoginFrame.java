package presentacion;

import javax.swing.*;
import java.awt.*;
import logica.*;
import dominio.*;

public class LoginFrame extends JFrame {

	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	private ISistema sistema;

	public LoginFrame() {
		super("AcademiCore - Login");

		sistema = Sistema.getInstancia();

		setSize(350, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 2, 5, 5));

		add(new JLabel("Usuario:"));
		txtUsuario = new JTextField();
		add(txtUsuario);

		add(new JLabel("Contraseña:"));
		txtContrasena = new JPasswordField();
		add(txtContrasena);

		JButton btnLogin = new JButton("Iniciar Sesión");
		add(btnLogin);

		btnLogin.addActionListener(e -> iniciarSesion());

		setVisible(true);
	}

	private void iniciarSesion() {
		String usuario = txtUsuario.getText();
		String contrasena = new String(txtContrasena.getPassword());

		Usuario u = sistema.iniciarSesion(usuario, contrasena);

		if (u == null) {
			JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
			return;
		}

		if (u.esAdministrador()) {
			new MenuAdminFrame((Administrador) u);
		} else if (u.esCoordinador()) {
			new MenuCoordinadorFrame((Coordinador) u);
		} else if (u.esEstudiante()) {
			new MenuEstudianteFrame((Estudiante) u);
		}

		dispose();
	}
}
