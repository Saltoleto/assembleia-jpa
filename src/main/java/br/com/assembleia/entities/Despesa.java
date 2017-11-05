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
@Table(name = "despesa")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Despesa.valorDespesaPeriodo",
                query = "Select SUM(r.valor) from Despesa r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano "),
        @NamedQuery(name = "Despesa.listarDespesasRecebidas",
                query = "Select sum(r.valor) from Despesa r where r.recebido = true "),
        @NamedQuery(name = "Despesa.listarDespesasMesAno",
                query = "Select r from Despesa r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano "),
        @NamedQuery(name = "Despesa.buscarDespesaGrafico",
                query = "Select sum(r.valor) from Despesa r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano"),
        @NamedQuery(name = "Despesa.listarDespesasTipoMesAno",
                query = "Select sum(r.valor) from Despesa r Where r.categoria.id =:id and extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano "),
        @NamedQuery(name = "Despesa.listarUltimasDespesasVisao",
                query = "Select r from Despesa r Where extract(MONTH FROM r.data) =:mes and extract(YEAR FROM r.data) =:ano AND r.recebido = true"),
        @NamedQuery(name = "Despesa.buscarDespesaMembroData",
                query = "Select r FROM Despesa r inner join r.membro m Where extract(MONTH FROM r.data) =:mes AND r.recebido = true order by m.nome "),
        @NamedQuery(name = "Despesa.listarPorIgreja",
                query = "SELECT r FROM Despesa m JOIN r.congregacao i WHERE i.id = :idIgreja"),
        @NamedQuery(name = "Despesa.listarDespesasPagas",
                query = "SELECT sum(d.valor) FROM Despesa d where d.pago = true"),
        @NamedQuery(name = "Despesa.despesasPagarVisaoGeral",
                query = "SELECT sum(d.valor) FROM Despesa d Where extract(MONTH FROM d.data) = ? and extract(YEAR FROM d.data) = ? AND d.pago = false ")
})
public class Despesa implements Serializable, Comparable<Despesa> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    @Temporal(TemporalType.DATE)
    private Date data;
    @ManyToOne
    private TipoDeDespesa tipoDeDespesa;
    @ManyToOne
    private Congregacao congregacao;
    private BigDecimal valor;
    @ManyToOne
    private Departamento departamento;
    @Column(nullable = true)
    private boolean pago;
    private String parcelas = "1";
    private Integer totalParcelar = 1;
    private static final Locale BRASIL = new Locale("pt", "BR");
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

    public TipoDeDespesa getTipoDeDespesa() {
        return tipoDeDespesa;
    }

    public void setTipoDeDespesa(TipoDeDespesa tipoDeDespesa) {
        this.tipoDeDespesa = tipoDeDespesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        String teste = null;
        DecimalFormat df = new DecimalFormat("###,###,##0.00", REAL);
        teste = df.format(valor);

        return teste;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public String getParcelas() {
        return parcelas;
    }

    public void setParcelas(String parcelas) {
        this.parcelas = parcelas;
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

    public Integer getTotalParcelar() {
        return totalParcelar;
    }

    public void setTotalParcelar(Integer totalParcelar) {
        this.totalParcelar = totalParcelar;
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
        if (!(object instanceof Despesa)) {
            return false;
        }
        Despesa other = (Despesa) object;
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
    public int compareTo(Despesa t) {
        return this.descricao.compareTo(t.descricao);
    }

    public String getRecebidoFormatado() {
        if (pago) {
            return "Sim";
        } else {
            return "Não";
        }
    }
}
