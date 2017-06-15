/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author diogo
 */
@Embeddable
public class TorneiosUsersPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @NotNull
    @Column(name = "torneio")
    private int torneio;

    public TorneiosUsersPK() {
    }

    public TorneiosUsersPK(String user, int torneio) {
        this.user = user;
        this.torneio = torneio;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTorneio() {
        return torneio;
    }

    public void setTorneio(int torneio) {
        this.torneio = torneio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (user != null ? user.hashCode() : 0);
        hash += (int) torneio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TorneiosUsersPK)) {
            return false;
        }
        TorneiosUsersPK other = (TorneiosUsersPK) object;
        if ((this.user == null && other.user != null) || (this.user != null && !this.user.equals(other.user))) {
            return false;
        }
        if (this.torneio != other.torneio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TorneiosUsersPK[ user=" + user + ", torneio=" + torneio + " ]";
    }
    
}
