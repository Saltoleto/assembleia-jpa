package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumEstadoCivil {

    SOLTEIRO("Solteiro"), CASADO("Casado"), DIVORCIADO("Divorciado"),
    VIUVO("Viúvo");
    private final String descricao;

    private EnumEstadoCivil(String descricao) {
        this.descricao = descricao;
    }

    public static EnumEstadoCivil busca(Integer codigo) {

        for (EnumEstadoCivil item : EnumEstadoCivil.values()) {
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
