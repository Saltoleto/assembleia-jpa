package br.com.assembleia.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

/**
 * @author fernandosaltoleto
 */
@Entity
@Table(name = "receita")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Receita.valorReceitaPeriodo",
                query = "Select SUM(r.valor) from Receita r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano "),
        @NamedQuery(name = "Receita.listarReceitasRecebidas",
                query = "Select sum(r.valor) from Receita r where r.recebido = true "),
        @NamedQuery(name = "Receita.listarReceitasMesAno",
                query = "Select r from Receita r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano "),
        @NamedQuery(name = "Receita.buscarReceitaGrafico",
                query = "Select sum(r.valor) from Receita r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano"),
        @NamedQuery(name = "Receita.listarReceitasTipoMesAno",
                query = "Select sum(r.valor) from Receita r JOIN r.tipoDeReceita tr Where  extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano "),
        @NamedQuery(name = "Receita.listarUltimasReceitasVisao",
                query = "Select r from Receita r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano AND r.recebido = true"),
        @NamedQuery(name = "Receita.buscarReceitaMembroData",
                query = "Select r FROM Receita r  JOIN r.membro m Where extract(MONTH FROM r.data) =:mes AND r.recebido = true order by m.nome "),
        @NamedQuery(name = "Receita.listarPorIgreja",
                query = "SELECT r FROM Receita r JOIN r.congregacao i WHERE i.id = :idIgreja"),
        @NamedQuery(name = "Receita.listarReceitasMesAnoCongregacao",
                query = "Select r from Receita r JOIN r.congregacao c Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano and c.id =:id "),
        @NamedQuery(name = "Receita.receitasRecebidasMeasAnoCongregacao",
                query = "Select SUM(r.valor) as total from Receita r JOIN r.congregacao c Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano and c.id =:idIgreja "),
        @NamedQuery(name = "Receita.receitasParametroMeasAnoCongregacao",
                query = "Select SUM(r.valor) as total from Receita r JOIN r.congregacao c Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano and c.id =:idIgreja and r.recebido=:recebido ")
})
public class Receita implements Serializable, Comparable<Receita> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    @ManyToOne
    private TipoDeReceita tipoDeReceita;
    private BigDecimal valor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "membro_id")
    private Membro membro;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Congregacao congregacao;
    @Column(nullable = true)
    private boolean recebido;
    private static final Locale BRASIL = new Locale("pt", "BR");
    private DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TipoDeReceita getTipoDeReceita() {
        return tipoDeReceita;
    }

    public void setTipoDeReceita(TipoDeReceita tipoDeReceita) {
        this.tipoDeReceita = tipoDeReceita;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return df.format(valor);
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public boolean isRecebido() {
        return recebido;
    }

    public void setRecebido(boolean recebido) {
        this.recebido = recebido;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof Receita)) {
            return false;
        }
        Receita other = (Receita) object;
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
    public int compareTo(Receita t) {
        return this.descricao.compareTo(t.descricao);
    }

    public String getRecebidoFormatado() {
        if (recebido) {
            return "Sim";
        } else {
            return "Não";
        }
    }
}
