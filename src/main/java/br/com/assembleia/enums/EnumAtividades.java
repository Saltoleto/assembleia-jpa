
package br.com.assembleia.enums;

/**
 * @author fernandosaltoleto
 */
public enum EnumAtividades {

    ALUNO("Aluno"), PROFESSOR("Professor");

    private String descricao;

    private EnumAtividades(String descricao) {
        this.descricao = descricao;
    }

    public static EnumAtividades getAtividade(String descricao) {
        if (descricao != null) {
            for (EnumAtividades atividade : values()) {
                if (descricao.equalsIgnoreCase(atividade.getDescricao())) {
                    return atividade;
                }
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
