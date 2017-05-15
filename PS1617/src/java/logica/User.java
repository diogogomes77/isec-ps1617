
package logica;

public class User {
    String username;
    String password;
    boolean ativo;

    public User(String usename, String password, boolean ativo) {
        this.username = usename;
        this.password = password;
        this.ativo = ativo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsename(String usename) {
        this.username = usename;
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
