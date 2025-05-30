package sockets.TCPSerializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import entidades.dtos.ReplyMessageDTO;
import entidades.dtos.RequestMessageDTO;
import entidades.interfaces.Consulta;
import entidades.modelos.ConsultaVeterinaria;

public class TCPServer {
  public static void main(String[] args) {
        Consulta consulta = new ConsultaVeterinaria(); // implementação concreta
        int porta = 12345;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor aguardando conexões na porta " + porta + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                try (InputStream in = socket.getInputStream();
                     OutputStream out = socket.getOutputStream()) {

                    RequestMessageDTO req = RequestMessageDTO.fromInputStream(in);
                    ReplyMessageDTO reply;

                    switch (req.getOperationId()) {
                        case 1: // realizarConsulta(Date data)
                            Date data = (Date) req.getPayload();
                            consulta.realizarConsulta(data);
                            reply = new ReplyMessageDTO("Consulta realizada com sucesso.");
                            break;

                        case 2: // cancelarConsulta(int id)
                            int id = (int) req.getPayload();
                            consulta.cancelarConsulta(id);
                            reply = new ReplyMessageDTO("Consulta cancelada com sucesso.");
                            break;

                        case 3: // getMedicamentos(String animal)
                            String animal = (String) req.getPayload();
                            Map<String, Integer> medicamentos = consulta.getMedicamentos(animal);
                            reply = new ReplyMessageDTO(medicamentos);
                            break;

                        default:
                            reply = new ReplyMessageDTO("Operação desconhecida.");
                    }

                    out.write(reply.toBytes());
                    out.flush();
                }

                socket.close(); // encerra o socket do cliente, mas o servidor continua
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
