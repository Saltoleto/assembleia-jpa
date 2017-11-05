package br.com.assembleia.controllers;

import br.com.assembleia.entities.Usuario;
import br.com.assembleia.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
@Component
public class ReiniciarSenhaControle {

    private Usuario usuario;
    private String titulo;
    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;

    @Autowired
    private UsuarioService service;

    @PostConstruct
    private void init() {
        titulo = "Reiniciar Senha";
    }

    public String salvar() {
        try {
            usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
             if (verificaSenhaAtualSalvar()) {
                 if(!verificaNovaSenha()){
                     adicionaMensagem("Corfirmação de senha inválida!", FacesMessage.SEVERITY_WARN);
                     return "/view/reiniciarsenha/form?faces-redirect=true";
                 }else{
                     usuario.setSenha(novaSenha);
                     usuario.setReiniciarSenha(Boolean.FALSE);
                     usuario.setReiniciada(Boolean.TRUE);
                     service.salvar(usuario);
                     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario"   , usuario);
                     return "/index?faces-redirect=true";
                 }
            }
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "index?faces-redirect=true";
    }

    public Boolean verificaSenhaAtualSalvar(){
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(usuario !=null && senhaAtual !=null ){
            return usuario.getSenha().equals(senhaAtual);
        }

        return false;
    }

    public String verificaSenhaAtualOnBlur(){
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(usuario !=null && senhaAtual !=null && ! usuario.getSenha().equals(senhaAtual)){
            adicionaMensagem("A senha atual não confere!", FacesMessage.SEVERITY_WARN);
        }
        return "/view/reiniciarsenha/form?faces-redirect=true";
    }


    public Boolean verificaNovaSenha() {
        if (novaSenha != null && confirmaSenha != null) {
            return novaSenha.equals(confirmaSenha);
        }
        return false;
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
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

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }
}
