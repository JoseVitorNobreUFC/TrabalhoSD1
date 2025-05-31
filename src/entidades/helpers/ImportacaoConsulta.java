package entidades.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportacaoConsulta {
    public static void importar(String nomeArq) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArq))) {
            String linha;
            int i = 1;

            while ((linha = reader.readLine()) != null) {
                System.out.printf("Registro %d:\n", i++);
                String[] partes = linha.split(";");
                for (String parte : partes) {
                    String[] chaveValor = parte.split("\\|");
                    if (chaveValor.length == 2) {
                        System.out.printf("%s: %s\n", chaveValor[0], chaveValor[1]);
                    }
                }
                System.out.println();
            }

            System.out.println("Importação de texto finalizada.");
        } catch (IOException e) {
            System.out.println("Erro ao importar: " + e.getMessage());
        }
    }
}
