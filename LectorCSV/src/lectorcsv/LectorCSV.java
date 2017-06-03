/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lectorcsv;

import controlador.Controlador;
import gui.MainForm;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author j4v13
 */
public class LectorCSV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        new MainForm();
//        Controlador c = new Controlador(new File("denue.csv"));
//        String[] s = c.getColumnNames();
//        for (String string : s) {
//            System.out.println(string);
//        }
//        c.insertar();
    }
    
}
