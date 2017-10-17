package br.com.assembleia.converter;


import br.com.assembleia.entities.Congregacao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@FacesConverter("congregacaoConverter")
public class CongregacaoConverter implements Converter {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.isEmpty()) { 
            Congregacao p = new Congregacao();
            p = em.find(Congregacao.class, Long.valueOf(value));
            return p;
        }
        return null;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
}
