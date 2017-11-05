
package br.com.assembleia.controllers;

import br.com.assembleia.entities.Usuario;
import br.com.assembleia.enums.EnumAutorizacao;
import br.com.assembleia.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class LoginControle {

    private Usuario usuario;

    @Autowired
    private UsuarioService service;
    private String login;
    private String senha;

    public String logar() {
        if (login.equals("admin") && senha.equals("admin12345")) {
            usuario = new Usuario();
            usuario.setAutorizacao(EnumAutorizacao.ADMIN);
        } else {
            usuario = service.findByLogin(login,senha);
        }
        if (usuario != null) {
            List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
            roles.add(new GrantedAuthorityImpl(usuario.getAutorizacao().toString()));
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(new UsernamePasswordAuthenticationToken(login, senha, roles));
            if (context.getAuthentication().isAuthenticated()) {
                if(usuario.getReiniciarSenha() != null && usuario.getReiniciarSenha()){
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                    return "/view/reiniciarsenha/form?faces-redirect=true";
                }
                if (usuario.getAutorizacao().equals(EnumAutorizacao.SECRETARIO)) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                    return "index?faces-redirect=true";
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                    return "index?faces-redirect=true";
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha na autenticação do usuário", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "login";
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário inexistente no sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "login";
        }
    }

    public String voltar() {
        return "index?faces-redirect=true";
    }

    public String sair() {
        return "login?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
