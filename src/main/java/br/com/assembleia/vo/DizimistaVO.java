package br.com.assembleia.vo;

public class DizimistaVO {

    protected String label;
    protected Integer quantidade;

    public DizimistaVO(String label, Integer quantidade) {
        this.label = label;
        this.quantidade = quantidade;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
