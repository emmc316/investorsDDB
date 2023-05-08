package view;

import javax.swing.*;
import java.awt.*;

public class ConnectToServers extends JDialog {
    private JPanel contentPane;
    private JProgressBar loadingBar;
    private JLabel connectLabel;
    private JLabel img;
    private JLabel info_text;
    public ConnectToServers() {
        setContentPane(contentPane);
        setModal(true);
        setSize(600,300);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setTitle("Loading");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(null);
        setResizable(false);
        loadingBar.setValue(0);
    }

    public void setInformation(String value){
        info_text.setText(value);
    }

    public void LoadingProgress(int value){
        loadingBar.setValue(value);
    }

    public void refresh(){
        pack();
        setVisible(true);
    }
    public int getProgress(){
        return this.loadingBar.getValue();
    }
}
