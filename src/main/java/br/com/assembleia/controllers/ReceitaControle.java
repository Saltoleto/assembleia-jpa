package br.com.assembleia.controllers;


import br.com.assembleia.entities.Departamento;
import br.com.assembleia.entities.Membro;
import br.com.assembleia.entities.Receita;
import br.com.assembleia.entities.TipoDeReceita;
import br.com.assembleia.enums.EnumMes;
import br.com.assembleia.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@ManagedBean
@SessionScoped
@Component
public class ReceitaControle {

    private Receita receita;
    private List<Receita> receitas;
    private String titulo;
    private BigDecimal valorReceitaPeriodo;
    private static final Locale BRASIL = new Locale("pt", "BR");
    private DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    private int mesPesquisa = Calendar.getInstance().get(Calendar.MONTH);
    private int anoPesquisa = Calendar.getInstance().get(Calendar.YEAR);
    private List<TipoDeReceita> tipoDeReceitas;
    private List<Membro> membros;
    private List<Departamento> departamentos;
    private TipoDeReceita tipoDeReceita;

    //    TELA VISAO GERAL
    private List<Receita> ultimasReceitasVisaoGeral = new ArrayList<Receita>();
    private Boolean inicio;
    private Boolean fluxoCaixa = false;

    //    USADOS PARA COMPOR O SALDO ATUAL
    private BigDecimal saldoAtual;
    private BigDecimal totalReceitasRecebidas;
    private BigDecimal totalDespesasPagas;

    @Autowired
    private ReceitaService service;
    @Autowired
    private DepartamentoService serviceDepartamento;
    @Autowired
    private TipoDeReceitaService serviceTipoDeReceita;
    @Autowired
    private MembroService serviceMembroService;
    @Autowired
    private DespesaService serviceDespesa;

    @PostConstruct
    private void init() {
        receita = new Receita();
        titulo = "Receitas";
        mesPesquisa = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public String novo() {
        novoTipoReceita();
        receita = new Receita();
        titulo = "Nova Receita";
        inicio = false;
        fluxoCaixa = false;
        return "form?faces-redirect=true";
    }

    public void novoTipoReceita() {
        tipoDeReceita = new TipoDeReceita();
    }

    public String editar(Receita receita) {
        if (receita != null) {
            novoTipoReceita();
            this.receita = receita;
            titulo = "Editar Receita";
            inicio = false;
            return "form?faces-redirect=true";
        }
        adicionaMensagem("Nenhuma Receita foi selecionada para a alteração!", FacesMessage.SEVERITY_INFO);
        return "lista?faces-redirect=true";

    }

    public void salvarTipReceita() {
        try {
            if (!tipoDeReceita.getDescricao().isEmpty()) {
                serviceTipoDeReceita.salvar(tipoDeReceita);
                tipoDeReceitas = serviceTipoDeReceita.listarTodos();
                tipoDeReceitas = null;
            }
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public String salvar() {

        try {
            receita.setCongregacao(AplicacaoControle.getInstance().getUsuario().getCongregacao());
            service.salvar(receita);
            adicionaMensagem("Receita salva com sucesso!", FacesMessage.SEVERITY_INFO);
            receita = null;
        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String deletar(Receita receita) {
        try {
            if (receita != null) {
                this.receita = receita;
                service.deletar(receita);
                receitas = null;
                adicionaMensagem("Receita excluída com Sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (PersistenceException ex) {
            adicionaMensagem("A Receita está emprestado, não pode ser exlcuído!", FacesMessage.SEVERITY_INFO);
            voltar();
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        receita = null;
        if (inicio) {
            inicio = false;
            return "/index.xhtml?faces-redirect=true";
        }
        if (fluxoCaixa) {
            fluxoCaixa = false;
            return "/fluxocaixa/lista.xhtml?faces-redirect=true";
        }
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public List<Receita> getReceitas() {

        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            receitas = service.listarReceitasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            receitas = service.listarReceitasMesAno(mesPesquisa,anoPesquisa);
        } else {
            receitas = service.listarReceitasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        Collections.sort(receitas, new Comparator<Receita>() {
            @Override
            public int compare(Receita o1, Receita o2) {
                return o1.getData().compareTo(o2.getData());
            }
        });

        return receitas;
    }

    public String getValorTotalReceita() {

        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null)  {
            valorReceitaPeriodo = service.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
        } else if(AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorReceitaPeriodo = service.valorReceitaPeriodo(mesPesquisa,anoPesquisa);
        }else{
            valorReceitaPeriodo = service.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }
        return df.format(valorReceitaPeriodo !=null ? valorReceitaPeriodo : BigDecimal.ZERO);
    }


    public String listar() {
        return "lista?faces-redirect=true";
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<TipoDeReceita> getTipoDeReceitas() {
        return tipoDeReceitas = serviceTipoDeReceita.listarTodos();
    }

    public void setSaldoAtual(BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public int getMesPesquisa() {
        return mesPesquisa;
    }

    public void setMesPesquisa(int mesPesquisa) {
        this.mesPesquisa = mesPesquisa;
    }

    public List<EnumMes> getListaMes() {
        return Arrays.asList(EnumMes.values());
    }

    public int getAnoPesquisa() {
        return anoPesquisa;
    }

    public void setAnoPesquisa(int anoPesquisa) {
        this.anoPesquisa = anoPesquisa;
    }

    public List<Membro> getMembros() {
        return membros = serviceMembroService.listarTodos();
    }

    public List<Departamento> getDepartamentos() {
        return departamentos = serviceDepartamento.listarTodos();
    }

    public BigDecimal getTotalReceitasRecebidas() {
        return totalReceitasRecebidas;
    }

    public void setTotalReceitasRecebidas(BigDecimal totalReceitasRecebidas) {
        this.totalReceitasRecebidas = totalReceitasRecebidas;
    }

    public BigDecimal getTotalDespesasPagas() {
        return totalDespesasPagas;
    }

    public void setTotalDespesasPagas(BigDecimal totalDespesasPagas) {
        this.totalDespesasPagas = totalDespesasPagas;
    }

    public Boolean getInicio() {
        return inicio;
    }

    public Boolean getFluxoCaixa() {
        return fluxoCaixa;
    }

    public void setFluxoCaixa(Boolean fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
    }

    public TipoDeReceita getTipoDeReceita() {
        return tipoDeReceita;
    }

    public void setTipoDeReceita(TipoDeReceita tipoDeReceita) {
        this.tipoDeReceita = tipoDeReceita;
    }
}
