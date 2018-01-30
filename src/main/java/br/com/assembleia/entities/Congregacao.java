package br.com.assembleia.entities;

import br.com.assembleia.enums.EnumEstado;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author fernandosaltoleto
 */
@Entity
@Table(name = "congregacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Congregacao.listarSedes",
            query = "SELECT c FROM Congregacao c where issede = true Order By c.nome  ")
    ,
@NamedQuery(name = "Congregacao.listarCongregacoes",
            query = "SELECT c FROM Congregacao c where issede = false Order By c.nome")
    ,
@NamedQuery(name = "Congregacao.buscarSede",
            query = "SELECT c FROM Congregacao c where issede = true Order By c.nome")
        ,
        @NamedQuery(name = "Congregacao.getById",
                query = "SELECT c FROM Congregacao c where id = :id")
})
public class Congregacao implements Comparable<Congregacao>, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String dirigente;
    private String cnpj;
    private String telefone;
    private String email;
    private EnumEstado estado;
    private String cidade;
    private String bairro;
    private String endereco;
    private String cep;
    private Boolean isSede;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="sede_id")
    private Congregacao sede;
    private byte[] logoIgreja;

    public byte[] getLogoIgreja() {
        return logoIgreja;
    }

    public void setLogoIgreja(byte[] logoIgreja) {
        this.logoIgreja = logoIgreja;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDirigente() {
        return dirigente;
    }

    public void setDirigente(String dirigente) {
        this.dirigente = dirigente;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumEstado getEstado() {
        return estado;
    }

    public void setEstado(EnumEstado estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsSede() {
        return isSede;
    }

    public void setIsSede(Boolean isSede) {
        this.isSede = isSede;
    }

    public Congregacao getSede() {
        return sede;
    }

    public void setSede(Congregacao sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Congregacao)) {
            return false;
        }
        Congregacao other = (Congregacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public int compareTo(Congregacao t) {
        return this.nome.compareToIgnoreCase(t.getNome());
    }

}
