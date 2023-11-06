import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilsIMC extends UtilsLogin {

    UtilsIMC(Banco banco) {
        super(banco);
    }

    public boolean validarValores(String altura, String peso) {
        if(Double.parseDouble(altura) > 3.0 && Double.parseDouble(peso) > 600) {
            JOptionPane.showMessageDialog(null, "Valores invalidos!!");
            return false;
        }
        return true;
    }

    public void Calcular(String altura, String peso, int idUser) throws SQLException {
        if(!validarValores(altura, peso)) {
            return;
        }

        double result = Double.parseDouble(peso) / (Double.parseDouble(altura) * Double.parseDouble(altura));
        String res = null;
        if(result <= 18.5) {
            res = "Magreza";
        } else if(result > 18.5 && result <= 24.9) {
            res = "Saudável";
        } else if(result > 25.0 && result <= 29.9){
            res = "Sobrepeso";
        } else if(result > 30 && result <= 34.9) {
            res = "Obesidade grau I";
        } else  if (result > 35 && result <= 39.9){
            res = "Obesidade grau II";
        } else if (result > 40){
            res = "Obesidade grau III";
        }
        JOptionPane.showMessageDialog(null, res);
        Statement st = getBanco().con.createStatement();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM paciente WHERE id_paciente = '"+idUser+"'";
        preparedStatement = getBanco().con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String id = null;
        while (rs.next()) {
            id = rs.getString("ID");
        }
        if(id != null) {
            String sqlUpdatePaciente = "UPDATE paciente SET altura = '"+altura+"', peso = '"+peso+"', imc = '"+result+"' WHERE id = '"+id+"'";
            String sqlUpdateClassificacao = "UPDATE classificacao SET classificacao = '"+res+"' WHERE id = '"+id+"'";
            st.executeUpdate(sqlUpdatePaciente);
            st.executeUpdate(sqlUpdateClassificacao);

            String[] sqlUpdatePacienteSeparado = sqlUpdatePaciente.split(" ");
            String[] sqlUpdateClassificacaoSeparado = sqlUpdateClassificacao.split(" ");
            Log log = new Log(getBanco(), sqlUpdatePacienteSeparado[0]);
            Log log2 = new Log(getBanco(), sqlUpdateClassificacaoSeparado[0]);

        } else {
            String sqlInsertPaciente = "INSERT INTO paciente VALUES (default,"+idUser+", '"+altura+"', '"+peso+"', '"+result+"')";
            String sqlInsertClassificacao = "INSERT INTO classificacao VALUES (default,"+idUser+", '"+res+"')";
            st.executeUpdate(sqlInsertPaciente);
            st.executeUpdate(sqlInsertClassificacao);

            String[] sqlInsertPacienteSeparado = sqlInsertPaciente.split(" ");
            String[] sqlInsertClassificacaoSeparado = sqlInsertClassificacao.split(" ");
            Log log = new Log(getBanco(), sqlInsertPacienteSeparado[0]);
            Log log2 = new Log(getBanco(), sqlInsertClassificacaoSeparado[0]);
        }



    }
}
