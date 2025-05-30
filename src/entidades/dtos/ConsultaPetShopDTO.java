package entidades.dtos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

    public static ConsultaPetShopDTO fromBytes(byte[] dados) {
        String texto = new String(dados, StandardCharsets.UTF_8);
        String[] linhas = texto.split("\n");

        if (!linhas[0].startsWith("PETSHOP")) {
            throw new IllegalArgumentException("Tipo inválido para ConsultaPetShopDTO");
        }

        ArrayList<String> produtos = new ArrayList<>();

        String produtosLinha = linhas[1].trim();
        if (produtosLinha.startsWith("PRODUTOS = [") && produtosLinha.endsWith("]")) {
            String produtosStr = produtosLinha.substring("PRODUTOS = [".length(), produtosLinha.length() - 1);
            if (!produtosStr.isEmpty()) {
                String[] produtosArray = produtosStr.split(",");
                for (String p : produtosArray) {
                    produtos.add(p.trim());
                }
            }
        }

        int quantidadeAnimais = 0;
        ArrayList<Date> agendamentos = new ArrayList<>();

        String quantidadeLinha = linhas[2].trim();
        if (quantidadeLinha.startsWith("QUANTIDADE_ANIMAIS = ")) {
            try {
                quantidadeAnimais = Integer.parseInt(quantidadeLinha.substring("QUANTIDADE_ANIMAIS = ".length()));
            } catch (NumberFormatException e) {
                quantidadeAnimais = 0;
            }
        }

        String agendamentosLinha = linhas[3].trim();
        if (agendamentosLinha.startsWith("AGENDAMENTOS = [") && agendamentosLinha.endsWith("]")) {
            String agendamentosStr = agendamentosLinha.substring("AGENDAMENTOS = [".length(), agendamentosLinha.length() - 1);
            if (!agendamentosStr.isEmpty()) {
                String[] agendamentosArray = agendamentosStr.split(",");

                // Usa o mesmo formato do Date.toString()
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                for (String data : agendamentosArray) {
                    try {
                        agendamentos.add(sdf.parse(data.trim()));
                    } catch (ParseException e) {
                        // Opcional: imprimir/logar erro
                        System.err.println("Erro ao converter data: " + data.trim());
                    }
                }
            }
        }

        return new ConsultaPetShopDTO(produtos, quantidadeAnimais, agendamentos);
    }


    @Override
    public String toString() {
        return "Tipo: PETSHOP\nProdutos: " + produtos + "\nAgendamentos: " + agendamentos;
    }
}
