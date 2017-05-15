package logica;
public class Users {
    String usename;
    String password;
    boolean ativo;

    public Users(String usename, String password, boolean ativo) {
        this.usename = usename;
        this.password = password;
        this.ativo = ativo;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    
    
}
