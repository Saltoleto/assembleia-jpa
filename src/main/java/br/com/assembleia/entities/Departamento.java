package br.com.assembleia.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernandosaltoleto
 */
@Entity
public class Departamento implements Comparable<Departamento>, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Departamento")
    private Long id;
    private String nome;
    private String descricao;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Membro> integrantes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Funcao> funcoes;
    private int quantidadeIntegrantes;

    public Departamento() {
        integrantes = new ArrayList<Membro>();
        funcoes = new ArrayList<Funcao>();
    }

    public int getQuantidadeIntegrantes() {
        return quantidadeIntegrantes;
    }

    public void setQuantidadeIntegrantes(int quantidadeIntegrantes) {
        this.quantidadeIntegrantes = quantidadeIntegrantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Membro> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Membro> integrantes) {
        this.integrantes = integrantes;
    }

    public List<Funcao> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(List<Funcao> funcoes) {
        this.funcoes = funcoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
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
    public int compareTo(Departamento t) {
        return this.descricao.compareToIgnoreCase(t.getDescricao());
    }

}
