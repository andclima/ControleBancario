import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaRepositorio {

    private final Connection conn;

    public ContaRepositorio(Connection conn) {
        this.conn = conn;
    }

    public void adicionar(ContaBancaria conta) {
        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO ContaBancaria (numero, nome, saldo) VALUES (?, ?, ?)");
            pstm.setString(1, conta.getNumero());
            pstm.setString(2, conta.getNome());
            pstm.setBigDecimal(3, conta.getSaldo());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro na inclusão da conta!");
        }
    }

    public void remover(String numero) {
        try {
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM ContaBancaria where numero = ?");
            pstm.setString(1,  numero);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro na exclusão da conta!");
        }
    }

    public void atualizar(ContaBancaria conta) {
        try {
            PreparedStatement pstm = conn.prepareStatement("UPDATE ContaBancaria set nome = ?, saldo = ? where numero = ?");
            pstm.setString(1, conta.getNome());
            pstm.setBigDecimal(2, conta.getSaldo());
            pstm.setString(3, conta.getNumero());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro na alteração da conta!");
        }
    }

    public List<ContaBancaria> pesquisar() {
        ArrayList<ContaBancaria> lista = new ArrayList<>();
        try {
            PreparedStatement pstm = conn.prepareStatement("select * from ContaBancaria order by numero");
            ResultSet rsConta = pstm.executeQuery();
            while (rsConta.next()) {
                String numero = rsConta.getString("numero");
                String nome = rsConta.getString("nome");
                BigDecimal saldo = rsConta.getBigDecimal("saldo");
                ContaBancaria nova = new ContaBancaria(numero, nome, saldo);
                lista.add(nova);
            }
            rsConta.close();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Erro na pesquisa de contas!");
        }
        return lista;
    }

    public ContaBancaria pesquisar(String numero) {
        ContaBancaria contaEncontrada = null;
        try {
            PreparedStatement pstm = conn.prepareStatement("select * from ContaBancaria where numero = ?");
            pstm.setString(1, numero);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String num = rs.getString("numero");
                String nome = rs.getString("nome");
                BigDecimal saldo = rs.getBigDecimal("saldo");
                contaEncontrada = new ContaBancaria(num, nome, saldo);
            }
        } catch (SQLException ex) {
            System.out.println("Erro na pesquisa de conta!");
        }
        return contaEncontrada;
    }

}
