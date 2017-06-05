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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diogo
 */
@Entity
@Table(name = "jogos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jogos.findAll", query = "SELECT j FROM Jogos j")
    , @NamedQuery(name = "Jogos.findByJogoId", query = "SELECT j FROM Jogos j WHERE j.jogoId = :jogoId")
    , @NamedQuery(name = "Jogos.findByEstado", query = "SELECT j FROM Jogos j WHERE j.estado = :estado")
    , @NamedQuery(name = "Jogos.findByTabuleiro", query = "SELECT j FROM Jogos j WHERE j.tabuleiro = :tabuleiro")
    , @NamedQuery(name = "Jogos.findByTurno", query = "SELECT j FROM Jogos j WHERE j.turno = :turno")})
public class Jogos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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

    public Jogos(Integer jogoId) {
        this.jogoId = jogoId;
    }

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

    public Users getCriador() {
        return criador;
    }

    public void setCriador(Users criador) {
        this.criador = criador;
    }

    public Users getParticipante() {
        return participante;
    }

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

    @Override
    public String toString() {
        return "entidades.Jogos[ jogoId=" + jogoId + " ]";
    }
    
}
