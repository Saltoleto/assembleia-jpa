package br.com.assembleia.converter;


import br.com.assembleia.entities.Membro;
import br.com.assembleia.services.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.Map;


@FacesConverter("alunoConverter")
public class AlunoConverter implements Converter {

    @Autowired
    private MembroService serviceMembro;


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return this.getAttributesFrom(uiComponent).get(value);
        }
        return null;
    }

    protected void addAttribute(UIComponent component, Membro o) {
        String key = o.getId().toString(); // codigo da empresa como chave neste caso
        this.getAttributesFrom(component).put(key, o);
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Membro) object).getId());
        }
        else {
            return null;
        }
    }
}
