package br.com.assembleia.entities;

import java.math.BigDecimal;

/**
 *
 * @author fernandosaltoleto
 */
public class ReceitasCatoriaDTO {

    private String descricao;
    private BigDecimal valorReceita;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorReceita() {
        return valorReceita;
    }

    public void setValorReceita(BigDecimal valorReceita) {
        this.valorReceita = valorReceita;
    }

    @Override
    public String toString() {
        return "[" + descricao + "," + valorReceita + "]";
    }

}
