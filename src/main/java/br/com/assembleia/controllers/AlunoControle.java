package br.com.assembleia.controllers;

import br.com.assembleia.entities.Aluno;
import br.com.assembleia.entities.Cargo;
import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.services.AlunoService;
import br.com.assembleia.services.CargoService;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class AlunoControle {

    private String titulo;
    private List<Cargo> cargos;
    private List<Aluno> alunos = new ArrayList<>();
    private List<Congregacao> congregacoes = new ArrayList<>();
    private Cargo cargo;
    private Aluno aluno;

    @Autowired
    private MembroService service;
    @Autowired
    private CargoService CargoService;
    @Autowired
    private CongregacaoService serviceCongregacao;
    @Autowired
    private AlunoService serviceAluno;


    @PostConstruct
    private void init() {

    }

    public String novo() throws FileNotFoundException {
        aluno = new Aluno();
        titulo = "Cadastro de Aluno";
        return "form?faces-redirect=true";
    }

    public String editar(Aluno aluno) throws FileNotFoundException {
        if (aluno != null) {
            this.aluno = aluno;
            titulo = "Editar Aluno";
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String salvar() throws FileNotFoundException, IOException {
        try {
            serviceAluno.salvar(aluno);
            adicionaMensagem("Aluno salvo com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public String deletar(Aluno aluno) {
        try {
            if (aluno != null) {
                this.aluno = aluno;
                serviceAluno.deletar(aluno);
                adicionaMensagem("Aluno excluido com sucesso!", FacesMessage.SEVERITY_INFO);
            }
        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Este Aluno não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        aluno = null;
        return "lista?faces-redirect=true";
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
