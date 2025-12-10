package logica;

import java.util.ArrayList;
import dominio.Nota;

public class EstrategiaPromedioGeneral implements EstrategiaCalculoPromedio {

    @Override
    public double calcular(ArrayList<Nota> notas) {
        if (notas == null || notas.isEmpty()) {
            return 0.0;
        }

        double suma = 0.0;
        int contador = 0;

        for (Nota n : notas) {
            suma += n.getCalificacion();
            contador++;
        }

        double promedio = suma / contador;

        return Math.round(promedio * 10.0) / 10.0;
    }
}
