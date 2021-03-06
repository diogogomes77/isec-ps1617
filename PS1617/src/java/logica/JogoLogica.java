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
import javax.persistence.MappedSuperclass;

/**
 *
 * @author diogo
 */
@MappedSuperclass
public abstract class JogoLogica extends Jogos implements InterfaceJogo {

    protected JogoLogica(Users criador) {
        super(criador);
    }
    
    protected JogoLogica(Users criador, Users participante) {
        super(criador, participante);
    }
protected JogoLogica() {
       
    }

    public abstract int verificaFim(InterfaceJogo jogo, Users username, List<Jogadas> listaJogadas);
}
