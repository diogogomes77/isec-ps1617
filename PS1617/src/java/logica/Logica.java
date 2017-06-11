package logica;

import autenticacao.Util;
import entidades.Jogos;
import entidades.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
    private static int jogosId;
    //private ArrayList<Users> users;
    //private ArrayList<JogoInterface> jogos;

  
    public Logica() {
        //users = new ArrayList<>();
        //jogos = new ArrayList<>();
        jogosId=0;
    }
  
    public Users verificaLogin(String username, String password) {
        // TODO usar facade
        Users u = (Users) ejbFacadeUsers.find(username);
        if (u!=null){
            System.out.println("----verificalogin---"+u.getUsername());
             if (password.equals(u.getPassword())){
                 System.out.println("----password---"+u.getPassword());
                 if (u.isAtivo()) {
                        //Caso o utilizador já exista e tenha uma sessão ativa
                        // terminar outras sessoes do user
                          
                          if (u.getSession()!=null){
                              u.getSession().invalidate();
                          }
                            HttpSession session = Util.getSession();
                            u.setSession(session);
                      
                    } else {
                        //Caso o utilizador já exista e não tenha ainda uma sessão ativa
                        u.setAtivo(true);
                       
                    }
                 return u;
             } else {
                    return null;
                }
             
        }
        
   /*     for (Users user : users) {
            if (username.equals(user.getUsername())) {
                if (password.equals(user.getPassword())) {
                    if (user.isAtivo()) {

                          HttpSession session = Util.getSession();
                            user.getSession().invalidate();
                            user.setSession(session);
                      
                    } else {
                       
                        user.setAtivo(true);
                       
                    }
                    return user;
                } else {
                    return null;
                }
            }
        }*/

        //Caso o utilizador ainda não exista
        return null;
    }

    /*public void logout(String username) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                user.setAtivo(false);
                return;
            }
        }
    }*/

    public int iniciarJogo(Users criador, EnumTipoJogo tipoJogo) {
        InterfaceJogo j;
        System.out.println("----tenta iniciar---"+tipoJogo.toString());
        j = new Jogos(criador,tipoJogo);
        System.out.println("----tenta CREATE---"+tipoJogo.toString());
        Jogos novo = ejbFacadeJogos.createJogo((Jogos)j);
         System.out.println("----NOVO JOGO ID=---"+novo.getJogoId());
       // int jogoId = ejbFacadeJogos.createJogo((Jogos)j);
        
      /*  switch (tipoJogo) {
            case JOGO_GALO:
               j = new JogoGalo(criador,tipoJogo);
               break;
            case JOGO_QUATRO_EM_LINHA:
            default:
                j = new JogoQuatroEmLinha(criador,tipoJogo);
        }*/
        //j.setId(jogosId);
        //jogos.add(j);
       // EntityTransaction trans = ejbFacadeJogos.getEntityManager().getTransaction();
      // trans.begin();
  
        //trans.commit();
        // TODO user id do objeto criado
       // jogosId++;
        return novo.getJogoId();
        //return j.getJogoId();
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

    public List<Jogos> getJogosIniciados() {
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
        return jogosIniciados;
    }

    public List<Jogos> getJogosDecorrer() {
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
        return jogosDecorrer;
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
    int turno = 0; // 0 -> criador ; 1 -> participante

    //metodo joga-> recebe nome do jogador que jogou, e posicao 
    public boolean joga(String username, int pos, int jogoId) {
        InterfaceJogo jogo = ejbFacadeJogos.find(jogoId);
        if (jogo!=null){
                if (turno == 0) {
                    if (!jogo.isConcluido()) {
                        turno = 1;
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (jogo.getParticipante().getUsername().equals(username)) {
                if (turno == 1) {
                    if (!jogo.isConcluido()) {
                        turno = 0;
                        return true;
                    }
                    return true;
                } else {
                    return false;
                }
            }
        
        return false;
    }

    public boolean termina(Users username, int pos, int JogoId) {
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
    }

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
