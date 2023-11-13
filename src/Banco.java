import java.sql.*;

public class Banco {
    private Statement st;
    private ResultSet rs;
    public Connection con;
    private String c_user="root";
    private String c_senha="";
    private String c_fonte="jdbc:mysql://localhost/projeto_java2";

    public Statement getSt(){
        return st;
    }
    public void setSt(Statement st){
        this.st = st;
    }

    public void SetRs(ResultSet rs){
        this.rs = rs;
    }

    public ResultSet getRs(){
        return rs;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public void conexao() {
        try {
            con=DriverManager.getConnection(this.c_fonte,this.c_user,this.c_senha);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
