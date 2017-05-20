
package logica;

import javax.ejb.EJB;
import javax.ejb.Stateful;


@Stateful
public class Sessao implements SessaoLocal {

    private String username;
    private String password;
    @EJB
    LogicaLocal lo;

    public Sessao() {
        username=null;
        password=null;
    }
    
    @Override
    public void login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void Logout() {
        lo.Logout(username);
        this.username = null;
        this.password = null;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
