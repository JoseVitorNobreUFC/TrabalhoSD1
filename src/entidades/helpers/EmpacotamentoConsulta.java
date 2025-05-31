package entidades.helpers;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;

public class EmpacotamentoConsulta {
    public static void gravarStreamBinario(Object objeto, OutputStream out) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(objeto);
        objOut.flush();
    }
}

