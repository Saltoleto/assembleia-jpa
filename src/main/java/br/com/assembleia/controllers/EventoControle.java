package br.com.assembleia.controllers;


import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.entities.Convidado;
import br.com.assembleia.entities.Evento;
import br.com.assembleia.entities.Membro;
import br.com.assembleia.enums.EnumMes;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.EventoService;
import br.com.assembleia.services.MembroService;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import java.util.*;

@ManagedBean
@SessionScoped
@Component
public class EventoControle {

    private Evento evento;
    private List<Evento> eventos;
    private Convidado convidado;
    private Membro participante;
    private List<Membro> participantes;
    private List<Membro> membros;
    private int tab = 0;
    private List<Evento> eventosVisaoGeral;
    private boolean inicio;
    Calendar agora;
    private String mesExtenso;
    private List<Congregacao> congregacoes;
    private String titulo;
    private Congregacao congregacao;

    @Autowired
    private EventoService service;
    @Autowired
    private MembroService serviceMembro;
    @Autowired
    private CongregacaoService serviceCongregacao;

    @PostConstruct
    private void init() {
        evento = new Evento();
        agora = Calendar.getInstance();

    }

    public String novo() {
        evento = new Evento();
        titulo = "Cadastro de Evento";
        convidado = new Convidado();
        tab = 0;
        return "form?faces-redirect=true";
    }

    public String novoVisao() {
        evento = new Evento();
        titulo = "Cadastro de Evento";
        convidado = new Convidado();
        inicio = true;
        return "/evento/lista.xhtml?faces-redirect=true";
    }

    public String editar(Evento evento) {
        if (evento != null) {
            this.evento = evento;
            titulo = "Editar o Evento";
            convidado = new Convidado();
            tab = 0;
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String salvar() {

        try {
            evento.setCongregacao(this.congregacao);
            service.salvar(evento);
            adicionaMensagem("Evento salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            evento = null;
        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public void adicionarConvidados() {

        if (convidado == null) {
            convidado = new Convidado();
        }
        if (!convidado.getNome().isEmpty() && !convidado.getFuncao().isEmpty()) {
            evento.getConvidados().add(convidado);
            convidado = new Convidado();
            tab = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", true);
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", false);
        }

    }

    public void adicionarParticipantes() {
        if (participante.getId() != null) {
            evento.getParticipantes().add(participante);
            tab = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", true);
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("loggedIn", false);
        }

    }

    public void retirarParticipanteLista() {

        if (evento != null) {
            this.participante = participante;
            evento.getParticipantes().remove(this.participante);
            tab = 1;
        }

    }

    public void retirarConvidadoLista() {

        if (evento != null) {
            List<Convidado> convidados = new ArrayList<Convidado>();
            for (Convidado conv : evento.getConvidados()) {
                if (!conv.getNome().equals(convidado.getNome())) {
                    convidados.add(conv);
                }
            }
            evento.setConvidados(convidados);
            convidado = new Convidado();
            tab = 0;
        }
    }


    public String deletar(Evento evento) {
        try {
            if(evento !=null){
                this.evento = evento;
                service.deletar(evento);
                adicionaMensagem("Evento excluido com Sucesso!", FacesMessage.SEVERITY_INFO);
            }
        } catch (PersistenceException ex) {
            adicionaMensagem("O  Evento não pode ser exlcuído!", FacesMessage.SEVERITY_INFO);
            voltar();
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        evento = null;
        if (inicio) {
            inicio = false;
            return "/index.xhtml?faces-redirect=true";
        }
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public Convidado getConvidado() {
        if (convidado == null) {
            convidado = new Convidado();
        }
        return convidado;
    }

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
    }

    public Membro getParticipante() {
        return participante;
    }

    public void setParticipante(Membro participante) {
        this.participante = participante;
    }

    public List<Membro> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Membro> participantes) {
        this.participantes = participantes;
    }

    public EventoService getService() {
        return service;
    }

    public void setService(EventoService service) {
        this.service = service;
    }

    public MembroService getServiceMembro() {
        return serviceMembro;
    }

    public void setServiceMembro(MembroService serviceMembro) {
        this.serviceMembro = serviceMembro;
    }

    public List<Membro> getMembros() {

        if(this.congregacao!= null){
            membros = serviceMembro.listarPorIgreja(congregacao.getId());
        }
        return membros;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Evento> getEventos() {

        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            eventos = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            eventos = service.listarTodos();
        } else {
            eventos = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        Collections.sort(eventos, new Comparator<Evento>() {

            public int compare(Evento o1, Evento o2) {
                return o2.getDataInicio().compareTo(o1.getDataInicio());
            }
        });

        return eventos;
    }

    public List<Congregacao> getCongregacoes() {
        if (AplicacaoControle.getInstance().adminSede()) {
            congregacoes = serviceCongregacao.listarTodos();
        } else {
            congregacoes = new ArrayList();
            congregacoes.add(serviceCongregacao.getById(AplicacaoControle.getInstance().getIdIgrejaPorUsuario()));
        }
        return congregacoes;
    }

    public void selectCongregacao() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("congregacaoEvento", this.congregacao);
    }
    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }


    public boolean isInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    public Calendar getAgora() {
        return agora;
    }

    public void setAgora(Calendar agora) {
        this.agora = agora;
    }

    public String getMesExtenso() {
        return mesExtenso = EnumMes.busca(agora.get(Calendar.MONTH)).toString();
    }

    public void setMesExtenso(String mesExtenso) {
        this.mesExtenso = mesExtenso;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }
}
