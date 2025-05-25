package interfaces;

import java.util.Date;
import java.util.Map;

public interface Consulta {
  public void realizarConsulta(Date data);
  public void cancelarConsulta(Integer id);
  public Map<String, Integer> getMedicamentos(String animala);
}
