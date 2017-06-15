
package beans;

import autenticacao.Util;
import entidades.Jogos;
import entidades.Torneios;
import entidades.TorneiosUsers;
import entidades.Users;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import facades.*;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.jms.Session;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import logica.EnumEstado;
import static logica.EnumEstado.INICIADO;
import logica.Sessao;

@ManagedBean
@SessionScoped
public class GestaoTorneios implements Serializable {

    @EJB
    private TorneiosFacade torneiosFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private TorneiosUsersFacade torneiosUsersFacade;
    @EJB
    Sessao sessao;
    
    private Users user;
    
    private Torneios torneio;
   
    public GestaoTorneios() {
        
    }

    public Torneios getTorneio() {
        return torneio;
    }

    public List<Torneios> getAllTorneios(){
        return torneiosFacade.findAll();
    }
    
    public List<Torneios> getAllTorneiosByEstado(int estado){
       
        TypedQuery<Torneios> query =
        torneiosFacade.getEntityManager().createNamedQuery("Torneios.findByEstado", Torneios.class)
                .setParameter("estado",estado);
        return query.getResultList();
        
    }
    public void setTorneio(Torneios torneio) {
        this.torneio = torneio;
    }


    public String criar(){
       return create(torneio);
       
    }
    
    private String create(Torneios torneio) {
            torneiosFacade.create(torneio);
            return "/area_privada/gestaojogos";
    }

    public String entrar(Torneios torneio) {
        if (user!=null){
             System.out.println("-----USER="+user.getUsername());
        // adiciona user ao torneio
        TorneiosUsers tu = new TorneiosUsers();
       // tu.setUserTorneioId(123);
        tu.setTorneio(torneio);
        tu.setUsername(user);
       // tu.setData(new Date());
        torneiosUsersFacade.create(tu);
        List<TorneiosUsers> usersTorneio = torneio.getTorneiosUsersList();
        usersTorneio.add(tu);
        torneio.setTorneiosUsersList(usersTorneio);
        torneiosFacade.edit(torneio);
        } else{
            System.out.println("----NO-USER...");
        }
       
        return "/area_privada/gestaojogos";
    }
    public boolean possoEntrar(Torneios torneio) {
        // TODO verificar se  user ainda nao esta no torneio
        return true;
    }
    public boolean possoVer(Torneios torneio) {
        // TODO verifica se user pode ver torneio
        return true;
    }
    public String ver(Torneios torneio) {
        // TODO mostrar pagina de torneio
        return "/area_privada/gestaojogos";
    }
    public String update(Torneios torneio) {
           torneiosFacade.edit(torneio);
            return "/area_privada/gestaojogos";
    }

    public String destroy(Torneios torneio) {
        torneiosFacade.remove(torneio);
        return "/area_privada/gestaojogos";
    }

    public Torneios getTorneios(Integer id) {
        return torneiosFacade.find(id);
    }

    public String addUserTorneio(Users user){
        
        return "/area_privada/gestaojogos";
    }
    
    @PostConstruct
    public void init() {
        if (torneio == null){
            torneio = new Torneios();
            torneio.setEstado(INICIADO.getValue());
        }
        if (sessao!=null){
            String username = sessao.getUsername();
            user = usersFacade.find(username);
        }
    } 
    
}
