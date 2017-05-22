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

  
    public void iniciarJogo(String criador) {
        jogos.add(new Jogo(criador));
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
    
    
}
