import animais.Animal;
import animais.Cachorro;
import animais.Gato;
import animais.Papagaio;
import streams.AnimalInputStream;
import streams.AnimalOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args){
        System.out.println("Criando alguns animais...");
        Animal rex = new Cachorro("Rex", 5, "Labrador");
        Animal felix = new Gato("Felix", 3, "Siamês");
        Animal louro = new Papagaio("Louro", 10, "Amazona");

        Animal[] animaisParaEscrever = {rex, felix, louro};

        System.out.println("\n--- Teste 1: Memória (ByteArrayOutputStream/ByteArrayInputStream) ---");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new AnimalOutputStream(animaisParaEscrever, animaisParaEscrever.length, baos);

            byte[] dadosSerializados = baos.toByteArray();
            System.out.println("Dados serializados (bytes): " + dadosSerializados.length + " bytes");

            ByteArrayInputStream bais = new ByteArrayInputStream(dadosSerializados);
            AnimalInputStream aisMemoria = new AnimalInputStream(bais);

            int numeroDeAnimaisLidos = aisMemoria.readNumberregister();
            System.out.println("Número de animais registrados no stream: " + numeroDeAnimaisLidos);

            List<Map<String, Object>> animaisLidosMemoria = new ArrayList<>();
            for (int i = 0; i < numeroDeAnimaisLidos; i++) {
                Map<String, Object> dadosAnimal = aisMemoria.readAnimalDate();
                if (dadosAnimal != null) {
                    animaisLidosMemoria.add(dadosAnimal);
                } else {
                    System.err.println("Erro: Esperava ler um animal, mas recebeu null.");
                    break;
                }
            }
            aisMemoria.close();

            System.out.println("Animais lidos da memória:");
            for (Map<String, Object> dados : animaisLidosMemoria) {
                System.out.println("  Nome: " + dados.get("nome") +
                                   ", Idade: " + dados.get("idade") +
                                   ", Raça: " + dados.get("raca") +
                                   ", Bytes Declarados: " + dados.get("totalBytesDeclarados"));
            }

        } catch (IOException e) {
            System.err.println("Erro no teste de memória: " + e.getMessage());
            e.printStackTrace();
        }

        // Teste 2: Arquivo
        System.out.println("\n--- Teste 2: Arquivo (FileOutputStream/FileInputStream) ---");
        String nomeArquivo = "animais.dat";
        try {
            FileOutputStream fos = new FileOutputStream(nomeArquivo);
            new AnimalOutputStream(animaisParaEscrever, animaisParaEscrever.length, fos);
            // fos.close(); // AnimalOutputStream.close() fechará o DataOutputStream, que por sua vez fecha o FileOutputStream.

            System.out.println("Animais escritos em " + nomeArquivo);

            FileInputStream fis = new FileInputStream(nomeArquivo);
            AnimalInputStream aisArquivo = new AnimalInputStream(fis);

            int numeroDeAnimaisArquivo = aisArquivo.readNumberregister();
            System.out.println("Número de animais registrados no arquivo: " + numeroDeAnimaisArquivo);

            List<Map<String, Object>> animaisLidosArquivo = new ArrayList<>();
            for (int i = 0; i < numeroDeAnimaisArquivo; i++) {
                 Map<String, Object> dadosAnimal = aisArquivo.readAnimalDate();
                if (dadosAnimal != null) {
                    animaisLidosArquivo.add(dadosAnimal);
                } else {
                    System.err.println("Erro: Esperava ler um animal do arquivo, mas recebeu null.");
                    break;
                }
            }
            aisArquivo.close();

            System.out.println("Animais lidos do arquivo:");
             for (Map<String, Object> dados : animaisLidosArquivo) {
                System.out.println("  Nome: " + dados.get("nome") +
                                   ", Idade: " + dados.get("idade") +
                                   ", Raça: " + dados.get("raca") +
                                   ", Bytes Declarados: " + dados.get("totalBytesDeclarados"));
            }

        } catch (IOException e) {
            System.err.println("Erro no teste de arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        // Teste 3: System.out
        System.out.println("\n--- Teste 3: Saída Padrão (System.out) ---");
        System.out.println("Os próximos bytes representam os animais serializados para System.out:");
        try {
            new AnimalOutputStream(animaisParaEscrever, animaisParaEscrever.length, System.out);
            // System.out.flush(); // AnimalOutputStream já faz flush.
            // Não feche System.out!
            System.out.println("\nEscrita para System.out concluída.");
        } catch (IOException e) {
             System.err.println("Erro no teste de System.out: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\nFim dos testes de streams.");
    }
}
