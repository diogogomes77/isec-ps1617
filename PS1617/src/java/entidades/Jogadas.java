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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diogo
 */
@Entity
@Table(name = "jogadas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jogadas.findAll", query = "SELECT j FROM Jogadas j")
    , @NamedQuery(name = "Jogadas.findByPosX", query = "SELECT j FROM Jogadas j WHERE j.posX = :posX")
    , @NamedQuery(name = "Jogadas.findByJogadaId", query = "SELECT j FROM Jogadas j WHERE j.jogadaId = :jogadaId")
    , @NamedQuery(name = "Jogadas.findByPosY", query = "SELECT j FROM Jogadas j WHERE j.posY = :posY")})
public class Jogadas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "posX")
    private Integer posX;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "jogada_id")
    private Integer jogadaId;
    @Column(name = "posY")
    private Integer posY;
    @JoinColumn(name = "jogo_id", referencedColumnName = "jogo_id")
    @ManyToOne
    private Jogos jogoId;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private Users username;

    public Jogadas() {
    }
    public Jogadas(Users username, int posX, int posY) {
        this.username = username;
        this.posX = posX;
        this.posY = posY;
    }
    public Jogadas(Integer jogadaId) {
        this.jogadaId = jogadaId;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getJogadaId() {
        return jogadaId;
    }

    public void setJogadaId(Integer jogadaId) {
        this.jogadaId = jogadaId;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Jogos getJogoId() {
        return jogoId;
    }

    public void setJogoId(Jogos jogoId) {
        this.jogoId = jogoId;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jogadaId != null ? jogadaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jogadas)) {
            return false;
        }
        Jogadas other = (Jogadas) object;
        if ((this.jogadaId == null && other.jogadaId != null) || (this.jogadaId != null && !this.jogadaId.equals(other.jogadaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Jogadas[ jogadaId=" + jogadaId + " ]";
    }
    
}
