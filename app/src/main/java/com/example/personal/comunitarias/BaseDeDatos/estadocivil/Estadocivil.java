/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.personal.comunitarias.BaseDeDatos.estadocivil;

/**
 *
 * @author Kattya Desiderio
 */
public class Estadocivil {
    int estadocivil; //pk
    String nombre;

    public Estadocivil() {
    }

    public int getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(int estadocivil) {
        this.estadocivil = estadocivil;
    }

    public Estadocivil(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
