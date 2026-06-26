package ar.edu.unahur.obj2.banco.observadores;

import ar.edu.unahur.obj2.banco.CuentaBancaria;

public class NotificacionCliente implements ObservadorCuenta {

    @Override
    public void reaccionar(CuentaBancaria cuentaBancaria, String tipo, Double monto) {
        if (tipo == "DEPOSITO")
            System.out.print(
                    "[CLIENTE] Se acreditaron " + "$" + monto + " en cuenta " + cuentaBancaria.getNumero());
        else {
            System.out.print(
                    "[CLIENTE] Se debitarion " + "$" + monto + " en cuenta " + cuentaBancaria.getNumero());
        }
    }
}
