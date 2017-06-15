/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "TorneiosUsers.findByUser", query = "SELECT t FROM TorneiosUsers t WHERE t.torneiosUsersPK.user = :user"),
    @NamedQuery(name = "TorneiosUsers.findByTorneio", query = "SELECT t FROM TorneiosUsers t WHERE t.torneiosUsersPK.torneio = :torneio"),
    @NamedQuery(name = "TorneiosUsers.findByData", query = "SELECT t FROM TorneiosUsers t WHERE t.data = :data")})
public class TorneiosUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TorneiosUsersPK torneiosUsersPK;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "torneio", referencedColumnName = "torneio_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Torneios torneios;
    @JoinColumn(name = "user", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public TorneiosUsers() {
    }

    public TorneiosUsers(TorneiosUsersPK torneiosUsersPK) {
        this.torneiosUsersPK = torneiosUsersPK;
    }

    public TorneiosUsers(String user, int torneio) {
        this.torneiosUsersPK = new TorneiosUsersPK(user, torneio);
    }

    public TorneiosUsersPK getTorneiosUsersPK() {
        return torneiosUsersPK;
    }

    public void setTorneiosUsersPK(TorneiosUsersPK torneiosUsersPK) {
        this.torneiosUsersPK = torneiosUsersPK;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Torneios getTorneios() {
        return torneios;
    }

    public void setTorneios(Torneios torneios) {
        this.torneios = torneios;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (torneiosUsersPK != null ? torneiosUsersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TorneiosUsers)) {
            return false;
        }
        TorneiosUsers other = (TorneiosUsers) object;
        if ((this.torneiosUsersPK == null && other.torneiosUsersPK != null) || (this.torneiosUsersPK != null && !this.torneiosUsersPK.equals(other.torneiosUsersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TorneiosUsers[ torneiosUsersPK=" + torneiosUsersPK + " ]";
    }
    
}
