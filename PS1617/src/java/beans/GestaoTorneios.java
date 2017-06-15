
package beans;

import autenticacao.Util;
import entidades.Torneios;
import entidades.Users;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import facades.*;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class GestaoTorneios implements Serializable {

    @EJB
    private TorneiosFacade torneiosFacade;
    private Torneios torneio;
   
    public GestaoTorneios() {
        
    }

    public Torneios getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneios torneio) {
        this.torneio = torneio;
    }

    
    private TorneiosFacade getFacade() {
        return torneiosFacade;
    }

    public String criar(){
       return create(torneio);
       
    }
    
    private String create(Torneios torneio) {
            getFacade().create(torneio);
            return "GestaoJogos";
    }

    public String update(Torneios torneio) {
            getFacade().edit(torneio);
            return "GestaoTorneios";
    }

    public String destroy(Torneios torneio) {
        getFacade().remove(torneio);
        return "GestaoTorneios";
    }

    public Torneios getTorneios(Integer id) {
        return torneiosFacade.find(id);
    }

    public String addUserTorneio(Users user){
        
        return "GestaoTorneios";
    }
    
       @PostConstruct
    public void init() {
        torneio = new Torneios();
    } 
    
}
