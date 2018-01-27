package br.com.assembleia.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fernandosaltoleto
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Curso.listarPorIgreja",
                query = "SELECT d FROM Curso d JOIN d.congregacao i WHERE i.id = :idIgreja")
})
public class Curso implements Comparable<Curso>, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Curso")
    private Long id;
    private String nome;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataEncerramento;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="curso_aluno", joinColumns=@JoinColumn(name="aluno_id"), inverseJoinColumns=@JoinColumn(name="id"))
    private List<Membro> alunos;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="curso_professor", joinColumns=@JoinColumn(name="professor_id"), inverseJoinColumns=@JoinColumn(name="id"))
    private List<Membro> professores;
    @ManyToOne
    private Congregacao congregacao;
    private int quantidadeAlunos;

    public Curso() {
        alunos = new ArrayList<Membro>();
        professores = new ArrayList<Membro>();
    }


    public Long getId() {
        return id;
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

    public List<Membro> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Membro> alunos) {
        this.alunos = alunos;
    }

    public List<Membro> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Membro> professores) {
        this.professores = professores;
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
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Curso t) {
        return this.nome.compareToIgnoreCase(t.getNome());
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    public int getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    public void setQuantidadeAlunos(int quantidadeAlunos) {
        this.quantidadeAlunos = quantidadeAlunos;
    }
}
