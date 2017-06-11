/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author diogo
 */
public enum EnumEstado {
    
    
    INICIADO(0),
    ESPERA(1), 
    DECORRER(2), 
    CONCLUIDO(3);
    
    private final int value;
    
    
    EnumEstado(final int newValue) {
        value = newValue;
    }
        
    public int getValue(){
        return value;
    }
    
}
