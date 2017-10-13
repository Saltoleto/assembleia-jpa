/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.assembleia.enums;

/**
 *
 * @author fernandosaltoleto
 */
public enum EnumOrder {

    ASCENDING,
    DESCENDING;

    public static EnumOrder identificaOrder(String descricao) {
        return Enum.valueOf(EnumOrder.class, descricao);
    }
}
