package ar.edu.unahur.obj2.banco.excepciones;

public class MontoInvalidoException extends RuntimeException{
    public MontoInvalidoException(String mensaje){
        super(mensaje);
    }
}
