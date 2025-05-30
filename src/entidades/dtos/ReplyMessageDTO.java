package entidades.dtos;

import java.io.*;

public class ReplyMessageDTO implements Serializable {
    private Object payload; // Resposta do servidor

    public ReplyMessageDTO(Object payload) {
        this.payload = payload;
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

    public static ReplyMessageDTO fromInputStream(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        Object payload = ois.readObject();
        return new ReplyMessageDTO(payload);
    }
}
