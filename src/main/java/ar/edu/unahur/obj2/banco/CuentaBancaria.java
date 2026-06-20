package ar.edu.unahur.obj2.banco;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.banco.excepciones.CuentaBloqueadaException;
import ar.edu.unahur.obj2.banco.excepciones.MontoInvalidoException;
import ar.edu.unahur.obj2.banco.excepciones.SaldoInsuficienteException;
import ar.edu.unahur.obj2.banco.observadores.ObservadorCuenta;

public class CuentaBancaria {
    private Integer numero;
    private Double saldo;
    private List<ObservadorCuenta> observadores;
    private static final Double Descubierto_Permitido = -50_000.0;
    private Boolean bloqueada = false;

    public CuentaBancaria(Integer numero) {
        this.numero = numero;
        this.saldo = 0.0;
        this.observadores = new ArrayList<>();
    }

    public Integer getNumero() {
        return numero;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void habilitarDeshabilitar() {
        if (bloqueada = true) {
            this.bloqueada = false;
        }
        else {
            this.bloqueada = true;
        }
    }

    public void depositar(Double monto) {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto debe que sea mayor a 0");
        }
        if(bloqueada) {
            throw new CuentaBloqueadaException("La cuenta esta bloqueada");
        }
        this.saldo += monto;
        notificarMovimiento("DEPOSITO", monto);
    }

    public void retirar(Double monto) {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto debe que sea mayor a 0");
        }
        if (this.saldo - monto < Descubierto_Permitido) {
            throw new SaldoInsuficienteException("El el saldo del usuario debe ser mayor que el saldo");
        }
        if(bloqueada) {
            throw new CuentaBloqueadaException("La cuenta esta bloqueada");
        }
        this.saldo -= monto;
        notificarMovimiento("RETIRO", monto);
    }

    //Observer
    public void registrarObservador(ObservadorCuenta observador) {
        this.observadores.add(observador);
    }

    public void eliminarObservador(ObservadorCuenta observador) {
        this.observadores.remove(observador);
    }

    private void notificarMovimiento(String tipo, Double monto) {
        for (ObservadorCuenta observador : this.observadores) {
            observador.reaccionar(this, tipo, monto);
        }
    }

}
