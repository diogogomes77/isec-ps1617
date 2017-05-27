package beans;

import autenticacao.Util;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.websocket.SessionException;
import logica.Jogo;
import logica.Logica;
import logica.Sessao;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class Gestaojogos implements Serializable {

    @EJB
    Sessao sessao;
    @EJB
    Logica lo;
    private String username;
    
    public Gestaojogos() {
        // this.username = sessao.getUsername();
        // this.username = "okok";
    }

    public String iniciarJogo() {
        if (username != null) {
            sessao.setJogoId(lo.iniciarJogo(username));
        }
        return "gestaojogos";
    }

    public String juntarJogo(int id) {
        if (username != null && !lo.getJogo(id).getCriador().equals(username)) {
            lo.juntarJogo(id, username);
            sessao.setJogoId(id);
            return "jogo";
        }
        return "gestaojogos";
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
        return "jogo";
    }
    
    public ArrayList<Jogo> listarJogosIniciados() {
        return lo.getJogosIniciados();
    }

    public ArrayList<Jogo> listarJogosDecorrer() {
        return lo.getJogosDecorrer();
    }
    
    public boolean fazJogada(String jogada) {
        return lo.fazJogada(sessao.getJogoId(),username, jogada);
    }

    public boolean terminaJogo() {
        if(lo.terminaJogo(sessao.getJogoId())){
            sessao.setJogoId(-1);
            return true;
        }
        return false;
    }
    
    @PostConstruct
    public void init() {
        HttpSession session = Util.getSession();
        this.username = (String) session.getAttribute("username");
    }
    

//----------------------------//--------------------------    

    public void joga(int pos){
        boolean ok = lo.joga(username, pos);
        if(!ok){
            RequestContext r = RequestContext.getCurrentInstance();
            r.execute("PF('dlg').show();");
        }
    }
}
