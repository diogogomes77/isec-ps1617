/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diogo
 */
@Entity
@Table(name = "torneios_jogos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TorneiosJogos.findAll", query = "SELECT t FROM TorneiosJogos t"),
    @NamedQuery(name = "TorneiosJogos.findByJogoTorneioId", query = "SELECT t FROM TorneiosJogos t WHERE t.jogoTorneioId = :jogoTorneioId"),
    @NamedQuery(name = "TorneiosJogos.findByRonda", query = "SELECT t FROM TorneiosJogos t WHERE t.ronda = :ronda")})
public class TorneiosJogos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "jogo_torneio_id")
    private Integer jogoTorneioId;
    @Column(name = "ronda")
    private Integer ronda;
    @JoinColumn(name = "jogo", referencedColumnName = "jogo_id")
    @ManyToOne
    private Jogos jogo;
    @JoinColumn(name = "torneio", referencedColumnName = "torneio_id")
    @ManyToOne
    private Torneios torneio;

    public TorneiosJogos() {
    }

    public TorneiosJogos(Integer jogoTorneioId) {
        this.jogoTorneioId = jogoTorneioId;
    }

    public Integer getJogoTorneioId() {
        return jogoTorneioId;
    }

    public void setJogoTorneioId(Integer jogoTorneioId) {
        this.jogoTorneioId = jogoTorneioId;
    }

    public Integer getRonda() {
        return ronda;
    }

    public void setRonda(Integer ronda) {
        this.ronda = ronda;
    }

    public Jogos getJogo() {
        return jogo;
    }

    public void setJogo(Jogos jogo) {
        this.jogo = jogo;
    }

    public Torneios getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneios torneio) {
        this.torneio = torneio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jogoTorneioId != null ? jogoTorneioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TorneiosJogos)) {
            return false;
        }
        TorneiosJogos other = (TorneiosJogos) object;
        if ((this.jogoTorneioId == null && other.jogoTorneioId != null) || (this.jogoTorneioId != null && !this.jogoTorneioId.equals(other.jogoTorneioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TorneiosJogos[ jogoTorneioId=" + jogoTorneioId + " ]";
    }
    
}
