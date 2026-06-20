package ar.edu.unahur.obj2.banco.excepciones;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(String mensaje){
        super(mensaje);
    }
}
