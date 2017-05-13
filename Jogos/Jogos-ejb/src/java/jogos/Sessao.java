/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogos;

import java.util.ArrayList;
import javax.ejb.Stateful;

/**
 *
 * @author João
 */
@Stateful
public class Sessao implements SessaoRemote {

    String username;
    String password;
    LogicaLocal lo;

    public Sessao() {
        username=null;
        password=null;
    }

    @Override
    public void Login(String username, String password) {
        if(username==null){
            this.username = username;
            this.password = password;
            lo.Login(username, password);
        }
    }

    @Override
    public void Logout() {
        if(username!=null){
            lo.Logout(username);
            this.username = null;
            this.password = null;
        }
    }

    @Override
    public void criaJogo() {
        if(username!=null){
            lo.criarJogo(password);
        }
    }

    @Override
    public void iniciaJogo(int id) {
        if(username!=null){
            lo.IniciarJogo(id, username);
        }
    }

    @Override
    public ArrayList<Jogo> listaJogos(ArrayList<Jogo> jogos) {
        return jogos;
    }
    
    
}
