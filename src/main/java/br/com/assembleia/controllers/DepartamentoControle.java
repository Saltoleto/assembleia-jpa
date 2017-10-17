package br.com.assembleia.controllers;

import br.com.assembleia.entities.Departamento;
import br.com.assembleia.entities.Funcao;
import br.com.assembleia.entities.Membro;
import br.com.assembleia.services.DepartamentoService;
import br.com.assembleia.services.MembroService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

@ManagedBean
@SessionScoped
@Component
public class DepartamentoControle {

    private Departamento departamento;
    private List<Departamento> departamentos;
    private Funcao funcaoTransiente;
    private Funcao funcaoFinal;
    private Membro membroTransiente;
    private List<Membro> membros;
    private int tab = 0;
    private Membro membroFinal;

    private String titulo;

    @Autowired
    private DepartamentoService service;
    @Autowired
    private MembroService serviceMembro;

    @PostConstruct
    private void init() {
        departamento = new Departamento();

    }

    public String novo() {
        departamento = new Departamento();
        funcaoTransiente = new Funcao();
        funcaoFinal = new Funcao();
        membroFinal = new Membro();
        titulo = "Cadastrar Departamento";
        return "form?faces-redirect=true";
    }

    public String editar(Departamento departamento) {
        if (departamento != null) {
            this.departamento = departamento;
            titulo = "Editar o Departamento";
            funcaoFinal = new Funcao();
            funcaoTransiente = new Funcao();
            membroFinal = new Membro();
            return "form?faces-redirect=true";
        }
        adicionaMensagem("Nenhum Departamento foi selecionado para a alteração!", FacesMessage.SEVERITY_INFO);
        return "lista?faces-redirect=true";

    }

    public String salvar() {

        try {
            departamento.setQuantidadeIntegrantes(departamento.getIntegrantes().size());
            service.salvar(departamento);
            adicionaMensagem("Departamento salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            departamento = null;
        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public void retirarFuncaoLista() {
        List<Funcao> funcoesTeste = new ArrayList<Funcao>();
        if (departamento != null) {
            for (Funcao func : departamento.getFuncoes()) {
                if (!func.getFuncao().equals(funcaoFinal.getFuncao())) {
                    funcoesTeste.add(func);
                }
            }
            tab = 0;
            departamento.setFuncoes(funcoesTeste);
            org.primefaces.context.RequestContext.getCurrentInstance().execute("confirmacaoFuncao.hide()");
        }

    }

    public void retirarIntegranteLista() {

        if (departamento != null) {
            departamento.getIntegrantes().remove(membroFinal);
            tab = 1;
            org.primefaces.context.RequestContext.getCurrentInstance().execute("confirmacaoIntegrante.hide()");
        }
    }

    public String deletar(Departamento departamento) {
        try {
            if (departamento != null) {
                this.departamento = departamento;
                service.deletar(departamento);
                adicionaMensagem("Departamento Excluido com Sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (PersistenceException ex) {
            adicionaMensagem("Departamento está emprestado, não pode ser exlcuído!", FacesMessage.SEVERITY_INFO);
            voltar();
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        departamento = null;

        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public void adicionarFuncoes() {

        if (funcaoFinal == null) {
            funcaoFinal = new Funcao();
        }
        if (membroTransiente.getId() != null && !funcaoTransiente.getFuncao().isEmpty()) {
            funcaoFinal.setMembro(membroTransiente);
            funcaoFinal.setFuncao(funcaoTransiente.getFuncao());
            departamento.getFuncoes().add(funcaoFinal);
            funcaoTransiente = new Funcao();
            funcaoFinal = new Funcao();
            tab = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", true);
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", false);
        }

    }

    public void adicionarIntegrantes() {
        membroFinal = membroTransiente;
        if (membroFinal.getId() != null) {
            departamento.getIntegrantes().add(membroFinal);
            membroFinal = new Membro();
            tab = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", true);
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", false);
        }

    }

    public List<Membro> getMembros() {
        return membros = serviceMembro.listarTodos();
    }

    public Funcao getFuncaoFinal() {
        return funcaoFinal;
    }

    public void setFuncaoFinal(Funcao funcaoFinal) {
        this.funcaoFinal = funcaoFinal;
    }

    public Funcao getFuncaoTransiente() {
        return funcaoTransiente;
    }

    public void setFuncaoTransiente(Funcao funcaoTransiente) {
        this.funcaoTransiente = funcaoTransiente;
    }

    public Membro getMembroTransiente() {
        return membroTransiente;
    }

    public void setMembroTransiente(Membro membroTransiente) {
        this.membroTransiente = membroTransiente;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos = service.listarTodos();
    }

    public Membro getMembroFinal() {
        return membroFinal;
    }

    public void setMembroFinal(Membro membroFinal) {
        this.membroFinal = membroFinal;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

}
