package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumEstado {

    Acre("Acre", "AC"), Alagoas("Alagoas", "AL"), Amapa("Amapa", "AP"),
    Amazonas("Amazonas", "AM"), Bahia("Bahia", "BA"), Ceara("Ceará", "CE"), Distrito_Federal("Distrito Federal", "DF"),
    Espirito_Santo("Espirito Santo", "ES"), Goias("Goiás", "GO"), Maranhao("Maranhão", "MA"), Mato_Grosso("Mato Grosso", "MT"),
    Mato_Grosso_do_Sul("Mato Grosso do Sul", "MS"), Minas_Gerais("Minas Gerais", "MG"),
    Para("Pará", "PA"), Paraiba("Paraíba", "PB"), Parana("Paraná", "PR"), Pernambuco("Pernambuco", "PE"), Piaui("Piauí", "PI"),
    Rio_de_Janeiro("Rio de Janeiro", "RJ"), Rio_Grande_do_Norte("Rio Grande do Norte", "RN"),
    Rio_Grande_do_Sul("Rio Grande do Sul", "RS"), Rondonia("Rondonia", "RO"), Roraima("Roraima", "RR"),
    Santa_Catarina("Santa Catarina", "SC"), Sao_Paulo("São Paulo", "SP"), Sergipe("Sergipe", "SE"), Tocantins("Tocantins", "TO");

    private final String descricao;
    private final String uf;

    private EnumEstado(String descricao, String uf) {
        this.descricao = descricao;
        this.uf = uf;
    }

     public static EnumEstado getEstado(String estado) {
        if (estado != null) {
            for (EnumEstado e : values()) {
                if (estado.equalsIgnoreCase(e.getDescricao())) {
                    return e;
                }
            }
        }
        return null;
    }

    public String getUf() {
        return uf;
    }
    
    

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
