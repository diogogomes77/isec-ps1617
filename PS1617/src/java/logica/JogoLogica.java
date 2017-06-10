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
public abstract class JogoLogica extends Jogos implements JogoInterface {

    protected JogoLogica(Users criador) {
        super(criador);
    }
    protected JogoLogica(Jogos j) {
        super(j.getCriador());
    }

    public abstract int verificaFim(Jogos jogo, Users username);
}
