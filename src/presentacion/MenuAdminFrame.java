package presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import dominio.Administrador;
import dominio.Coordinador;
import dominio.Estudiante;
import dominio.Usuario;
import logica.ISistema;
import logica.Sistema;

public class MenuAdminFrame extends JFrame {

	private Administrador admin;
	private ISistema sistema;

	public MenuAdminFrame(Administrador admin) {
		super("AcademiCore - Administrador");
		this.admin = admin;
		this.sistema = Sistema.getInstancia();

		setSize(650, 420);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Crear usuario", crearPanelCrear());
		tabs.addTab("Eliminar usuario", crearPanelEliminar());
		tabs.addTab("Restablecer contraseña", crearPanelReset());
		tabs.addTab("Listar usuarios", crearPanelListar());

		add(tabs);
		setVisible(true);
	}

	private JPanel crearPanelCrear() {
		JPanel root = new JPanel(new BorderLayout(10, 10));
		root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		String[] tipos = { "Estudiante", "Coordinador" };
		JComboBox<String> cbTipo = new JComboBox<>(tipos);

		JTextField txtUsuario = new JTextField();
		JPasswordField txtPass = new JPasswordField();

		JTextField txtRut = new JTextField();
		JTextField txtCarrera = new JTextField();
		JTextField txtSemestre = new JTextField();
		JTextField txtCorreo = new JTextField();

		JTextField txtArea = new JTextField();

		JPanel panelComun = new JPanel(new GridLayout(3, 2, 8, 8));
		panelComun.setBorder(BorderFactory.createTitledBorder("Datos de la cuenta"));
		panelComun.add(new JLabel("Tipo:"));
		panelComun.add(cbTipo);
		panelComun.add(new JLabel("Usuario:"));
		panelComun.add(txtUsuario);
		panelComun.add(new JLabel("Contraseña:"));
		panelComun.add(txtPass);

		JPanel panelEst = new JPanel(new GridLayout(4, 2, 8, 8));
		panelEst.setBorder(BorderFactory.createTitledBorder("Datos del estudiante"));
		panelEst.add(new JLabel("RUT:"));
		panelEst.add(txtRut);
		panelEst.add(new JLabel("Carrera:"));
		panelEst.add(txtCarrera);
		panelEst.add(new JLabel("Semestre:"));
		panelEst.add(txtSemestre);
		panelEst.add(new JLabel("Correo:"));
		panelEst.add(txtCorreo);

		JPanel panelCoord = new JPanel(new GridLayout(1, 2, 8, 8));
		panelCoord.setBorder(BorderFactory.createTitledBorder("Datos del coordinador"));
		panelCoord.add(new JLabel("Área:"));
		panelCoord.add(txtArea);

		JPanel form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		form.add(panelComun);
		form.add(Box.createVerticalStrut(10));
		form.add(panelEst);
		form.add(Box.createVerticalStrut(10));
		form.add(panelCoord);

		JScrollPane scroll = new JScrollPane(form);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		JButton btnCrear = new JButton("Crear");
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBoton.add(btnCrear);

		Runnable refrescar = () -> {
			boolean esEst = cbTipo.getSelectedItem().equals("Estudiante");

			txtRut.setEnabled(esEst);
			txtCarrera.setEnabled(esEst);
			txtSemestre.setEnabled(esEst);
			txtCorreo.setEnabled(esEst);

			txtArea.setEnabled(!esEst);
		};

		cbTipo.addActionListener(e -> refrescar.run());
		refrescar.run();

		btnCrear.addActionListener(e -> {
			String tipo = cbTipo.getSelectedItem().toString();
			String usuario = txtUsuario.getText().trim();
			String pass = new String(txtPass.getPassword()).trim();

			if (usuario.isEmpty() || pass.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Usuario y contraseña son obligatorios");
				return;
			}

			if (tipo.equals("Estudiante")) {
				String rut = txtRut.getText().trim();
				String carrera = txtCarrera.getText().trim();
				String semTxt = txtSemestre.getText().trim();
				String correo = txtCorreo.getText().trim();

				if (rut.isEmpty() || carrera.isEmpty() || semTxt.isEmpty() || correo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Complete todos los datos del estudiante");
					return;
				}

				int semestre;
				try {
					semestre = Integer.parseInt(semTxt);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Semestre debe ser un número");
					return;
				}

				Estudiante est = new Estudiante(usuario, pass, rut, carrera, semestre, correo);
				sistema.agregarUsuario(est);
				JOptionPane.showMessageDialog(this, "Estudiante creado");
			} else {
				String area = txtArea.getText().trim();
				if (area.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Área es obligatoria");
					return;
				}

				Coordinador coord = new Coordinador(usuario, pass, area);
				sistema.agregarUsuario(coord);
				JOptionPane.showMessageDialog(this, "Coordinador creado");
			}

			txtUsuario.setText("");
			txtPass.setText("");
			txtRut.setText("");
			txtCarrera.setText("");
			txtSemestre.setText("");
			txtCorreo.setText("");
			txtArea.setText("");
		});

		root.add(scroll, BorderLayout.CENTER);
		root.add(panelBoton, BorderLayout.SOUTH);

		return root;
	}

