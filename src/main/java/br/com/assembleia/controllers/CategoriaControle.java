package br.com.assembleia.controllers;

import br.com.assembleia.entities.Categoria;
import br.com.assembleia.services.CategoriaService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Component;

@ManagedBean
@SessionScoped
@Component
public class CategoriaControle {

    private Categoria categoria;
    private List<Categoria> categorias;
    private List<Categoria> categoriasFiltrados;
    private String titulo;

    @Autowired
    private CategoriaService service;

    @PostConstruct
    private void init() {
        categoria = new Categoria();
    }

    public String novo() {
        categoria = new Categoria(); 
        titulo = "Cadastro de Categoria";
        return "form?faces-redirect=true";
    }

    public String carregarCadastro() {
        if (categoria != null) {
            titulo = "Editar Categoria";
            return "form?faces-redirect=true";
        }
        adicionaMensagem("Nenhuma categoria foi selecionada para a alteração!", FacesMessage.SEVERITY_INFO);
        return "lista?faces-redirect=true";

    }

    public String salvar() {
        try {
            service.salvar(categoria);
            adicionaMensagem("Categoria salva com sucesso!", FacesMessage.SEVERITY_INFO);
            categoria = null;
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
            if (categoria == null) {
                adicionaMensagem("Nenhuma categoria foi selecionada para a exclusão!", FacesMessage.SEVERITY_INFO);
                return;
            }
            org.primefaces.context.RequestContext.getCurrentInstance().execute("confirmacaoMe.show()");
        }
    }

    public String deletar() {
        try {
            if (categoria != null) {

                service.deletar(categoria);
                categorias = null;
                adicionaMensagem("Categoria excluida com sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (PersistenceException ex) {
            adicionaMensagem("A categoria possui vínculos, não pode ser excluída!", FacesMessage.SEVERITY_ERROR);
            voltar();
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        categoria = null;
        return "lista?faces-redirect=true";
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias = service.listarTodos();
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Categoria> getCategoriasFiltrados() {
        return categoriasFiltrados;
    }

    public void setCategoriasFiltrados(List<Categoria> categoriasFiltrados) {
        this.categoriasFiltrados = categoriasFiltrados;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    

}
