package streams;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AnimalInputStream extends InputStream {
    private DataInputStream dis;

    public AnimalInputStream(InputStream in){
        this.dis = new DataInputStream(in);
    }

    public int readNumberregister() throws IOException {
        return dis.readInt();
    }

    public Map<String, Object> readAnimalDate() throws IOException{
        Map<String, Object> animalData = new HashMap<>();

        int totalBytes;

        try{
            totalBytes = dis.readInt();
            animalData.put("Total de Bytes declarados", totalBytes);
        } catch (EOFException e) {
            return null; // End of stream
        }

        int nameLength = dis.readInt();
        byte[] nameBytes = new byte[nameLength];
        dis.readFully(nameBytes);
        animalData.put("nome", new String(nameBytes, StandardCharsets.UTF_8));

        animalData.put("idade", dis.readInt());

        int raceLength = dis.readInt();
        byte[] raceBytes = new byte[raceLength];
        dis.readFully(raceBytes);
        animalData.put("raça", new String(raceBytes, StandardCharsets.UTF_8));

        return animalData;
    }

    @Override
    public int read() throws IOException {
        return dis.read();
    }

    @Override
    public int read(byte [] b, int off, int len) throws IOException {
        return dis.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return dis.read(b);
    }

    @Override
    public long skip(long n) throws IOException {
        return dis.skip(n);
    }

    @Override
    public int available() throws IOException {
        return dis.available();
    }

    @Override
    public void close() throws IOException {
        dis.close();
    }
    @Override
    public synchronized void mark(int readlimit) {
        dis.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        dis.reset();
    }

    @Override
    public boolean markSupported() {
        return dis.markSupported();
    }


}