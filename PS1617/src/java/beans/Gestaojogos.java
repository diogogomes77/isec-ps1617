package beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import logica.Jogo;
import logica.LogicaLocal;
import logica.SessaoLocal;

@ManagedBean
@SessionScoped
public class Gestaojogos implements Serializable{

    @EJB
    SessaoLocal sessao;
    @EJB
    LogicaLocal lo;
    private String username;
    
    public Gestaojogos() {
      //  this.username = sessao.getUsername();
       this.username = "okok";
    }
    
    public String iniciarJogo() {
        lo.iniciarJogo(username);
        return "gestaojogos";
    }

    public void juntarJogo(int id) {
        lo.juntarJogo(id, username);
    }

    public ArrayList<Jogo> listarJogosIniciados() {
        return lo.listarJogos();
    }

    public boolean fazJogada(int idJogo,String jogada) {
        return lo.fazJogada(idJogo, username, jogada);
    }

    public boolean terminaJogo(int idJogo) {
        return lo.terminaJogo(idJogo);
    }
   
}
