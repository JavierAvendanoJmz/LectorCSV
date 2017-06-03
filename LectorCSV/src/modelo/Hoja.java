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
public class Hoja<T extends Comparable<T> & Serializable> implements Serializable {
    
    private T dato;
    private Hoja izquierda;
    private Hoja derecha;

    public Hoja() {
    }

    public Hoja(T dato) {
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Hoja getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Hoja izquierda) {
        this.izquierda = izquierda;
    }

    public Hoja getDerecha() {
        return derecha;
    }

    public void setDerecha(Hoja derecha) {
        this.derecha = derecha;
    }

    @Override
    public String toString() {
        return "Nodo{" + "dato=" + dato + ", izquierda=" + (izquierda != null) +
                ", derecha=" + (derecha != null) + '}';
    }        
    
}
