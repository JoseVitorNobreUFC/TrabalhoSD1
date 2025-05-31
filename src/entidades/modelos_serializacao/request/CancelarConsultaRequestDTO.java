package entidades.modelos_serializacao.request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import entidades.dtos.RequestMessageDTO;

public class CancelarConsultaRequestDTO extends RequestMessageDTO {
    private int id;

    public CancelarConsultaRequestDTO(int id) {
        super("cancelarConsulta", id, null, null);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        dout.writeInt(id);
        return bout.toByteArray();
    }

    public static CancelarConsultaRequestDTO fromBytes(byte[] bytes) throws IOException {
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
        return new CancelarConsultaRequestDTO(din.readInt());
    }
}

