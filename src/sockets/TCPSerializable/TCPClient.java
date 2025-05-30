package sockets.TCPSerializable;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

import entidades.dtos.ReplyMessageDTO;
import entidades.dtos.RequestMessageDTO;

public class TCPClient {
      public static void main(String[] args) {
        String host = "localhost";
        int porta = 12345;

        try (Socket socket = new Socket(host, porta);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {

            // Exemplo: enviar uma requisição para realizarConsulta
            RequestMessageDTO req = new RequestMessageDTO(1, new Date());
            out.write(req.toBytes());
            out.flush();

            ReplyMessageDTO reply = ReplyMessageDTO.fromInputStream(in);
            System.out.println("Resposta do servidor: " + reply.getPayload());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
