package logica;

import java.util.ArrayList;
import javax.ejb.Singleton;

@Singleton
public class Logica implements LogicaLocal {

    private ArrayList<User> users;
    private ArrayList<Jogo> jogos;

    public Logica() {
        users = new ArrayList<>();
        jogos = new ArrayList<>();
    }
    
    @Override
    public boolean verificaLogin(String username, String password){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                if (user.isAtivo()) {
                    //Caso o utilizador já exista e tenha uma sessão ativa
                    return false;
                } else {
                    //Caso o utilizador já exista e não tenha ainda uma sessão ativa
                    user.setAtivo(true);
                    return true;
                }
            }
        }
        
        //Caso o utilizador ainda não exista
        users.add(new User(username, password, true));
        return true;
    }
    
    @Override
    public void Logout(String username){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                user.setAtivo(false);
                return;
            }
        }
    }

    @Override
    public void iniciarJogo(String criador) {
        jogos.add(new Jogo(criador));
    }

    @Override
    public void juntarJogo(int idJogo, String participante) {
        for (Jogo jogo : jogos) {
            if (idJogo == jogo.getId()) {
                jogo.setParticipante(participante);
                jogo.setEmEspera(false);
                return;
            }
        }
    }

    @Override
    public ArrayList<Jogo> listarJogos() {
        ArrayList<Jogo> jogosEmEspera = new ArrayList<>();
        for (Jogo jogo : jogos) {
            if (jogo.isEmEspera()) {
                jogosEmEspera.add(jogo);
            }
        }
        
        return jogosEmEspera;
    }

    @Override
    public boolean fazJogada(int idJogo, String por, String jogada) {
        for (Jogo jogo : jogos) {
            if (idJogo == jogo.getId()) {
                return jogo.avaliaJogada(por, jogada);
            }
        }
        
        return false;
    }

    @Override
    public boolean terminaJogo(int idJogo) {
        for (Jogo jogo : jogos) {
            if (idJogo == jogo.getId()) {
                return jogo.terminaJogo();
            }
        }
        
        return false;
    }

    @Override
    public ArrayList<String> listarAtivos() {
        ArrayList<String> utilizadoresAtivos = new ArrayList<>();
        for (User user : users) {
            if (user.isAtivo()) {
                utilizadoresAtivos.add(user.getUsername());
            }
        }
        
        return utilizadoresAtivos;
    }
    
    
}
