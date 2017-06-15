/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diogo
 */
@Entity
@Table(name = "torneios_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TorneiosUsers.findAll", query = "SELECT t FROM TorneiosUsers t"),
    @NamedQuery(name = "TorneiosUsers.findByData", query = "SELECT t FROM TorneiosUsers t WHERE t.data = :data"),
    @NamedQuery(name = "TorneiosUsers.findByUserTorneioId", query = "SELECT t FROM TorneiosUsers t WHERE t.userTorneioId = :userTorneioId")})
public class TorneiosUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_torneio_id")
    private Integer userTorneioId;
    @JoinColumn(name = "torneio", referencedColumnName = "torneio_id")
    @ManyToOne
    private Torneios torneio;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private Users username;

    public TorneiosUsers() {
    }

    public TorneiosUsers(Integer userTorneioId) {
        this.userTorneioId = userTorneioId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getUserTorneioId() {
        return userTorneioId;
    }

    public void setUserTorneioId(Integer userTorneioId) {
        this.userTorneioId = userTorneioId;
    }

    public Torneios getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneios torneio) {
        this.torneio = torneio;
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
        hash += (userTorneioId != null ? userTorneioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TorneiosUsers)) {
            return false;
        }
        TorneiosUsers other = (TorneiosUsers) object;
        if ((this.userTorneioId == null && other.userTorneioId != null) || (this.userTorneioId != null && !this.userTorneioId.equals(other.userTorneioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TorneiosUsers[ userTorneioId=" + userTorneioId + " ]";
    }
    
}
