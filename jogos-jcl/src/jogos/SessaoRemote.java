/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogos;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author João
 */
@Remote
public interface SessaoRemote {

    void Login(String username, String password);

    void Logout();

    void criaJogo();

    void iniciaJogo(int id);

    ArrayList<Jogo> listaJogos(ArrayList<Jogo> jogos);
    
}
