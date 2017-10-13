package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumSituacao {

    ATIVO("Ativo"), AFASTADO("Afastado"), TRASFERIDO("Transferido"),
    VISITANTE("Visitante");
    private final String descricao;

    private EnumSituacao(String descricao) {
        this.descricao = descricao;
    }

    public static EnumSituacao busca(Integer codigo) {

        for (EnumSituacao item : EnumSituacao.values()) {
            if (item.ordinal() == codigo) {
                return item;
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
