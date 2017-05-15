
package logica;

import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface LogicaLocal {
    boolean verificaLogin(String username, String password);
    
    void Logout(String username);
    
    void criarJogo(String criador);

    void IniciarJogo(int idJogo, String participante);

    ArrayList<Jogo> listarJogos();

    boolean fazJogada(int idJogo, String por, String jogada);

    boolean terminaJogo(int idJogo);

    ArrayList<String> listarAtivos();
}
