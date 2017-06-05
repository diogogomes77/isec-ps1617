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
import logica.Sessao;

/**
 *
 * @author João
 */
@ManagedBean
@SessionScoped
public class GestaoAlterar implements Serializable {

    @EJB
    Sessao session;
    
    @EJB
    Logica logica;
    
    String password;
    String repassword;
    String email;
    String morada;
    
    String mensagem;
    
    public GestaoAlterar() {
        this.password = "";
        this.repassword = "";
        this.email = "";
        this.morada = "";
        this.mensagem = "";
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public void alterar(){
        if(!password.equals("") && !repassword.equals("")){
            if(repassword.equals(logica.getPassword(session.getUsername()))){
                logica.alterarPassword(session.getUsername(), password);
                mensagem = "";
            }
            else{
                mensagem = "Password velha esta errada";
            }
        }
        
        if(!email.equals("")){
            logica.alterarEmail(session.getUsername(), email);
            email="";
        }
        
        if(!morada.equals("")){
            logica.alterarMorada(session.getUsername(), morada);
            morada="";
        }
    }
    
}
