package streams;

import java.io.*;

import entidades.dtos.ConsultaDTO;

public class ConsultaOutputStream extends OutputStream {
    private final OutputStream destino;

    public ConsultaOutputStream(ConsultaDTO[] objetos, int quantidade, OutputStream destino) throws IOException {
        this.destino = destino;

        DataOutputStream dataOut = new DataOutputStream(destino);

        for (int i = 0; i < quantidade; i++) {
            ConsultaDTO obj = objetos[i];
            byte[] dados = obj.toBytes();
            int tamanho = dados.length;

            dataOut.writeInt(tamanho);
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
