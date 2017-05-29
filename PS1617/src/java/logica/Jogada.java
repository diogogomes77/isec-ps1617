package logica;

public class Jogada {
    
    String userId;
    int posX, posY;
    
    public Jogada(String userId, int posX, int posY) {
        this.userId = userId;
        this.posX = posX;
        this.posY = posY;
    }
    
    public String getUserId() {
        return userId;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
