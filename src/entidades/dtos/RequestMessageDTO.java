package entidades.dtos;

import java.io.Serializable;

import java.io.*;

public class RequestMessageDTO implements Serializable {
    private int operationId; 
    private Object payload; 

    public RequestMessageDTO(int operationId, Object payload) {
        this.operationId = operationId;
        this.payload = payload;
    }

    public int getOperationId() {
        return operationId;
    }

    public Object getPayload() {
        return payload;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);

        dataOut.writeInt(operationId); // 4 bytes

        // Serializa o payload
        ByteArrayOutputStream payloadOut = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(payloadOut);
        oos.writeObject(payload);
        oos.flush();
        byte[] payloadBytes = payloadOut.toByteArray();

        dataOut.writeInt(payloadBytes.length); // tamanho do payload
        dataOut.write(payloadBytes); // conteúdo serializado

        return byteOut.toByteArray();
    }

    public static RequestMessageDTO fromInputStream(InputStream in) throws IOException, ClassNotFoundException {
        DataInputStream dataIn = new DataInputStream(in);

        int operationId = dataIn.readInt(); // lê operação
        int payloadLength = dataIn.readInt(); // lê tamanho do payload
        byte[] payloadBytes = new byte[payloadLength];
        dataIn.readFully(payloadBytes); // lê os bytes do payload

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(payloadBytes));
        Object payload = ois.readObject();

        return new RequestMessageDTO(operationId, payload);
    }
}
