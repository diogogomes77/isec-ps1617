/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.Jogadas;
import entidades.Users;
import java.util.List;

/**
 *
 * @author diogo
 */
public interface JogoInterface {
    
    Users getCriador();
   Users getParticipante();
    void adicionaJogada(Jogadas j);
    List<Jogadas> getJogadasList();
    int getJogoId();
    void setParticipante(Users u);
    void setEmEspera(Boolean b);
    boolean avaliaJogada(Users u,String j);
    boolean terminaJogo();
    boolean isConcluido();
    boolean isEmEspera();
    boolean terminaTemp(Users u,int pos);
}
