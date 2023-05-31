package view;

import com.toedter.calendar.JDateChooser;
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
        botonAgregarII.addActionListener(evt);
        botonAgregarIC.addActionListener(evt);
        botonAgregarIP.addActionListener(evt);
        botonActualizarAI.addActionListener(evt);
        botonActualizarAC.addActionListener(evt);
        botonActualizarAP.addActionListener(evt);
        botonEliminarEI.addActionListener(evt);
        botonEliminarEC.addActionListener(evt);
        botonEliminarEP.addActionListener(evt);
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
    private JPanel segundaPestañaC;
    private JLabel etiquetaRFCContratos;
    private JTextField campoRFCContratos;
    private JLabel etiquetaConsultarPorContratos;
    private JButton botonConsultarContratos;
    private JPanel segundaPestañaCentro;
    private JPanel primeraPestañaC;
    private JPanel primeraPestañaNorte;
    private JComboBox comboConsultarPorInversionistas;
    private JTextField campoRFCInversionistas;
    private JButton botonConsultarInversionistas;
    private JLabel etiquetaConsultarPorInversionistas;
    private JLabel etiquetaRFCInversionistas;
    private JPanel primeraPestañaCentro;
    private JPanel terceraPestañaC;
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
    private JTabbedPane PanelPestañasInsertar;
    private JPanel primeraPestañaI;
    private JPanel segundaPestañaI;
    private JPanel terceraPestañaI;
    private JTextField campoRFCII;
    private JTextField campoNombreII;
    private JTextField campoTelefonoII;
    private JTextField campoDireccionII;
    private JTextField campoCorreoII;
    private JComboBox comboTipoPersonaII;
    private JButton botonAgregarII;
    private JLabel etiquetaTitulo;
    private JTextField campoContratoIC;
    private JTextField campoRCFIC;
    private JTextField campoMontoIC;
    private JButton botonAgregarIC;
    private JTextField campoContratoIP;
    private JTextField campoPagareIP;
    private JTextField campoVencimientoIP;
    private JButton botonAgregarIP;
    private JTabbedPane panelPestañasActualizar;
    private JTextField campoRFCAI;
    private JTextField campoTelefonoAI;
    private JTextField campoDireccionAI;
    private JTextField campoCorreoAI;
    private JButton botonActualizarAI;
    private JTextField campoContratoAC;
    private JTextField campoMontoAC;
    private JComboBox comboStatusAC;
    private JButton botonActualizarAC;
    private JTextField campoPagareAP;
    private JButton botonActualizarAP;
    private JComboBox comboTasa;
    private JComboBox comboTasaIP;
    private JPanel primeraPestañaA;
    private JPanel segundaPestañaA;
    private JPanel terceraPestañaA;
    private JTabbedPane panelPestañasEliminar;
    private JPanel primeraPestañaE;
    private JPanel segundaPestañaE;
    private JPanel terceraPestañaE;
    private JTextField campoPagareEP;
    private JButton botonEliminarEP;
    private JTextField campoRFCEI;
    private JButton botonEliminarEI;
    private JTextField campoContratoEC;
    private JButton botonEliminarEC;
    private JTextField campoRFCIP;
    private JTextField campoRFCAC;
    private JTextField campoRFCAP;
    private JTextField campoRFCEC;
    private JTextField campoRFCEP;

    private JDateChooser fechaInicial;
    private JDateChooser fechaFinal;

    private JDateChooser fechaVencimiento;

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

        DefaultComboBoxModel modelPersona = (DefaultComboBoxModel) comboTipoPersonaII.getModel();
        modelPersona.addElement("Fisica");
        modelPersona.addElement("Moral");
        comboTipoPersonaII.updateUI();

        DefaultComboBoxModel modelTasa = (DefaultComboBoxModel) comboTasa.getModel();
        modelTasa.addElement("A");
        comboTasa.updateUI();

        DefaultComboBoxModel modelTasaIP = (DefaultComboBoxModel) comboTasaIP.getModel();
        modelTasaIP.addElement("A");
        comboTasaIP.updateUI();

        DefaultComboBoxModel modelStatus = (DefaultComboBoxModel) comboStatusAC.getModel();
        modelStatus.addElement("Activo");
        modelStatus.addElement("Concluido");
        comboStatusAC.updateUI();
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
        fechaVencimiento = new JDateChooser("yyyy-MM-dd", "####-##-##", '_');
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
        gb.gridx = 1;
        gb.gridy = 5;
        gb.insets = new Insets(0,0,5,0);
        gb.ipadx = 30;
        gb.anchor = GridBagConstraints.WEST;
        terceraPestañaI.add(fechaVencimiento,gb);
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

    public JTextField getCampoRFCII() {
        return campoRFCII;
    }

    public JTextField getCampoNombreII() {
        return campoNombreII;
    }

    public JTextField getCampoTelefonoII() {
        return campoTelefonoII;
    }

    public JTextField getCampoDireccionII() {
        return campoDireccionII;
    }

    public JTextField getCampoCorreoII() {
        return campoCorreoII;
    }

    public JComboBox getComboTipoPersonaII() {
        return comboTipoPersonaII;
    }

    public JTextField getCampoContratoIC() {
        return campoContratoIC;
    }

    public JTextField getCampoRCFIC() {
        return campoRCFIC;
    }

    public JTextField getCampoMontoIC() {
        return campoMontoIC;
    }

    public JTextField getCampoContratoIP() {
        return campoContratoIP;
    }

    public JTextField getCampoPagareIP() {
        return campoPagareIP;
    }

    public JTextField getCampoVencimientoIP() {
        return campoVencimientoIP;
    }

    public JTextField getCampoRFCAI() {
        return campoRFCAI;
    }

    public JTextField getCampoTelefonoAI() {
        return campoTelefonoAI;
    }

    public JTextField getCampoDireccionAI() {
        return campoDireccionAI;
    }

    public JTextField getCampoCorreoAI() {
        return campoCorreoAI;
    }

    public JTextField getCampoContratoAC() {
        return campoContratoAC;
    }

    public JTextField getCampoMontoAC() {
        return campoMontoAC;
    }

    public JComboBox getComboStatusAC() {
        return comboStatusAC;
    }

    public JTextField getCampoPagareAP() {
        return campoPagareAP;
    }

    public JComboBox getComboTasa() {
        return comboTasa;
    }

    public JComboBox getComboTasaIP() {
        return comboTasaIP;
    }

    public JTextField getCampoPagareEP() {
        return campoPagareEP;
    }

    public JTextField getCampoRFCEI() {
        return campoRFCEI;
    }

    public JTextField getCampoContratoEC() {
        return campoContratoEC;
    }

    public JDateChooser getFechaVencimiento() {
        return fechaVencimiento;
    }

    public JTextField getCampoRFCIP() {
        return campoRFCIP;
    }

    public JTextField getCampoRFCAC() {
        return campoRFCAC;
    }

    public JTextField getCampoRFCAP() {
        return campoRFCAP;
    }

    public JTextField getCampoRFCEC() {
        return campoRFCEC;
    }

    public JTextField getCampoRFCEP() {
        return campoRFCEP;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
