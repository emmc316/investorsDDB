package view;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class ControlPane extends JFrame{

    public ControlPane(){
        initComponents();
        this.setTitle("Panel de control");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private JPanel panelBorde;
    private JTabbedPane panelPestañas;
    private JPanel panelConsultar;
    private JPanel panelInsertar;
    private JPanel panelActualizar;
    private JPanel panelEliminar;
    private JPanel panelConsultarNorte;
    private JComboBox comboConsultarPor;
    private JTextField campoRFC;
    private JLabel etiquetaConsultarPor;
    private JLabel etiquetaRFC;
    private JLabel etiquetaFechaIncial;
    private JLabel etiquetaFechaFinal;
    private JPanel panelConsultarCentro;
    private JPanel panelConsultarPor;
    private JButton botonConsultar;

    private void initComponents(){
        this.setContentPane(panelBorde);
        comboElements();

        JDateChooser fechaInicial = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        JDateChooser fechaFinal = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 3;
        gb.gridy = 1;
        gb.insets = new Insets(5,0,5,0);
        gb.ipadx = 30;
        gb.anchor = GridBagConstraints.WEST;
        panelConsultarNorte.add(fechaInicial, gb);
        gb.gridx = 3;
        gb.gridy = 2;
        gb.insets = new Insets(5,0,5,0);
        gb.ipadx = 30;
        gb.anchor = GridBagConstraints.WEST;
        panelConsultarNorte.add(fechaFinal, gb);
    }

    private void comboElements() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) comboConsultarPor.getModel();
        model.addElement("Todos los clientes");
        model.addElement("RFC de un cliente");
        model.addElement("Un intervalo de fechas");
        comboConsultarPor.updateUI();
    }
}
