import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Percentuais extends JFrame {

    Banco banco;
    Container tela;

    public Banco getBanco(){
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    Percentuais(Banco banco) throws SQLException {
        float qtd = 0;
        float qtd_c = 0;
        float[] porcentagem = new float[6];

        setBanco(banco);

        setSize(700,500);
        setVisible(true);
        setResizable(false);
        this.tela = getContentPane();
        this.tela.setLayout(null);

        JLabel titulo = new JLabel("Percentuais");
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 30);
        titulo.setFont(fonte_titulo);
        PreparedStatement preparedStatement = null;
        String sql = "SELECT count(*) AS qtd FROM classificacao GROUP BY classificacao;";
        preparedStatement = getBanco().con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String[] sqlSeparado = sql.split(" ");
        Log log = new Log(getBanco(), sqlSeparado[0]);

        if(rs.next()) {
            qtd = rs.getFloat("QTD");
        }

        String[] classificacoes = {"Magreza", "Saudavel", "Sobrepeso", "Obesidade Grau I", "Obesidade Grau II", "Obesidade Grau III"};
        int x = 50;

        for(int i = 0; i <= classificacoes.length -1; i++) {
            String sql2 = "SELECT count(*) AS qtd FROM classificacao WHERE classificacao = '"+classificacoes[i]+"' GROUP BY classificacao";
            preparedStatement = getBanco().con.prepareStatement(sql2);
            ResultSet rs2 = preparedStatement.executeQuery();

            if(rs2.next()){
                qtd_c = rs2.getFloat("QTD");
                porcentagem[i] = (qtd_c / qtd) * 100;
                JLabel lblimc = new JLabel();
                lblimc.setText(classificacoes[i] +" = "+porcentagem[i]+"%");
                this.tela.add(lblimc);
                lblimc.setBounds(225, x, 300, 25);
                x += 20;
            }

        }

    }
}
