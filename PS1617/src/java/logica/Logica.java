package logica;

import java.util.ArrayList;
import javax.ejb.Singleton;

@Singleton
public class Logica  {

    private ArrayList<User> users;
    private ArrayList<Jogo> jogos;

    public Logica() {
        users = new ArrayList<>();
        jogos = new ArrayList<>();
    }
   
    public boolean verificaLogin(String username, String password){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                if(password.equals(user.getPassword())){
                    if (user.isAtivo()) {
                        //Caso o utilizador já exista e tenha uma sessão ativa
                        return false;
                    } else {
                        //Caso o utilizador já exista e não tenha ainda uma sessão ativa
                        user.setAtivo(true);
                        return true;
                    }
                }
                else{
                    return false;
                }
            }
        }
        
        //Caso o utilizador ainda não exista
        users.add(new User(username, password, true));
        return true;
    }
    
   
    public void logout(String username){
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                user.setAtivo(false);
                return;
            }
        }
    }

  
    public int iniciarJogo(String criador) {
        Jogo j = new Jogo(criador);
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

    public Jogo getJogo(int id){
         for (Jogo jogo : jogos) {
            if (jogo.getId()==id) {
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
    
    public int getJogoCriadoAtualmente(String username){
        for (Jogo jogo : jogos) {
            if(jogo.getCriador().equals(username) && !jogo.isConcluido())
                return  jogo.getId();
        }
        return -1;
    }
    
     //-------------------------------------//-------------------------------//-----------------------
    
    int turno = 0; // 0 -> criador ; 1 -> participante
    
    //metodo joga-> recebe nome do jogador que jogou, e posicao 
    public boolean joga(String username, int pos){
        for(Jogo jogo : jogos){
            if(jogo.getCriador().equals(username)){
                if(turno == 0){
                    turno = 1;
                    //guarda jogada e verifica se jogo terminou
                    //....
                    return true;
                }
                else{
                    return false;
                }
            }
            else if(jogo.getParticipante().equals(username)){
                if(turno == 1){
                    turno = 0;
                    //guarda jogada e verifica se jogo terminou
                    //....
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }   
}
