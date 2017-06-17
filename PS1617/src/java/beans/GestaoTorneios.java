
package beans;

import autenticacao.Util;
import entidades.Jogos;
import entidades.Torneios;
import entidades.TorneiosJogos;
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
    private TorneiosJogosFacade torneiosJogosFacade;
    @EJB
    Sessao sessao;
    
    private Users user;
    
    private Torneios torneio;
    
    private String mensagem;
   
    public GestaoTorneios() {
        
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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
        if((torneio.getTipo().equals("ELIMINACAO") && torneio.getMaxJogadores()>2 &&  (torneio.getMaxJogadores() & (torneio.getMaxJogadores() - 1)) == 0) 
                || (torneio.equals("ROUND_ROBIN") && torneio.getMaxJogadores()>2)){
            torneio.setRondaAtual(1);
            mensagem = "";
            return create(torneio);
        }
        if(torneio.getTipo().equals("ELIMINACAO"))
            mensagem = "Torneios por Eliminacao necessitam ter pelo menos 3 jogadores e ser potencia de 2";
        else
            mensagem = "Torneios Round-Robin necessitam ter pelo menos 3 jogadores";;
        return null;
    }
    
    private String create(Torneios torneio) {
            torneiosFacade.create(torneio);
            return "/area_privada/gestaojogos";
    }

    public String addJogo(Jogos jogo){
        // 3 = torneio para testes que existe na BD
        this.torneio = torneiosFacade.find(3);
        if (torneio!=null){
            TorneiosJogos tj =  new TorneiosJogos();
            tj.setJogo(jogo);
            torneiosJogosFacade.create(tj);
            List<TorneiosJogos> jogosTorneio = torneio.getTorneiosJogosList();
            jogosTorneio.add(tj);
            torneio.setTorneiosJogosList(jogosTorneio);
            torneiosFacade.edit(torneio);
        }
        
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
        if (user!=null){
            System.out.println("-----USER="+user.getUsername());
            List<TorneiosUsers> tus = torneio.getTorneiosUsersList();
            for (TorneiosUsers tu : tus) {
                if(user.getUsername().equals(tu.getUsername().getUsername())){
                    return false;
                }
            }
            return true;
        }
        return false;
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
