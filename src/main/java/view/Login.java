package view;

import controller.LoginController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private boolean logged = false;
    public Login(){
        initComponentes();
        setTitle("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initComponentes(){
    setContentPane(panelBorde);
    }
    public boolean isLogged() {
        return logged;
    }
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void addButtonEvent(ActionListener loginController){
    this.ingresarButton.addActionListener(loginController);
    }

    public String getUser(){return this.campoUsuario.getText();}
    public String getPass(){
        return String.valueOf(this.campoPassword.getPassword());
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
