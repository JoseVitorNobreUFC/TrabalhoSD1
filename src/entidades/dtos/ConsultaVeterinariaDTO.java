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

    public static ConsultaVeterinariaDTO fromBytes(byte[] dados) {
        String texto = new String(dados, StandardCharsets.UTF_8);
        String[] linhas = texto.split("\n");

        if (!linhas[0].startsWith("VETERINARIA")) {
            throw new IllegalArgumentException("Tipo inválido para ConsultaVeterinariaDTO");
        }

        ArrayList<String> veterinarios = new ArrayList<>();
        
        String veterinariosLinha = linhas[1].trim();
        if (veterinariosLinha.startsWith("VETERINARIOS = [") && veterinariosLinha.endsWith("]")) {
            String veterinariosStr = veterinariosLinha.substring("VETERINARIOS = [".length(), veterinariosLinha.length() - 1);
            if (!veterinariosStr.isEmpty()) {
                String[] veterinariosArray = veterinariosStr.split(",");
                for (String vet : veterinariosArray) {
                    veterinarios.add(vet.trim());
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

        return new ConsultaVeterinariaDTO(veterinarios, quantidadeAnimais, agendamentos);
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
