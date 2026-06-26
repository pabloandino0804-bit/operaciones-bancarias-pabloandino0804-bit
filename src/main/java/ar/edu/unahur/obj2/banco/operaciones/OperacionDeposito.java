package ar.edu.unahur.obj2.banco.operaciones;

import ar.edu.unahur.obj2.banco.CuentaBancaria;

public class OperacionDeposito implements Operacion {
    private CuentaBancaria cuenta;
    private Double monto;

    public OperacionDeposito(CuentaBancaria cuenta, Double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        this.cuenta.depositar(monto);
    }

    @Override
    public void deshacer() {
        this.cuenta.retirar(monto);
    }

}
