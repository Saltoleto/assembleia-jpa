/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.assembleia.converter;


import br.com.assembleia.enums.EnumTipoCartao;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EnumType;

/**
 *
 * @author ferna
 */
@FacesConverter("enumTipoCartaoConverter")
public class EnumTipoCartaoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return EnumTipoCartao.getTipo(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof EnumType) {
            return ((EnumTipoCartao) value).name();
        }
        return null;
    }
}
