import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IMC extends JFrame {
    Banco banco;
    Container tela;
    String peso;
    String altura;
    int id;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Banco getBanco(){
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getPeso(){
        return peso;
    }
    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getAltura(){
        return altura;
    }
    IMC(Banco banco, int id) {
        setBanco(banco);
        setId(id);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        this.tela = getContentPane();
        this.tela.setLayout(null);
        JLabel titulo = new JLabel("Calcular IMC");
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 30);
        titulo.setFont(fonte_titulo);
        JLabel txtaltura = new JLabel("Altura:");
        JLabel txtpeso = new JLabel("Peso:");

        this.tela.add(txtaltura);
        this.tela.add(txtpeso);
        this.tela.add(titulo);
        titulo.setBounds(240,20,200,30);
        txtaltura.setBounds(200,75,200,30);
        txtpeso.setBounds(200,115,200,30);

        JTextField caixaAltura = new JTextField(20);
        JTextField caixaPeso = new JTextField (20);
        this.tela.add(caixaAltura);
        this.tela.add(caixaPeso);
        caixaAltura.setBounds(240,80,200,20);
        caixaPeso.setBounds(240,120,200,20);
        JButton btn = new JButton("Calcular");
        JButton btnPerfil = new JButton("Alterar perfil");
        JButton btnPercertual = new JButton("Percentual");
        JButton btnRelatorio = new JButton("Gerar relatorio");
        this.tela.add(btn);
        this.tela.add(btnPerfil);
        this.tela.add(btnPercertual);
        this.tela.add(btnRelatorio);
        btn.setBounds(220, 160, 200, 20);
        btnPerfil.setBounds(220, 200, 200, 20);
        btnPercertual.setBounds(220, 240, 200, 20);
        btnRelatorio.setBounds(220, 280, 200, 20);

        btnPercertual.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Percentuais percentuais = new Percentuais(getBanco());
                    dispose();

                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });

        btnRelatorio.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    UtilsIMC utilsIMC = new UtilsIMC(getBanco());
                    String caminho = "C:\\Users\\curti\\OneDrive\\Documentos\\Projetos\\projeto-java-4-bimestre\\src\\relatorio.html";
                    utilsIMC.gerarRelatorio(caminho);
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });

        btn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String peso = new String(caixaPeso.getText());
                String altura = new String(caixaAltura.getText());
                setPeso(peso);
                setAltura(altura);
                try {
                    UtilsIMC utilsIMC = new UtilsIMC(getBanco());
                    utilsIMC.Calcular(getAltura(), getPeso(), getId());
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });

        btnPerfil.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Paciente paciente = new Paciente(getBanco(), getId());
                dispose();
            }
        });



    }



}
