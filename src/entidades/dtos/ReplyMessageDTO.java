package entidades.dtos;

import java.io.*;

public class ReplyMessageDTO implements Serializable {
    private Object payload; // Resposta do servidor
    private String mensagem;

    public ReplyMessageDTO(Object payload) {
        this.payload = payload;
    }

    public ReplyMessageDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Object getPayload() {
        return payload;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteOut);
        oos.writeObject(payload);
        oos.flush();
        return byteOut.toByteArray();
    }

    public static ReplyMessageDTO fromBytes(InputStream input) throws IOException {
        DataInputStream in = new DataInputStream(input);
        String msg = in.readUTF();
        return new ReplyMessageDTO(msg);
    }

    public static ReplyMessageDTO fromInputStream(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        Object payload = ois.readObject();
        return new ReplyMessageDTO(payload);
    }


}
