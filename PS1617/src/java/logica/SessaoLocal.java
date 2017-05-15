/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author João
 */
@Local
public interface SessaoLocal {
    void Login(String username, String password);

    void Logout();

    void criaJogo();

    void iniciaJogo(int id);

    ArrayList<jogos.Jogo> listaJogos(ArrayList<jogos.Jogo> jogos);

    boolean fazJogada(int idJogo,String jogada);

    boolean terminaJogo(int idJogo);
}
