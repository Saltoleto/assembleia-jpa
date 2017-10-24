package br.com.assembleia.controllers;

import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.entities.Fornecedor;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class FornecedorControle {

    private Fornecedor fornecedor;
    private Congregacao congregacao;
    private List<Congregacao> congregacoes;
    private List<Fornecedor> fornecedores;
    private String titulo;

    @Autowired
    private FornecedorService service;
    @Autowired
    private CongregacaoService congregacaoService;


    public String novo() {
        fornecedor = new Fornecedor();
        titulo = "Cadastrar Fornecedor";
        return "form?faces-redirect=true";
    }

    public String editar(Fornecedor fornecedor) {
        if (fornecedor != null) {
            this.fornecedor = fornecedor;
            titulo = "Editar Fornecedor";
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }


    public String salvar() {
        try {
            service.salvar(fornecedor);
            adicionaMensagem("Fornecedor salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            fornecedor = null;
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String deletar(Fornecedor fornecedor) {
        try {
            if (fornecedor != null) {
                this.fornecedor = fornecedor;
                service.deletar(fornecedor);
                congregacoes = null;
                adicionaMensagem("Fornecedor excluído com sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Este Fornecedor não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        fornecedor = null;
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public List<Congregacao> getCongregacoes() {
        if (AplicacaoControle.getInstance().adminSede()) {
            congregacoes = congregacaoService.listarTodos();
        } else {
            congregacoes =  new ArrayList<>();
            congregacoes.add(congregacaoService.getById(AplicacaoControle.getInstance().getIdIgrejaPorUsuario()));
        }
        return congregacoes;
    }

    public List<Fornecedor> getFornecedores() {
        if (AplicacaoControle.getInstance().getUsuario().isAdmin() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            fornecedores = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().getUsuario().isAdmin()) {
            fornecedores = service.listarTodos();
        } else {
            fornecedores = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        return fornecedores;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
