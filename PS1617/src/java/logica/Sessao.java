
package logica;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateful;


@Stateful
public class Sessao implements SessaoLocal {

    String username;
    String password;
    @EJB
    LogicaLocal lo;

    public Sessao() {
        username=null;
        password=null;
    }
    
    @Override
    public void Login(String username, String password) {
        this.username = username;
        this.password = password;
        lo.Login(username, password);
    }

    @Override
    public void Logout() {
        lo.Logout(username);
        this.username = null;
        this.password = null;
    }

    @Override
    public void criaJogo() {
        lo.criarJogo(password);
    }

    @Override
    public void iniciaJogo(int id) {
        lo.IniciarJogo(id, username);
    }

    @Override
    public ArrayList<Jogo> listaJogos(ArrayList<Jogo> jogos) {
        return jogos;
    }

    @Override
    public boolean fazJogada(int idJogo,String jogada) {
        return lo.fazJogada(idJogo, username, jogada);
    }

    @Override
    public boolean terminaJogo(int idJogo) {
        return lo.terminaJogo(idJogo);
    }

    @Override
    public String getUsername() {
        return username;
    }
}
