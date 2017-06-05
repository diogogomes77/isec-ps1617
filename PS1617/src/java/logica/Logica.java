package logica;

import autenticacao.Util;
import java.util.ArrayList;
import javax.ejb.Singleton;
import javax.servlet.http.HttpSession;

@Singleton
public class Logica {

    private ArrayList<User> users;
    private ArrayList<Jogo> jogos;

    public enum TipoJogo {
        JOGO_GALO,
        JOGO_QUATRO_EM_LINHA
    }
  
    public Logica() {
        users = new ArrayList<>();
        jogos = new ArrayList<>();
    }
  
    public User verificaLogin(String username, String password) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                if (password.equals(user.getPassword())) {
                    if (user.isAtivo()) {
                        //Caso o utilizador já exista e tenha uma sessão ativa
                        // TODO: terminar outras sessoes do user
                          HttpSession session = Util.getSession();
                            user.getSession().invalidate();
                            user.setSession(session);
                      
                    } else {
                        //Caso o utilizador já exista e não tenha ainda uma sessão ativa
                        user.setAtivo(true);
                       
                    }
                    return user;
                } else {
                    return null;
                }
            }
        }

        //Caso o utilizador ainda não exista
        return null;
    }

    /*public void logout(String username) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                user.setAtivo(false);
                return;
            }
        }
    }*/

    public int iniciarJogo(String criador, TipoJogo tipoJogo) {
        Jogo j;
        switch (tipoJogo) {
            case JOGO_GALO:
               j = new JogoGalo(criador);
               break;
            case JOGO_QUATRO_EM_LINHA:
            default:
                j = new JogoQuatroEmLinha(criador);
        }
        
        jogos.add(j);
        return j.getId();
    }

    public void juntarJogo(int idJogo, String participante) {
        for (Jogo jogo : jogos) {
            if (idJogo == jogo.getId()) {
                jogo.setParticipante(participante);
                jogo.setEmEspera(false);
                return;
            }
        }
    }

    public Jogo getJogo(int id) {
        for (Jogo jogo : jogos) {
            if (jogo.getId() == id) {
                return jogo;
            }
        }
        return null;
    }

    public ArrayList<Jogo> getJogosIniciados() {
        ArrayList<Jogo> jogosEmEspera = new ArrayList<>();
        for (Jogo jogo : jogos) {
            if (jogo.isEmEspera()) {
                jogosEmEspera.add(jogo);
            }
        }

        return jogosEmEspera;
    }

    public ArrayList<Jogo> getJogosDecorrer() {
        ArrayList<Jogo> jogosDecorrer = new ArrayList<>();
        for (Jogo jogo : jogos) {
            if (!jogo.isEmEspera()) {
                jogosDecorrer.add(jogo);
            }
        }

        return jogosDecorrer;
    }

    public boolean fazJogada(int idJogo, String por, String jogada) {
        for (Jogo jogo : jogos) {
            if (idJogo == jogo.getId()) {
                return jogo.avaliaJogada(por, jogada);
            }
        }

        return false;
    }

    public boolean terminaJogo(int idJogo) {
        for (Jogo jogo : jogos) {
            if (idJogo == jogo.getId()) {
                return jogo.terminaJogo();
            }
        }

        return false;
    }

    public ArrayList<String> listarAtivos() {
        ArrayList<String> utilizadoresAtivos = new ArrayList<>();
        for (User user : users) {
            if (user.isAtivo()) {
                utilizadoresAtivos.add(user.getUsername());
            }
        }

        return utilizadoresAtivos;
    }

    public int getJogoCriadoAtualmente(String username) {
        for (Jogo jogo : jogos) {
            if (jogo.getCriador().equals(username) && !jogo.isConcluido()) {
                return jogo.getId();
            }
        }
        return -1;
    }
    
    public boolean jogoTerminado(int idJogo) {
        for (Jogo jogo : jogos) {
            if (idJogo == jogo.getId()) {
                return jogo.isConcluido();
            }
        }

        return false;
    }
    
    public boolean verificaJogadorExiste(String username){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }
    
    public void registaJogador(String username,String password,String email,String morada){
        users.add(new User(username, password, email, morada, false));
    }
    
    public String getPassword(String username){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return user.getPassword();
            }
        }
        return "";
    }
    
    public void alterarPassword(String username, String password){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                user.setPassword(password);
                return;
            }
        }
    }
    
    public void alterarEmail(String username, String email){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                user.setEmail(email);
                return;
            }
        }
    }
    
    public void alterarMorada(String username, String morada){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                user.setMorada(morada);
                return;
            }
        }
    }

    //-------------------------------------//-------------------------------//-----------------------
    int turno = 0; // 0 -> criador ; 1 -> participante

    //metodo joga-> recebe nome do jogador que jogou, e posicao 
    public boolean joga(String username, int pos) {
        for (Jogo jogo : jogos) {
            if (jogo.getCriador().equals(username)) {
                if (turno == 0) {
                    if (!jogo.isConcluido()) {
                        turno = 1;
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (jogo.getParticipante().equals(username)) {
                if (turno == 1) {
                    if (!jogo.isConcluido()) {
                        turno = 0;
                        return true;
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean termina(String username, int pos) {
        for (Jogo jogo : jogos) {
            if (jogo.getCriador().equals(username)) {
                if (turno == 0) {
                    if (!jogo.isConcluido()) {
                        jogo.terminaTemp(username, pos);
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (jogo.getParticipante().equals(username)) {
                if (turno == 1) {
                    if (!jogo.isConcluido()) {
                        jogo.terminaTemp(username, pos);
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isAtivo(String username) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                if (user.isAtivo()) {
                    return true;
                }
            }
        }
        return false;
    }

}
