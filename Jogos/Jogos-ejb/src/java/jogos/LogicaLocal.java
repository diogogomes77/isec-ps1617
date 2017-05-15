/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogos;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author João
 */
@Local
public interface LogicaLocal {

    void Login(String username, String password);
    
    void Logout(String username);
    
    void criarJogo(String criador);

    void IniciarJogo(int idJogo, String participante);

    ArrayList<Jogo> listarJogos();

    boolean fazJogada(int idJogo, String por, String jogada);

    boolean terminaJogo(int idJogo);
    
}
