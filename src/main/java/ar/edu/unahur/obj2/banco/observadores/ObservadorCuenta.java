package ar.edu.unahur.obj2.banco.observadores;

import ar.edu.unahur.obj2.banco.CuentaBancaria;

public interface ObservadorCuenta {
    void reaccionar(CuentaBancaria cuentaBancaria, String tipo, Double monto);

}
