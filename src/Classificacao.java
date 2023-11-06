import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Classificacao extends JFrame {
    Banco banco;
    Container tela;

    public Banco getBanco() {
        return banco;
    }
    public void setBanco(Banco banco){
        this.banco = banco;
    }

    Classificacao(Banco banco) throws SQLException {
        setBanco(banco);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        this.tela = getContentPane();
        this.tela.setLayout(null);

        JLabel titulo = new JLabel("Classificação");
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 30);
        titulo.setFont(fonte_titulo);

        getBanco().conexao();
        PreparedStatement preparedStatement = null;
        String nome = null;
        String id = null;
        String idPaciente = null;
        String classificacao = null;
        String imc = null;
        String sql = "SELECT * FROM login";
        preparedStatement = getBanco().con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String[] sqlSelectLoginSeparado = sql.split(" ");
        Log log = new Log(getBanco(), sqlSelectLoginSeparado[0]);
        int y = 20;
        int x = 200;
        while (rs.next()) {
            nome = rs.getString("NOME");
            id = rs.getString("ID");
            JLabel txtpaciente = new JLabel(nome + ": ");
            this.tela.add(txtpaciente);
            txtpaciente.setBounds(x,y,200,30);

            String sqlSelect = "SELECT * FROM classificacao";
            preparedStatement = getBanco().con.prepareStatement(sqlSelect);
            ResultSet rs2 = preparedStatement.executeQuery();
            String[] sqlSelectClassificacaoSeparado = sqlSelect.split(" ");
            Log log2 = new Log(getBanco(), sqlSelectClassificacaoSeparado[0]);
            x = x + 65;
            while (rs2.next()) {

                idPaciente = rs2.getString("ID_PACIENTE");
                classificacao = rs2.getString("CLASSIFICACAO");
                JLabel txtclassificao = new JLabel(classificacao);
                this.tela.add(txtclassificao);
                txtclassificao.setBounds(x,y,200,30);

            }
            String sqlSelectPaciente = "SELECT * FROM paciente";
            preparedStatement = getBanco().con.prepareStatement(sqlSelectPaciente);
            ResultSet rs3 = preparedStatement.executeQuery();
            String[] sqlSelectPacienteSeparado = sqlSelectPaciente.split(" ");
            Log log3 = new Log(getBanco(), sqlSelectPacienteSeparado[0]);
            x = x + 75;
            while (rs3.next()) {

                imc = rs3.getString("IMC");
                JLabel txtImc = new JLabel(imc);
                this.tela.add(txtImc);
                txtImc.setBounds(x,y,200,30);
            }
            y = y + 20;
            x = 200;

        }
    }
}
