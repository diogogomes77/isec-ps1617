
package logica;

import autenticacao.Util;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.servlet.http.HttpSession;


@Stateful
public class Sessao {

    private String username;
    private String password;
    private int jogo;
    @EJB
    Logica lo;

    public Sessao() {
        username=null;
        password=null;
    }
    
    public void login(String username, String password) {
        this.username = username;
        this.password = password;
        HttpSession session = Util.getSession();
            session.setAttribute("username", username);
    }

    public int getJogo() {
        return jogo;
    }

    public void setJogo(int jogo) {
        this.jogo = jogo;
    }

    public void logout() {
        lo.logout(username);
        this.username = null;
        this.password = null;
        HttpSession session = Util.getSession();
        session.setAttribute("username", null);
    }

    public String getUsername() {
        HttpSession session = Util.getSession();
         return  (String)  session.getAttribute("username");
         
    }
}
