package com.superheroes.pruebatecnica.exceptions;

public class MensajeError {

    private String mensaje;

    private String path;

    public MensajeError(String mensaje, String path) {

        this.mensaje = mensaje;
        this.path = path;
    }

    @Override
    public String toString() {
        return "ErrorMensaje{" + ", message='" + mensaje + '\'' + ", path='" + path + '\'' + '}';
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
