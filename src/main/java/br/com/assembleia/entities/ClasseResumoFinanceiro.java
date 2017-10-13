
package br.com.assembleia.entities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author fernandosaltoleto
 */
public class ClasseResumoFinanceiro {
    private String descricao;
    private BigDecimal valor;
    private static final Locale BRASIL = new Locale("pt", "BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    
     public String getValorFormatado() {
        String teste = null;
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        teste = df.format(valor);

        return teste;
    }
}
