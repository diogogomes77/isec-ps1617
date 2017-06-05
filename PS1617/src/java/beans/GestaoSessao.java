/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import autenticacao.Util;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import logica.Logica;
import logica.Sessao;
import logica.User;


@ManagedBean
@SessionScoped
public class GestaoSessao implements Serializable{
    @EJB
    Sessao sessao;
    
    @EJB
    Logica logica;
    
    String username;
    String password;
    
    String mensagem;
    
    String s;

    public GestaoSessao() {
    }
    
    public GestaoSessao(String username, String password) {
        this.username = "";
        this.password = "";
        this.mensagem = "";
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String bemvindo(){
        return sessao.getUsername();
    }
    
    public String login(){
        if(!username.equals("") && !password.equals("")){
            System.out.println("-----Tenta reconhecer login --");
            User user =logica.verificaLogin(username, password);
            
            if(user!=null){
                mensagem = "";
                System.out.println("-----login reconhecido --");
                sessao.login(user);
                
                sessao.setJogoId(logica.getJogoCriadoAtualmente(username));
                if(redirectQuandoJogoIniciado())
                    return "/area_privada/jogo?faces-redirect=true";
                else
                    return "/area_privada/gestaojogos?faces-redirect=true";
            }
            else{
                mensagem = "Erro de autentificacao";
                return "/login?faces-redirect=true";
            }
        }
        mensagem = "Campo nao preenchido";
        System.out.println("-----login vazio --");
        return "/login?faces-redirect=true";
    }
    
    public boolean redirectQuandoJogoIniciado(){
        if(sessao.getJogoId()==-1)
            return false;
        if(!logica.getJogo(sessao.getJogoId()).isEmEspera() && !logica.getJogo(sessao.getJogoId()).isConcluido())
           return true;
        else
            return false;
    }
    
    public String logout(){
        sessao.logout();
        return "/login?faces-redirect=true";
    }
    
    public ArrayList<String> listaAtivos(){
        return logica.listarAtivos();
    }
}
