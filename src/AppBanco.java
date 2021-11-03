import java.sql.Connection;
import java.util.List;

public class AppBanco {
    
    public static void main(String[] args) {
        FormConta form = new FormConta();
        form.setVisible(true);
    }

    public static void testeRepositorio() throws Exception {

        Connection conexao = Conexao.getInstance();
        ContaRepositorio repo = new ContaRepositorio(conexao);
        
        // Adicionar uma conta
        ContaBancaria contaNova = new ContaBancaria("567-8", "Sicrano");
        repo.adicionar(contaNova);
        System.out.println("Conta adicionada com sucesso: ");
        System.out.println(contaNova);

        // // Atualizar uma conta
        ContaBancaria contaParaAtualizar = repo.pesquisar("567-8");
        if (contaParaAtualizar != null) {
            contaParaAtualizar.setNome("Sicrano da Silva");
            repo.atualizar(contaParaAtualizar);
            System.out.println("Conta atualizada com sucesso: ");
            System.out.println(contaParaAtualizar);
        }
        
        // // Remover uma conta
        ContaBancaria contaParaRemover = repo.pesquisar("567-8");
        if (contaParaRemover != null) {
            repo.remover(contaParaRemover.getNumero());
            System.out.println("Conta removida com sucesso! ");
        }

        // Listar contas cadastradas
        System.out.println("Lista de Contas Bancárias:");
        List<ContaBancaria> lista = repo.pesquisar();
        for (ContaBancaria conta:lista) {
            System.out.println(conta);
        }
        Conexao.fechar();
    }
}


