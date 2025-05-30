package entidades.modelos_serializacao.reply;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CancelarConsultaReplyDTO {
    private String mensagem;

    public CancelarConsultaReplyDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        dout.writeUTF(mensagem);
        return bout.toByteArray();
    }

    public static CancelarConsultaReplyDTO fromBytes(byte[] bytes) throws IOException {
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
        return new CancelarConsultaReplyDTO(din.readUTF());
    }
}

