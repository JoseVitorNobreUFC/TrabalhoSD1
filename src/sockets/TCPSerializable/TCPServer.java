package sockets.TCPSerializable;

import entidades.dtos.ConsultaRegistro;
import entidades.dtos.ReplyMessageDTO;
import entidades.dtos.RequestMessageDTO;
import entidades.helpers.DesempacotamentoConsulta;
import entidades.helpers.EmpacotamentoConsulta;
import entidades.modelos.ConsultaVeterinaria;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {
    public static void main(String[] args) {
        int porta = 12345;
        ArrayList<ConsultaRegistro> historico = new ArrayList<>();
        ConsultaVeterinaria consulta = new ConsultaVeterinaria(historico);

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                try {
                    // Desempacota requisição
                    RequestMessageDTO request = (RequestMessageDTO) DesempacotamentoConsulta.lerStreamBinario(clientSocket.getInputStream());

                    // Processa
                    ReplyMessageDTO resposta = request.processar(consulta);

                    // Empacota resposta
                    EmpacotamentoConsulta.gravarStreamBinario(resposta, clientSocket.getOutputStream());

                } catch (Exception e) {
                    System.out.println("Erro ao processar cliente:");
                    e.printStackTrace();
                } finally {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro no servidor:");
            e.printStackTrace();
        }
    }
}
