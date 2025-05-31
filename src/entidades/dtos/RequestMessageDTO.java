package entidades.dtos;

import java.io.*;
import java.util.Date;

import entidades.interfaces.Consulta;

public class RequestMessageDTO implements Serializable {
    private String operacao; // "realizarConsulta", "cancelarConsulta", "getMedicamentos"
    private int idConsulta;
    private Date dataConsulta;
    private String nomeAnimal;

    public RequestMessageDTO(String operacao, int idConsulta, Date dataConsulta, String nomeAnimal) {
        this.operacao = operacao;
        this.idConsulta = idConsulta;
        this.dataConsulta = dataConsulta;
        this.nomeAnimal = nomeAnimal;
    }

    public static RequestMessageDTO novaConsulta(Date data) {
        return new RequestMessageDTO("realizarConsulta", -1, data, null);
    }

    public static RequestMessageDTO cancelarConsulta(int id) {
        return new RequestMessageDTO("cancelarConsulta", id, null, null);
    }

    public static RequestMessageDTO getMedicamentos(String animal) {
        return new RequestMessageDTO("getMedicamentos", -1, null, animal);
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        out.writeUTF(operacao);
        out.writeInt(idConsulta);
        out.writeLong(dataConsulta != null ? dataConsulta.getTime() : -1L);
        out.writeBoolean(nomeAnimal != null);
        if (nomeAnimal != null) out.writeUTF(nomeAnimal);

        return baos.toByteArray();
    }

    public static RequestMessageDTO fromBytes(InputStream input) throws IOException {
        DataInputStream in = new DataInputStream(input);

        String operacao = in.readUTF();
        int id = in.readInt();
        long timestamp = in.readLong();
        Date data = timestamp >= 0 ? new Date(timestamp) : null;

        String animal = null;
        if (in.readBoolean()) {
            animal = in.readUTF();
        }

        return new RequestMessageDTO(operacao, id, data, animal);
    }

    public ReplyMessageDTO processar(Consulta servico) {
        try {
            switch (operacao) {
                case "realizarConsulta":
                    servico.realizarConsulta(dataConsulta);
                    return new ReplyMessageDTO("Consulta realizada com sucesso.");
                case "cancelarConsulta":
                    servico.cancelarConsulta(idConsulta);
                    return new ReplyMessageDTO("Consulta cancelada com sucesso.");
                case "getMedicamentos":
                    var mapa = servico.getMedicamentos(nomeAnimal);
                    return new ReplyMessageDTO("Medicamentos: " + mapa.toString());
                default:
                    return new ReplyMessageDTO("Operação desconhecida.");
            }
        } catch (Exception e) {
            return new ReplyMessageDTO("Erro ao processar: " + e.getMessage());
        }
    }
}
