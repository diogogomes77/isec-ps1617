package beans;

import autenticacao.Util;
import com.sun.faces.component.visit.FullVisitContext;
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
import logica.Jogada;
import logica.Jogo;
import logica.JogoGalo;
import logica.Logica;
import logica.Logica.TipoJogo;
import logica.Sessao;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class Gestaojogos implements Serializable {

    @EJB
    Sessao sessao;
    @EJB
    Logica lo;
    private String username;
    private  HttpSession session;
    
    public Gestaojogos() {
        // this.username = sessao.getUsername();
        // System.out.println("---- Gestao jogos iniciada "+username);
        // this.username = "okok";
        this.session = Util.getSession();
        this.username=session.getAttribute("username").toString();
    }

    public String iniciarJogo(TipoJogo tipoJogo) {
        if (username != null) {
            if(sessao.getJogoId() < 1){
                sessao.setJogoId(lo.iniciarJogo(username, tipoJogo));
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
        if (username != null && !lo.getJogo(id).getCriador().equals(username)) {
            lo.juntarJogo(id, username);
            sessao.setJogoId(id);
            return "/area_privada/jogo";
        }
        return "/area_privada/gestaojogos";
    }
    
    public boolean possoJuntar(int id){
       for (Jogo jogo : lo.getJogosIniciados()) {
            if (jogo.getId()==id) {
                return !jogo.getCriador().equals(username);
            }
        }
        return false;
    }
    public boolean possoJogar(int id){
       for (Jogo jogo : lo.getJogosDecorrer()) {
            if (jogo.getId()==id) {
                if (jogo.getCriador().equals(username) || jogo.getParticipante().equals(username))
                    return true;
            }
        }
        return false;
    }
    
    public String jogar(int id){
        sessao.setJogoId(id);
        return "/area_privada/jogo";
    }
    
    public ArrayList<Jogo> listarJogosIniciados() {
        return lo.getJogosIniciados();
    }

    public ArrayList<Jogo> listarJogosDecorrer() {
        return lo.getJogosDecorrer();
    }
    
    public String fazJogada(String jogada) {
        if(lo.fazJogada(sessao.getJogoId(),username, jogada)){
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
        ok = lo.joga(username, pos);
        if(ok){
            for(Jogo j : lo.getJogosDecorrer()){
                if(j.getCriador().equals(username) || j.getParticipante().equals(username)){
                    j.adicionaJogada(new Jogada(username, pos, 0));
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
        List <Jogada> jog = null;
        boolean criador = false;
        Jogo jogo = null;
        for(Jogo j : lo.getJogosDecorrer()){
            if(j.getCriador().equals(username)){
                jog = j.jogadas;
                criador = true;
                jogo = j;
                break;
            }
            if(j.getParticipante().equals(username)){
                jog = j.jogadas;
                criador = false;
                jogo = j;
                break;
            }
        }
        for(Jogada j : jog){
            String id = "btn" + j.getPosX();
            CommandButton btn = (CommandButton) findComponent(id);
            btn.setDisabled(true);
            if((j.getUserId().equals(username) && criador) || (!j.getUserId().equals(username) && !criador)){
                btn.setValue("X");
            }
            else{
                btn.setValue("O");
            }
        }
        
        int fim = jogo.verificaFim(jogo, username);
       
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
