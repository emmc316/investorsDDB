package view;

import mdlaf.MaterialLookAndFeel;
import javax.swing.*;
import java.awt.*;

public class Appearance {
    private Font fontBold;
    private Font fontRegular;
    private Color fontColor;
    private Color background;

    public Appearance(){
        initProperties();
        addProperties();
        setLookAndFeel();
    }

    private void initProperties(){
        fontBold = new Font("Liberation Sans",1,14);
        fontRegular = new Font("Liberation Sans",0,14);
        fontColor = Color.WHITE;
        background = new Color(60,63,65);
    }

    private void addProperties(){
        //FUENTES
        UIManager.put("Button.font", fontBold);
        UIManager.put("Label.font", fontBold);
        UIManager.put("TextField.font", fontRegular);
        UIManager.put("ComboBox.font", fontRegular);
        UIManager.put("TabbedPane.font", fontRegular);
        UIManager.put("Table.font", fontRegular);
        //COLOR DE LAS FUENTES
        UIManager.put("Button.foreground", fontColor);
        UIManager.put("TextField.foreground", fontColor);
        UIManager.put("Label.foreground", fontColor);
        UIManager.put("ComboBox.foreground", fontColor);
        UIManager.put("TabbedPane.foreground", fontColor);
        UIManager.put("Table.foreground", fontColor);
        //COLOR DEL FONDO
        UIManager.put("Panel.background",background);
        UIManager.put("ScrollPane.background",background);
        UIManager.put("Table.background",background);
        UIManager.put("TabbedPane.background",background);
        UIManager.put("ComboBox.background",background);
        UIManager.put("Button.background",background);
        UIManager.put("TextField.background",background);

    }

    private void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Error de look and feel");
        }
    }
}
