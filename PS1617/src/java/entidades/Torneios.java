/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diogo
 */
@Entity
@Table(name = "torneios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Torneios.findAll", query = "SELECT t FROM Torneios t"),
    @NamedQuery(name = "Torneios.findByTorneioId", query = "SELECT t FROM Torneios t WHERE t.torneioId = :torneioId"),
    @NamedQuery(name = "Torneios.findByMaxJogadores", query = "SELECT t FROM Torneios t WHERE t.maxJogadores = :maxJogadores"),
    @NamedQuery(name = "Torneios.findByTipoJogo", query = "SELECT t FROM Torneios t WHERE t.tipoJogo = :tipoJogo"),
    @NamedQuery(name = "Torneios.findByRondaAtual", query = "SELECT t FROM Torneios t WHERE t.rondaAtual = :rondaAtual"),
    @NamedQuery(name = "Torneios.findByEstado", query = "SELECT t FROM Torneios t WHERE t.estado = :estado"),
    @NamedQuery(name = "Torneios.findByNada", query = "SELECT t FROM Torneios t WHERE t.nada = :nada"),
    @NamedQuery(name = "Torneios.findByNada2", query = "SELECT t FROM Torneios t WHERE t.nada2 = :nada2")})
public class Torneios implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "torneios")
    private List<TorneiosUsers> torneiosUsersList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "torneio_id")
    private Integer torneioId;
    @Column(name = "max_jogadores")
    private Integer maxJogadores;
    @Size(max = 2147483647)
    @Column(name = "tipo_jogo")
    private String tipoJogo;
    @Column(name = "ronda_atual")
    private Integer rondaAtual;
    @Column(name = "estado")
    private Integer estado;
    @Basic(optional = false)
    @Column(name = "nada")
    private int nada;
    @Basic(optional = false)
    @Column(name = "nada2")
    private int nada2;
    @OneToMany(mappedBy = "torneio")
    private List<TorneiosJogos> torneiosJogosList;

    public Torneios() {
    }

    public Torneios(Integer torneioId) {
        this.torneioId = torneioId;
    }

    public Torneios(Integer torneioId, int nada, int nada2) {
        this.torneioId = torneioId;
        this.nada = nada;
        this.nada2 = nada2;
    }

    public Integer getTorneioId() {
        return torneioId;
    }

    public void setTorneioId(Integer torneioId) {
        this.torneioId = torneioId;
    }

    public Integer getMaxJogadores() {
        return maxJogadores;
    }

    public void setMaxJogadores(Integer maxJogadores) {
        this.maxJogadores = maxJogadores;
    }

    public String getTipoJogo() {
        return tipoJogo;
    }

    public void setTipoJogo(String tipoJogo) {
        this.tipoJogo = tipoJogo;
    }

    public Integer getRondaAtual() {
        return rondaAtual;
    }

    public void setRondaAtual(Integer rondaAtual) {
        this.rondaAtual = rondaAtual;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public int getNada() {
        return nada;
    }

    public void setNada(int nada) {
        this.nada = nada;
    }

    public int getNada2() {
        return nada2;
    }

    public void setNada2(int nada2) {
        this.nada2 = nada2;
    }

    @XmlTransient
    public List<TorneiosJogos> getTorneiosJogosList() {
        return torneiosJogosList;
    }

    public void setTorneiosJogosList(List<TorneiosJogos> torneiosJogosList) {
        this.torneiosJogosList = torneiosJogosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (torneioId != null ? torneioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Torneios)) {
            return false;
        }
        Torneios other = (Torneios) object;
        if ((this.torneioId == null && other.torneioId != null) || (this.torneioId != null && !this.torneioId.equals(other.torneioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Torneios[ torneioId=" + torneioId + " ]";
    }

    @XmlTransient
    public List<TorneiosUsers> getTorneiosUsersList() {
        return torneiosUsersList;
    }

    public void setTorneiosUsersList(List<TorneiosUsers> torneiosUsersList) {
        this.torneiosUsersList = torneiosUsersList;
    }
    
}
