package logica;

import dominio.RegistroCertificacion;

public interface RegistroCertificacionVisitor {
    void visitar(RegistroCertificacion registro);
}
