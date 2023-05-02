package view;

import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class Appearance {
    private Font fontBold;
    private Font fontRegular;
    private Color fontColor;

    public Appearance(){
        initProperties();
        addProperties();
    }

    private void initProperties(){
        fontBold = new Font("Liberation Sans",1,14);
        fontRegular = new Font("Liberation Sans",0,14);
        fontColor = Color.BLACK;
    }

    public void addProperties(){
        UIManager.put("Button.font", fontBold);
        UIManager.put("TextField.font", fontRegular);
        UIManager.put("Label.font", fontBold);

        UIManager.put("Button.foreground", fontColor);
        UIManager.put("TextField.foreground", fontColor);
        UIManager.put("Label.foreground", fontColor);
        UIManager.put("ComboBox.foreground", fontColor);
        UIManager.put("TabbedPane.foreground", fontColor);

        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Error de look and feel");
        }
    }

}
