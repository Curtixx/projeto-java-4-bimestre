import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Paciente extends JFrame {

    Banco banco;
    String email;
    String senha;
    String nome;
    int id;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    Container tela;

    public Banco getBanco(){
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    Paciente(Banco banco, int id){
        setBanco(banco);
        setId(id);

        setSize(700,500);
        setVisible(true);
        setResizable(false);
        this.tela = getContentPane();
        this.tela.setLayout(null);
        JLabel titulo = new JLabel("Alterar cadastro");
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 30);
        titulo.setFont(fonte_titulo);
        JLabel txtemail = new JLabel("Email:");
        JLabel txtnome = new JLabel("Nome:");
        JLabel txtsenha = new JLabel("Senha:");
        this.tela.add(txtemail);
        this.tela.add(txtnome);
        this.tela.add(txtsenha);
        this.tela.add(titulo);
        titulo.setBounds(240,20,200,30);
        txtemail.setBounds(200,75,200,30);
        txtnome.setBounds(200,95,200,30);
        txtsenha.setBounds(200,115,200,30);

        JTextField caixaEmail = new JTextField(20);
        JTextField caixaNome = new JTextField(20);
        JPasswordField caixaSenha = new JPasswordField (20);
        this.tela.add(caixaEmail);
        this.tela.add(caixaNome);
        this.tela.add(caixaSenha);
        caixaEmail.setBounds(240,80,200,20);
        caixaNome.setBounds(240,100,200,20);
        caixaSenha.setBounds(240,120,200,20);
        JButton btn = new JButton("Alterar");
        JButton btnDeletar = new JButton("Excluir");
        this.tela.add(btn);
        this.tela.add(btnDeletar);
        btn.setBounds(220, 160, 200, 20);
        btnDeletar.setBounds(220, 200, 200, 20);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String email = new String(caixaEmail.getText());
                String senha = new String(caixaSenha.getPassword());
                String nome = new String(caixaNome.getText());
                setEmail(email);
                setSenha(senha);
                setNome(nome);
                try {
                    UtilsPaciente utilsPaciente = new UtilsPaciente(getBanco());

                    if(utilsPaciente.alterarLogin(getEmail(), getSenha(), getNome())) {
                        IMC imc = new IMC(getBanco(), getId());
                        dispose();
                    }
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });

        btnDeletar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    UtilsPaciente utilsPaciente = new UtilsPaciente(getBanco());
                    if(utilsPaciente.deletarLogin(getId())) {
                        Cadastro cadastro = new Cadastro(getBanco());
                        dispose();
                    }
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
    }

}
