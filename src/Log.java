import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    Banco banco;
    String clausula;

    public Banco getBanco(){
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    public String getClausula(){
        return clausula;
    }

    public void setClausula(String clausula) {
        this.clausula = clausula;
    }

    Log(Banco banco, String clausula) throws SQLException {
        setBanco(banco);
        setClausula(clausula);

        getBanco().conexao();
        Statement st = getBanco().con.createStatement();
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        st.executeUpdate("INSERT INTO log VALUES (default,'"+getClausula()+"', now())");
    }


}
