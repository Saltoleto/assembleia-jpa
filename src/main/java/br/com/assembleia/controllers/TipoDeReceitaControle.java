package br.com.assembleia.controllers;

import br.com.assembleia.entities.TipoDeReceita;
import br.com.assembleia.services.TipoDeReceitaService;
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
public class TipoDeReceitaControle {

    private TipoDeReceita tipoDeReceita;
    private List<TipoDeReceita> listTipoDeReceitas;
    private String titulo;

    @Autowired
    private TipoDeReceitaService service;


    public String novo() {
        tipoDeReceita = new TipoDeReceita();
        titulo = "Cadastro de Tipo de Receita";
        return "form?faces-redirect=true";
    }

    public String salvar() {
        try {
            service.salvar(tipoDeReceita);
            adicionaMensagem("Tipo de Receita salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            tipoDeReceita = null;
        } catch (DataIntegrityViolationException e) {
            adicionaMensagem("Ops! Já existe este Tipo de Receita Cadastrado", FacesMessage.SEVERITY_WARN);
        }
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public String editar(TipoDeReceita TipoDeReceita) {
        if (TipoDeReceita != null) {
            this.tipoDeReceita = TipoDeReceita;
            titulo = "Editar Tipo de Receita";
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String deletar(TipoDeReceita tipoDeReceita) {
        try {
            if (tipoDeReceita != null) {
                this.tipoDeReceita = tipoDeReceita;
                service.deletar(tipoDeReceita);
                tipoDeReceita = null;
                adicionaMensagem("Tipo de Receita excluido com sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Este Tipo de Receita não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        tipoDeReceita = null;
        return "lista?faces-redirect=true";
    }

    public TipoDeReceita getTipoDeReceita() {
        return tipoDeReceita;
    }

    public void setTipoDeReceita(TipoDeReceita tipoDeReceita) {
        this.tipoDeReceita = tipoDeReceita;
    }

    public List<TipoDeReceita> getListTipoDeReceitas() {
        return listTipoDeReceitas = service.listarTodos();
    }

    public void setListTipoDeReceitas(List<TipoDeReceita> listTipoDeReceitas) {
        this.listTipoDeReceitas = listTipoDeReceitas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
