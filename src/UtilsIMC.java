import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class UtilsIMC extends UtilsLogin {

    UtilsIMC(Banco banco) {
        super(banco);
    }

    public void gerarRelatorio(String caminho) throws IOException, SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT classificacao, count(*) AS qtd FROM classificacao GROUP BY classificacao;";
        preparedStatement = getBanco().con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String[] sqlSeparado = sql.split(" ");
        Log log = new Log(getBanco(), sqlSeparado[0]);
        String qtd = null;
        String classificacao = null;
        int[] qtds = new int[6];
        int i = 0;
        while (rs.next()) {
            qtd = rs.getString("QTD");
            classificacao = rs.getString("CLASSIFICACAO");
            qtds[i] = Integer.parseInt(qtd);
            i++;
        }
        String sqlInnerJoin = "SELECT id_paciente, altura, peso, imc, classificacao FROM paciente INNER JOIN classificacao ON paciente.id = classificacao.id_paciente;";
        preparedStatement = getBanco().con.prepareStatement(sqlInnerJoin);
        ResultSet rs2 = preparedStatement.executeQuery();
        String[] sqlJoinSeparado = sql.split(" ");
        Log log2 = new Log(getBanco(), sqlSeparado[0]);
        String id_paciente = null;
        String altura = null;
        String peso = null;
        String imc = null;
        String classificacaoPaciente = null;


        String grafico = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Document</title>\n" +
                "<script src='https://cdn.plot.ly/plotly-2.27.0.min.js'></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<center><div id='myDiv' style=\"width: 500px; height:400px;\">Pesquisa de Indice\n" +
                "de Massa Corporal</div></center>\n" +
                "<script>\n" +
                "var data = [{\n" +
                "type: 'scatterpolar',\n" +
                "r: ['"+qtds[0]+"', '"+qtds[1]+"', '"+qtds[2]+"', '"+qtds[3]+"', '"+qtds[4]+"', '"+qtds[5]+"'],\n" +
                "theta: ['Magreza','Obesidade grau I','Obesidade grau II', 'Obesidade grau III', 'Saudável', 'Sobrepeso'],\n" +
                "fill: 'toself'\n" +
                "}]\n" +
                "layout = {\n" +
                "polar: {\n" +
                "radialaxis: {\n" +
                "visible: true,\n" +
                "range: [0, 50]\n" +
                "}\n" +
                "},\n" +
                "showlegend: false\n" +
                "}\n" +
                "Plotly.newPlot(\"myDiv\", data, layout)\n" +
                "</script>\n " +
                "</body>\n" +
                "</html>";

        String tbl = "<center><table style='margin-top: 50px;' border='1'>" +
                "<tr>" +
                "<th>ID_PACIENTE</th>" +
                "<th>ALTURA</th>" +
                "<th>PESO</th>" +
                "<th>IMC</th>" +
                "<th>CLASSIFICACAO</th>" +
                "</tr>";

        while (rs2.next()) {
            id_paciente = rs2.getString("ID_PACIENTE");
            altura = rs2.getString("ALTURA");
            peso = rs2.getString("PESO");
            imc = rs2.getString("IMC");
            classificacaoPaciente = rs2.getString("CLASSIFICACAO");

            tbl += "<tr>" +
                    "<td>" + id_paciente + "</td>" +
                    "<td>" + altura + "</td>" +
                    "<td>" + peso + "</td>" +
                    "<td>" + imc + "</td>" +
                    "<td>" + classificacaoPaciente + "</td>" +
                    "</tr>";
        }

        tbl += "</table></center>";

        grafico += tbl;

        FileWriter fw = new FileWriter(caminho, true);
        fw.write(grafico+"\n");
        fw.close();
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
        String sql = "SELECT * FROM paciente WHERE id = '"+idUser+"'";
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
            String idPaciente = null;
            String sqlInsertPaciente = "INSERT INTO paciente VALUES (default,'"+altura+"', '"+peso+"', '"+result+"')";
            st.executeUpdate(sqlInsertPaciente);
            String sqlId = "SELECT * FROM paciente WHERE peso='"+peso+"'";
            preparedStatement = getBanco().con.prepareStatement(sqlId);
            ResultSet rs2 = preparedStatement.executeQuery();
            while (rs2.next()) {
                idPaciente = rs2.getString("ID");
            }
            System.out.println(idPaciente);
            String sqlInsertClassificacao = "INSERT INTO classificacao VALUES (default,'"+idPaciente+"', '"+res+"')";

            st.executeUpdate(sqlInsertClassificacao);

            String[] sqlSelectPacienteSeparado = sqlId.split(" ");
            String[] sqlInsertPacienteSeparado = sqlInsertPaciente.split(" ");
            String[] sqlInsertClassificacaoSeparado = sqlInsertClassificacao.split(" ");
            Log log = new Log(getBanco(), sqlInsertPacienteSeparado[0]);
            Log log2 = new Log(getBanco(), sqlInsertClassificacaoSeparado[0]);
            Log log3 = new Log(getBanco(), sqlSelectPacienteSeparado[0]);
        }



    }
}
