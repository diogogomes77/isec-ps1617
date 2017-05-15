/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogos;

/**
 *
 * @author João
 */
public class Jogo {
    //Variáveis que vão ser comuns a todos os jogos
    static int idProg;
    int id;
    String criador;
    String participante;
    boolean emEspera;
    boolean concluido;
    String turno;
    //Indicador do vencedor
    //Ainda não decidido: -1
    //Empate: 0 
    //Criador: 1
    //Vencedor: 2
    int vencedor;

    //Variaveis para este jogo em especifico
    String comando;
    
    public Jogo(String criador) {
        this.id = idProg;
        idProg++;
        this.criador = criador;
        this.participante = null;
        this.emEspera = true;
        this.concluido = false;
        this.vencedor = -1;
        turno = criador;
        
        this.comando = "";
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
    
    public boolean avaliaJogada(String por,String jogada){
        if(por.equals(turno) && emEspera==false){
            switch(jogada){
                case "Ganhar":
                    comando = jogada;
                    return true;
                case "Perder":
                    comando = jogada;
                    return true;
                case "Empate":
                    comando = jogada;
                    return true;
                case "Passou":
                    if(por.equals(criador))
                        turno=participante;
                    else
                        turno=criador;
                    comando = jogada;
                    return true;
                default:
                    //Jogada inválida
                    return false;
            }
        }
        else{
            //Jogada fora de turno
            return false;
        }
    }
    
    public boolean terminaJogo(){
        switch(comando){
                case "Ganhar":
                    if(turno.equals(criador))
                        vencedor=1;
                    else
                        vencedor=2;
                    concluido=true;
                    return true;
                case "Perder":
                    if(turno.equals(criador))
                        vencedor=2;
                    else
                        vencedor=1;
                    concluido=true;
                    return true;
                case "Empate":
                    vencedor=0;
                    concluido=true;
                    return true;
                default:
                    //Jogada não termina
                    return false;
        }
    }
}
