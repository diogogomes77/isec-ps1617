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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diogo
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username")
    , @NamedQuery(name = "Users.findByAtivo", query = "SELECT u FROM Users u WHERE u.ativo = :ativo")
    , @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email")
    , @NamedQuery(name = "Users.findByMorada", query = "SELECT u FROM Users u WHERE u.morada = :morada")
    , @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password")})
public class Users implements Serializable {

    @OneToMany(mappedBy = "username")
    private List<Jogadas> jogadasList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username")
    protected String username;
    @Column(name = "ativo")
    protected Boolean ativo;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    protected String email;
    @Size(max = 255)
    @Column(name = "morada")
    protected String morada;
    @Size(max = 255)
    @Column(name = "password")
    protected String password;
    @OneToMany(mappedBy = "criador")
    protected List<Jogos> jogosList;
    @OneToMany(mappedBy = "participante")
    protected List<Jogos> jogosList1;
    @OneToMany(mappedBy = "vencedor")
    protected List<Jogos> jogosList2;

    @Transient
    private HttpSession session;
    
    public Users() {
    }

    public Users(String username) {
        this.username = username;
    }

    public Users(String usename, String password, boolean ativo) {
   
        this.username = usename;
        this.password = password;
        this.ativo = ativo;
        
        
    }
    
    public Users(String usename, String password, String email, String morada, boolean ativo) {

        this.username = usename;
        this.password = password;
        this.ativo = ativo;
 
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Jogos> getJogosList() {
        return jogosList;
    }

    public void setJogosList(List<Jogos> jogosList) {
        this.jogosList = jogosList;
    }

    @XmlTransient
    public List<Jogos> getJogosList1() {
        return jogosList1;
    }

    public void setJogosList1(List<Jogos> jogosList1) {
        this.jogosList1 = jogosList1;
    }

    @XmlTransient
    public List<Jogos> getJogosList2() {
        return jogosList2;
    }

    public void setJogosList2(List<Jogos> jogosList2) {
        this.jogosList2 = jogosList2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
        //return "entidades.Users[ username=" + username + " ]";
    }
        
    public boolean isAtivo() {
        return (boolean) ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    @XmlTransient
    public List<Jogadas> getJogadasList() {
        return jogadasList;
    }

    public void setJogadasList(List<Jogadas> jogadasList) {
        this.jogadasList = jogadasList;
    }
}
