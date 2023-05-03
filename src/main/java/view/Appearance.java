package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.*;

public class Appearance {
    private Font fontBold;
    private Font fontRegular;

    public Appearance(){
        initProperties();
        addProperties();
        setLookAndFeel();
    }

    private void initProperties(){
        fontBold = new Font("Liberation Sans",1,14);
        fontRegular = new Font("Liberation Sans",0,14);
    }

    private void addProperties(){
        //FUENTES
        UIManager.put("Button.font", fontBold);
        UIManager.put("Label.font", fontBold);
        UIManager.put("TextField.font", fontRegular);
        UIManager.put("ComboBox.font", fontRegular);
        UIManager.put("TabbedPane.font", new Font("Liberation Sans", 1, 16));
        UIManager.put("Table.font", fontRegular);
    }

    private void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Error de look and feel");
        }
    }
}
