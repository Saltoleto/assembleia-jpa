package br.com.assembleia.controllers;

import br.com.assembleia.entities.TipoDeDespesa;
import br.com.assembleia.services.TipoDeDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class TipoDeDespesaControle {

    private TipoDeDespesa tipoDeDespesa;
    private List<TipoDeDespesa> listTipoDeDespesas;
    private String titulo;

    @Autowired
    private TipoDeDespesaService service;


    public String novo() {
        tipoDeDespesa = new TipoDeDespesa();
        titulo = "Cadastro de Tipo de Despesa";
        return "form?faces-redirect=true";
    }

    public String salvar() {
        try {
            service.salvar(tipoDeDespesa);
            adicionaMensagem("Tipo de Despesa salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            tipoDeDespesa = null;
        } catch (DataIntegrityViolationException e) {
            adicionaMensagem("Ops! Já existe este Tipo de Despesa Cadastrado", FacesMessage.SEVERITY_WARN);
        }
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public String editar(TipoDeDespesa TipoDeDespesa) {
        if (TipoDeDespesa != null) {
            this.tipoDeDespesa = TipoDeDespesa;
            titulo = "Editar Tipo de Despesa";
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String deletar(TipoDeDespesa tipoDeDespesa) {
        try {
            if (tipoDeDespesa != null) {
                this.tipoDeDespesa = tipoDeDespesa;
                service.deletar(tipoDeDespesa);
                tipoDeDespesa = null;
                adicionaMensagem("Tipo de Despesa excluido com sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Este Tipo de Despesa não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        tipoDeDespesa = null;
        return "lista?faces-redirect=true";
    }

    public TipoDeDespesa getTipoDeDespesa() {
        return tipoDeDespesa;
    }

    public void setTipoDeDespesa(TipoDeDespesa tipoDeDespesa) {
        this.tipoDeDespesa = tipoDeDespesa;
    }

    public List<TipoDeDespesa> getListTipoDeDespesas() {
        return listTipoDeDespesas = service.listarTodos();
    }

    public void setListTipoDeDespesas(List<TipoDeDespesa> listTipoDeDespesas) {
        this.listTipoDeDespesas = listTipoDeDespesas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
