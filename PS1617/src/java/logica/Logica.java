/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import javax.ejb.Singleton;

/**
 *
 * @author João
 */
@Singleton
public class Logica implements LogicaLocal {

    private ArrayList<Users> users;
    private ArrayList<Jogo> jogos;

    public Logica() {
        users = new ArrayList<>();
        jogos = new ArrayList<>();
    }
    
    @Override
    public void Login(String username, String password){
        for(int i=0;i<users.size();i++){
            if(username.equals(users.get(i).getUsename())){
                if(users.get(i).isAtivo()){
                    //Caso o utilizador já exista e tenha uma sessão ativa
                }
                else{
                    //Caso o utilizador já exista e não tenha ainda uma sessão ativa
                    users.get(i).isAtivo();
                }
            }
        }
        //Caso o utilizador ainda não exista
        users.add(new Users(username, password, true));
    }
    
    @Override
    public void Logout(String username){
        for(int i=0;i<users.size();i++){
            if(!username.equals(users.get(i).getUsename())){
                users.get(i).setAtivo(false);
                return;
            }
        }
    }

    @Override
    public void criarJogo(String criador) {
        jogos.add(new Jogo(criador));
    }

    @Override
    public void IniciarJogo(int idJogo, String participante) {
        for(int i=0;i<jogos.size();i++){
            if(jogos.get(i).getId()==idJogo){
                jogos.get(i).setParticipante(participante);
                jogos.get(i).setEmEspera(false);
            }
        }
    }

    @Override
    public ArrayList<Jogo> listarJogos() {
        ArrayList<Jogo> jogosEmEspera = new ArrayList<>();
        for(int i=0;i<jogos.size();i++){
            if(jogos.get(i).isEmEspera()==true){
                jogosEmEspera.add(jogos.get(i));
            }
        }
        return jogosEmEspera;
    }

    @Override
    public boolean fazJogada(int idJogo, String por, String jogada) {
        for(int i=0;i<jogos.size();i++){
            if(jogos.get(i).getId()==idJogo){
                return jogos.get(i).avaliaJogada(por, jogada);
            }
        }
        return false;
    }

    @Override
    public boolean terminaJogo(int idJogo) {
        for(int i=0;i<jogos.size();i++){
            if(jogos.get(i).getId()==idJogo){
                return jogos.get(i).terminaJogo();
            }
        }
        return false;
    }
}
