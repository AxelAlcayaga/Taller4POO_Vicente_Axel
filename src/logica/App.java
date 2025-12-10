package logica;

import java.io.FileNotFoundException;

import presentacion.LoginFrame;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		Sistema.getInstancia().cargarDatos();
		new LoginFrame();

	}

}
