package presentacion;

import javax.swing.*;
import java.awt.*;

import dominio.Administrador;
import dominio.Coordinador;
import dominio.Estudiante;
import dominio.Usuario;
import logica.ISistema;
import logica.Sistema;

public class MenuAdminFrame extends JFrame {

	private Administrador admin;
	private ISistema sistema;

	private JComboBox<String> comboUsuarios;
	private JTextArea areaInfo;

	public MenuAdminFrame(Administrador admin) {
		super("AcademiCore - Administrador");
		this.admin = admin;
		this.sistema = Sistema.getInstancia();

		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Crear usuario", crearPanelCrear());
		tabs.addTab("Modificar usuario", crearPanelModificar());
		tabs.addTab("Eliminar usuario", crearPanelEliminarUsuario());
		tabs.addTab("Restablecer contraseña", crearPanelReset());

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelCrear() {
		JPanel p = new JPanel(new GridLayout(7, 2, 5, 5));

		String[] tipos = { "Estudiante", "Coordinador" };
		JComboBox<String> cbTipo = new JComboBox<>(tipos);

		JTextField txtUsuario = new JTextField();
		JPasswordField txtPass = new JPasswordField();

		JTextField txtRut = new JTextField();
		JTextField txtCarrera = new JTextField();
		JTextField txtSemestre = new JTextField();
		JTextField txtCorreo = new JTextField();
		JTextField txtArea = new JTextField();

		p.add(new JLabel("Tipo:"));
		p.add(cbTipo);
		p.add(new JLabel("Usuario:"));
		p.add(txtUsuario);
		p.add(new JLabel("Contraseña:"));
		p.add(txtPass);
		p.add(new JLabel("RUT (solo estudiante):"));
		p.add(txtRut);
		p.add(new JLabel("Carrera (solo estudiante):"));
		p.add(txtCarrera);
		p.add(new JLabel("Semestre (solo estudiante):"));
		p.add(txtSemestre);
		p.add(new JLabel("Correo (solo estudiante):"));
		p.add(txtCorreo);
		p.add(new JLabel("Área (solo coordinador):"));
		p.add(txtArea);

		JButton btnCrear = new JButton("Crear");
		p.add(new JLabel());
		p.add(btnCrear);

		btnCrear.addActionListener(e -> {
			String tipo = (String) cbTipo.getSelectedItem();
			String usuario = txtUsuario.getText();
			String pass = new String(txtPass.getPassword());

			if (usuario.isEmpty() || pass.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Usuario y contraseña son obligatorios");
				return;
			}

			if ("Estudiante".equals(tipo)) {
				try {
					String rut = txtRut.getText();
					String carrera = txtCarrera.getText();
					int semestre = Integer.parseInt(txtSemestre.getText());
					String correo = txtCorreo.getText();

					Estudiante est = new Estudiante(usuario, pass, rut, carrera, semestre, correo);
					sistema.agregarUsuario(est);
					JOptionPane.showMessageDialog(this, "Estudiante creado correctamente");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Semestre debe ser numérico");
				}
			} else {
				String area = txtArea.getText();
				Coordinador coord = new Coordinador(usuario, pass, area);
				sistema.agregarUsuario(coord);
				JOptionPane.showMessageDialog(this, "Coordinador creado correctamente");
			}
		});

		cbTipo.addActionListener(e -> {
			String tipo = (String) cbTipo.getSelectedItem();
			boolean esEstudiante = "Estudiante".equals(tipo);

			txtRut.setEnabled(esEstudiante);
			txtCarrera.setEnabled(esEstudiante);
			txtSemestre.setEnabled(esEstudiante);
			txtCorreo.setEnabled(esEstudiante);

			txtArea.setEnabled(!esEstudiante);
		});

		cbTipo.setSelectedItem("Estudiante");
		txtArea.setEnabled(false);

		return p;
	}

	private JPanel crearPanelModificar() {
		JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));

		JTextField txtUsuario = new JTextField();
		JPasswordField txtNuevaPass = new JPasswordField();

		p.add(new JLabel("Usuario:"));
		p.add(txtUsuario);
		p.add(new JLabel("Nueva contraseña:"));
		p.add(txtNuevaPass);

		JButton btnCambiar = new JButton("Cambiar");
		p.add(new JLabel());
		p.add(btnCambiar);

		btnCambiar.addActionListener(e -> {
			String usuario = txtUsuario.getText();
			String nuevaPass = new String(txtNuevaPass.getPassword());

			sistema.cambiarContraseña(usuario, nuevaPass);
			JOptionPane.showMessageDialog(this, "Contraseña modificada (si el usuario existe)");
		});

		return p;
	}


	private JPanel crearPanelReset() {
		JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));

		JTextField txtUsuario = new JTextField();
		p.add(new JLabel("Usuario:"));
		p.add(txtUsuario);

		JButton btnReset = new JButton("Restablecer a '1234'");
		p.add(new JLabel());
		p.add(btnReset);

		btnReset.addActionListener(e -> {
			String usuario = txtUsuario.getText();
			sistema.cambiarContraseña(usuario, "1234");
			JOptionPane.showMessageDialog(this, "Contraseña restablecida a '1234' (si el usuario existe).");
		});

		return p;
	}

	private JPanel crearPanelEliminarUsuario() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel arriba = new JPanel(new GridLayout(2, 1, 5, 5));

		arriba.add(new JLabel("Seleccionar usuario a eliminar:"));

		comboUsuarios = new JComboBox<>();

		for (Usuario u : sistema.listarUsuarios()) {
			comboUsuarios.addItem(u.getNombreUsuario());
		}

		arriba.add(comboUsuarios);

		panel.add(arriba, BorderLayout.NORTH);

		areaInfo = new JTextArea();
		areaInfo.setEditable(false);
		panel.add(new JScrollPane(areaInfo), BorderLayout.CENTER);

		JButton btnEliminar = new JButton("Eliminar usuario");
		panel.add(btnEliminar, BorderLayout.SOUTH);

		btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());

		return panel;
	}

	private void eliminarUsuarioSeleccionado() {
		Object sel = comboUsuarios.getSelectedItem();
		if (sel == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario.", "Mensaje", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String nombreUsuario = sel.toString();

		int opcion = JOptionPane.showConfirmDialog(this,
				"¿Seguro que desea eliminar al usuario \"" + nombreUsuario + "\" y todas sus referencias?",
				"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

		if (opcion != JOptionPane.YES_OPTION) {
			return;
		}

		boolean ok = sistema.eliminarUsuarioYReferencias(nombreUsuario);

		if (ok) {
			areaInfo.append("Usuario " + nombreUsuario + " eliminado.\n");
			comboUsuarios.removeItem(nombreUsuario); // sacarlo del combo

			JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "No se encontró el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
