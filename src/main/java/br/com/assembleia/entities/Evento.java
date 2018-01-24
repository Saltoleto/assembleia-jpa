package br.com.assembleia.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fernandosaltoleto
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Evento.listarPorIgreja",
                query = "SELECT d FROM Evento d JOIN d.congregacao i WHERE i.id = :idIgreja")
})
public class Evento implements Serializable, Comparable<Evento> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataEncerramento;
    private String localEvento;
    private String telefone;
    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Convidado> convidados;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Membro> participantes;
    @ManyToOne
    private Congregacao congregacao;

    public Evento() {
        convidados = new ArrayList<Convidado>();
        participantes = new ArrayList<Membro>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Convidado> getConvidados() {
        return convidados;
    }

    public void setConvidados(List<Convidado> convidados) {
        this.convidados = convidados;
    }

    public List<Membro> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Membro> participantes) {
        this.participantes = participantes;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Evento t) {
        return this.nome.compareTo(t.nome);
    }
}
