/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dao;

import java.util.Objects;

/**
 *
 * @author Gad
 */
public class ApacBaseDTO {
    
    private String llave;
    private String valor;
    private String digestion;
    private Long ultimaActualizacion;

    public ApacBaseDTO() {
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDigestion() {
        return digestion;
    }

    public void setDigestion(String digestion) {
        this.digestion = digestion;
    }

    public Long getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Long ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    @Override
    public String toString() {
        return "ApacBaseDTO{" + "llave=" + llave + ", valor=" + valor + ", digestion=" + digestion + ", ultimaActualizacion=" + ultimaActualizacion + '}';
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApacBaseDTO other = (ApacBaseDTO) obj;
        if (!Objects.equals(this.llave, other.llave)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.digestion, other.digestion)) {
            return false;
        }
        if (!Objects.equals(this.ultimaActualizacion, other.ultimaActualizacion)) {
            return false;
        }
        return true;
    }
    
    
}
