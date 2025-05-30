package entidades.modelos_serializacao.reply;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GetMedicamentosReplyDTO {
    private Map<String, Integer> medicamentos;

    public GetMedicamentosReplyDTO(Map<String, Integer> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Map<String, Integer> getMedicamentos() {
        return medicamentos;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        dout.writeInt(medicamentos.size());
        for (Map.Entry<String, Integer> entry : medicamentos.entrySet()) {
            dout.writeUTF(entry.getKey());
            dout.writeInt(entry.getValue());
        }

        return bout.toByteArray();
    }

    public static GetMedicamentosReplyDTO fromBytes(byte[] bytes) throws IOException {
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
        int size = din.readInt();

        Map<String, Integer> medicamentos = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String nome = din.readUTF();
            int quantidade = din.readInt();
            medicamentos.put(nome, quantidade);
        }

        return new GetMedicamentosReplyDTO(medicamentos);
    }
}
