/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author diogo
 */
public abstract class JogoLogica extends Jogo {

    protected JogoLogica(String criador) {
        super(criador);
    }

    @Override
    public boolean avaliaJogada(String por, String jogada) {
        if (por.equals(turno) && emEspera == false) {
            switch (jogada) {
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
                    if (por.equals(criador)) {
                        turno = participante;
                    } else {
                        turno = criador;
                    }
                    comando = jogada;
                    return true;
                default:
                    //Jogada inválida
                    return false;
            }
        } else {
            //Jogada fora de turno
            return false;
        }
    }

    @Override
    public boolean terminaJogo() {
        switch (comando) {
            case "Ganhar":
                if (turno.equals(criador)) {
                    vencedor = 1;
                } else {
                    vencedor = 2;
                }
                concluido = true;
                return true;
            case "Perder":
                if (turno.equals(criador)) {
                    vencedor = 2;
                } else {
                    vencedor = 1;
                }
                concluido = true;
                return true;
            case "Empate":
                vencedor = 0;
                concluido = true;
                return true;
            default:
                //Jogada não termina
                return false;
        }
    }

    //Função para condizer com o método que o pedro fez de fazer jogada vai ser remvida no futuro
    @Override
    public boolean terminaTemp(String username, int i) {
        switch (i) {
            //Jogador Atual Perde
            case -1:
                if (username.equals(criador)) {
                    vencedor = 2;
                } else {
                    vencedor = 1;
                }
                concluido = true;
                return true;
            //Empate
            case 0:
                vencedor = 0;
                concluido = true;
                return true;
            //Jogador Atual Ganha
            case 1:
                if (username.equals(criador)) {
                    vencedor = 1;
                } else {
                    vencedor = 2;
                }
                concluido = true;
                return true;
        }
        return false;
    }

    @Override
    public void adicionaJogada(Jogada jogada) {
        this.jogadas.add(jogada);
    }

    @Override
    public abstract int verificaFim(Jogo jogo, String username);
}
