package view;

import com.toedter.calendar.JDateChooser;
import controller.ControlPanelController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ControlPane extends JFrame {

    private User node;
    public ControlPane(User node){
        this.node = node;
        initComponents();
        this.setTitle("Panel de control");
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void addEventsItems(ItemListener evt){
        comboConsultarPorInversionistas.addItemListener(evt);
        comboConsultarPorContratos.addItemListener(evt);
        comboConsultarPorPagares.addItemListener(evt);
    }

    public void addEventsButtons(ActionListener evt){
        botonConsultarInversionistas.addActionListener(evt);
        botonConsultarContratos.addActionListener(evt);
        botonConsultarPagares.addActionListener(evt);
    }

    private JPanel panelBorde;
    private JTabbedPane panelPestañas;
    private JPanel panelConsultar;
    private JPanel panelInsertar;
    private JPanel panelActualizar;
    private JPanel panelEliminar;
    private JPanel segundaPestañaNorte;
    private JComboBox comboConsultarPorContratos;
    private JTabbedPane panelPestañasConsultar;
    private JPanel segundaPestaña;
    private JLabel etiquetaRFCContratos;
    private JTextField campoRFCContratos;
    private JLabel etiquetaConsultarPorContratos;
    private JButton botonConsultarContratos;
    private JPanel segundaPestañaCentro;
    private JPanel primeraPestaña;
    private JPanel primeraPestañaNorte;
    private JComboBox comboConsultarPorInversionistas;
    private JTextField campoRFCInversionistas;
    private JButton botonConsultarInversionistas;
    private JLabel etiquetaConsultarPorInversionistas;
    private JLabel etiquetaRFCInversionistas;
    private JPanel primeraPestañaCentro;
    private JPanel terceraPestaña;
    private JPanel terceraPestañaNorte;
    private JComboBox comboConsultarPorPagares;
    private JTextField campoRFCPagares;
    private JLabel etiquetaConsultarPorPagares;
    private JLabel etiquetaRFCPagares;
    private JLabel etiquetaFechaInicialPagarre;
    private JLabel etiquetaFechaFinalPagare;
    private JButton botonConsultarPagares;
    private JScrollPane panelBarrasInversionistas;
    private JTable tablaInversionistas;
    private JScrollPane panelBarrasContratos;
    private JTable tablaContratos;
    private JPanel terceraPestañaCentro;
    private JScrollPane panelBarrasPagares;
    private JTable tablaPagares;

    private JDateChooser fechaInicial;
    private JDateChooser fechaFinal;

    private DefaultTableModel modelInversionista;
    private DefaultTableModel modelContrato;
    private DefaultTableModel modelPagare;


    private void initComponents(){
        this.setContentPane(panelBorde);
        this.botonConsultarInversionistas.setName("inversors");
        this.botonConsultarContratos.setName("contracts");
        this.botonConsultarPagares.setName("promissoryNotes");

        addComboElements();
        addTableColumns();
        addDateChooser();
        addIcons();
    }

    private void addIcons(){
        String ruta = getClass().getResource("").getPath().replace("view/","imagenes/");
        System.out.println(ruta);
        panelPestañas.setIconAt(0,new ImageIcon(ruta + "consulta.png"));
        panelPestañas.setIconAt(1,new ImageIcon(ruta + "insertar.png"));
        panelPestañas.setIconAt(2,new ImageIcon(ruta + "actualizar.png"));
        panelPestañas.setIconAt(3,new ImageIcon(ruta + "eliminar.png"));
    }

    private void addComboElements() {
        DefaultComboBoxModel modelClientes = (DefaultComboBoxModel) comboConsultarPorInversionistas.getModel();
        modelClientes.addElement("Todos los clientes");
        modelClientes.addElement("RFC de un cliente");
        comboConsultarPorInversionistas.updateUI();

        DefaultComboBoxModel modelContratos = (DefaultComboBoxModel) comboConsultarPorContratos.getModel();
        modelContratos.addElement("Todos los contratos");
        modelContratos.addElement("RFC de un cliente");
        comboConsultarPorContratos.updateUI();

        DefaultComboBoxModel modelPagares = (DefaultComboBoxModel) comboConsultarPorPagares.getModel();
        modelPagares.addElement("Todos los pagares");
        modelPagares.addElement("RFC de un cliente");
        modelPagares.addElement("En un intervalo de fechas");
        comboConsultarPorPagares.updateUI();


    }

    private void addTableColumns(){
        modelInversionista = (DefaultTableModel) tablaInversionistas.getModel();
        modelInversionista.addColumn("RFC");
        modelInversionista.addColumn("Nombre");
        modelInversionista.addColumn("Telefono");
        modelInversionista.addColumn("Dirección");
        modelInversionista.addColumn("Email");
        modelInversionista.addColumn("Tipo persona");
        tablaInversionistas.setEnabled(false);
        tablaInversionistas.updateUI();


        modelContrato = (DefaultTableModel) tablaContratos.getModel();
        modelContrato.addColumn("Clave contrato");
        modelContrato.addColumn("Clave sucursal");
        modelContrato.addColumn("RFC");
        modelContrato.addColumn("Monto total");
        modelContrato.addColumn("Status");
        tablaContratos.setEnabled(false);
        tablaContratos.updateUI();

        modelPagare = (DefaultTableModel) tablaPagares.getModel();
        modelPagare.addColumn("Clave Pagare");
        modelPagare.addColumn("Clave contrato");
        modelPagare.addColumn("Tipo de tasa");
        modelPagare.addColumn("Fecha emisión");
        modelPagare.addColumn("Fecha vencimiento");
        tablaPagares.setEnabled(false);
        tablaPagares.updateUI();
    }

    public void addDateChooser(){
        fechaInicial = new JDateChooser("yyyy-MM-dd", "####-##-##", '_');
        fechaFinal = new JDateChooser("yyyy-MM-dd", "####-##-##", '_');
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 6;
        gb.gridy = 0;
        gb.insets = new Insets(5,0,5,0);
        gb.ipadx = 30;
        gb.anchor = GridBagConstraints.WEST;
        terceraPestañaNorte.add(fechaInicial, gb);
        gb.gridx = 6;
        gb.gridy = 1;
        gb.insets = new Insets(5,0,5,0);
        gb.ipadx = 30;
        gb.anchor = GridBagConstraints.WEST;
        terceraPestañaNorte.add(fechaFinal, gb);
        fechaInicial.setEnabled(false);
        fechaFinal.setEnabled(false);
    }

    public void enableComponents(int value, boolean enable){
        switch (value){
            case 1:
                campoRFCInversionistas.setEnabled(enable);
                break;
            case 2:
                campoRFCContratos.setEnabled(enable);
                break;
            case 3:
                campoRFCPagares.setEnabled(enable);
                break;
            case 4:
                fechaInicial.setEnabled(enable);
                fechaFinal.setEnabled(enable);
        }
    }

    public User getNode() {
        return node;
    }

    public DefaultTableModel getModelInversionista() {
        return modelInversionista;
    }

    public DefaultTableModel getModelContrato() {
        return modelContrato;
    }

    public DefaultTableModel getModelPagare() {
        return modelPagare;
    }

    public JTextField getCampoRFCInversionistas(){
        return campoRFCInversionistas;
    }

    public JTextField getCampoRFCContratos() {
        return campoRFCContratos;
    }

    public JTextField getCampoRFCPagares() {
        return campoRFCPagares;
    }

    public JDateChooser getFechaInicial() {
        return fechaInicial;
    }

    public JDateChooser getFechaFinal() {
        return fechaFinal;
    }
}
