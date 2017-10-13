package br.com.assembleia.entities;

import java.math.BigDecimal;

/**
 *
 * @author fernandosaltoleto
 */
public class DespesasCatoriaDTO {

    private String descricao;
    private BigDecimal valorDespesa;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(BigDecimal valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

   

    @Override
    public String toString() {
        return "[" + descricao + "," + valorDespesa + "]";
    }

}
