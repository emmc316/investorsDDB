package view;

import javax.swing.*;

public class Login extends JFrame{

    public Login(){
        initComponentes();
        setTitle("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,500);
        setResizable(false);
        setVisible(true);
    }

    private void initComponentes(){
    setContentPane(panelBorde);
    }

    private JPanel panelBorde;
    private JPanel panelCentro;
    private JLabel etiquetaTitulo;
    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JLabel etiquetaUsuario;
    private JLabel etiquetaPassword;
    private JPanel panelLogo;
    private JLabel etiquetaLogo;
    private JButton ingresarButton;


}
