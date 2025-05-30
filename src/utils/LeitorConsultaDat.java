package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LeitorConsultaDat {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("consultas.dat");
             DataInputStream dataIn = new DataInputStream(fis)) {

            int contador = 1;
            while (dataIn.available() > 0) {
                int tamanho = dataIn.readInt(); 
                byte[] buffer = new byte[tamanho];
                dataIn.readFully(buffer); 

                String conteudo = new String(buffer, StandardCharsets.UTF_8);
                System.out.println("Consulta #" + contador++);
                System.out.println(conteudo);
                System.out.println("---------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
