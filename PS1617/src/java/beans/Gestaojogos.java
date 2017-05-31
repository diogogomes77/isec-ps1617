package beans;

import autenticacao.Util;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import logica.Jogo;
import logica.Logica;
import logica.Logica.TipoJogo;
import logica.Sessao;
import org.primefaces.context.RequestContext;

@Named("gestaojogos")
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
        return "index";
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

    public String joga(int pos){
        boolean ok;
        boolean t=false;
        switch (pos) {
            case 0:
                ok = lo.joga(username, pos);
                break;
            case 1:
                ok = lo.termina(username, 1);
                t=true;
                break;
            case 2:
                ok = lo.termina(username, -1);
                t=true;
                break;
            default:
                ok = lo.termina(username, 0);
                t=true;
                break;
        }
        
        if(!ok){
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlg').show();");
        }
        if(t==true){
            return "/area_privada/gestaojogos";
        }
        else{
            return "/area_privada/jogo";
        }
    }
}
