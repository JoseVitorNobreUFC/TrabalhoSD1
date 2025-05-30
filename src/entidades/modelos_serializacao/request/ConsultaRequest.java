package entidades.modelos_serializacao.request;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import entidades.enums.TipoRequisicao;

public class ConsultaRequest {
    private TipoRequisicao tipo;
    private byte[] dados; // dados serializados específicos do tipo de request

    public ConsultaRequest(TipoRequisicao tipo, byte[] dados) {
        this.tipo = tipo;
        this.dados = dados;
    }

    public TipoRequisicao getTipo() {
        return tipo;
    }

    public byte[] getDados() {
        return dados;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        dout.writeInt(tipo.ordinal());
        dout.writeInt(dados.length);
        dout.write(dados);

        return bout.toByteArray();
    }

    public static ConsultaRequest fromBytes(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);

        int tipoOrdinal = din.readInt();
        TipoRequisicao tipo = TipoRequisicao.values()[tipoOrdinal];

        int tamanhoDados = din.readInt();
        byte[] dados = new byte[tamanhoDados];
        din.readFully(dados);

        return new ConsultaRequest(tipo, dados);
    }
}

