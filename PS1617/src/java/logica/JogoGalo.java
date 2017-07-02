package logica;

import entidades.Jogadas;
import entidades.Jogos;
import entidades.Users;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.primefaces.context.RequestContext;

@Entity
@DiscriminatorValue("JOGO_GALO")
public class JogoGalo extends JogoLogica implements InterfaceJogo{
 

    public JogoGalo(Users criador) {
       super(criador);
    }
    
    public JogoGalo(Users criador, Users participante) {
       super(criador,participante);
    }
    
    public JogoGalo(){
        
    }
   

        
    @Override
    public int verificaFim(InterfaceJogo jogo, Users username, List<Jogadas> listaJogadas){    
        int [] jj = new int [10];
        for(int i = 0; i < 10; i++){
            jj[i] = -1;
        }
        for(Jogadas jogadas : listaJogadas)
            if(jogadas.getUsername().equals(username)){
                jj[jogadas.getPos_x()] = 1;
            }
            else{
                jj[jogadas.getPos_x()] = 2;
            }
         if((jj[1] == 1 && jj[2] == 1 && jj[3] == 1)||(jj[4] == 1 && jj[5] == 1 && jj[6] == 1)||(jj[7] == 1 && jj[8] == 1 && jj[9] == 1)
                ||(jj[1] == 1 && jj[4] == 1 && jj[7] == 1)||(jj[2] == 1 && jj[5] == 1 && jj[8] == 1)||(jj[3] == 1 && jj[6] == 1 && jj[9] == 1)
                ||(jj[1] == 1 && jj[5] == 1 && jj[9] == 1) || (jj[3] == 1 && jj[5] == 1 && jj[7] == 1)){
            jogo.setConcluido(true);
            //jogo.setVencedor(username);
            return 0;
        }
        if((jj[1] == 2 && jj[2] == 2 && jj[3] == 2)||(jj[4] == 2 && jj[5] == 2 && jj[6] == 2)||(jj[7] == 2 && jj[8] == 2 && jj[9] == 2)
                ||(jj[1] == 2 && jj[4] == 2 && jj[7] == 2)||(jj[2] == 2 && jj[5] == 2 && jj[8] == 2)||(jj[3] == 2 && jj[6] == 2 && jj[9] == 2)
                ||(jj[1] == 2 && jj[5] == 2 && jj[9] == 2) || (jj[3] == 2 && jj[5] == 2 && jj[7] == 2)){
            jogo.setConcluido(true);
            return 1;
        }
        
        if(listaJogadas.size() > 8){
            return 2;
        }
        return -1;
    }
    @Override
    public String returnTabuleiro(){
        return "/area_privada/jogo";
    }

    @Override
    public void atualizaJogada(Jogadas novaJogada, List<Jogadas> listaJogadas) {
        // Do nothing.
    }
}