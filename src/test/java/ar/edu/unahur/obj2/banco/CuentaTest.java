package ar.edu.unahur.obj2.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.banco.excepciones.SaldoInsuficienteException;
import ar.edu.unahur.obj2.banco.observadores.NotificacionCliente;
import ar.edu.unahur.obj2.banco.observadores.ObservadorCuenta;
import ar.edu.unahur.obj2.banco.operaciones.Operacion;
import ar.edu.unahur.obj2.banco.operaciones.OperacionDeposito;
import ar.edu.unahur.obj2.banco.operaciones.OperacionRetiro;
import ar.edu.unahur.obj2.banco.excepciones.CuentaBloqueadaException;
import ar.edu.unahur.obj2.banco.excepciones.MontoInvalidoException;
import ar.edu.unahur.obj2.banco.observadores.Auditoria;

public class CuentaTest {
    @Test
    void retiroFallaSiSuperaElDescubierto() {
        CuentaBancaria cuenta = new CuentaBancaria(1234, 0.0);

        assertThrows(SaldoInsuficienteException.class, () -> cuenta.retirar(50_001.00));
    }

    @Test
    void elDepositarAlIgualQueRetiroFallaSiElMontoEsNegativo() {
        CuentaBancaria cuenta = new CuentaBancaria(1234, 0.0);

        assertThrows(MontoInvalidoException.class, () -> cuenta.retirar(-15.00));

        assertThrows(MontoInvalidoException.class, () -> cuenta.depositar(-15.00));
    }

    @Test
    void elDepositarAlIgualQueRetiroFallaSiElMontoLaCuentaEstaBloqueada() {
        CuentaBancaria cuenta = new CuentaBancaria(1234, 34.0);
        cuenta.habilitarDeshabilitar();

        assertThrows(CuentaBloqueadaException.class, () -> cuenta.retirar(15.00));

        assertThrows(CuentaBloqueadaException.class, () -> cuenta.depositar(15.00));
    }

    @Test
    void testGetNumero() {
        CuentaBancaria cuenta = new CuentaBancaria(1234, 1340.0);

        assertEquals(1234, cuenta.getNumero());
    }

    @Test
    void elSaldoAlCrearUnaCuentaEs0_SiDepositaSuSaldoCambiara() {
        CuentaBancaria cuenta = new CuentaBancaria(2567, 0.0);

        assertEquals(0.0, cuenta.getSaldo());

        cuenta.depositar(15.7);

        assertEquals(15.7, cuenta.getSaldo());
    }

    @Test
    void AlRegistrarUnaNotificacionClienteSeMuestraEnElSistemaLaCantDepositadaYRetiradaRealizada() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567, 0.0);
        ObservadorCuenta observador = new NotificacionCliente();

        miCuenta.registrarObservador(observador);

        miCuenta.depositar(16000.0);

        miCuenta.retirar(9000.0);

        miCuenta.eliminarObservador(observador);

        assertEquals(7000.0, miCuenta.getSaldo());
    }

    @Test
    void AlRegistrarUnaNotificacionClienteSeRegistraLosMovimientosRealizadosSobreCadaCuenta() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567, 0.0);
        ObservadorCuenta observador = new Auditoria();

        miCuenta.registrarObservador(observador);

        miCuenta.depositar(70000.0);

        miCuenta.retirar(5000.0);

        miCuenta.depositar(16000.0);

        miCuenta.retirar(9000.0);

        miCuenta.eliminarObservador(observador);

        assertEquals(72000.0, miCuenta.getSaldo());
    }

    @Test
    void alEjecutarUnLoteDeOperacionesLaCuentaDebeTenerUnSaldoDe300000() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567, 10000.0);

        AdministradorDeOperaciones adm = new AdministradorDeOperaciones();

        Operacion operacion1 = new OperacionRetiro(miCuenta, 5000.0);
        Operacion operacion2 = new OperacionDeposito(miCuenta, 300000.0);
        Operacion operacion3 = new OperacionRetiro(miCuenta, 50000.0);

        adm.registrarEnLote(operacion1);
        adm.registrarEnLote(operacion2);
        adm.registrarEnLote(operacion3);

        adm.ejecutarLote();

        assertEquals(miCuenta.getSaldo(), 255000.0);

        operacion3.deshacer();

        assertEquals(miCuenta.getSaldo(), 305000.0);

        operacion2.deshacer();

        assertEquals(miCuenta.getSaldo(), 5000.0);
    }

    @Test
    void alEjecutarSiHaUnoDeSaldoNegativoTiraUnaExcepcionDeMontoInvalido() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567, 10000.0);

        AdministradorDeOperaciones adm = new AdministradorDeOperaciones();

        Operacion operacion1 = new OperacionDeposito(miCuenta, 0.0);
        Operacion operacion2 = new OperacionRetiro(miCuenta, -300_000.0);
        Operacion operacion3 = new OperacionDeposito(miCuenta, -50000.0);

        adm.registrarEnLote(operacion1);
        adm.registrarEnLote(operacion2);
        adm.registrarEnLote(operacion3);

        adm.ejecutarLote();
    }

    @Test
    void OperacionEnLoteFallaSiElSaldoDeLaCuentaActualNoEsSuficiente() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567, 10000.0);

        AdministradorDeOperaciones adm = new AdministradorDeOperaciones();

        Operacion operacion1 = new OperacionRetiro(miCuenta, 500000.0);
        Operacion operacion2 = new OperacionDeposito(miCuenta, 300000.0);
        Operacion operacion3 = new OperacionRetiro(miCuenta, 50000.0);

        adm.registrarEnLote(operacion1);
        adm.registrarEnLote(operacion2);
        adm.registrarEnLote(operacion3);

        adm.ejecutarLote();
    }

    @Test
    void OperacionEnLoteFallaSiLaCuentaActualEstaBloqueada() {
        CuentaBancaria miCuenta = new CuentaBancaria(2567, 10000.0);
        miCuenta.habilitarDeshabilitar();

        AdministradorDeOperaciones adm = new AdministradorDeOperaciones();

        Operacion operacion1 = new OperacionDeposito(miCuenta, 5000.0);
        Operacion operacion2 = new OperacionRetiro(miCuenta, 30000_000.0);
        Operacion operacion3 = new OperacionDeposito(miCuenta, 50000.0);

        adm.registrarEnLote(operacion1);
        adm.registrarEnLote(operacion2);
        adm.registrarEnLote(operacion3);

        adm.ejecutarLote();
    }
}