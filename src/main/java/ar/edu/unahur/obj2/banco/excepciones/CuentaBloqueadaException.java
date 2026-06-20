package ar.edu.unahur.obj2.banco.excepciones;

public class CuentaBloqueadaException extends RuntimeException{

    public CuentaBloqueadaException(String mensaje) {
        super(mensaje);
    }
    
}
