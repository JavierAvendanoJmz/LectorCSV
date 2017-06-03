/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import modelo.Arbol;
import modelo.Dato;

/**
 *
 * @author j4v13
 */
public class Controlador {
    
    private File file;
    private FileReader fr;
    private BufferedReader buffer;    
    private Arbol<Dato> indice;
    private ArrayList<ArrayList<String>> datos;
    private int n;

    public Controlador(File file) throws FileNotFoundException {
        this.file = file;
        this.fr = new FileReader(file);
        this.buffer = new BufferedReader(fr);
        datos = new ArrayList();
        n = 0;
    }                
    
    private void cerrar() throws IOException {
        this.buffer.close();
        this.fr.close();
    }
    
    private void reiniciar() throws IOException {
        cerrar();
        this.fr = new FileReader(file);
        this.buffer = new BufferedReader(fr);
    }        
    
    public void imprimir() {
        indice.imprimir();
    }
    
    public void leerIndice() throws IOException, ClassNotFoundException {
        File f = new File(file.getName()+".ind");
        if(f.exists()) {
            FileInputStream stream = new FileInputStream(f);
            ObjectInputStream input = new ObjectInputStream(stream);
            this.indice = (Arbol)input.readObject();
            input.close();
            stream.close();
        } else {
            insertar();
            crearIndice();
            reiniciar();
        }
    }
    
    private void crearIndice() throws FileNotFoundException, IOException {
        File f = new File(file.getName()+".ind");
        FileOutputStream stream = new FileOutputStream(f);
        ObjectOutputStream output = new ObjectOutputStream(stream);
        output.writeObject(indice);
        output.close();
        stream.close();
    }
    
    private ArrayList<String> limpiar(String fila) {
        ArrayList<String> registro = new ArrayList();
        String s = fila;
        s = s.replaceAll(String.valueOf('"') +
                "," + String.valueOf('"'), "~");
        s = s.replaceAll(String.valueOf('"'), "");
        String[] dividido = s.split("~");        
        for (String campo : dividido) {
            if (campo.contains(",,") && !campo.contains(", ")) {
                String[] aux = campo.split(",");
                registro.addAll(Arrays.asList(aux));
            } else if (campo.contains(",,")) {
                String[] aux = campo.split(",,");
                registro.add(aux[0]);
                registro.add("");
                registro.add(aux[1]);
            } else
                registro.add(campo);
        }
        return registro;
    }
    
    public String[] getColumnNames() throws IOException {   
        reiniciar();
        String s = this.buffer.readLine();
        ArrayList<String> l = new ArrayList(limpiar(s));
        String[] columnNames = new String[l.size()];
        for (int i = 0; i < l.size(); i++){            
            columnNames[i] = l.get(i);
        }
        return columnNames;
    }
    
    public ArrayList<ArrayList<String>> getDatos( ) throws IOException {
        try {
            n += 50;
            String registro = this.buffer.readLine();
            int i = 0;
            while(i < n) {
                registro = this.buffer.readLine();
                datos.add(limpiar(registro));
                i++;
            }
        } catch(Exception ex) {
        }      
        return datos;
    }
    
    public void insertar() throws IOException {
        this.indice = new Arbol();
        String registro = this.buffer.readLine();        
        int posicion = registro.length();
        while (buffer.ready()) {
            registro = this.buffer.readLine();
            Integer id = Integer.valueOf(limpiar(registro).get(0));
            Dato r = new Dato(id, posicion+1);
            indice.agregar(r);
            posicion += registro.length()+1;
        }
    }
    
    public ArrayList<String> buscar(Integer id) throws IOException {
        FileReader auxfr = new FileReader(file);
        BufferedReader auxbf = new BufferedReader(auxfr);
        Dato d = indice.buscarDato(new Dato(id));
        if(d != null) {
            Integer i = d.getPosicion();
            auxbf.skip(i);
            String s = auxbf.readLine();
            return limpiar(s);
        }        
        auxbf.close();
        auxfr.close();
        return null;
    }    
    
}
