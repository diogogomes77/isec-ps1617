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
public class JogoQuatroEmLinha extends JogoLogica implements InterfaceJogo {
    
    private static int TAMANHO_MATRIZ = 42;
    private static int NUMERO_LINHAS = 6;
    private static int NUMERO_COLUNAS = 7;
    
    private static int JOGAGOR_NENHUM = -1;
    private static int JOGADOR_PROPRIO = 1;
    private static int JOGADOR_ADVERSARIO = 2;
    
    public JogoQuatroEmLinha(Users criador) {
        super(criador);
    }
    
    public JogoQuatroEmLinha(Users criador, Users participante) {
        super(criador,participante);
    }
    
    public JogoQuatroEmLinha() {
        // Fazer nada.
    }
    
    @Override
    public void atualizaJogada(Jogadas novaJogada, List<Jogadas> listaJogadas) {
        int [] jj = new int [TAMANHO_MATRIZ];
        for(int i = 0; i < TAMANHO_MATRIZ; i++){
            jj[i] = JOGAGOR_NENHUM;
        }
        

        for(Jogadas j : listaJogadas) {
            if(j.getUsername().equals(novaJogada.getUsername())){
                jj[j.getPos_x()] = JOGADOR_PROPRIO;
            
            } else {
                jj[j.getPos_x()] = JOGADOR_ADVERSARIO;
            }
        }
        
        int posAVerificar = novaJogada.getPos_x();
        while (true) {
           posAVerificar += getWidth();
           if (posAVerificar > TAMANHO_MATRIZ - 1) break;
           
           if (jj[posAVerificar] == JOGAGOR_NENHUM) {
               novaJogada.setPos_x(posAVerificar);
           }
        }
    }
    
    @Override
    public int verificaFim(InterfaceJogo jogo, Users username, List<Jogadas> listaJogadas) {
        int [] jj = new int [TAMANHO_MATRIZ];
        for(int i = 0; i < TAMANHO_MATRIZ; i++) {
            jj[i] = -1;
        }
        
        for(Jogadas j : listaJogadas) {
            if(j.getUsername().equals(username)){
                jj[j.getPos_x()] = JOGADOR_PROPRIO;
            }
            else{
                jj[j.getPos_x()] = JOGADOR_ADVERSARIO;
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
                
        if(areFourConnected(tabuleiro, JOGADOR_PROPRIO)){
            jogo.setConcluido(true);
            return 0; // 0 - vitoria
        }
        if(areFourConnected(tabuleiro, JOGADOR_ADVERSARIO)){
            jogo.setConcluido(true);
            return 1; // 1 - derrota
        }
        
        if(listaJogadas.size() > TAMANHO_MATRIZ - 1){
            return 2; // 2 - empate
        }
        
        return -1; // -1 - jogo nao terminado  
    }
    
    private int getHeight() {
        return NUMERO_LINHAS;
    }
    
    private int getWidth() {
        return NUMERO_COLUNAS;
    }
    
    private boolean areFourConnected(int [][]tabuleiro, int jogador) {
        // horizontalCheck 
        for (int j = 0; j<getWidth()-3 ; j++ ) {
            for (int i = 0; i<getHeight(); i++) {
                if (tabuleiro[i][j] == jogador && tabuleiro[i][j+1] == jogador && tabuleiro[i][j+2] == jogador && tabuleiro[i][j+3] == jogador) {
                    return true;
                }           
            }
        }
        // verticalCheck
        for (int i = 0; i<getHeight()-3 ; i++ ) {
            for (int j = 0; j<getWidth(); j++) {
                if (tabuleiro[i][j] == jogador && tabuleiro[i+1][j] == jogador && tabuleiro[i+2][j] == jogador && tabuleiro[i+3][j] == jogador) {
                    return true;
                }           
            }
        }
        // ascendingDiagonalCheck 
        for (int i=3; i<getHeight(); i++) {
            for (int j=0; j<getWidth()-3; j++) {
                if (tabuleiro[i][j] == jogador && tabuleiro[i-1][j+1] == jogador && tabuleiro[i-2][j+2] == jogador && tabuleiro[i-3][j+3] == jogador) {
                    return true;
                }
            }
        }
        // diagonal
        for (int i=3; i<getHeight(); i++) {
            for (int j=3; j<getWidth(); j++) {
                if (tabuleiro[i][j] == jogador && tabuleiro[i-1][j-1] == jogador && tabuleiro[i-2][j-2] == jogador && tabuleiro[i-3][j-3] == jogador) {
                    return true;
                }
            }
        }
    
        return false;
    }
    
    @Override
    public String returnTabuleiro() {
        return "/area_privada/jogoemlinha";
    }
 }