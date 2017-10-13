package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumMesInt {

    JANEIRO("Janeiro", 1), FEVEREIRO("Fevereiro", 2), MARCO("Março", 3),
    ABRIL("Abril", 4), MAIO("Maio", 5), JUNHO("Junho", 6), JULHO("Julho", 7), AGOSTO("Agosto", 8),
    SETEMBRO("Setembro", 9), OUTUBRO("Outubro", 10), NOVEMBRO("Novembro", 11), DEZEMBRO("Dezembro", 12);
    private final String descricao;
    private final int numeroMes;

    private EnumMesInt(String descricao, int numeroMes) {
        this.descricao = descricao;
        this.numeroMes = numeroMes;
    }

    public static EnumMesInt busca(Integer codigo) {

        for (EnumMesInt item : EnumMesInt.values()) {
            if (item.ordinal() == codigo) {
                return item;
            }
        }

        return null;
    }

    public int getNumeroMes() {
        return numeroMes;
    }
    
    

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
