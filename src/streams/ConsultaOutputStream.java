package streams;

import java.io.*;

import entidades.dtos.ConsultaDTO;
import entidades.dtos.ReplyMessageDTO;
import entidades.dtos.RequestMessageDTO;

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

    public ConsultaOutputStream(OutputStream destino) {
        this.destino = destino;
    }

    public void writeRequest(RequestMessageDTO request) throws IOException {
        byte[] data = request.toBytes();
        destino.write(data);
        destino.flush();
    }

    public void writeReply(ReplyMessageDTO reply) throws IOException {
        byte[] data = reply.toBytes();
        destino.write(data);
        destino.flush();
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
