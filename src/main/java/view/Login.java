package view;

import controller.LoginController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

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

    public void addButtonEvent(ActionListener AppController){
    this.ingresarButton.addActionListener(AppController);
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
