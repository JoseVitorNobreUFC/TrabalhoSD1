package entidades.dtos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class ConsultaVeterinariaDTO implements Serializable, ConsultaDTO {
    private ArrayList<String> veterinarios;
    private int quantidadeAnimais;
    private ArrayList<Date> agendamentos;

    public ConsultaVeterinariaDTO(ArrayList<String> veterinarios, int quantidadeAnimais, ArrayList<Date> agendamentos) {
        this.veterinarios = veterinarios;
        this.quantidadeAnimais = quantidadeAnimais;
        this.agendamentos = agendamentos;
    }

    public ArrayList<String> getVeterinarios() {
        return veterinarios;
    }

    public int getQuantidadeAnimais() {
        return quantidadeAnimais;
    }

    public ArrayList<Date> getAgendamentos() {
        return agendamentos;
    }

    @Override
    public byte[] toBytes() {
        StringBuilder sb = new StringBuilder("VETERINARIA:\n");
        sb.append("\tVETERINARIOS = [");
        for (int i = 0; i < veterinarios.size(); i++) {
            sb.append(veterinarios.get(i));
            if (i < veterinarios.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]\n");
        sb.append("\tQUANTIDADE_ANIMAIS = ").append(quantidadeAnimais).append("\n");
        sb.append("\tAGENDAMENTOS = [");
        for (int i = 0; i < agendamentos.size(); i++) {
            sb.append(agendamentos.get(i));
            if (i < agendamentos.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void serialize(DataOutputStream out) throws IOException {
        out.writeUTF("VETERINARIA");

        out.writeInt(veterinarios.size());
        for (String vet : veterinarios) {
            out.writeUTF(vet);
        }

        out.writeInt(quantidadeAnimais);

        out.writeInt(agendamentos.size());
        for (Date data : agendamentos) {
            out.writeLong(data.getTime());
        }
    }

    @Override
    public String toString() {
        return "Tipo: VETERINARIA\nVeterinários: " + veterinarios + "\nAgendamentos: " + agendamentos;
    }
}
