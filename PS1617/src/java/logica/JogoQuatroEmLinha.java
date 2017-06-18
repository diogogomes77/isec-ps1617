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

@Entity
@DiscriminatorValue("JOGO_QUATRO_EM_LINHA")
public class JogoQuatroEmLinha extends JogoLogica implements InterfaceJogo  {
    
    public JogoQuatroEmLinha(Users criador) {
        super(criador);
    }
    
    public JogoQuatroEmLinha() {}

    @Override
    public void adicionaJogada(Jogadas jogada) {
        int [] jj = new int [42];
        for(int i = 0; i < 42; i++){
            jj[i] = -1;
        }

        for(Jogadas j : getJogadasList()) {
            if(j.getUsername().equals(jogada.getUsername())){
                jj[j.getPos_x()] = 1;
            }
            else{
                jj[j.getPos_x()] = 2;
            }
        }
       
        int posAVerificar = jogada.getPos_x();
        while (true) {
           posAVerificar += getWidth();
           if (posAVerificar > 41) break;
           
           if (jj[posAVerificar] == -1){
               jogada.setPos_x(posAVerificar);
           }
        }
        
        super.adicionaJogada(jogada);
    }
    
    @Override
    public int verificaFim(InterfaceJogo jogo, Users username, List<Jogadas> listaJogadas) {
        int [] jj = new int [42];
        for(int i = 0; i < 42; i++){
            jj[i] = -1;
        }
        
        for(Jogadas j : listaJogadas) {
            if(j.getUsername().equals(username)){
                jj[j.getPos_x()] = 1;
            }
            else{
                jj[j.getPos_x()] = 2;
            }
        }
        
        int [][]tabuleiro = new int[getHeight()][getWidth()];
        int contador = 0;
        for(int i=0; i<getHeight(); i++) {
              for(int j=0; j<getWidth(); j++) {
                tabuleiro[i][j] = jj[contador];
                contador++;
            }
        }
                
        if(areFourConnected(tabuleiro, 0)){
            jogo.setConcluido(true);
            return 0;
        }
        if(areFourConnected(tabuleiro, 1)){
            jogo.setConcluido(true);
            return 1;
        }
        
        if(listaJogadas.size() > 41){
            return 2;
        }
        return -1;    
    }
    
    private int getHeight() {
        return 6;
    }
    
    private int getWidth() {
        return 7;
    }
    
    private boolean areFourConnected(int [][]tabuleiro, int jogador){
        // horizontalCheck 
        for (int j = 0; j<getHeight()-3 ; j++ ){
            for (int i = 0; i<getWidth(); i++){
                if (tabuleiro[i][j] == jogador && tabuleiro[i][j+1] == jogador && tabuleiro[i][j+2] == jogador && tabuleiro[i][j+3] == jogador){
                    return true;
                }           
            }
        }
        // verticalCheck
        for (int i = 0; i<getWidth()-3 ; i++ ){
            for (int j = 0; j<this.getHeight(); j++){
                if (tabuleiro[i][j] == jogador && tabuleiro[i+1][j] == jogador && tabuleiro[i+2][j] == jogador && tabuleiro[i+3][j] == jogador){
                    return true;
                }           
            }
        }
        // ascendingDiagonalCheck 
        for (int i=3; i<getWidth(); i++){
            for (int j=0; j<getHeight()-3; j++){
                if (tabuleiro[i][j] == jogador && tabuleiro[i-1][j+1] == jogador && tabuleiro[i-2][j+2] == jogador && tabuleiro[i-3][j+3] == jogador){
                    return true;
                }
            }
        }
        // diagonal
        for (int i=3; i<getWidth(); i++){
            for (int j=3; j<getHeight(); j++){
                if (tabuleiro[i][j] == jogador && tabuleiro[i-1][j-1] == jogador && tabuleiro[i-2][j-2] == jogador && tabuleiro[i-3][j-3] == jogador){
                    return true;
                }
            }
        }
    
        return false;
    }
    
    @Override
    public String returnTabuleiro(){
        return "/area_privada/jogoemlinha";
    }
 }