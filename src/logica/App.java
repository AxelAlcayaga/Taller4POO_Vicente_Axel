//Vicente Andres Rojas Lillo - 22.141.463-2 - ICCI
//Axel Ignacio Alcayaga Flores -  20.832.945-6 - ICCI
package logica;

import java.io.FileNotFoundException;

import presentacion.LoginFrame;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		Sistema.getInstancia().cargarDatos();
		new LoginFrame();

	}

}
