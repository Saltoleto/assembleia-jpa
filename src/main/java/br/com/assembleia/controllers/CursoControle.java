package br.com.assembleia.controllers;

import br.com.assembleia.entities.*;
import br.com.assembleia.enums.EnumAtividades;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.CursoService;
import br.com.assembleia.services.DepartamentoService;
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
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class CursoControle {

    private Curso curso;
    private Membro alunoTransiente;
    private Congregacao congregacao;
    private List<Curso> cursos;
    private List<Membro> alunos;
    private List<Membro> professores;
    private List<Congregacao> congregacoes;
    private int tab = 0;

    private String titulo;

    @Autowired
    private CursoService service;
    @Autowired
    private MembroService serviceMembro;
    @Autowired
    private CongregacaoService serviceCongregacao;


    public String novo() {
        curso = new Curso();
        titulo = "Cadastrar Curso";
        tab = 0;
        return "form?faces-redirect=true";
    }

    public String editar(Curso curso) {
        if (curso != null) {
            this.curso = curso;
            titulo = "Editar o Curso";
            tab = 0;
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String salvar() {

        try {
            curso.setQuantidadeAlunos(curso.getAlunos().size());
            service.salvar(curso);
            adicionaMensagem("Curso salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            curso = null;
        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String deletar(Curso curso) {
        try {
            if (curso != null) {
                this.curso = curso;
                service.deletar(curso);
                adicionaMensagem("Curso Excluido com Sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Este Curso não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        curso = null;

        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public void adicionarAlunos() {
        if (this.alunoTransiente != null) {
            this.curso.getAlunos().add(this.alunoTransiente);
            tab = 0;
            this.alunoTransiente = null;
        }
    }

    public void retirarAluno(Membro aluno) {
        if (aluno != null) {
            this.curso.getAlunos().remove(aluno);
            tab = 0;
        }
    }

    public void adicionarProfessores() {
        if (professores.size() > 0) {
            curso.setProfessores(professores);
            tab = 1;
        }
    }

    public void retirarProfessor(Membro professor) {
        if (professor != null) {
            this.curso.getProfessores().remove(professor);
            tab = 1;
        }
    }

    public List<Congregacao> getCongregacoes() {
        congregacoes = new ArrayList<Congregacao>();
        if (AplicacaoControle.getInstance().adminSede()) {
            congregacoes = serviceCongregacao.listarTodos();
        } else {
            congregacoes.add(serviceCongregacao.getById(AplicacaoControle.getInstance().getIdIgrejaPorUsuario()));
        }
        return congregacoes;
    }

    public List<Curso> getCursos() {
        if (AplicacaoControle.getInstance().getUsuario().isAdmin() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            cursos = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().getUsuario().isAdmin()) {
            cursos = service.listarTodos();
        } else {
            cursos = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }
        return cursos;
    }

    public List<Membro> getAlunos() {

            alunos = serviceMembro.listarPorAtividadeCongregacao(EnumAtividades.ALUNO,11L);
        return alunos;
    }

    public List<Membro> getProfessores() {
        if(this.congregacao!= null){
            professores = serviceMembro.listarPorAtividadeCongregacao(EnumAtividades.PROFESSOR,congregacao.getId());
        }
        return professores;
    }

    public List<Membro> completeAluno(String query) {
        List<Membro> alunos = serviceMembro.listarPorAtividadeCongregacao(EnumAtividades.ALUNO,AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        List<Membro> alunosFiltrados = new ArrayList<Membro>();

        for (int i = 0; i < alunos.size(); i++) {
            Membro skin = alunos.get(i);
            if(skin.getNome().toLowerCase().startsWith(query) ) {
                alunosFiltrados.add(skin);
            }
        }

        return alunosFiltrados;
    }

    public int getTab() {
        return tab;
    }
    public void setTab(int tab) {
        this.tab = tab;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Membro getAlunoTransiente() {
        return alunoTransiente;
    }

    public void setAlunoTransiente(Membro alunoTransiente) {
        this.alunoTransiente = alunoTransiente;
    }
}
