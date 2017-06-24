package logica;

import autenticacao.Util;
import beans.GestaoTorneios;
import entidades.Jogadas;
import entidades.Jogos;
import entidades.Torneios;
import entidades.TorneiosJogos;
import entidades.Users;
import facades.TorneiosFacade;
import facades.TorneiosJogosFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import static logica.EnumEstado.*;

@Singleton
public class Logica {

    @EJB
    private facades.UsersFacade ejbFacadeUsers;
    @EJB
    private facades.JogosFacade ejbFacadeJogos;
    @EJB
    private facades.JogadasFacade ejbFacadeJogadas;
    @EJB
    private TorneiosFacade torneiosFacade;
    @EJB
    private TorneiosJogosFacade torneiosJogosFacade;
    
    private HashMap<Users,HttpSession> sessoes; 
    private static int jogosId;
    //private ArrayList<Users> users;
    //private ArrayList<JogoInterface> jogos;

  
    public Logica() {
        sessoes= new HashMap<>(); 
    }
  
    @Schedule(second = "30")
    public void avancaRonda(){
        boolean pass;
        TypedQuery<Torneios> query =
        torneiosFacade.getEntityManager().createNamedQuery("Torneios.findByEstado", Torneios.class)
                .setParameter("estado",2);
        List<Torneios> torneios = query.getResultList();
        for(Torneios t : torneios){
            pass=false;
            List<TorneiosJogos> tornJogs = t.getTorneiosJogosList();
            for(TorneiosJogos tj : tornJogs){
                if(tj.getJogo().getVencedor()==null){
                    pass = true;
                    break;
                }
            }
            if(pass==false){
                t.setRondaAtual(t.getRondaAtual()+1);
                torneiosFacade.edit(t);
                if(t.getTipo().equals("ELIMINACAO")){
                    createOutrasRondasEliminacao(t);
                }
            }
        }
    }
    
    @Schedule(second = "30")
    public void acabaToreneio(){
        boolean pass;
        TypedQuery<Torneios> query =
        torneiosFacade.getEntityManager().createNamedQuery("Torneios.findByEstado", Torneios.class)
                .setParameter("estado",2);
        List<Torneios> torneios = query.getResultList();
        for(Torneios t : torneios){
            pass=false;
            List<TorneiosJogos> tornJogs = t.getTorneiosJogosList();
            for(TorneiosJogos tj : tornJogs){
                if(tj.getJogo().getVencedor()==null){
                    pass = true;
                    break;
                }
            }
            if(pass==false){
                t.setEstado(EnumEstado.CONCLUIDO.getValue());
                torneiosFacade.edit(t);
            }
        }
    }
    
