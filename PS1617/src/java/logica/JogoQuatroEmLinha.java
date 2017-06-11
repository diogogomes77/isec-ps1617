package logica;

import entidades.Jogos;
import entidades.Users;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

//@Table(name = "jogos")
//@Inheritance(strategy = InheritanceType.JOINED)
public class JogoQuatroEmLinha extends JogoLogica  {
    
    public JogoQuatroEmLinha(Users criador, TipoJogo tipo) {
        super(criador,tipo);
    }

    @Override
    public int verificaFim(JogoInterface jogo, Users username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
