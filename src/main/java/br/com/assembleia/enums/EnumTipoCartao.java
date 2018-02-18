package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumTipoCartao {

    CARTAO("Cartão"), CREDENCIAL("Credencial");

    private final String descricao;

    private EnumTipoCartao(String descricao) {
        this.descricao = descricao;     
    }

    public static EnumTipoCartao getTipo(String tipo) {
            if (tipo != null) {
            for (EnumTipoCartao e : values()) {
                if (tipo.equalsIgnoreCase(e.descricao)) {
                    return e;
                }
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
