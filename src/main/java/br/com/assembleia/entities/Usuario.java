/**
 *
 */
package br.com.assembleia.entities;

import br.com.assembleia.enums.EnumAutorizacao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    ,
    @NamedQuery(name = "Usuario.findByUserId", query = "SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId")
    ,
    @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login and u.senha = :senha ")

})
public class Usuario implements Comparable<Usuario>, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @Column(name = "login", unique = true, nullable = false) //tratar o erro
    private String login;
    @Column(name = "senha")
    private String senha;
    @Column(name = "autorizacao")
    @Enumerated(EnumType.STRING)
    private EnumAutorizacao autorizacao;
    @ManyToOne
    private Congregacao congregacao;
    private String email;
    @Column(name = "reiniciarSenha")
    private Boolean reiniciarSenha;
    @Column(name = "reiniciada")
    private Boolean reiniciada;

    public Usuario() {
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public EnumAutorizacao getAutorizacao() {
        return autorizacao;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int compareTo(Usuario t) {
        return this.login.compareToIgnoreCase(t.getLogin());
    }

    public void setAutorizacao(EnumAutorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    public boolean isAdmin(){
        return EnumAutorizacao.ADMIN.equals(this.getAutorizacao());
    }

    public Boolean getReiniciarSenha() {
        return reiniciarSenha;
    }

    public void setReiniciarSenha(Boolean reiniciarSenha) {
        this.reiniciarSenha = reiniciarSenha;
    }

    public Boolean getReiniciada() {
        return reiniciada;
    }

    public void setReiniciada(Boolean reiniciada) {
        this.reiniciada = reiniciada;
    }
}
