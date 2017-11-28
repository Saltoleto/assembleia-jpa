package br.com.assembleia.entities;

import br.com.assembleia.enums.EnumAtividades;

import javax.persistence.Embeddable;

@Embeddable
public class MembroAtividades {

    private EnumAtividades atividade;

    public EnumAtividades getAtividade() {
        return atividade;
    }

    public void setAtividade(EnumAtividades atividade) {
        this.atividade = atividade;
    }
}
