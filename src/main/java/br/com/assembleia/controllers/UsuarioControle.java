package br.com.assembleia.controllers;

import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.entities.Usuario;
import br.com.assembleia.enums.EnumAutorizacao;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class UsuarioControle {

    private Usuario usuario;
    private List<Usuario> usuarios;
    private List<Congregacao> congregacoes;
    private String titulo;

    @Autowired
    private UsuarioService service;

    @Autowired
    private CongregacaoService serviceCongregacao;

    @PostConstruct
    private void init() {
        usuario = new Usuario();
    }

    public String novo() {
        usuario = new Usuario();
        titulo = "Cadastrar Usuário";
        return "form?faces-redirect=true";
    }

    public String editar(Usuario usuario) {
        if (usuario != null) {
            this.usuario = usuario;
            titulo = "Editar Usuário";
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String salvar() {
        try {
            service.salvar(usuario);
            adicionaMensagem("Usuario salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            usuario = null;
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public String deletar(Usuario usuario) {
        try {
            if (usuario != null) {
                this.usuario = usuario;
                service.deletar(usuario);
                usuarios = null;
                adicionaMensagem("Usuário excluido com sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Esta Usuário não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public List<EnumAutorizacao> getAutorizacoes() {
        return Arrays.asList(EnumAutorizacao.values());
    }

    public String voltar() {
        usuario = null;
        return "lista?faces-redirect=true";
    }

    public List<Usuario> getUsuarios() {


        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            usuarios = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            usuarios = service.listarTodos();
        } else {
            usuarios = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        Collections.sort(usuarios);
        return usuarios;
    }

    public List<Congregacao> getCongregacoes() {

        if (AplicacaoControle.getInstance().adminSede()) {
            congregacoes = serviceCongregacao.listarTodos();
        } else {
            congregacoes = new ArrayList();
            congregacoes.add(serviceCongregacao.getById(AplicacaoControle.getInstance().getUsuario().getCongregacao().getId()));
        }
        return congregacoes;
    }

    public void setCongregacoes(List<Congregacao> congregacoes) {
        this.congregacoes = congregacoes;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


}
