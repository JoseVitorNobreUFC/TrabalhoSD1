package entidades.modelos_serializacao.request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

public class RealizarConsultaRequestDTO {
    private long timestamp;

    public RealizarConsultaRequestDTO(Date data) {
        this.timestamp = data.getTime();
    }

    public Date getData() {
        return new Date(timestamp);
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        dout.writeLong(timestamp);
        return bout.toByteArray();
    }

    public static RealizarConsultaRequestDTO fromBytes(byte[] bytes) throws IOException {
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
        long timestamp = din.readLong();
        return new RealizarConsultaRequestDTO(new Date(timestamp));
    }
}

