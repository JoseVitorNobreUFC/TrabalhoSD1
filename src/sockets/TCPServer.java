package sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
    private static final int PORTA = 5000;

    public static void main(String[] args) {
        System.out.println("Servidor escutando na porta " + PORTA + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dataIn = new DataInputStream(socket.getInputStream())) {

                    System.out.println("Cliente conectado.");

                    // Ler quantidade de objetos
                    int quantidade = dataIn.readInt();
                    System.out.println("Quantidade de consultas a receber: " + quantidade);

                    for (int i = 0; i < quantidade; i++) {
                        int tamanho = dataIn.readInt();
                        byte[] buffer = new byte[tamanho];
                        dataIn.readFully(buffer);

                        String conteudo = new String(buffer, StandardCharsets.UTF_8);
                        System.out.println("Consulta #" + (i + 1) + ":");
                        System.out.println(conteudo);
                        System.out.println("---------------");
                    }

                } catch (IOException e) {
                    System.err.println("Erro na conexão com cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
        }
    }
}
