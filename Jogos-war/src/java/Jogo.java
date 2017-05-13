/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author João
 */
public class Jogo {
    static int idProg;
    int id;
    String criador;
    String participante;
    boolean emEspera;
    boolean concluido;
    //Indicador do vencedor
    //Ainda não decidido: -1
    //Empate: 0 
    //Criador: 1
    //Vencedor: 2
    int vencedor;

    public Jogo(String criador) {
        this.id = idProg;
        idProg++;
        this.criador = criador;
        this.participante = null;
        this.emEspera = true;
        this.concluido = false;
        this.vencedor = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public boolean isEmEspera() {
        return emEspera;
    }

    public void setEmEspera(boolean emEspera) {
        this.emEspera = emEspera;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public int getVencedor() {
        return vencedor;
    }

    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
    }
}
