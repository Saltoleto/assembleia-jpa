package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumStatusAcervo {

    EMPRESTADO("Emprestado"), DISPONIVEL("Disponivel");
    private final String descricao;

    private EnumStatusAcervo(String descricao) {
        this.descricao = descricao;
    }

    public static EnumStatusAcervo busca(Integer codigo) {

        for (EnumStatusAcervo item : EnumStatusAcervo.values()) {
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
