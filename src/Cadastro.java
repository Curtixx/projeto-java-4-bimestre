import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cadastro extends JFrame {
    Container tela;
    Banco banco;

    public Banco getBanco(){
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    Cadastro(Banco banco){
        setBanco(banco);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        this.tela = getContentPane();
        this.tela.setLayout(null);
        JLabel titulo = new JLabel("Cadastro");
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 26);
        titulo.setFont(fonte_titulo);
        JLabel txtemail = new JLabel("Email:");
        JLabel txtnome = new JLabel("Nome:");
        JLabel txtsenha = new JLabel("Senha:");

        this.tela.add(txtemail);
        this.tela.add(txtnome);
        this.tela.add(txtsenha);
        this.tela.add(titulo);
        titulo.setBounds(300,20,200,30);
        txtemail.setBounds(200,80,200,20);
        txtnome.setBounds(200,100,200,20);
        txtsenha.setBounds(200,120,200,20);

        JTextField caixaemail = new JTextField(10);
        JTextField caixanome = new JTextField(10);
        JPasswordField caixasenha = new JPasswordField (10);
        this.tela.add(caixaemail);
        this.tela.add(caixanome);
        this.tela.add(caixasenha);
        caixaemail.setBounds(240,80,200,20);
        caixanome.setBounds(240,100,200,20);
        caixasenha.setBounds(240,120,200,20);
        JButton btn = new JButton("Cadastrar");

        this.tela.add(btn);

        btn.setBounds(220, 160, 200, 20);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String email = new String(caixaemail.getText());
                String nome = new String(caixanome.getText());
                String senha = new String(caixasenha.getPassword());
                try{
                    UtilsCadastro utilsCadastro = new UtilsCadastro(getBanco());
                    int id = utilsCadastro.fazerCadastro(email, senha, nome);
                    if(id != 0) {
                        IMC imc = new IMC(banco, id);
                        dispose();
                    }
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });

    }





}
