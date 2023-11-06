import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilsPaciente extends UtilsLogin {

    UtilsPaciente(Banco banco) {
        super(banco);
    }

    public boolean validaValores(String email, String senha) {
        if(email.indexOf('@') == -1 || email.indexOf(".com") == -1 && senha.length() < 4){
            JOptionPane.showMessageDialog(null, "Login invalido");
            return false;
        }
        return true;
    }

    public boolean alterarLogin(String email, String senha) throws SQLException {
        if(!this.validaValores(email, senha)) {
            return false;
        }
        Statement st = getBanco().con.createStatement();

        String sqlUpdatePaciente = "UPDATE login SET email = '"+email+"', senha = '"+senha+"'";
        st.executeUpdate(sqlUpdatePaciente);

        String[] sqlSelectLoginSeparado = sqlUpdatePaciente.split(" ");
        Log log = new Log(getBanco(), sqlSelectLoginSeparado[0]);

        JOptionPane.showMessageDialog(null, "Alterado!!");
        return true;
    }

    public boolean deletarLogin(int id) throws SQLException {
        Statement st = getBanco().con.createStatement();
        System.out.println(id);
        String sqlUpdatePaciente = "DELETE FROM login WHERE id = '"+id+"'";
        st.executeUpdate(sqlUpdatePaciente);
        String[] sqlUpdatePacienteSeparado = sqlUpdatePaciente.split(" ");
        Log log = new Log(getBanco(), sqlUpdatePacienteSeparado[0]);
        return true;
    }
}
