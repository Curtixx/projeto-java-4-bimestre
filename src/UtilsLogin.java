import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilsLogin {
    Banco banco;
    String id;
    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    UtilsLogin(Banco banco){
        setBanco(banco);
    }


    public boolean validaValores(String email, String senha) {
        if(email.indexOf('@') == -1 || email.indexOf(".com") == -1 || senha.length() < 4){
            JOptionPane.showMessageDialog(null, "Login invalido");
            return false;
        }
        return true;
    }

    public int fazerLogin(String email, String senha) throws SQLException {
        if(!this.validaValores(email, senha)) {
            return 0;
        }
        getBanco().conexao();
        PreparedStatement preparedStatement = null;
        String id = null;
        String sqlSelectLogin = "SELECT * FROM login WHERE email = '"+email+"'";
        preparedStatement = getBanco().con.prepareStatement(sqlSelectLogin);
        ResultSet rs = preparedStatement.executeQuery();
        String[] sqlSelectLoginSeparado = sqlSelectLogin.split(" ");
        Log log = new Log(getBanco(), sqlSelectLoginSeparado[0]);

        while (rs.next()) {
            id = rs.getString("ID");
        }
        if(id == null) {
            JOptionPane.showMessageDialog(null, "Crie uma conta primeiro!!");
            return 0;
        }

        JOptionPane.showMessageDialog(null, "Logado!!");
        int idUser = Integer.parseInt(id);
        return idUser;



    }
}
