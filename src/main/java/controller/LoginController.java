package controller;

import view.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    String name;
    String pass;
    private Login login;
    public LoginController(String name,String pass,Login login){
        this.name = name;
        this.pass = pass;
        this.login = login;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    JComponent component = (JComponent) e.getSource();
            switch (component.getName()){
                case "buttonLogin":{
                    if(login(login.getUser(), login.getPass()))
                    {
                        JOptionPane.showMessageDialog(this.login,"Acces Allowed","It is  Ok",JOptionPane.INFORMATION_MESSAGE);
                        login.setLogged(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(this.login,"Acces does not allowed","Error",JOptionPane.ERROR_MESSAGE);
                        login.setLogged(false);
                    }
               break;
                }
            }
    }

    public boolean login(String name, String passwd) {
        if(this.name.equals(name) && this.pass.equals(passwd)) {
            return true;
        }
        else {
            return false;
        }
    }
}
