package sockets.TCPSerializable;

import entidades.dtos.ReplyMessageDTO;
import entidades.dtos.RequestMessageDTO;
import entidades.helpers.DesempacotamentoConsulta;
import entidades.helpers.EmpacotamentoConsulta;
import entidades.modelos_serializacao.request.CancelarConsultaRequestDTO;
import entidades.modelos_serializacao.request.GetMedicamentosRequestDTO;
import entidades.modelos_serializacao.request.RealizarConsultaRequestDTO;
import streams.ConsultaInputStream;
import streams.ConsultaOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Cliente conectado ao servidor.");
            System.out.println("Escolha a opção:");
            System.out.println("1. Realizar Consulta");
            System.out.println("2. Cancelar Consulta");
            System.out.println("3. Ver Medicamentos");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar quebra de linha
            RequestMessageDTO request;

            switch (opcao) {
                case 1:
                    request = new RealizarConsultaRequestDTO(new Date());
                    break;
                case 2:
                    System.out.print("Digite o ID da consulta: ");
                    int id = scanner.nextInt();
                    request = new CancelarConsultaRequestDTO(id);
                    break;
                case 3:
                    System.out.print("Digite o nome do animal: ");
                    String nome = scanner.nextLine();
                    request = new GetMedicamentosRequestDTO(nome);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            // Empacota e envia
            EmpacotamentoConsulta.gravarStreamBinario(request, out);

            // Recebe e desempacota resposta
            ReplyMessageDTO resposta = (ReplyMessageDTO) DesempacotamentoConsulta.lerStreamBinario(in);
            System.out.println("Resposta do servidor:");
            System.out.println(resposta.getMensagem());

        } catch (Exception e) {
            System.out.println("Erro de conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
