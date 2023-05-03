package view;

import com.toedter.calendar.JDateChooser;
import controller.ComboSelectedController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ControlPane extends JFrame{

    public ControlPane(){
        initComponents();
        addComboEvent();
        this.setTitle("Panel de control");
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    ComboSelectedController comboController;
    public void addComboEvent(){
        comboController = new ComboSelectedController(this);
        comboConsultarPorInversionistas.addItemListener(comboController);
        comboConsultarPorContratos.addItemListener(comboController);
        comboConsultarPorPagares.addItemListener(comboController);
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

    private void initComponents(){
        this.setContentPane(panelBorde);
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
        DefaultTableModel modelInversionista = (DefaultTableModel) tablaInversionistas.getModel();
        modelInversionista.addColumn("RFC");
        modelInversionista.addColumn("Nombre");
        modelInversionista.addColumn("Telefono");
        modelInversionista.addColumn("Dirección");
        modelInversionista.addColumn("Email");
        modelInversionista.addColumn("Tipo persona");
        tablaInversionistas.updateUI();

        DefaultTableModel modelContrato = (DefaultTableModel) tablaContratos.getModel();
        modelContrato.addColumn("Clave contrato");
        modelContrato.addColumn("Clave sucursal");
        modelContrato.addColumn("RFC");
        modelContrato.addColumn("Monto total");
        modelContrato.addColumn("Status");
        tablaContratos.updateUI();

        DefaultTableModel modelPagare = (DefaultTableModel) tablaPagares.getModel();
        modelPagare.addColumn("Clave Pagare");
        modelPagare.addColumn("Clave contrato");
        modelPagare.addColumn("Clave sucursal");
        modelPagare.addColumn("Tipo de tasa");
        modelPagare.addColumn("Fecha emisión");
        modelPagare.addColumn("Fecha vencimiento");
        tablaPagares.updateUI();
    }

    public void addDateChooser(){
        fechaInicial = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        fechaFinal = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
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

    public JTextField getCampoRFCInversionistas(){
        return campoRFCInversionistas;
    }
}
