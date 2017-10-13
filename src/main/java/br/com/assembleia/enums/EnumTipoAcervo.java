package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumTipoAcervo {

    CD("CD"), DVD("DVD"), LIVRO("Livro"),
    REVISTA("Revista");
    private final String descricao;

    private EnumTipoAcervo(String descricao) {
        this.descricao = descricao;
    }

    public static EnumTipoAcervo busca(Integer codigo) {

        for (EnumTipoAcervo item : EnumTipoAcervo.values()) {
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
