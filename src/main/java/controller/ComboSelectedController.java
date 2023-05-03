package controller;

import view.ControlPane;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboSelectedController implements ItemListener {

    private ControlPane pane;

    public  ComboSelectedController(ControlPane pane){
        this.pane = pane;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox origen = (JComboBox) e.getSource();
        switch (origen.getName()){
            case "comboInversionista":
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selected = (String)e.getItem();
                    switch (selected){
                        case "Todos los clientes":
                            pane.enableComponents(1,false);
                            break;
                        case "RFC de un cliente":
                            pane.enableComponents(1,true);
                            break;
                    }
                }
                break;
            case "comboContrato":
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selected = (String)e.getItem();
                    switch (selected){
                        case "Todos los contratos":
                            pane.enableComponents(2,false);
                            break;
                        case "RFC de un cliente":
                            pane.enableComponents(2,true);
                            break;
                    }
                }
                break;
            case "comboPagare":
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selected = (String)e.getItem();
                    switch (selected){
                        case "Todos los pagares":
                            pane.enableComponents(3,false);
                            pane.enableComponents(4,false);
                            break;
                        case "RFC de un cliente":
                            pane.enableComponents(3,true);
                            pane.enableComponents(4,false);
                            break;
                        case "En un intervalo de fechas":
                            pane.enableComponents(3, false);
                            pane.enableComponents(4, true);
                            break;
                    }
                }
                break;
        }
    }
}
