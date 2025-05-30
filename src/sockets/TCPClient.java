package sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import entidades.dtos.*;

public class TCPClient {
    private final String host;
    private final int port;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void enviarConsultas(ConsultaDTO[] consultas) {
        try (Socket socket = new Socket(host, port);
             DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Conectado ao servidor.");

            // Enviar quantidade primeiro
            dataOut.writeInt(consultas.length);

            for (ConsultaDTO consulta : consultas) {
                byte[] dados = consulta.toBytes();
                dataOut.writeInt(dados.length);
                dataOut.write(dados);
            }
            dataOut.flush();

            System.out.println("Dados enviados com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao enviar dados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList<String> vets = new ArrayList<>();
        vets.add("Dr. Lucas");

        ArrayList<String> produtos = new ArrayList<>();
        produtos.add("Ração Premium");

        ArrayList<Date> agendamentos = new ArrayList<>();
        agendamentos.add(new Date());

        ConsultaDTO[] consultas = new ConsultaDTO[2];
        consultas[0] = new ConsultaVeterinariaDTO(vets, 1, agendamentos);
        consultas[1] = new ConsultaPetShopDTO(produtos, 1, agendamentos);
        new TCPClient("localhost", 5000).enviarConsultas(consultas);
    }
}
