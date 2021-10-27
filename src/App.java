import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    
    public static void main(String[] args) {
        
        String url_bd = "jdbc:postgresql://kesavan.db.elephantsql.com/pjrkwanq";
        try {
            Connection conn = DriverManager.getConnection(url_bd, "pjrkwanq", "7SsnBMygm54VuJ2sbnUu4aMNSfnNL_5j");
            if (conn != null) {
                System.out.println("Conexão realizada com sucesso!");
            } else {
                System.out.println("Erro na conexão co o banco de dados!");
            }

            // Criar a tabela Departamento
            PreparedStatement pstm = conn.prepareStatement("insert into departamento (coddep, nome) values (?, ?)");
            pstm.setInt(1, 40);
            pstm.setString(2, "Ciências Exatas");
            int pos = pstm.executeUpdate();
            pstm.close();
            System.out.println("Quantidade inserida: " + pos);

            Statement stm = conn.createStatement();
            ResultSet rsDepto = stm.executeQuery("select * from departamento");
            while (rsDepto.next()) {
                Departamento depto = new Departamento(rsDepto.getInt("coddep"), rsDepto.getString("nome"));

                System.out.println(depto);
            }
            rsDepto.close();
            stm.close();
            conn.close();


            // stm.execute("create table Departamento (" +
            //             "       coddep int primary key, " + 
            //             "       nome   varchar(100) " + 
            //             ")");
            
            // System.out.println("Tabela Departamento criada!");

            // int qtd = 0;
            // qtd = stm.executeUpdate("insert into departamento (coddep, nome) values (10, 'Computação')");
            // System.out.println("1. Quantidade: " + qtd);
            
            // qtd = stm.executeUpdate("insert into departamento (coddep, nome) values (20, 'Engenharia')");
            // System.out.println("2. Quantidade: " + qtd);
            
            // qtd = stm.executeUpdate("insert into departamento (coddep, nome) values (30, 'Ciências Humanas')");
            // System.out.println("3. Quantidade: " + qtd);

            // stm.execute("drop table departamento");



            // stm.close();

            // conn.close();
        }
        catch (SQLException ex) {
            System.out.println("Erro de conexão!");
        }
        catch (Exception ex) {
            System.out.println("Erro do sistema!");
        }

    }

}
