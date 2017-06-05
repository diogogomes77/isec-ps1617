/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import logica.Logica;

/**
 *
 * @author João
 */
@ManagedBean
@SessionScoped
public class GestaoRegisto implements Serializable {

    @EJB
    Logica logica;
    
    String username;
    String password;
    String repassword;
    String email;
    String morada;
    
    public GestaoRegisto() {
        this.username = "";
        this.password = "";
        this.repassword = "";
        this.email = "";
        this.morada = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
    
    public void registar(){
        if(password.equals(repassword) && !logica.verificaJogadorExiste(username)){
            logica.registaJogador(username,password,email,morada);
            this.username = "";
            this.password = "";
            this.repassword = "";
            this.email = "";
            this.morada = "";
        }
    }
}
