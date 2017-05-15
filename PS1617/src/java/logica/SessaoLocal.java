
package logica;

import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface SessaoLocal {
    void Login(String username, String password);

    void Logout();

    void criaJogo();

    void iniciaJogo(int id);

    ArrayList<Jogo> listaJogos(ArrayList<Jogo> jogos);

    boolean fazJogada(int idJogo,String jogada);

    boolean terminaJogo(int idJogo);

    String getUsername();
}
