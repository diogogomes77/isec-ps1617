/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import logica.JogoInterface;
import logica.TipoJogo;


/**
 *
 * @author diogo
 */
@Entity
@Table(name = "jogos")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jogos.findAll", query = "SELECT j FROM Jogos j")
    , @NamedQuery(name = "Jogos.findByJogoId", query = "SELECT j FROM Jogos j WHERE j.jogoId = :jogoId")
    , @NamedQuery(name = "Jogos.findByEstado", query = "SELECT j FROM Jogos j WHERE j.estado = :estado")
    , @NamedQuery(name = "Jogos.findByTabuleiro", query = "SELECT j FROM Jogos j WHERE j.tabuleiro = :tabuleiro")
    , @NamedQuery(name = "Jogos.findByTurno", query = "SELECT j FROM Jogos j WHERE j.turno = :turno")
    , @NamedQuery(name = "Jogos.findByTipo", query = "SELECT j FROM Jogos j WHERE j.tipo = :tipo")})
public class Jogos implements Serializable , JogoInterface{

    private static final long serialVersionUID = 1L;
    /*
        @Id
    @SequenceGenerator(name="jogos_seq",
                       sequenceName="jogos_seq",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="jogos_seq")
    @Column(name = "jogo_id", updatable=false)
    
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "jogo_id")
    private Integer jogoId;
    @Column(name = "estado")
    private Integer estado;
    @Size(max = 255)
    @Column(name = "tabuleiro")
    private String tabuleiro;
    @Size(max = 255)
    @Column(name = "turno")
    private String turno;
    @Size(max = 255)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "jogoId")
    private List<Jogadas> jogadasList;
    @JoinColumn(name = "criador", referencedColumnName = "username")
    @ManyToOne
    private Users criador;
    @JoinColumn(name = "participante", referencedColumnName = "username")
    @ManyToOne
    private Users participante;
    @JoinColumn(name = "vencedor", referencedColumnName = "username")
    @ManyToOne
    private Users vencedor;

    public Jogos() {
    }

    public Jogos(Users criador,TipoJogo tipo) {
        this.criador=criador;
        this.tipo=tipo.toString();
    }

    public Jogos(Integer jogoId) {
        this.jogoId = jogoId;
    }

    @Override
    public Integer getJogoId() {
        return jogoId;
    }

    public void setJogoId(Integer jogoId) {
        this.jogoId = jogoId;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(String tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    @Override
    public List<Jogadas> getJogadasList() {
        return jogadasList;
    }

    public void setJogadasList(List<Jogadas> jogadasList) {
        this.jogadasList = jogadasList;
    }

    @Override
    public Users getCriador() {
        return criador;
    }

    public void setCriador(Users criador) {
        this.criador = criador;
    }

    @Override
    public Users getParticipante() {
        return participante;
    }

    @Override
    public void setParticipante(Users participante) {
        this.participante = participante;
    }

    public Users getVencedor() {
        return vencedor;
    }

    public void setVencedor(Users vencedor) {
        this.vencedor = vencedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jogoId != null ? jogoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jogos)) {
            return false;
        }
        Jogos other = (Jogos) object;
        if ((this.jogoId == null && other.jogoId != null) || (this.jogoId != null && !this.jogoId.equals(other.jogoId))) {
            return false;
        }
        return true;
    }


        //Função para condizer com o método que o pedro fez de fazer jogada vai ser remvida no futuro
    //public abstract boolean terminaTemp(String username,int i);
    
    @Override
    public void adicionaJogada(Jogadas jogada) {
        this.jogadasList.add(jogada);
    }
    
   // public int getNumeroJogadas_() {
   //     return this.jogadasList.size();
   // }
    @Override
    public String toString(){
        String result;
        if (participante==null) result = "Iniciado por "+criador.getUsername();
        else result = criador+" vs "+participante;
        return result;
    }
    
    //@Override
    public String toString_() {
        return "entidades.Jogos[ jogoId=" + jogoId + " ]";
    }
    
    @Transient
    protected boolean emEspera;
    @Transient
    protected boolean concluido;
 //Variaveis para este jogo em especifico
    @Transient
    protected String comando;
    @Override
    public boolean isEmEspera() {
        return emEspera;
    }

    @Override
    public void setEmEspera(Boolean emEspera) {
        this.emEspera = emEspera;
    }

    @Override
    public boolean isConcluido() {
        return concluido;
    }

    @Override
    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

@Override
    public boolean avaliaJogada(Users por, String jogada) {
        if (por.getUsername().equals(turno) && emEspera == false) {
            switch (jogada) {
                case "Ganhar":
                    comando = jogada;
                    return true;
                case "Perder":
                    comando = jogada;
                    return true;
                case "Empate":
                    comando = jogada;
                    return true;
                case "Passou":
                    if (por.equals(criador)) {
                        turno = participante.getUsername();
                    } else {
                        turno = criador.getUsername();
                    }
                    comando = jogada;
                    return true;
                default:
                    //Jogada inválida
                    return false;
            }
        } else {
            //Jogada fora de turno
            return false;
        }
    }

@Override
    public boolean terminaJogo() {
        switch (comando) {
            case "Ganhar":
                if (turno.equals(criador.getUsername())) {
                    vencedor = criador;
                } else {
                    vencedor = participante;
                }
                concluido = true;
                return true;
            case "Perder":
                if (turno.equals(criador.getUsername())) {
                    vencedor = participante;
                } else {
                    vencedor = criador;
                }
                concluido = true;
                return true;
            case "Empate":
                vencedor = null;
                concluido = true;
                return true;
            default:
                //Jogada não termina
                return false;
        }
    }

 @Override
    public boolean terminaTemp(Users username, int i) {
        switch (i) {
            //Jogador Atual Perde
            case -1:
                if (username.equals(criador)) {
                    vencedor = participante;
                } else {
                    vencedor = criador;
                }
                concluido = true;
                return true;
            //Empate
            case 0:
                vencedor = null;
                concluido = true;
                return true;
            //Jogador Atual Ganha
            case 1:
                if (username.equals(criador)) {
                    vencedor = criador;
                } else {
                    vencedor = participante;
                }
                concluido = true;
                return true;
        }
        return false;
    }

   
    
}
