/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.Jogadas;
import entidades.Jogos;
import entidades.Users;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diogo
 */
public interface InterfaceJogo {
    
    Users getCriador();
   Users getParticipante();
    void adicionaJogada(Jogadas j);
    List<Jogadas> getJogadasList();
    Integer getJogoId();
    void setParticipante(Users u);
    void setEmEspera(Boolean b);
    boolean avaliaJogada(Users u,String j);
    boolean terminaJogo();
    boolean isConcluido();
    boolean isEmEspera();
    boolean terminaTemp(Users u,int pos);
    void setConcluido(boolean b);

    public void setJogoId(Integer jogoId) ;

    public Integer getEstado() ;

    public void setEstado(Integer estado);

    public String getTabuleiro();

    public void setTabuleiro(String tabuleiro) ;

    public String getTurno() ;

    public void setTurno(String turno);

    public String getTipo() ;

    public void setTipo(String tipo) ;

    public void setJogadasList(List<Jogadas> jogadasList) ;

    public void setCriador(Users criador) ;

    public Users getVencedor();

    public void setVencedor(Users vencedor);


    public int hashCode() ;

    public boolean equals(Object object) ;
}
