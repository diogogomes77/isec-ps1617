/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Login implements Serializable{
    @EJB
    SessaoLocal sessao;
    
    @EJB
    LogicaLocal logica;
    
    String username;
    String password;
    
    String s;

    public Login() {
    }
    
    public Login(String username, String password) {
        this.username = "";
        this.password = "";
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
    
    public String bemvindo(){
        return sessao.getUsername();
    }
    
    public String login(){
        if(!username.equals("") && !password.equals("")){
            if(logica.verificaLogin(username, password)){
                sessao.login(username, password);
                return "escolherJogo";
            }
        }
        return "login";
    }
    
    public ArrayList<String> listaAtivos(){
        return logica.listarAtivos();
    }
}
