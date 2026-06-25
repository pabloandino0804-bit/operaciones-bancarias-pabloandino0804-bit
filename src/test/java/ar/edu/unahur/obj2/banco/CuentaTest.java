package ar.edu.unahur.obj2.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.banco.excepciones.SaldoInsuficienteException;
import ar.edu.unahur.obj2.banco.observadores.NotificacionCliente;
import ar.edu.unahur.obj2.banco.observadores.ObservadorCuenta;
import ar.edu.unahur.obj2.banco.excepciones.MontoInvalidoException;

public class CuentaTest {
    @Test
    void retiroFallaSiSuperaElDescubierto() {
        CuentaBancaria cuenta = new CuentaBancaria(1234);

        assertThrows(SaldoInsuficienteException.class, () -> cuenta.retirar(50_001.00));
    }

    @Test
    void elDepositoalIgualQueRetiroFallaSiElMontoEsNegativo() {
        CuentaBancaria cuenta = new CuentaBancaria(1234);

        assertThrows(MontoInvalidoException.class, () -> cuenta.retirar(-15.00));

        assertThrows(MontoInvalidoException.class, () -> cuenta.depositar(-15.00));
    }

    @Test
    void testGetNumero() {
        CuentaBancaria cuenta = new CuentaBancaria(1234);

        assertEquals(1234, cuenta.getNumero());
    }

    @Test
    void elSaldoAlCrearUnaCuentaEs0_SiDepositaSuSaldoCambiara() {
        CuentaBancaria cuenta = new CuentaBancaria(2567);

        assertEquals(0.0, cuenta.getSaldo());

        cuenta.depositar(15.7);

        assertEquals(15.7, cuenta.getSaldo());
    }

    @Test
    void testRegistrarObservador() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567);
        ObservadorCuenta observador = new NotificacionCliente();

        miCuenta.registrarObservador(observador);

        miCuenta.depositar(50.0);

    }

    @Test
    void testEliminarObservador() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567);
        ObservadorCuenta observador = new NotificacionCliente();

        miCuenta.registrarObservador(observador);

    }
}
