import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame {
    Banco banco;

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    Container tela;

    Login(Banco banco) {
        setBanco(banco);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        this.tela = getContentPane();
        this.tela.setLayout(null);
        JLabel titulo = new JLabel("Login");
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 30);
        titulo.setFont(fonte_titulo);
        JLabel txtemail = new JLabel("Email:");
        JLabel txtsenha = new JLabel("Senha:");

        this.tela.add(txtemail);
        this.tela.add(txtsenha);
        this.tela.add(titulo);
        titulo.setBounds(300,20,200,30);
        txtemail.setBounds(200,75,200,30);
        txtsenha.setBounds(200,115,200,30);

        JTextField caixaemail = new JTextField(20);
        JPasswordField caixasenha = new JPasswordField (20);
        this.tela.add(caixaemail);
        this.tela.add(caixasenha);
        caixaemail.setBounds(240,80,200,20);
        caixasenha.setBounds(240,120,200,20);
        JButton btn = new JButton("Entrar");
        JButton btnCadastro = new JButton("Cadastrar");
        this.tela.add(btn);
        this.tela.add(btnCadastro);
        btn.setBounds(220, 160, 200, 20);
        btnCadastro.setBounds(220, 200, 200, 20);

        btnCadastro.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Cadastro cadastro = new Cadastro(banco);
                dispose();
            }
        });

        btn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String senha = new String(caixasenha.getPassword());
                String email = new String(caixaemail.getText());
                try {
                    UtilsLogin utilsLogin = new UtilsLogin(getBanco());
                    int id = utilsLogin.fazerLogin(email,senha);
                    if(id != 0) {
                        IMC imc = new IMC(getBanco(), id);
                        dispose();
                    }

                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
    }


}
