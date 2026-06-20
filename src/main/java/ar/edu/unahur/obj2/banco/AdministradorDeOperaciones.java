package ar.edu.unahur.obj2.banco;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.banco.excepciones.CuentaBloqueadaException;
import ar.edu.unahur.obj2.banco.excepciones.MontoInvalidoException;
import ar.edu.unahur.obj2.banco.operaciones.Operacion;

public class AdministradorDeOperaciones {
    private List<Operacion> loteOperaciones = new ArrayList<>();
    
    public void registrarEnLote(Operacion operacion) {
        this.loteOperaciones.add(operacion);
    }

    public void ejecutarLote() {
        for (Operacion operacion : this.loteOperaciones) {
            try {
                operacion.ejecutar();
            }
            catch (CuentaBloqueadaException e) {
                System.out.print(
                    "Operacion fallida en lote" + e.getMessage()
                );
            }
            catch (MontoInvalidoException e) {
                System.out.print(
                    "Operacion fallida en lote" + e.getMessage()
                );
            }
        }
        this.loteOperaciones.clear();
    }
}
