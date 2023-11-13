import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilsCadastro extends UtilsIMC {

    UtilsCadastro(Banco banco) {
        super(banco);
    }

    public boolean validaValores(String email, String senha) {
        if(email.indexOf('@') == -1 || email.indexOf(".com") == -1 || senha.length() < 4){
            JOptionPane.showMessageDialog(null, "Credenciais invalidas");
            return false;
        }
        return true;
    }

    public int fazerCadastro(String email, String senha, String nome) throws SQLException {
        if(!this.validaValores(email, senha)) {
            return 0;
        }

        getBanco().conexao();
        Statement st = getBanco().con.createStatement();
        String idLogin = null;
        String idCadastro = null;

        PreparedStatement preparedStatement = null;
        String sqlSelectCadastro = "SELECT * FROM login WHERE email = '"+email+"'";
        preparedStatement = getBanco().con.prepareStatement(sqlSelectCadastro);
        ResultSet rs = preparedStatement.executeQuery();
        String[] sqlSelectCadastroSeparado = sqlSelectCadastro.split(" ");
        Log log = new Log(getBanco(), sqlSelectCadastroSeparado[0]);

        while (rs.next()) {
            idLogin = rs.getString("ID");
        }
        int idUser = 0;

        if(idLogin == null){
            String sqlInsertCadastro = "INSERT INTO login VALUES (default,'"+email+"', '"+senha+"', '"+nome+"')";
            st.executeUpdate(sqlInsertCadastro);

            String[] sqlInsertCadastroSeparado = sqlInsertCadastro.split(" ");
            Log log2 = new Log(getBanco(), sqlInsertCadastroSeparado[0]);

            String sqlSelectLogin = "SELECT * FROM login WHERE email = '"+email+"'";
            preparedStatement = getBanco().con.prepareStatement(sqlSelectLogin);
            ResultSet rsSelect = preparedStatement.executeQuery();
            String[] sqlSelectLoginSeparado = sqlSelectLogin.split(" ");
            Log log3 = new Log(getBanco(), sqlSelectLoginSeparado[0]);
            JOptionPane.showMessageDialog(null, "Logado!!");

            while (rsSelect.next()) {
                idCadastro = rsSelect.getString("ID");
            }
            System.out.println(idCadastro);
            idUser = Integer.parseInt(idCadastro);
        }



        return idUser;



    }
}
