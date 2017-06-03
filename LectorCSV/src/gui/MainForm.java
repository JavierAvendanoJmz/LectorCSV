/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import modelo.CSVTableModel;

/**
 *
 * @author j4v13
 */
public class MainForm extends JFrame {
    
    private Controlador controlador;
    private JTable tblCSV;
    private JTextField txtBuscar;
    private JButton btnBuscar;

    public MainForm() {
        super("Lector CSV");
        super.setSize(1280, 720);
        super.setLayout(new BorderLayout());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.add(menu(), BorderLayout.NORTH);        
        super.add(pnlBuscar(), BorderLayout.SOUTH);
        super.setVisible(true);  
    }
    
    private JMenuBar menu() {
        JMenuBar mainMenu = new JMenuBar();    
        
        JMenu mmArchivo = new JMenu("Archivo");  
        JMenuItem mnAbrir = new JMenuItem("Abrir");   
        JMenuItem mnSalir = new JMenuItem("Salir");   
        
        JMenu mmAyuda = new JMenu("Ayuda");
        JMenuItem mnAcercaDe =  new JMenuItem("Acerca de");
        
         mnAbrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(MainForm.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {   
                    try {
                        File file = fc.getSelectedFile();
                        controlador = new Controlador(file);                        
                        controlador.leerIndice();
                        MainForm.super.add(pnlTable(), BorderLayout.CENTER);
                        MainForm.super.setVisible(true);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }                
            }
        });
        
        mnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                MainForm.this.dispose();
            }
        });
        
        mnAcercaDe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){                
                JOptionPane.showMessageDialog(MainForm.this, "Software Hecho por Javier.",
                        "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        });
                
        mmArchivo.add(mnAbrir);
        mmArchivo.add(mnSalir);
        
        mmAyuda.add(mnAcercaDe);
        
        mainMenu.add(mmArchivo); 
        mainMenu.add(mmAyuda);                               
        
        return mainMenu;
    }
    
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
}
    
    private JPanel pnlTable() throws IOException {
        JPanel pnlTable = new JPanel();
        pnlTable.setLayout(new BorderLayout());
        
        CSVTableModel csvModel = 
                new CSVTableModel(controlador.getColumnNames(),controlador.getDatos());
        JTable tblCSV = new JTable(csvModel);
        
        resizeColumnWidth(tblCSV);
        tblCSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);     
        
        JScrollPane js = new JScrollPane(tblCSV);                
        js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        js.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent ae) {
                int extent = js.getVerticalScrollBar().getModel().getExtent();
                if(js.getVerticalScrollBar().getValue()+extent == 
                        js.getVerticalScrollBar().getMaximum()) {
                    try {
                        csvModel.setLista(controlador.getDatos());
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });      
        
        pnlTable.add(js);
        return pnlTable;
    }
    
    private JPanel pnlBuscar() {
        JPanel pnlBuscar = new JPanel();
        pnlBuscar.setLayout(new FlowLayout());
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar id");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JTextArea txtArea = new JTextArea(20, 40);
                try {
                    ArrayList<String> busqueda = (controlador.buscar(Integer.parseInt(txtBuscar.getText())));
                    for (String s : busqueda) {
                        if(s.length()>0)
                            txtArea.append(s+'\n');
                    }
                    JOptionPane.showMessageDialog(MainForm.this, new JScrollPane(txtArea));
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        pnlBuscar.add(txtBuscar);
        pnlBuscar.add(btnBuscar);
        return pnlBuscar;
    }
    
    
}
