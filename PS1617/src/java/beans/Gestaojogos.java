package beans;

import autenticacao.Util;
import com.sun.faces.component.visit.FullVisitContext;
import entidades.Jogadas;
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
    private  Users user;
    private  HttpSession session;
    @EJB
    private beans.UsersFacade ejbFacade;
    
    public Gestaojogos() {
        // this.username = sessao.getUsername();
        // System.out.println("---- Gestao jogos iniciada "+username);
        // this.username = "okok";
        this.session = Util.getSession();
        this.username=session.getAttribute("username").toString();
        System.out.println("---- Gestao jogos iniciada "+username);
       
    }

    public String iniciarJogo(TipoJogo tipoJogo) {
        if (username != null) {
            if(sessao.getJogoId() < 1){
                this.user = ejbFacade.find(username);
                System.out.println("---- Encontrou "+user.getUsername());
                sessao.setJogoId(lo.iniciarJogo(user, tipoJogo));
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
       for (JogoLogica jogo : lo.getJogosIniciados()) {
            if (jogo.getJogoId()==id) {
                return !jogo.getCriador().equals(user);
            }
        }
        return false;
    }
    public boolean possoJogar(int id){
       for (JogoLogica jogo : lo.getJogosDecorrer()) {
            if (jogo.getJogoId()==id) {
                if (jogo.getCriador().equals(user) || jogo.getParticipante().equals(user))
                    return true;
            }
        }
        return false;
    }
    
    public String jogar(int id){
        sessao.setJogoId(id);
        return "/area_privada/jogo";
    }
    
    public ArrayList<JogoLogica> listarJogosIniciados() {
        return lo.getJogosIniciados();
    }

    public ArrayList<JogoLogica> listarJogosDecorrer() {
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
            for(JogoLogica j : lo.getJogosDecorrer()){
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
        JogoLogica jogo = null;
        for(JogoLogica j : lo.getJogosDecorrer()){
            if(j.getCriador().equals(user)){
                jog = j.jogadasList;
                criador = true;
                jogo = j;
                break;
            }
            if(j.getParticipante().equals(user)){
                jog = j.jogadasList;
                criador = false;
                jogo = j;
                break;
            }
        }
        for(Jogadas j : jog){
            String id = "btn" + j.getPosX();
            CommandButton btn = (CommandButton) findComponent(id);
            btn.setDisabled(true);
            if((j.getUsername()==user && criador) || (!(j.getUsername()==user) && !criador)){
                btn.setValue("X");
            }
            else{
                btn.setValue("O");
            }
        }
        
        int fim = jogo.verificaFim(jogo, user);
       
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
