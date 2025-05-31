package entidades.helpers;



import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

import entidades.dtos.ConsultaRegistro;

public class ExportacaoConsulta {
    public static void exportarParaTexto(List<ConsultaRegistro> lista, String nomeArq) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArq))) {
            for (ConsultaRegistro c : lista) {
                writer.printf("Animal|%s;Data|%s;Observação|%s%n",
                    c.getAnimal(), c.getData(), c.getObservacao());
            }
            System.out.println("Exportação realizada com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar: " + e.getMessage());
        }
    }
}

