package beans;

import autenticacao.Util;
import com.sun.faces.component.visit.FullVisitContext;
import entidades.Jogadas;
import entidades.Jogos;
import entidades.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import logica.JogoLogica;
import logica.Logica;
import logica.Sessao;
import logica.EnumTipoJogo;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;
import logica.InterfaceJogo;
import logica.JogoGalo;

@ManagedBean
@SessionScoped
public class Gestaojogos implements Serializable {

    @EJB
    Sessao sessao;
    @EJB
    Logica lo;
    
    private String username;
    private  Users user;
    private InterfaceJogo jogo;
    private  HttpSession session;
    @EJB
    private facades.UsersFacade ejbFacadeUsers;
    @EJB
    private facades.JogosFacade ejbFacadeJogos;
    @EJB
    private facades.JogadasFacade ejbFacadeJogadas;
    
    public Gestaojogos() {
        // this.username = sessao.getUsername();
        // System.out.println("---- Gestao jogos iniciada "+username);
        // this.username = "okok";
    }

    public String iniciarJogo(EnumTipoJogo tipoJogo) {
       // user = ejbFacadeUsers.find(username);
        if (user != null) {
            System.out.println("---- Encontrou "+user.getUsername());
          //  if(sessao.getJogoId() < 1){
            //    System.out.println("---- sessao jogo "+sessao.getJogoId());
                int jId = lo.iniciarJogo(user, tipoJogo);
               /// sessao.setJogoId(jId);
               jogo = lo.getJogo(jId);
                return "/area_privada/gestaojogos";
          //  }
          //  else{
          //      RequestContext r = RequestContext.getCurrentInstance();
          //      r.execute("PF('dlgIni').show();");                
          //      return "/area_privada/gestaojogos";
          //  }
        }
        return "login";
    }

    public String juntarJogo(int id) {
        if (username != null && !lo.getJogo(id).getCriador().equals(user)) {
            lo.juntarJogo(id, user);
           // sessao.setJogoId(id);
           jogo = lo.getJogo(id);
           return jogo.getTabuleiro();
          //  if (lo.getJogo(id) instanceof JogoGalo) {
          //      return "/area_privada/jogo";
          //  } else {
          //      return "/area_privada/jogoemlinha";
          //  }
        }
        return "/area_privada/gestaojogos";
    }
    
    public boolean possoJuntar(int id){
       Jogos j = ejbFacadeJogos.find(id);
       System.out.println("----possoJuntar?--id-"+id);
       
       if (j!=null){
           System.out.println("----Jogo not null--id-"+id);
            System.out.println("----JOGO---"+j.toString());
            System.out.println("----CRIADOR---"+j.getCriador().getUsername());
            System.out.println("----USER---"+user.getUsername());
                return !j.getCriador().equals(user);
       }
       /*for (JogoLogica jogo : lo.getJogosIniciados()) {
            if (jogo.getJogoId()==id) {
                return !jogo.getCriador().equals(user);
            }
        }*/
        return true;
    }
    
    public boolean possoJogar(int id){
       Jogos j = ejbFacadeJogos.find(id);
       if (j!=null){
            System.out.println("----JOGO---"+j.toString());
                if (j.getCriador().equals(user) || j.getParticipante().equals(user))
                    return true;
       }
       
       /*for (JogoLogica jogo : lo.getJogosDecorrer()) {
           System.out.println("----JOGO---"+jogo.toString());
            if (jogo.getJogoId()==id) {
                if (jogo.getCriador().equals(user) || jogo.getParticipante().equals(user))
                    return true;
            }
        }*/
        return false;
    }
    
    public String jogar(int id){
       // sessao.setJogoId(id);
       jogo = lo.getJogo(id);
       System.out.println("-----GestaoJogos--jogar id--"+id);
       if (jogo!=null){
           System.out.println("-----GestaoJogos--jogo encontrado id--" + jogo.getJogoId() );
            return jogo.returnTabuleiro();     
       }
        System.out.println("-----GestaoJogos--jogo nao encontrado id--"  );
       return "/area_privada/gestaojogos";
       /* if (lo.getJogo(id) instanceof JogoGalo) {
            return "/area_privada/jogo";
        } else {
            return "/area_privada/jogoemlinha";
        }*/
    }
    
