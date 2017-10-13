package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumMes {
    
    

    JANEIRO("Janeiro"), FEVEREIRO("Fevereiro"), MARCO("Março"),
    ABRIL("Abril"), MAIO("Maio"),JUNHO("Junho"),JULHO("Julho"),AGOSTO("Agosto"),
    SETEMBRO("Setembro"),OUTUBRO("Outubro"),NOVEMBRO("Novembro"),DEZEMBRO("Dezembro");
    private final String descricao;

    private EnumMes(String descricao) {
        this.descricao = descricao;
    }

    public static EnumMes busca(Integer codigo) {

        for (EnumMes item : EnumMes.values()) {
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
