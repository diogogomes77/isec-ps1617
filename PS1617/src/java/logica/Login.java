package logica;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author João
 */
@Named(value = "login")
@SessionScoped
public class Login {
    @EJB
    SessaoLocal sessao;
    String username;
    String password;

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
    
    public String login(){
        if(!username.equals("") && !password.equals("")){
            sessao.Login(username, password);
            return "escolherJogo";
        }
        return "";
    }
}
