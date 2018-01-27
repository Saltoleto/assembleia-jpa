package br.com.assembleia.controllers;


import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.entities.Usuario;
import br.com.assembleia.enums.EnumAutorizacao;
import br.com.assembleia.services.CongregacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class AplicacaoControle {

    private static AplicacaoControle uniqueInstance;
    private List<Congregacao> congregacoes;
    private Congregacao congregacao;
    private Congregacao congregacaoSelecionada;
    private Usuario usuario;

    @Autowired
    private CongregacaoService serviceCongregacao;

    public AplicacaoControle() {

    }

    public static synchronized AplicacaoControle getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new AplicacaoControle();
        return uniqueInstance;
    }

    public String sair() {
        return "login?faces-redirect=true";
    }

    public Usuario getUsuario() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usuario;
    }

    public boolean validaUsuario() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuario != null) {
            if (usuario.getAutorizacao().toString().equals("ADMIN")) {
                return true;
            }
        }
        adicionaMensagem("Usuário sem permissão para Exclusão!", FacesMessage.SEVERITY_WARN);
        return false;
    }

    public boolean adminSede() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return (usuario.getCongregacao().getIsSede() && EnumAutorizacao.ADMIN.equals(usuario.getAutorizacao()));
    }

    public boolean adminCongregacao() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return (!usuario.getCongregacao().getIsSede() && EnumAutorizacao.ADMIN.equals(usuario.getAutorizacao()));
    }

    public boolean secretario() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return EnumAutorizacao.SECRETARIO.equals(usuario.getAutorizacao());
    }

    public boolean tesoureiro() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return EnumAutorizacao.TESOUREIRO.equals(usuario.getAutorizacao());
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public String dataAno() {
        Calendar data = Calendar.getInstance();
        String ano = String.valueOf(data.get(Calendar.YEAR));
        return ano;
    }

    public static Integer dataAnoInteger() {
        Calendar data = Calendar.getInstance();
        Integer ano = (data.get(Calendar.YEAR));
        return ano;
    }

    public boolean adminSedeSelecionouIgreja(){
       return AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null;
    }

    public boolean adminSedeNaoSelecionouIgreja(){
        return AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null;
    }

    public Long getIdIgreja() {
        congregacaoSelecionada = (Congregacao) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("congregacao");
        if (congregacaoSelecionada != null) {
            return congregacaoSelecionada.getId();
        }

        return null;
    }

    public Long getIdIgrejaPorUsuario() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuario != null) {
            return usuario.getCongregacao().getId();
        }

        return null;
    }

    public List<Congregacao> getCongregacoes() {
        return congregacoes = serviceCongregacao.listarTodos();
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public Congregacao getCongregacaoSelecionada() {
        return congregacaoSelecionada;
    }

    public void setCongregacaoSelecionada(Congregacao congregacaoSelecionada) {
        this.congregacaoSelecionada = congregacaoSelecionada;
    }

    public void selectCongregagao() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("congregacao", this.congregacao);
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

}
