/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author j4v13
 */
public class Arbol<T extends Comparable<T> & Serializable> implements Serializable {
    
    private Hoja<T> raiz;   
    private String diccionario;

    public Arbol() {
    }
    
    public Hoja<T> getRaiz() {
        return raiz;
    }   
    
    public void agregar(T t) {
        Hoja<T> h = new Hoja(t);
        if(raiz == null)
            raiz = h;
        else {
            Hoja<T> aux = raiz;
            boolean f = false;            
            while(!f) {
                if(aux.getDato().compareTo(t) >= 0) {
                    if(aux.getIzquierda()!=null) {
                        aux = aux.getIzquierda();
                    } else {
                        aux.setIzquierda(h);
                        f = true;
                    }
                } else {
                    if(aux.getDerecha()!=null) {
                        aux = aux.getDerecha();
                    } else {
                        aux.setDerecha(h);
                        f = true;
                    }
                }
            }       
        }
    }
    
    public T buscarDato(T t) {
        if(this.raiz != null) {
            Hoja<T> aux = this.raiz;                    
            while(aux != null && aux.getDato().compareTo(t) != 0) {
                if(aux.getDato().compareTo(t) >= 0) {
                    aux = aux.getIzquierda();
                } else {
                    aux = aux.getDerecha();
                }
            }
            return aux.getDato();
        }
        return null;
    }
    
    private void imprimir(Hoja<T> t) {
        if(t!=null) {
            imprimir(t.getIzquierda());
            System.out.println(t);
            imprimir(t.getDerecha());            
        }
    }
    
    public void imprimir() {
        imprimir(this.raiz);
    }        

//    @Override
//    public String toString() {
//        return raiz.getDato().toString();
//    }

//    @Override
//    public int compareTo(Arbol t) {
//        return this.raiz.compareTo(t.getRaiz());
//    }
    
}
