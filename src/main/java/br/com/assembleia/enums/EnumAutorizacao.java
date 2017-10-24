
package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumAutorizacao {

    ADMIN("Administrador"), TESOUREIRO("Tesoureiro"), SECRETARIO("Secretário");

    private String descricao;

    private EnumAutorizacao(String descricao) {
        this.descricao = descricao;
    }

    public static EnumAutorizacao getAutorizacao(String descricao) {
        if (descricao != null) {
            for (EnumAutorizacao autorizacao : values()) {
                if (descricao.equalsIgnoreCase(autorizacao.getDescricao())) {
                    return autorizacao;
                }
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
