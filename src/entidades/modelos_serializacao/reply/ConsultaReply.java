package entidades.modelos_serializacao.reply;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConsultaReply {
    private boolean sucesso;
    private byte[] dados;

    public ConsultaReply(boolean sucesso, byte[] dados) {
        this.sucesso = sucesso;
        this.dados = dados;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public byte[] getDados() {
        return dados;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        dout.writeBoolean(sucesso);
        dout.writeInt(dados.length);
        dout.write(dados);

        return bout.toByteArray();
    }

    public static ConsultaReply fromBytes(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);

        boolean sucesso = din.readBoolean();
        int tamanho = din.readInt();
        byte[] dados = new byte[tamanho];
        din.readFully(dados);

        return new ConsultaReply(sucesso, dados);
    }
}

