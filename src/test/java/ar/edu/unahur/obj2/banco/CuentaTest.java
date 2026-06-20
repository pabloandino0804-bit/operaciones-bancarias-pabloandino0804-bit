package ar.edu.unahur.obj2.banco;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.banco.excepciones.SaldoInsuficienteException;

public class CuentaTest {
    
    @Test
    void retiroFallaSiSuperaElDescubierto() {
        CuentaBancaria cuenta = new CuentaBancaria(1234);
        assertThrows(SaldoInsuficienteException.class, () -> cuenta.retirar(50_001.00));
    }

    @Test
    void testDepositar() {
        
    }

    @Test
    void testEliminarObservador() {
        
    }

    @Test
    void testGetNumero() {
        
    }

    @Test
    void testGetSaldo() {
        
    }

    @Test
    void testRegistrarObservador() {
        
    }
    
}
