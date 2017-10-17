package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumSituacaoPatrimonio {

    DISPONIVEL("Disponivel"), MANUTENCAO("Em Manutenção"), DOADO("Doado"),
    COMDEFEITO("Com Defeito");
    private final String descricao;

    private EnumSituacaoPatrimonio(String descricao) {
        this.descricao = descricao;
    }

    public static EnumSituacaoPatrimonio busca(Integer codigo) {

        for (EnumSituacaoPatrimonio item : EnumSituacaoPatrimonio.values()) {
            if (item.ordinal() == codigo) {
                return item;
            }
        }

        return null;
    }
    
        public static EnumSituacaoPatrimonio getSituacao(String situacao) {
        if (situacao != null) {
            for (EnumSituacaoPatrimonio e : values()) {
                if (situacao.equalsIgnoreCase(e.getDescricao())) {
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
