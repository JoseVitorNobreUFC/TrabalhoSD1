package streams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import entidades.dtos.*;

public class TestConsultaOutput {
public static void main(String[] args) throws Exception {
        ArrayList<String> veterinarios = new ArrayList<>(Arrays.asList("Dr. João", "Dra. Maria"));
        ArrayList<Date> agendamentosVet = new ArrayList<>(Arrays.asList(new Date(), new Date(System.currentTimeMillis() + 86400000)));
        ConsultaVeterinariaDTO consultaVet = new ConsultaVeterinariaDTO(veterinarios, 2, agendamentosVet);

        ArrayList<String> produtos = new ArrayList<>(Arrays.asList("Ração Premium", "Ração de Peixe"));
        ArrayList<Date> agendamentosPet = new ArrayList<>(Arrays.asList(new Date(), new Date(System.currentTimeMillis() + 86400000)));
        ConsultaPetShopDTO consultaPet = new ConsultaPetShopDTO(produtos, 2, agendamentosPet);

        ConsultaDTO[] consultas = new ConsultaDTO[] { consultaVet, consultaPet };
        int quantidade = consultas.length;

        System.out.println("\n>>> Gravando em arquivo: consultas.dat");
        OutputStream fileOut = new FileOutputStream("consultas.dat");
        new ConsultaOutputStream(consultas, quantidade, fileOut);
        fileOut.close();
        System.out.println("Arquivo gerado com sucesso.");

        System.out.println("\n>>> Imprimindo objetos:");
        for (ConsultaDTO consulta : consultas) {
            System.out.println(consulta);
            System.out.println("---------------");
        }
    }
}
