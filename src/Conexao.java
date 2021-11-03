import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private final static String URL_DATABASE = "jdbc:postgresql://kesavan.db.elephantsql.com/pjrkwanq";
    private final static String USER_DATABASE = "pjrkwanq";
    private final static String PASSWORD_DATABASE = "7SsnBMygm54VuJ2sbnUu4aMNSfnNL_5j";
    
    private static Connection conexao;

    public static Connection getInstance() {
        if (conexao == null) {
            try {
                conexao = DriverManager.getConnection(URL_DATABASE, USER_DATABASE, PASSWORD_DATABASE);
            } catch (SQLException ex) {
                System.out.println("Erro ao abrir conexão com o banco de dados!");
            }
        }
        return conexao;
    }

    public static void fechar() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar conexão com o banco de dados!");
            }
        }
    }
}


