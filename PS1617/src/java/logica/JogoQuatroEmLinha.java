package logica;

import entidades.Jogos;
import entidades.Users;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("JOGO_QUATRO_EM_LINHA")
public class JogoQuatroEmLinha extends JogoLogica  {
    
    public JogoQuatroEmLinha(Users criador) {
        super(criador);
    }
public JogoQuatroEmLinha() {
      
    }
    @Override
    public int verificaFim(InterfaceJogo jogo, Users username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
