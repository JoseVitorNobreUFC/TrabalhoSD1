package streams;

import java.io.*;

import entidades.dtos.ConsultaDTO;

public class ConsultaOutputStream extends OutputStream {
    private final OutputStream destino;

    public ConsultaOutputStream(ConsultaDTO[] objetos, int quantidade, OutputStream destino) throws IOException {
        this.destino = destino;

        DataOutputStream dataOut = new DataOutputStream(destino);

        for (ConsultaDTO obj : objetos) {
            byte[] dados = obj.toBytes();
            dataOut.writeInt(dados.length);
            dataOut.write(dados);
        }

        dataOut.flush();
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }

    @Override
    public void close() throws IOException {
        destino.close();
    }
}