    public void createOutrasRondasEliminacao(Torneios torneio){
        Users c=null;
        Users p=null;
        List<TorneiosJogos> tjs = torneio.getTorneiosJogosList();
        for (TorneiosJogos tje : tjs) {
            if(torneio.getRondaAtual() == tje.getRonda()){
                if(c==null){
                    c=tje.getJogo().getVencedor();
                }
                else{
                    p=tje.getJogo().getVencedor();
                    //Create game
                    int jId = iniciarJogo(c, p,torneio.getTipoJogo());
                    Jogos jogo = getJogosClass(jId);
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
        }
    }
    
    public boolean existeUsername(String username){
        System.out.println("----existeUsername--"+username);
        Users u = (Users) ejbFacadeUsers.find(username);
        if (u!=null)
            return true;
        return false;
    }
    public Users verificaLogin(String username, String password) {
        Users u = (Users) ejbFacadeUsers.find(username);
        if (u!=null){
            System.out.println("----verificalogin---"+u.getUsername());
             if (password.equals(u.getPassword())){
                 System.out.println("----password---"+u.getPassword());
                 HttpSession session = Util.getSession();
                 if (sessoes.get(u)!=null){
                     sessoes.get(u).invalidate();
                 }
                 sessoes.put(u, session);
                 u.setAtivo(true);
                 u.setSession(session);
                 ejbFacadeUsers.edit(u);                   

                 return u;
             } else {
                    return null;
                }
             
        }
       

        //Caso o utilizador ainda não exista
        return null;
    }

    public void logout(String username) {
        Users u = (Users) ejbFacadeUsers.find(username);
        if (u!=null){
                u.setAtivo(false);
                 ejbFacadeUsers.edit(u);
               sessoes.remove(u);
            }
    }

    public int iniciarJogo(Users criador, EnumTipoJogo tipoJogo) {
        InterfaceJogo j;
       // System.out.println("----tenta iniciar---"+tipoJogo.toString());
       // j = new Jogos(criador,tipoJogo);
      //  System.out.println("----tenta CREATE---"+tipoJogo.toString());
      //  Jogos novo = ejbFacadeJogos.createJogo((Jogos)j);
      //   System.out.println("----NOVO JOGO ID=---"+novo.getJogoId());
       // int jogoId = ejbFacadeJogos.createJogo((Jogos)j);
        
        switch (tipoJogo) {
            case JOGO_GALO:
               j = ejbFacadeJogos.createJogo(new JogoGalo(criador));
               break;
            case JOGO_QUATRO_EM_LINHA:
            default:
                j = ejbFacadeJogos.createJogo(new JogoQuatroEmLinha(criador));
        }
        //j.setId(jogosId);
        //jogos.add(j);
       // EntityTransaction trans = ejbFacadeJogos.getEntityManager().getTransaction();
      // trans.begin();
  
        //trans.commit();
        // TODO user id do objeto criado
       // jogosId++;
       
        return j.getJogoId();
        //return j.getJogoId();
    }
    
    //Funcao para torneios para criar jogos automaticos entre dois jogadores
    public int iniciarJogo(Users criador, Users participante, String tipoJogo) {
        InterfaceJogo j;
        
        switch (tipoJogo) {
            case "JOGO_GALO":
               j = ejbFacadeJogos.createJogo(new JogoGalo(criador,participante));
               break;
            case "JOGO_QUATRO_EM_LINHA":
            default:
                j = ejbFacadeJogos.createJogo(new JogoQuatroEmLinha(criador,participante));
        }
       
        return j.getJogoId();
    }

    public void juntarJogo(int idJogo, Users participante) {
        InterfaceJogo jogo = ejbFacadeJogos.find(idJogo);
        
            if (jogo!=null) {
                jogo.setParticipante(participante);
                jogo.setEmEspera(false);
                jogo.setEstado(DECORRER.getValue());
                ejbFacadeJogos.edit((Jogos)jogo);
            }
        
    }

    public InterfaceJogo getJogo(int id) {
        InterfaceJogo jogo = ejbFacadeJogos.find(id);
        
            if (jogo!= null) {
                
                return jogo;
           }
        
        return null;
    }
    
    public Jogos getJogosClass(int id) {
        Jogos jogo = ejbFacadeJogos.find(id);
        
        if (jogo!= null) {        
            return jogo;
        }
        
        return null;
    }

    public List<Jogos> getJogosIniciados(String jogo) {
        // TODO query por jogos em espera
        Integer estado = INICIADO.getValue();
        TypedQuery<Jogos> query =
        ejbFacadeJogos.getEntityManager().createNamedQuery("Jogos.findByEstado", Jogos.class)
                .setParameter("estado",estado);
                
        List<Jogos> jogosIniciados = query.getResultList();
        
       // ArrayList<JogoInterface> jogosEmEspera = new ArrayList<>();
       // for (InterfaceJogo jogo : jogosEmEspera) {
       //     if (jogo.isEmEspera()) {
                //JogoLogica j = new InterfaceJogo(jogo);
       //         jogosEmEspera.add(jogo);
       //     }
       // }
       //return jogosIniciados;
       if (jogosIniciados!=null){
            List<Jogos> js = new ArrayList<>();
        for(Jogos j: jogosIniciados){
            if(j.getTipo().equals(jogo))
                js.add(j);
        }
        return js;
       }else
           return null;
       
    }

    public List<Jogos> getJogosDecorrer(String jogo) {
        // TODO query por jogos a decorrer
        List<Jogos> todosJogos = ejbFacadeJogos.findAll();
        Integer estado = DECORRER.getValue();
        TypedQuery<Jogos> query =
        ejbFacadeJogos.getEntityManager().createNamedQuery("Jogos.findByEstado", Jogos.class)
                .setParameter("estado",estado);
        List<Jogos> jogosDecorrer = query.getResultList();
        //ArrayList<JogoInterface> jogosDecorrer = new ArrayList<>();
        //for (InterfaceJogo jogo : todosJogos) {
        //    if (!jogo.isEmEspera()) {
        //        jogosDecorrer.add(jogo);
        //    }
       // }
        //return jogosDecorrer;
        
        if (jogosDecorrer!=null){
            List<Jogos> js = new ArrayList<>();
        for(Jogos j: jogosDecorrer){
            if(j.getTipo().equals(jogo))
                js.add(j);
        }
        return js;
       }else
           return null;
        
      
    }

    public boolean fazJogada(int idJogo, Users por, String jogada) {
        InterfaceJogo j = ejbFacadeJogos.find(idJogo);
        
            if (j!=null) {
                return j.avaliaJogada(por, jogada);
            }
        

        return false;
    }

    public boolean terminaJogo(int idJogo) {
        InterfaceJogo j = ejbFacadeJogos.find(idJogo);
        if (j!=null) {
                return j.terminaJogo();
            }
        

        return false;
    }

    public ArrayList<String> listarAtivos() {
        List<Users> todosUsers = ejbFacadeUsers.findAll();
        ArrayList<String> utilizadoresAtivos = new ArrayList<>();
        for (Users user : todosUsers) {
            if (user.isAtivo()) {
                utilizadoresAtivos.add(user.getUsername());
            }
        }

        return utilizadoresAtivos;
    }

    public int getJogoCriadoAtualmente(Users u) {
        System.out.println("----getJogoCriadoAtualmente---");
        // TODO melhorar isto
        List<Jogos> todosJogos = ejbFacadeJogos.findAll();
        for (InterfaceJogo jogo : todosJogos) {
            if (jogo.getCriador().equals(u) && !jogo.isConcluido()) {
                return jogo.getJogoId();
            }
        }
        return -1;
    }
    
    public boolean jogoTerminado(int idJogo) {
         InterfaceJogo j = ejbFacadeJogos.find(idJogo);
        if (j!=null) {
                return j.isConcluido();
            }
        return false;
    }
    
    public boolean verificaJogadorExiste(String username){
        Users u = ejbFacadeUsers.find(username);
        if (u!=null){
             return true;
        }
        return false;
    }
    
    public void registaJogador(String username,String password,String email,String morada){
        Users u = new Users(username, password, email, morada, false);
        //users.add(u);
        try {
            ejbFacadeUsers.create(u);
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
    }
    
    public String getPassword(String username){
        Users u = ejbFacadeUsers.find(username);
        if (u!=null){
            return u.getPassword();
        }
        return "";
    }
    
    public void alterarPassword(String username, String password){
        Users u = ejbFacadeUsers.find(username);
       if (u!=null){
                u.setPassword(password);

            }
    }
    
    public void alterarEmail(String username, String email){
        Users u = ejbFacadeUsers.find(username);
       if (u!=null){
                u.setEmail(email);
        }
    }
    
    public void alterarMorada(String username, String morada){
         Users u = ejbFacadeUsers.find(username);
       if (u!=null){
                u.setMorada(morada);
        }
    }

    //-------------------------------------//-------------------------------//-----------------------
   // int turno = 0; // 0 -> criador ; 1 -> participante

    //metodo podeJogar-> recebe nome do jogador que jogou, e posicao 
    public boolean podeJogar(String username, int pos, int jogoId) {
       InterfaceJogo jogo = ejbFacadeJogos.find(jogoId);
       System.out.println("------Logica.podeJogar---"+jogo.getJogoId());
       if (jogo!=null){
           System.out.println("------turno---"+jogo.getTurno());
           String turno = jogo.getTurno();
           if (turno!=null){
               if (jogo.getTurno().equals(username)) {
                   if (!jogo.isConcluido()) {
                       if(jogo.getCriador().getUsername().equals(username)){
                           jogo.setTurno(jogo.getParticipante().getUsername());
                       }
                       else{                        
                           jogo.setTurno(jogo.getCriador().getUsername());
                       }
                       return true;
                   }
                   else {
                       return false;
                   }
               }
               else{
                   return false;
               }
           }else {
               jogo.setTurno(jogo.getCriador().getUsername());
               return (username.equals(jogo.getTurno()));

           }
           
       }
       return false;
   }
    
    public Jogadas fazJogada(Users user, int posX, int posY, InterfaceJogo jogo){
        Jogadas nova = new Jogadas(user, posX, posY, jogo);
        jogo.atualizaJogada(nova);
        ejbFacadeJogadas.createJogada(nova);
        return nova;
    }
   
   /* public boolean termina(Users username, int pos, int JogoId) {
        InterfaceJogo jogo = ejbFacadeJogos.find(JogoId);
            if (jogo!=null) {
                if (turno == 0) {
                    if (!jogo.isConcluido()) {
                        jogo.terminaTemp(username, pos);
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (jogo.getParticipante().equals(username)) {
                if (turno == 1) {
                    if (!jogo.isConcluido()) {
                        jogo.terminaTemp(username, pos);
                        return true;
                    }
                } else {
                    return false;
                }
            }
        
        return false;
    }*/

    public boolean isAtivo(String username) {
        Users u = ejbFacadeUsers.find(username);
    
            if (u!=null) {
                if (u.isAtivo()) {
                    return true;
                }
            }
        
        return false;
    }
}
