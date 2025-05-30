package streams;

import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;

import entidades.dtos.ConsultaDTO;
import entidades.dtos.ConsultaPetShopDTO;
import entidades.dtos.ConsultaVeterinariaDTO;

public class ConsultaInputStream extends InputStream {
    private final InputStream origem;

    public ConsultaInputStream(InputStream origem) {
        this.origem = origem;
    }

    public ConsultaDTO[] lerConsultas(int quantidade) throws IOException {
        ConsultaDTO[] consultas = new ConsultaDTO[quantidade];
        DataInputStream dataIn = new DataInputStream(origem);

        for (int i = 0; i < quantidade; i++) {
            int tamanho = dataIn.readInt();
            byte[] dados = new byte[tamanho];
            dataIn.readFully(dados);

            String conteudo = new String(dados, "UTF-8");
            if (conteudo.startsWith("VETERINARIA")) {
                consultas[i] = ConsultaVeterinariaDTO.fromBytes(dados);
            } else if (conteudo.startsWith("PETSHOP")) {
                consultas[i] = ConsultaPetShopDTO.fromBytes(dados);
            } else {
                throw new IllegalArgumentException("Tipo de consulta desconhecido.");
            }
        }

        return consultas;
    }

    @Override
    public int read() throws IOException {
        return origem.read();
    }
}
