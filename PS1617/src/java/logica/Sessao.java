
package logica;

import autenticacao.Util;
import entidades.Users;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Stateful
public class Sessao {

   //private String username;
    //private String password;
    private int jogoId;
    private Users user;
    @EJB
    Logica lo;

    public void login(Users user) {
        this.user=user;
        HttpSession session = Util.getSession();
        session.setAttribute("username", user.getUsername());
        user.setSession(session);
        System.out.println("------SESSAO login -----"+session.getAttribute("username"));
    }

    public int getJogoId() {
        return jogoId;
    }

    public void setJogoId(int jogoId) {
        this.jogoId = jogoId;
    }

    public void logout() {
        user.setAtivo(false);
        this.user=null;
        HttpSession session = Util.getSession();
        session.invalidate();
       // user.setSession(session);
    }

    public String getUsername() {
        HttpSession session = Util.getSession();
         return  (String)  session.getAttribute("username");
         
    }
    
    public boolean isAtivo(){
        if (user==null){
             System.out.println("-----Sessao.isAtivo: user==null ");
            return false;
        }
            
        return user.isAtivo();
    }
}
