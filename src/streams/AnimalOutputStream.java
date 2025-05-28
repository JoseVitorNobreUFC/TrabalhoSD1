package streams;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import animais.Animal;


public class AnimalOutputStream extends OutputStream  {
    private DataOutputStream dos;

    public AnimalOutputStream(Animal[] animais,int numeroObjetos,OutputStream destino) throws IOException {
        this.dos = new DataOutputStream(destino);
        escreverAnimais(animais, numeroObjetos);
    }

    private void escreverAnimais(Animal[] animais, int numeroObjetos) throws IOException {
        if(numeroObjetos <= 0 || numeroObjetos > animais.length) {
            throw new IllegalArgumentException("Número de objetos inválido");
        }

        dos.writeInt(numeroObjetos); // Escreve o número de objetos

        for (int i = 0; i < numeroObjetos; i++) {
            Animal animal = animais[i];
            if(animais[i] == null) {
                throw new IllegalArgumentException("Animal não pode ser nulo");
            }

            byte[] nomeBytes = animal.getNome().getBytes(StandardCharsets.UTF_8);
            byte[] racaBytes = animal.getRaca().getBytes(StandardCharsets.UTF_8);
            int idade = animal.getIdade();

            int tamanhoNome = nomeBytes.length;
            int tamanhoRaca = racaBytes.length;
            int bytesIdade = 4;

            int totalBytes = tamanhoNome + tamanhoRaca + bytesIdade;

            dos.writeInt(totalBytes);
            dos.writeInt(tamanhoNome);
            dos.write(nomeBytes);
            dos.writeInt(idade);
            dos.writeInt(tamanhoRaca);
            dos.write(racaBytes);
        }
        dos.flush(); // Garante que todos os dados sejam escritos no stream
    }

    @Override
    public void write(int b) throws IOException {
        dos.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        dos.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        dos.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        dos.flush();
    }

    @Override
    public void close() throws IOException {
        dos.close();
    }





}
