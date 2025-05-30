package sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import entidades.dtos.ConsultaDTO;
import streams.ConsultaInputStream;

public class TCPServer {
    public static void main(String[] args) throws Exception {
        int porta = 12345;

        try (ServerSocket servidor = new ServerSocket(porta)) {
            System.out.println(">>> Servidor aguardando conexões na porta " + porta + "...");

            while (true) {
                try (Socket cliente = servidor.accept()) {
                    System.out.println(">>> Cliente conectado: " + cliente.getInetAddress());

                    InputStream entrada = cliente.getInputStream();
                    ConsultaInputStream consultaIn = new ConsultaInputStream(entrada);

                    try {
                        ConsultaDTO[] consultasRecebidas = consultaIn.lerConsultas(2); // ou quantidade fixa
                        System.out.println(">>> Começo da mensagem:");
                        for (ConsultaDTO c : consultasRecebidas) {
                            System.out.println(c);
                            System.out.println("-----------");
                        }
                    } catch (IOException e) {
                        System.err.println(">>> Erro ao ler consulta do cliente: " + e.getMessage());
                    }

                    System.out.println(">>> Fim da mensagem.\n");
                }
            }
        }
    }
}
