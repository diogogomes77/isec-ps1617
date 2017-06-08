
package logica;

import java.util.ArrayList;
import java.util.List;

public abstract class Jogo {
    //Variáveis que vão ser comuns a todos os jogos
    static int idProg=0;
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
    //Participante: 2
    int vencedor;

    //Variaveis para este jogo em especifico
    String comando;
    
    public List<Jogada> jogadas;
    
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
        this.jogadas = new ArrayList<>();
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
    
    public abstract boolean avaliaJogada(String por,String jogada);
    
    public abstract boolean terminaJogo();
    
    //Função para condizer com o método que o pedro fez de fazer jogada vai ser remvida no futuro
    public abstract boolean terminaTemp(String username,int i);
    
    public void adicionaJogada(Jogada jogada) {
        this.jogadas.add(jogada);
    }
    
    public int getNumeroJogadas() {
        return this.jogadas.size();
    }
    
    public abstract int verificaFim(Jogo jogo, String username);
    
    @Override
    public String toString(){
        String result;
        if (participante==null) result = "Iniciado por "+criador;
        else result = criador+" vs "+participante;
        return result;
    }
}
