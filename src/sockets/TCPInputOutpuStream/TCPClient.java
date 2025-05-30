package sockets.TCPInputOutpuStream;

import java.io.*;
import java.net.Socket;
import java.util.*;

import entidades.dtos.*;
import streams.ConsultaOutputStream;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int porta = 12345;

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

        try (Socket socket = new Socket(host, porta)) {
            OutputStream saida = socket.getOutputStream();
            new ConsultaOutputStream(consultas, quantidade, saida);
            System.out.println(">>> Consultas enviadas ao servidor com sucesso.");
        }
    }
}
