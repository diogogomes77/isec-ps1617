
package logica;

import javax.ejb.EJB;
import javax.ejb.Stateful;


@Stateful
public class Sessao {

    private String username;
    private String password;
    @EJB
    Logica lo;

    public Sessao() {
        username=null;
        password=null;
    }
    
    public void login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void Logout() {
        lo.Logout(username);
        this.username = null;
        this.password = null;
    }

    public String getUsername() {
        return username;
    }
}
