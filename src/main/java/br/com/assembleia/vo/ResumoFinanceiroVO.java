package br.com.assembleia.vo;

import java.math.BigDecimal;

public class ResumoFinanceiroVO {

    private String mes;
    private BigDecimal valor;

    public ResumoFinanceiroVO(String mes, BigDecimal valor) {
        this.mes = mes;
        this.valor = valor;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
