package entidades.dtos;

import java.io.DataOutputStream;
import java.io.IOException;

public interface ConsultaDTO {
    void serialize(DataOutputStream out) throws IOException;
    byte[] toBytes() throws IOException;
}
