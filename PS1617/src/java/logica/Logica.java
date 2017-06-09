package logica;

import autenticacao.Util;
import entidades.Jogos;
import entidades.Users;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpSession;

@Singleton
public class Logica {

    @EJB
    private beans.UsersFacade ejbFacadeUsers;
    @EJB
    private beans.JogosFacade ejbFacadeJogos;
    private static int jogosId;
    private ArrayList<Users> users;
    private ArrayList<JogoLogica> jogos;

    public enum TipoJogo {
        JOGO_GALO,
        JOGO_QUATRO_EM_LINHA
    }
  
    public Logica() {
        users = new ArrayList<>();
        jogos = new ArrayList<>();
        jogosId=0;
    }
  
    public Users verificaLogin(String username, String password) {
        for (Users user : users) {
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

    public int iniciarJogo(Users criador, TipoJogo tipoJogo) {
        JogoLogica j;
        switch (tipoJogo) {
            case JOGO_GALO:
               j = new JogoGalo(criador);
               break;
            case JOGO_QUATRO_EM_LINHA:
            default:
                j = new JogoQuatroEmLinha(criador);
        }
        
        jogos.add(j);
       // ejbFacadeJogos.create((Jogos)j);
        // TODO user id do objeto criado
        jogosId++;
        return jogosId;
        //return j.getJogoId();
    }

    public void juntarJogo(int idJogo, Users participante) {
        for (JogoLogica jogo : jogos) {
            if (idJogo == jogo.getJogoId()) {
                jogo.setParticipante(participante);
                jogo.setEmEspera(false);
                return;
            }
        }
    }

    public JogoLogica getJogo(int id) {
        for (JogoLogica jogo : jogos) {
            if (jogo.getJogoId() == id) {
                return jogo;
            }
        }
        return null;
    }

    public ArrayList<JogoLogica> getJogosIniciados() {
        ArrayList<JogoLogica> jogosEmEspera = new ArrayList<>();
        for (JogoLogica jogo : jogos) {
            if (jogo.isEmEspera()) {
                jogosEmEspera.add(jogo);
            }
        }

        return jogosEmEspera;
    }

    public ArrayList<JogoLogica> getJogosDecorrer() {
        ArrayList<JogoLogica> jogosDecorrer = new ArrayList<>();
        for (JogoLogica jogo : jogos) {
            if (!jogo.isEmEspera()) {
                jogosDecorrer.add(jogo);
            }
        }

        return jogosDecorrer;
    }

    public boolean fazJogada(int idJogo, Users por, String jogada) {
        for (JogoLogica jogo : jogos) {
            if (idJogo == jogo.getJogoId()) {
                return jogo.avaliaJogada(por, jogada);
            }
        }

        return false;
    }

    public boolean terminaJogo(int idJogo) {
        for (JogoLogica jogo : jogos) {
            if (idJogo == jogo.getJogoId()) {
                return jogo.terminaJogo();
            }
        }

        return false;
    }

    public ArrayList<String> listarAtivos() {
        ArrayList<String> utilizadoresAtivos = new ArrayList<>();
        for (Users user : users) {
            if (user.isAtivo()) {
                utilizadoresAtivos.add(user.getUsername());
            }
        }

        return utilizadoresAtivos;
    }

    public int getJogoCriadoAtualmente(String username) {
        for (JogoLogica jogo : jogos) {
            if (jogo.getCriador().equals(username) && !jogo.isConcluido()) {
                return jogo.getJogoId();
            }
        }
        return -1;
    }
    
    public boolean jogoTerminado(int idJogo) {
        for (JogoLogica jogo : jogos) {
            if (idJogo == jogo.getJogoId()) {
                return jogo.isConcluido();
            }
        }

        return false;
    }
    
    public boolean verificaJogadorExiste(String username){
        for (Users user : users) {
            if (username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }
    
    public void registaJogador(String username,String password,String email,String morada){
        Users u = new Users(username, password, email, morada, false);
        users.add(u);
        try {
            ejbFacadeUsers.create(u);
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
    }
    
    public String getPassword(String username){
        for (Users user : users) {
            if (username.equals(user.getUsername())) {
                return user.getPassword();
            }
        }
        return "";
    }
    
    public void alterarPassword(String username, String password){
        for (Users user : users) {
            if (username.equals(user.getUsername())) {
                user.setPassword(password);
                return;
            }
        }
    }
    
    public void alterarEmail(String username, String email){
        for (Users user : users) {
            if (username.equals(user.getUsername())) {
                user.setEmail(email);
                return;
            }
        }
    }
    
    public void alterarMorada(String username, String morada){
        for (Users user : users) {
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
        for (JogoLogica jogo : jogos) {
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

    public boolean termina(Users username, int pos) {
        for (JogoLogica jogo : jogos) {
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
        for (Users user : users) {
            if (username.equals(user.getUsername())) {
                if (user.isAtivo()) {
                    return true;
                }
            }
        }
        return false;
    }

}
