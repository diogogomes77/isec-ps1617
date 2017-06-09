/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.Jogadas;
import entidades.Jogos;
import entidades.Users;

/**
 *
 * @author diogo
 */
public abstract class JogoLogica extends Jogos {

    protected JogoLogica(Users criador) {
        super(criador);
    }

  //  @Override
    public boolean avaliaJogada(Users por, String jogada) {
        if (por.getUsername().equals(turno) && emEspera == false) {
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
                        turno = participante.getUsername();
                    } else {
                        turno = criador.getUsername();
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

  //  @Override
    public boolean terminaJogo() {
        switch (comando) {
            case "Ganhar":
                if (turno.equals(criador)) {
                    vencedor = criador;
                } else {
                    vencedor = participante;
                }
                concluido = true;
                return true;
            case "Perder":
                if (turno.equals(criador)) {
                    vencedor = participante;
                } else {
                    vencedor = criador;
                }
                concluido = true;
                return true;
            case "Empate":
                vencedor = null;
                concluido = true;
                return true;
            default:
                //Jogada não termina
                return false;
        }
    }

    //Função para condizer com o método que o pedro fez de fazer jogada vai ser remvida no futuro
  //  @Override
    public boolean terminaTemp(Users username, int i) {
        switch (i) {
            //Jogador Atual Perde
            case -1:
                if (username.equals(criador)) {
                    vencedor = participante;
                } else {
                    vencedor = criador;
                }
                concluido = true;
                return true;
            //Empate
            case 0:
                vencedor = null;
                concluido = true;
                return true;
            //Jogador Atual Ganha
            case 1:
                if (username.equals(criador)) {
                    vencedor = criador;
                } else {
                    vencedor = participante;
                }
                concluido = true;
                return true;
        }
        return false;
    }

    @Override
    public void adicionaJogada(Jogadas jogada) {
        this.jogadasList.add(jogada);
    }

 //   @Override
    public abstract int verificaFim(Jogos jogo, Users username);
}
