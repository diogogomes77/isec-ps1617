
package logica;

import javax.servlet.http.HttpSession;

public class User {
    String username;
    String password;
    String email;
    String morada;
    boolean ativo;
    private HttpSession session;

    public User(String usename, String password, boolean ativo) {
        this.username = usename;
        this.password = password;
        this.ativo = ativo;
    }
    
    public User(String usename, String password, String email, String morada, boolean ativo) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
    
}