	private JPanel crearPanelEliminar() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JComboBox<String> cb = new JComboBox<>();
		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btnRefrescar = new JButton("Refrescar lista");
		JButton btnEliminar = new JButton("Eliminar seleccionado");

		JPanel arriba = new JPanel(new BorderLayout(10, 10));
		arriba.add(cb, BorderLayout.CENTER);

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botones.add(btnRefrescar);
		botones.add(btnEliminar);

		p.add(arriba, BorderLayout.NORTH);
		p.add(new JScrollPane(area), BorderLayout.CENTER);
		p.add(botones, BorderLayout.SOUTH);

		Runnable cargar = () -> {
			cb.removeAllItems();
			ArrayList<Usuario> lista = sistema.listarUsuarios();
			for (Usuario u : lista) {
				cb.addItem(u.getNombreUsuario() + " (" + u.getRolUsuario() + ")");
			}
		};

		btnRefrescar.addActionListener(e -> cargar.run());
		cargar.run();

		btnEliminar.addActionListener(e -> {
			Object sel = cb.getSelectedItem();
			if (sel == null)
				return;

			String nombreUsuario = sel.toString().split(" \\(")[0].trim();

			int op = JOptionPane.showConfirmDialog(this, "¿Eliminar \"" + nombreUsuario + "\" y sus referencias?",
					"Confirmar", JOptionPane.YES_NO_OPTION);

			if (op != JOptionPane.YES_OPTION)
				return;

			boolean ok = sistema.eliminarUsuarioYReferencias(nombreUsuario);

			if (ok) {
				area.append("Eliminado: " + nombreUsuario + "\n");
				cargar.run();
			} else {
				JOptionPane.showMessageDialog(this, "No se encontró el usuario");
			}
		});

		return p;
	}

	private JPanel crearPanelReset() {
		JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextField txtUsuario = new JTextField();
		JButton btn = new JButton("Restablecer a 1234");

		p.add(new JLabel("Usuario:"));
		p.add(txtUsuario);
		p.add(new JLabel());
		p.add(btn);

		btn.addActionListener(e -> {
			String user = txtUsuario.getText().trim();
			if (user.isEmpty())
				return;
			sistema.cambiarContraseña(user, "1234");
			JOptionPane.showMessageDialog(this, "Contraseña restablecida (si existe el usuario)");
		});

		return p;
	}

	private JPanel crearPanelListar() {
		JPanel p = new JPanel(new BorderLayout(10, 10));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea area = new JTextArea();
		area.setEditable(false);

		JButton btn = new JButton("Listar usuarios");

		btn.addActionListener(e -> {
			area.setText("");
			for (Usuario u : sistema.listarUsuarios()) {
				area.append(u.getNombreUsuario() + " | " + u.getRolUsuario() + "\n");
			}
		});

		p.add(new JScrollPane(area), BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);
		return p;
	}
}
