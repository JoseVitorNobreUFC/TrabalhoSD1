package streams;

import entidades.dtos.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TestStream {
    public static void main(String[] args) throws Exception {
        ArrayList<String> veterinarios = new ArrayList<>(Arrays.asList("Dr. João", "Dra. Maria"));
        ArrayList<String> produtos = new ArrayList<>(Arrays.asList("Ração Premium", "Ração de Peixe"));
        ArrayList<Date> agendamentos = new ArrayList<>(Arrays.asList(
                new Date(),
                new Date(System.currentTimeMillis() + 86400000)
        ));

        ConsultaVeterinariaDTO consultaVet = new ConsultaVeterinariaDTO(veterinarios, 2, agendamentos);
        ConsultaPetShopDTO consultaPet = new ConsultaPetShopDTO(produtos, 2, agendamentos);
        ConsultaDTO[] consultas = new ConsultaDTO[]{consultaVet, consultaPet};
        int quantidade = consultas.length;

        // >>> Teste Console
        System.out.println(">>> Teste Console - Escrita em memória e leitura simulada via InputStream");
        ByteArrayOutputStream consoleBuffer = new ByteArrayOutputStream();
        new ConsultaOutputStream(consultas, quantidade, consoleBuffer);
        byte[] dadosConsole = consoleBuffer.toByteArray();

        ByteArrayInputStream inputSimulado = new ByteArrayInputStream(dadosConsole);
        ConsultaDTO[] lidosConsole = new ConsultaInputStream(inputSimulado).lerConsultas(quantidade);

        System.out.println(">>> Resultado da leitura simulada:");
        for (ConsultaDTO c : lidosConsole) {
            System.out.println(c);
            System.out.println("-----------");
        }

        // >>> Teste Arquivo
        System.out.println("\n>>> Teste Arquivo - Gravando e lendo de consultas.dat");
        try (OutputStream fileOut = new FileOutputStream("consultas.dat")) {
            new ConsultaOutputStream(consultas, quantidade, fileOut);
        }

        try (InputStream fileIn = new FileInputStream("consultas.dat")) {
            ConsultaDTO[] lidosArquivo = new ConsultaInputStream(fileIn).lerConsultas(quantidade);

            System.out.println(">>> Resultado da leitura do arquivo:");
            for (ConsultaDTO c : lidosArquivo) {
                System.out.println(c);
                System.out.println("-----------");
            }
        }
    }
}
