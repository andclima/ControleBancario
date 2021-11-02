import java.util.List;

public class AppBanco {
    public static void main(String[] args) throws Exception {
        
        ContaRepositorio repo = new ContaRepositorio();

        // ContaBancaria nova = new ContaBancaria("432-1", "Beltrano");

        ContaBancaria contaExistente = repo.pesquisar("432-1");
        if (contaExistente != null) {
            contaExistente.setNome("Sicrano");
            repo.atualizar(contaExistente);
        }
        
        List<ContaBancaria> lista = repo.pesquisar();
        for (ContaBancaria conta:lista) {
            System.out.println(conta);
        }

        repo.fecharConexao();
        
    }

}
