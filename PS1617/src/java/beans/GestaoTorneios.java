
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.jms.Session;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import logica.EnumEstado;
import static logica.EnumEstado.INICIADO;
import logica.EnumTipoJogo;
import logica.Logica;
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
    @EJB
    Logica lo;
    
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
    
    public List<Jogos> getAllJogosTorneiosByEstado(int estado){
        List<Jogos> js = new ArrayList<>();
        TypedQuery<Torneios> query =
        torneiosFacade.getEntityManager().createNamedQuery("Torneios.findByEstado", Torneios.class)
                .setParameter("estado",estado);
        List<Torneios> torneios = query.getResultList();
        for(Torneios t : torneios){
            List<TorneiosJogos> jogosTorneio = t.getTorneiosJogosList();
            for(TorneiosJogos tj : jogosTorneio){
                if(tj.getTorneio().getRondaAtual()==tj.getRonda())
                    js.add(tj.getJogo());
            }
        }
        return js;
    }
    
    public void setTorneio(Torneios torneio) {
        this.torneio = torneio;
    }

    public String criar(){
        if((torneio.getTipo().equals("ELIMINACAO") && torneio.getMaxJogadores()>2 &&  (torneio.getMaxJogadores() & (torneio.getMaxJogadores() - 1)) == 0) 
                || (torneio.getTipo().equals("ROUND_ROBIN") && torneio.getMaxJogadores()>2)){
            torneio.setRondaAtual(1);
            mensagem = "";
            return create(torneio);
        }
        if(torneio.getTipo().equals("ELIMINACAO"))
            mensagem = "Torneios por Eliminacao necessitam ter pelo menos 3 jogadores e ser potencia de 2";
        else
            mensagem = "Torneios Round-Robin necessitam ter pelo menos 3 jogadores";
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
            if(usersTorneio.size()>=torneio.getMaxJogadores()){
                if(torneio.getTipo().equals("ELIMINACAO"))
                    createPrimeiraRondaEliminacao(torneio);
                else
                    createRoundRobin(torneio);
                torneio.setEstado(2);
                torneiosFacade.edit(torneio);
            }
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
    
    public void createPrimeiraRondaEliminacao(Torneios torneio){
        Users c=null;
        Users p=null;
        List<TorneiosUsers> tus = torneio.getTorneiosUsersList();
        for (TorneiosUsers tu : tus) {
            if(c==null){
                c=tu.getUsername();
            }
            else{
                p = tu.getUsername();
                //Create torney game
                int jId = lo.iniciarJogo(c, p,torneio.getTipoJogo());
                Jogos jogo = lo.getJogosClass(jId);
                TorneiosJogos tj =  new TorneiosJogos();
                tj.setJogo(jogo);
                tj.setTorneio(torneio);
                tj.setRonda(torneio.getRondaAtual());
                torneiosJogosFacade.create(tj);
                List<TorneiosJogos> jogosTorneio = torneio.getTorneiosJogosList();
                jogosTorneio.add(tj);
                torneio.setTorneiosJogosList(jogosTorneio);
                torneiosFacade.edit(torneio);
                c = p = null;
            }
        }
        //Increase number of actual round
//        torneio.setRondaAtual(torneio.getRondaAtual() + 1);
//        torneiosFacade.edit(torneio);
    }
    
    public void createRoundRobin(Torneios torneio){
        Users u[][];
        int size, rondas;
        
        List<TorneiosUsers> tus = torneio.getTorneiosUsersList();
        if(tus.size()%2==0){
            rondas = tus.size()-1;
            size = tus.size()/2;
        }
        else{
            rondas = tus.size();
            size = (tus.size()+1)/2;
        }
        
        u = new Users[size][2];
        
        //Insere os jogadores inscritos no torneio no array
        int i = 0, j = size - 1;
        for (TorneiosUsers tu : tus) {
            if(i>=size && j >= 0){
                u[j][1] = tu.getUsername();
                j--;
            }
            if(i < size){
                u[i][0] = tu.getUsername();
                i++;
            }
        }
        
        if(tus.size()%2!=0)
            u[0][1] = null;
        
        //Cria rondas
        for(int n1=0;n1<rondas;n1++){
            //Cria jogos para esta ronda
            for(int n2=0;n2<size;n2++){
                if(u[n2][0] != null && u[n2][1] != null){
                    //Create game
                    int r = n1+1;
                    int jId = lo.iniciarJogo(u[n2][0], u[n2][1],torneio.getTipoJogo());
                    Jogos jogo = lo.getJogosClass(jId);
                    TorneiosJogos tj =  new TorneiosJogos();
                    tj.setJogo(jogo);
                    tj.setTorneio(torneio);
                    tj.setRonda(r);
                    torneiosJogosFacade.create(tj);
                    List<TorneiosJogos> jogosTorneio = torneio.getTorneiosJogosList();
                    jogosTorneio.add(tj);
                    torneio.setTorneiosJogosList(jogosTorneio);
                    torneiosFacade.edit(torneio);
                }
            }
            //Prepara o array para a próxima ronda
            Users[][] aux = new Users[size][2];
            aux[1][0] = u[0][1];
            for(int n2=1;n2<size-1;n2++){
                aux[n2+1][0] = u[n2][0];
            }
            aux[size-1][1] = u[size-1][0];
            for(int n2=size-1; n2>0; n2--){
                aux[n2-1][1] = u[n2][1];
            }
            
            for(int n2 = 0;n2<size;n2++){
                for(int n3=0;n3<2;n3++)
                    u[n2][n3] = aux[n2][n3];
                }
            }
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
