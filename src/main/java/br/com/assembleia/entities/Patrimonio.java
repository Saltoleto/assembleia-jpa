package br.com.assembleia.entities;

import br.com.assembleia.enums.EnumSituacaoPatrimonio;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernandosaltoleto
 */
@Entity
@Table(name = "patrimonio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patrimonio.valorPatrimonio",
            query = "SELECT SUM(valorTotal) as valor FROM Patrimonio ")
})
public class Patrimonio implements Serializable, Comparable<Patrimonio> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer codigo;
    private String nome;
    private String descricao;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Congregacao congregacao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAquisicao;
    private BigDecimal valorUnitario;
    private BigDecimal quantidade;
    private EnumSituacaoPatrimonio situacaoPatrimonio;
    private String obervacao;
    private BigDecimal valorTotal;
    private static final Locale BRASIL = new Locale("pt", "BR");
    private DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public String getValorUnitarioFormatado() {
        return df.format(valorUnitario);
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public EnumSituacaoPatrimonio getSituacaoPatrimonio() {
        return situacaoPatrimonio;
    }

    public void setSituacaoPatrimonio(EnumSituacaoPatrimonio situacaoPatrimonio) {
        this.situacaoPatrimonio = situacaoPatrimonio;
    }

    public String getObervacao() {
        return obervacao;
    }

    public void setObervacao(String obervacao) {
        this.obervacao = obervacao;
    }

    public String getValorTotal() {
        return df.format(valorTotal);
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
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
        if (!(object instanceof Patrimonio)) {
            return false;
        }
        Patrimonio other = (Patrimonio) object;
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
    public int compareTo(Patrimonio t) {
        return this.nome.compareTo(t.nome);
    }
}
