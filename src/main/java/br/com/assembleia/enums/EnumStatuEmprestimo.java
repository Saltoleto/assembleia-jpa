package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumStatuEmprestimo {

    SIM("Sim"), NAO("Não");
    private final String descricao;

    private EnumStatuEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    public static EnumStatuEmprestimo busca(Integer codigo) {

        for (EnumStatuEmprestimo item : EnumStatuEmprestimo.values()) {
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
