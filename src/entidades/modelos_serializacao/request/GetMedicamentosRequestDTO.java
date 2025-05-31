package entidades.modelos_serializacao.request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import entidades.dtos.RequestMessageDTO;

public class GetMedicamentosRequestDTO extends RequestMessageDTO {
    private String animal;

    public GetMedicamentosRequestDTO(String animal) {
        super("getMedicamentos", -1, null, animal);
        this.animal = animal;
    }

    public String getAnimal() {
        return animal;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        dout.writeUTF(animal);
        return bout.toByteArray();
    }

    public static GetMedicamentosRequestDTO fromBytes(byte[] bytes) throws IOException {
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
        return new GetMedicamentosRequestDTO(din.readUTF());
    }
}

