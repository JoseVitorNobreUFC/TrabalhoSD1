package modelos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import controladores.Estoque;
import interfaces.Consulta;

public abstract class ConsultaBase implements Consulta {
    protected Estoque estoque;
    protected Map<Integer, Date> agendamentos;

    public ConsultaBase() {
        this.estoque = new Estoque();
        this.agendamentos = new HashMap<>();
    }

    @Override
    public void cancelarConsulta(Integer id) {
        if (agendamentos.containsKey(id)) {
            agendamentos.remove(id);
        } else {
            throw new IllegalArgumentException("Consulta " + id + " não encontrada.");
        }
    }

    @Override
    public Map<String, Integer> getMedicamentos(String animal) {
        return estoque.getMedicamentos(animal);
    }
    
    @Override
    public void realizarConsulta(Date data) {
        agendamentos.put(agendamentos.size(), data);
    }

    public void aplicarMedicamento(String animal, String medicamento, int quantidade) {
      estoque.removerItem(animal, medicamento, quantidade);
    }

    public void adicionarMedicamento(String animal, String medicamento, int quantidade) {
      estoque.adicionarItem(animal, medicamento, quantidade);
    }
}

