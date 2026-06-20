package ar.edu.unahur.obj2.banco.operaciones;

import ar.edu.unahur.obj2.banco.CuentaBancaria;

public class OperacionRetiro implements Operacion {
    private CuentaBancaria cuenta;
    private Double monto;
    
    public OperacionRetiro(CuentaBancaria cuenta ,Double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        this.cuenta.retirar(monto);
    }

    @Override
    public void deshacer() {
        this.cuenta.depositar(monto);
    }
}
