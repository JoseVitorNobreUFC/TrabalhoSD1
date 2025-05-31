package entidades.helpers;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DesempacotamentoConsulta {
    public static Object lerStreamBinario(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objIn = new ObjectInputStream(in);
        return objIn.readObject();
    }
}
