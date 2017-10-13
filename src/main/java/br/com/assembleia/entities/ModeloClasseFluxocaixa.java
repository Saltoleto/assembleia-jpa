
package br.com.assembleia.entities;

import java.util.Date;

/**
 *
 * @author fernandosaltoleto
 */
public class ModeloClasseFluxocaixa {
    private String descricao;
    private String categoria;
    private String membroDepartamento;
    private String valor;
    private String recebidoPago;
    private Date data;
    private Integer tipo;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMembroDepartamento() {
        return membroDepartamento;
    }

    public void setMembroDepartamento(String membroDepartamento) {
        this.membroDepartamento = membroDepartamento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getRecebidoPago() {
        return recebidoPago;
    }

    public void setRecebidoPago(String recebidoPago) {
        this.recebidoPago = recebidoPago;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
    
    
    
}
