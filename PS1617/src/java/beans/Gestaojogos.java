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
import javax.servlet.http.HttpSession;
import logica.JogoLogica;
import logica.Logica;
import logica.Sessao;
import logica.EnumTipoJogo;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;
import logica.InterfaceJogo;

@ManagedBean
@SessionScoped
public class Gestaojogos implements Serializable {

    @EJB
    Sessao sessao;
    @EJB
    Logica lo;
    
    private String username;
    private  Users user;
    private  HttpSession session;
    @EJB
    private facades.UsersFacade ejbFacadeUsers;
    @EJB
    private facades.JogosFacade ejbFacadeJogos;
    
    public Gestaojogos() {
        // this.username = sessao.getUsername();
        // System.out.println("---- Gestao jogos iniciada "+username);
        // this.username = "okok";

        
    }

    public String iniciarJogo(EnumTipoJogo tipoJogo) {
       // user = ejbFacadeUsers.find(username);
        if (user != null) {
            System.out.println("---- Encontrou "+user.getUsername());
            if(sessao.getJogoId() < 1){
                System.out.println("---- sessao jogo "+sessao.getJogoId());
                int jId = lo.iniciarJogo(user, tipoJogo);
                sessao.setJogoId(jId);
                return "/area_privada/gestaojogos";
            }
            else{
                RequestContext r = RequestContext.getCurrentInstance();
                r.execute("PF('dlgIni').show();");                
                return "/area_privada/gestaojogos";
            }
        }
        return "login";
    }

    public String juntarJogo(int id) {
        if (username != null && !lo.getJogo(id).getCriador().equals(user)) {
            lo.juntarJogo(id, user);
            sessao.setJogoId(id);
            return "/area_privada/jogo";
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
        sessao.setJogoId(id);
        return "/area_privada/jogo";
    }
    
    public List<Jogos> listarJogosIniciados() {
        return lo.getJogosIniciados();
    }

    public List<Jogos> listarJogosDecorrer() {
        return lo.getJogosDecorrer();
    }
    
    public String fazJogada(String jogada) {
        if(lo.fazJogada(sessao.getJogoId(),user, jogada)){
            return verificaTerminaJogo();
        }if(lo.jogoTerminado(sessao.getJogoId())){
            sessao.setJogoId(-1);
            return "/area_privada/gestaojogos";
        }else{
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlg').show();");
            return "/area_privada/jogo";
        }
    }

    public String verificaTerminaJogo() {
        if(lo.terminaJogo(sessao.getJogoId())){
            sessao.setJogoId(-1);
            return "/area_privada/gestaojogos";
        }
        return "/area_privada/jogo";
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
        boolean ok = false;        
        String id = "btn" + pos;
        int jogoId = sessao.getJogoId();
        ok = lo.joga(username, pos, jogoId);
        if(ok){
            for(InterfaceJogo j : lo.getJogosDecorrer()){
                if(j.getCriador().equals(user) || j.getParticipante().equals(user)){
                    j.adicionaJogada(new Jogadas(user, pos, 0));
                    break;
                }
            }
        }
        else{
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlg').show();");
        }
    }
    
    public void atualiza(){  
        List <Jogadas> jog = null;
        boolean criador = false;
        InterfaceJogo jogo = null;
        for(InterfaceJogo j : lo.getJogosDecorrer()){
            if(j.getCriador().equals(user)){
                jog = j.getJogadasList();
                criador = true;
                jogo = j;
                break;
            }
            if(j.getParticipante().equals(user)){
                jog = j.getJogadasList();
                criador = false;
                jogo =  j;
                break;
            }
        }
        for(Jogadas j : jog){
            String id = "btn" + j.getPos_x();
            CommandButton btn = (CommandButton) findComponent(id);
            btn.setDisabled(true);
            if((j.getUsername()==user && criador) || (!(j.getUsername()==user) && !criador)){
                btn.setValue("X");
            }
            else{
                btn.setValue("O");
            }
        }
        
     /*   int fim = jogo.verificaFim(jogo, user);
       
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
        */
    }
}
