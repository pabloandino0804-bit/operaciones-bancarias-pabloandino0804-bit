package ar.edu.unahur.obj2.banco.observadores;

import ar.edu.unahur.obj2.banco.CuentaBancaria;

public class Auditoria implements ObservadorCuenta {

    @Override
    public void reaccionar(CuentaBancaria cuentaBancaria, String tipo, Double monto) {
        System.out.print(
                "[AUDITORÍA] Registro " + tipo + " de $" + monto + " en cuenta " + cuentaBancaria.getNumero());
    }

}
