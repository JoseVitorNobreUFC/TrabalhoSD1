package entidades.dtos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class ConsultaPetShopDTO implements Serializable, ConsultaDTO {
    private ArrayList<String> produtos;
    private int quantidadeAnimais;
    private ArrayList<Date> agendamentos;

    public ConsultaPetShopDTO(ArrayList<String> produtos, int quantidadeAnimais, ArrayList<Date> agendamentos) {
        this.produtos = produtos;
        this.quantidadeAnimais = quantidadeAnimais;
        this.agendamentos = agendamentos;
    }

    @Override
    public void serialize(DataOutputStream out) throws IOException {
        out.writeUTF("PETSHOP");

        out.writeInt(produtos.size());
        for (String p : produtos) {
            out.writeUTF(p);
        }

        out.writeInt(quantidadeAnimais);

        out.writeInt(agendamentos.size());
        for (Date data : agendamentos) {
            out.writeLong(data.getTime());
        }
    }

    public ArrayList<String> getProdutos() {
        return produtos;
    }

    public int getQuantidadeAnimais() {
        return quantidadeAnimais;
    }

    public ArrayList<Date> getAgendamentos() {
        return agendamentos;
    }

    @Override
    public byte[] toBytes() {
        StringBuilder sb = new StringBuilder("PETSHOP:\n");
        sb.append("\tPRODUTOS = [");
        for (int i = 0; i < produtos.size(); i++) {
            sb.append(produtos.get(i));
            if (i < produtos.size() - 1) {
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
    public String toString() {
        return "Tipo: PETSHOP\nProdutos: " + produtos + "\nAgendamentos: " + agendamentos;
    }
}
