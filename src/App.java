import entidades.controladores.Estoque;

public class App {
    public static void main(String[] args) throws Exception {
        Estoque estoque = new Estoque();
        estoque.adicionarItem("Cachorro", "Antipulgas", 5);
        estoque.adicionarItem("Gato", "Vermífugo", 3);

        System.out.println(estoque);
    }
}
