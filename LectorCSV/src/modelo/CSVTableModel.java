/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author j4v13
 */
public class CSVTableModel extends AbstractTableModel {
    
    private String[] columnNames;
    private ArrayList<ArrayList<String>> lista;

    public CSVTableModel(String[] columnNames, ArrayList<ArrayList<String>> lista) {
        this.columnNames = columnNames;
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int x, int y) {
        try {
            return lista.get(x).get(y);
        } catch (Exception ex) {
            return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setLista(ArrayList<ArrayList<String>> lista) {
        this.lista = lista;
    }
    
}
