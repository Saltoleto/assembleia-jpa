package br.com.assembleia.controllers;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;


import br.com.assembleia.entities.Usuario;
import br.com.assembleia.services.UsuarioService;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Component;

@ManagedBean
@SessionScoped
@Component
public class UsuarioControle {

    private Usuario usuario;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFintrados;
    private String titulo;

    @Autowired
    private UsuarioService service;

    @PostConstruct
    private void init() {
        usuario = new Usuario();
    }

    public String novo() {
        usuario = new Usuario();
        titulo = "Cadastrar Usuario";
        return "form?faces-redirect=true";
    }

    public String carregarCadastro() {
        if (usuario != null) {
            titulo = "Editar Usuario";
            return "form?faces-redirect=true";
        }
        adicionaMensagem("Nenhum Elemento foi Selecionado para a Alteração!", FacesMessage.SEVERITY_INFO);
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

    public void chamarExclusao() {
        if (new AplicacaoControle().validaUsuario()) {
            if (usuario == null) {
                adicionaMensagem("Nenhum Elemento foi Selecionado para a Exclusão!", FacesMessage.SEVERITY_INFO);
                return;
            }
            org.primefaces.context.RequestContext.getCurrentInstance().execute("confirmacaoUs.show()");
        }
    }

    public String deletar() {
        try {
            service.deletar(usuario);
            usuarios = null;
            adicionaMensagem("Usuário Excluido com Sucesso!", FacesMessage.SEVERITY_INFO);

        } catch (PersistenceException ex) {
            adicionaMensagem("O Usuário esta ativo, não pode ser excluído!", FacesMessage.SEVERITY_ERROR);
            voltar();
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        usuario = null;
        return "lista?faces-redirect=true";
    }

    public List<Usuario> getUsuarios() {
        usuarios = service.listarTodos();
        Collections.sort(usuarios);
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuariosFintrados() {

        usuariosFintrados = service.listarTodos();

        Collections.sort(usuariosFintrados);
        return usuariosFintrados;
    }

    public void setUsuariosFintrados(List<Usuario> usuariosFintrados) {
        this.usuariosFintrados = usuariosFintrados;
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
