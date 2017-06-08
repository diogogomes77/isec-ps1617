package logica;

import java.util.List;
import org.primefaces.context.RequestContext;

public class JogoGalo extends JogoLogica {
    
    public JogoGalo(String criador) {
        super(criador);
    }
    
        
    @Override
    public int verificaFim(Jogo jogo, String username){
        List<Jogada> jog = jogo.jogadas;        
        int [] jj = new int [10];
        for(int i = 0; i < 10; i++){
            jj[i] = -1;
        }
        for(Jogada j : jog)
            if(j.getUserId().equals(username)){
                jj[j.getPosX()] = 1;
            }
            else{
                jj[j.getPosX()] = 2;
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
        
        if(jog.size() > 8){
            return 2;
        }
        return -1;
    }
}
