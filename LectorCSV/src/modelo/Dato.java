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
public class Dato implements Comparable<Dato>, Serializable{
    
    private Integer id;
    private Integer posicion;   

    public Dato(Integer id, Integer posicion) {
        this.id = id;
        this.posicion = posicion;
    }   

    public Dato(Integer id) {
        this.id = id;
    }
    
    public Dato() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "Dato{" + "id=" + id + ", posicion=" + posicion + '}';
    }

    @Override
    public int compareTo(Dato t) {
        return this.id.compareTo(t.getId());
    }
    
}