    public List<Jogos> listarJogosIniciados() {
        return lo.getJogosIniciados();
    }

    public List<Jogos> listarJogosDecorrer() {
        return lo.getJogosDecorrer();
    }
    
    public String fazJogada(String jogada) {
        if(lo.fazJogada(jogo.getJogoId(),user, jogada)){
            return verificaTerminaJogo();
        }if(lo.jogoTerminado(jogo.getJogoId())){
            //sessao.setJogoId(-1);
            return "/area_privada/gestaojogos";
        }else{
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlg').show();");
            return jogo.getTabuleiro();
            //if (lo.getJogo(sessao.getJogoId()) instanceof JogoGalo) {
            //    return "/area_privada/jogo";
            //} else {
            //    return "/area_privada/jogoemlinha";
           // }
        }
    }

    public String verificaTerminaJogo() {
        if(lo.terminaJogo(jogo.getJogoId())){
            //sessao.setJogoId(-1);
            return "/area_privada/gestaojogos";
        }
        return jogo.getTabuleiro();
      //  if (lo.getJogo(sessao.getJogoId()) instanceof JogoGalo) {
      //      return "/area_privada/jogo";
      //  } else {
      //      return "/area_privada/jogoemlinha";
      //  }
    }
    
    @PostConstruct
    public void init() {
        HttpSession session = Util.getSession();
        this.username = (String) session.getAttribute("username");
        this.session = session;

            this.user = ejbFacadeUsers.find(username);
        
        
        if (user!=null)
        System.out.println("---- Gestao jogos iniciada "+user.getUsername());
        else 
            System.out.println("---- USER NULL!!!! ");
    }
    

//----------------------------//--------------------------    

    public UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance(); 
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {     
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if(component.getId().equals(id)){
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;              
            }
        });

        return found[0];
    }
    
    public void joga(int pos){
        if(lo.podeJogar(username, pos, jogo.getJogoId())){                             
                   System.out.println("------JOGA id--------" + jogo.getJogoId()+"---------");
                   Jogadas jog = lo.fazJogada(user, pos, 0, jogo);
                   List <Jogadas> jogadas = jogo.getJogadasList();                   
                   jogadas.add(jog);
                   jogo.setJogadasList(jogadas);
       }
        else{
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlg').show();");
        }
    }

    public void atualiza(){  
        
        List <Jogadas> listaJogadas = null;
        boolean criador = false;
        TypedQuery<Jogadas> query =
        ejbFacadeJogadas.getEntityManager().createNamedQuery("Jogadas.findByJogoId", Jogadas.class)
                .setParameter("jogoId",jogo);
        listaJogadas = query.getResultList();
        // listaJogadas = jogo.getJogadasList();
       
        if (jogo.getCriador().getUsername().equals(user.getUsername()))
            criador=true;
        int i=0;
        for(Jogadas jogada : listaJogadas){
            String id = "btn" + jogada.getPos_x();
            CommandButton btn = (CommandButton) findComponent(id);
            btn.setDisabled(true);
            boolean serCriador = jogada.getUsername().getUsername().equals(user.getUsername()) && criador;
            boolean serParticipante = !(jogada.getUsername().getUsername().equals(user.getUsername())) && !criador;
            if(serCriador || serParticipante){
                btn.setValue("X");
            }
            else{
                btn.setValue("O");
            }
            i++;
        }
         System.out.println("---n jogadas ="+i);
        
        int fim = jogo.verificaFim(jogo, user, listaJogadas);
       
        if(fim == 0){
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlgGanhar').show();");
        }
        if(fim == 1){
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlgPerder').show();");
        }        
        if(fim == 2){            
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlgEmpate').show();");
        }
    
    }
}
