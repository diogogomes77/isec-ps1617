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

    protected JogoLogica(Users criador, TipoJogo tipo) {
        super(criador, tipo);
    }


    public abstract int verificaFim(JogoInterface jogo, Users username);
}
