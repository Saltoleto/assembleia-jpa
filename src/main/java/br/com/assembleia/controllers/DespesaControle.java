package br.com.assembleia.controllers;


import br.com.assembleia.entities.*;
import br.com.assembleia.enums.EnumMes;
import br.com.assembleia.services.*;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@ManagedBean
@SessionScoped
@Component
public class DespesaControle {

    private Despesa despesa;
    private List<Despesa> despesas;
    private List<Despesa> despesasFiltrados;
    private List<Fornecedor> fornecedores;
    private String titulo;
    private BigDecimal valorDespesaPeriodo;
    private static final Locale BRASIL = new Locale("pt", "BR");
    private DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    private int mesPesquisa = Calendar.getInstance().get(Calendar.MONTH);

    private int anoPesquisa = Calendar.getInstance().get(Calendar.YEAR);
    private List<TipoDeDespesa> tipoDeDespesas;
    private List<Membro> membros;
    private List<Departamento> departamentos;

    //    TELA VISAO GERAL
    private List<Despesa> despesaPagarVisaoGeral = new ArrayList<Despesa>();
    private List<ClasseResumoFinanceiro> listaResumoFinanceiro;
    private Boolean inicio = false;
    private Boolean fluxoCaixa = false;
    private Boolean desabilitaParcela = true;
    private BigDecimal totalReceitasRecebidas;
    private BigDecimal totalDespesasPagas;
    private BigDecimal saldoAtual;
    private TipoDeDespesa tipoDeDespesa;

    //    LISTA DE RECEITAS PARA FLUXO DE CAIXA
    private List<Receita> listaReceitasFluxoCaixa = new ArrayList<Receita>();
    private BigDecimal valorPrevistoPeriodo;
    private BigDecimal receitasPeriodo;
    private BigDecimal despesasPeriodo;

    @Autowired
    private DespesaService service;
    @Autowired
    private DepartamentoService serviceDepartamento;
    @Autowired
    private TipoDeDespesaService serviceTipoDeDespesa;
    @Autowired
    private MembroService serviceMembroService;
    @Autowired
    private ReceitaService serviceReceita;
    @Autowired
    FornecedorService serviceFornecedor;

    @PostConstruct
    private void init() {
        despesa = new Despesa();
        titulo = "Despesas";
        mesPesquisa = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public String novo() {
        despesa = new Despesa();
        titulo = "Nova Despesa";
        desabilitaParcela = false;
        fluxoCaixa = false;
        inicio = false;
        return "form?faces-redirect=true";
    }

    public String fluxoCaixa() {
        return "/fluxocaixa/lista.xhtml?faces-redirect=true";
    }

    public String editar(Despesa despesa) {
        if (despesa != null) {
            this.despesa = despesa;
            titulo = "Editar Despesa";
            desabilitaParcela = true;
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String salvar() {

        try {

            if (despesa.getId() == null) {
                Integer teste = Integer.parseInt(despesa.getParcelas());
                BigDecimal parcelas = new BigDecimal(BigInteger.ZERO);
                parcelas = despesa.getValor().divide(new BigDecimal(teste));
                if (teste > 1) {
                    for (int i = 1; i < teste; i++) {

                        Despesa despesaparcelada = new Despesa();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(despesa.getData());
                        calendar.add(calendar.MONTH, i);
                        despesaparcelada.setTipoDeDespesa(despesa.getTipoDeDespesa());
                        despesaparcelada.setData(calendar.getTime());
                        despesaparcelada.setDepartamento(despesa.getDepartamento());
                        despesaparcelada.setDescricao(despesa.getDescricao() + "(" + (i + 1) + "/" + teste + ")");
                        despesaparcelada.setParcelas("" + (i + 1) + "/" + (i + 1) + "");
                        despesaparcelada.setValor(parcelas);
                        despesaparcelada.setTotalParcelar(teste);
                        service.salvar(despesaparcelada);

                    }
                    despesa.setCongregacao(AplicacaoControle.getInstance().getUsuario().getCongregacao());
                    despesa.setTotalParcelar(teste);
                    despesa.setDescricao(despesa.getDescricao() + "(" + 1 + "/" + teste + ")");
                    despesa.setParcelas("" + 1 + "/" + teste + "");
                    despesa.setValor(parcelas);
                    service.salvar(despesa);
                    adicionaMensagem("Despesa salva com sucesso!", FacesMessage.SEVERITY_INFO);
                    despesa = null;
                } else {
                    despesa.setCongregacao(AplicacaoControle.getInstance().getUsuario().getCongregacao());
                    despesa.setParcelas("1/1");
                    despesa.setTotalParcelar(teste);
                    service.salvar(despesa);
                    adicionaMensagem("Despesa salva com sucesso!", FacesMessage.SEVERITY_INFO);
                    despesa = null;
                }
            } else {
                despesa.setCongregacao(AplicacaoControle.getInstance().getUsuario().getCongregacao());
                service.salvar(despesa);
                adicionaMensagem("Despesa salva com sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public void salvarCategoria() {
        try {
            if (!tipoDeDespesa.getDescricao().isEmpty()) {
                serviceTipoDeDespesa.salvar(tipoDeDespesa);
                tipoDeDespesas = serviceTipoDeDespesa.listarTodos();
                tipoDeDespesa = null;
                org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogCategoria').hide();");
            } else {

                RequestContext context = RequestContext.getCurrentInstance();
                context.addCallbackParam("loggedIn", false);
            }
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public String deletar(Despesa despesa) {
        try {
            if (despesa != null) {
                this.despesa = despesa;
                service.deletar(despesa);
                despesas = null;
                adicionaMensagem("Despesa excluída com Sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (PersistenceException ex) {
            adicionaMensagem("O Despesa está emprestado, não pode ser exlcuído!", FacesMessage.SEVERITY_INFO);
            voltar();
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        despesa = null;
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

    public List<Despesa> getDespesas() {
        if (anoPesquisa < 2014) {
            voltar();
        } else {
            despesas = service.listarDespesasMesAno(mesPesquisa, anoPesquisa);
            Collections.sort(despesas, new Comparator<Despesa>() {
                @Override
                public int compare(Despesa o1, Despesa o2) {
                    return o1.getData().compareTo(o2.getData());
                }
            });

        }

        return despesas;
    }

    public List<Despesa> getDespesaPagarVisaoGeral() {

        despesaPagarVisaoGeral = service.despesasPagarVisaoGeral(mesPesquisa, anoPesquisa);

        Collections.sort(despesaPagarVisaoGeral, new Comparator<Despesa>() {
            @Override
            public int compare(Despesa o1, Despesa o2) {
                return o2.getData().compareTo(o1.getData());
            }
        });

        return despesaPagarVisaoGeral;
    }

    public List<Fornecedor> getFornecedores() {

        return fornecedores = serviceFornecedor.listarPorIgreja(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
    }

    public Boolean getInicio() {
        return inicio;
    }

    public void setInicio(Boolean inicio) {
        this.inicio = inicio;
    }

    public String listar() {
        return "lista?faces-redirect=true";
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Despesa> getDespesasFiltrados() {
        return despesasFiltrados;
    }

    public void setDespesasFiltrados(List<Despesa> despesasFiltrados) {
        this.despesasFiltrados = despesasFiltrados;
    }

    public TipoDeDespesa getTipoDeDespesa() {
        return tipoDeDespesa;
    }

    public void setTipoDeDespesa(TipoDeDespesa tipoDeDespesa) {
        this.tipoDeDespesa = tipoDeDespesa;
    }

    public List<TipoDeDespesa> getTipoDeDespesas() {
        return tipoDeDespesas = serviceTipoDeDespesa.listarTodos();
    }

    public String getValorTotalDespesa() {
        valorDespesaPeriodo = service.valorDespesaPeriodo(mesPesquisa, anoPesquisa);
        if (valorDespesaPeriodo == null) {
            valorDespesaPeriodo = new BigDecimal(BigInteger.ZERO);
        }
        String teste = null;
        if (valorDespesaPeriodo != null) {
            DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
            return teste = df.format(valorDespesaPeriodo);
        } else {
            return teste;
        }

    }

    public String despesasTotalMeasAnoCongregacao() {
        Long idIgreja = null;
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            idIgreja = AplicacaoControle.getInstance().getIdIgreja();
        } else {
            idIgreja = AplicacaoControle.getInstance().getIdIgrejaPorUsuario();
        }

        valorTotal = service.despesasDespesaMeasAnoCongregacao(mesPesquisa, anoPesquisa, idIgreja);

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }


    public String despesasPagasMeasAnoCongregacao() {
        Long idIgreja = null;
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            idIgreja = AplicacaoControle.getInstance().getIdIgreja();
        } else {
            idIgreja = AplicacaoControle.getInstance().getIdIgrejaPorUsuario();
        }

        valorTotal = service.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, idIgreja,Boolean.TRUE);

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String despesasNaoPagasMeasAnoCongregacao() {
        Long idIgreja = null;
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            idIgreja = AplicacaoControle.getInstance().getIdIgreja();
        } else {
            idIgreja = AplicacaoControle.getInstance().getIdIgrejaPorUsuario();
        }

        valorTotal = service.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, idIgreja,Boolean.FALSE);

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }




    public List<ClasseResumoFinanceiro> resumoFinanceiroTelaVisaoGerao() {
        listaResumoFinanceiro = new ArrayList<ClasseResumoFinanceiro>();

        receitasPeriodo = serviceReceita.valorReceitaPeriodo(mesPesquisa, anoPesquisa);
        despesasPeriodo = service.valorDespesaPeriodo(mesPesquisa, anoPesquisa);

        totalReceitasRecebidas = serviceReceita.listarReceitasRecebidas();
        totalDespesasPagas = service.listarDespesasPagas();

        if (receitasPeriodo == null) {
            receitasPeriodo = new BigDecimal(BigInteger.ZERO);
        }
        if (despesasPeriodo == null) {
            despesasPeriodo = new BigDecimal(BigInteger.ZERO);
        }

        if (totalReceitasRecebidas == null) {
            totalReceitasRecebidas = new BigDecimal(BigInteger.ZERO);
        }
        if (totalDespesasPagas == null) {
            totalDespesasPagas = new BigDecimal(BigInteger.ZERO);
        }
        saldoAtual = totalReceitasRecebidas.subtract(totalDespesasPagas);
        ClasseResumoFinanceiro resumo = new ClasseResumoFinanceiro();
        resumo.setDescricao("Saldo Atual");
        resumo.setValor(saldoAtual);
        listaResumoFinanceiro.add(resumo);
        ClasseResumoFinanceiro resumo2 = new ClasseResumoFinanceiro();
        resumo2.setDescricao("Total de Receitas");
        resumo2.setValor(receitasPeriodo);
        listaResumoFinanceiro.add(resumo2);
        ClasseResumoFinanceiro resumo3 = new ClasseResumoFinanceiro();
        resumo3.setDescricao("Total de Despesas");
        resumo3.setValor(despesasPeriodo);
        listaResumoFinanceiro.add(resumo3);
        ClasseResumoFinanceiro resumo4 = new ClasseResumoFinanceiro();
        resumo4.setDescricao("Saldo Previsto");
        resumo4.setValor(receitasPeriodo.subtract(despesasPeriodo));
        listaResumoFinanceiro.add(resumo4);

        return listaResumoFinanceiro;

    }

    public List<ClasseResumoFinanceiro> getListaResumoFinanceiro() {
        return listaResumoFinanceiro = resumoFinanceiroTelaVisaoGerao();
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(BigDecimal saldoAtual) {
        this.saldoAtual = saldoAtual;
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

    public void setValorPrevistoPeriodo(BigDecimal valorPrevistoPeriodo) {
        this.valorPrevistoPeriodo = valorPrevistoPeriodo;
    }

    public BigDecimal getReceitasPeriodo() {
        return receitasPeriodo;
    }

    public void setReceitasPeriodo(BigDecimal receitasPeriodo) {
        this.receitasPeriodo = receitasPeriodo;
    }

    public BigDecimal getDespesasPeriodo() {
        return despesasPeriodo;
    }

    public void setDespesasPeriodo(BigDecimal despesasPeriodo) {
        this.despesasPeriodo = despesasPeriodo;
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

    public Boolean getDesabilitaParcela() {
        return desabilitaParcela;
    }

    public void setDesabilitaParcela(Boolean desabilitaParcela) {
        this.desabilitaParcela = desabilitaParcela;
    }

    public Boolean getFluxoCaixa() {
        return fluxoCaixa;
    }

    public void setFluxoCaixa(Boolean fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
    }

    public List<Receita> getListaReceitasFluxoCaixa() {
        listaReceitasFluxoCaixa = serviceReceita.listarReceitasMesAno(mesPesquisa, anoPesquisa);

        Collections.sort(listaReceitasFluxoCaixa, new Comparator<Receita>() {
            @Override
            public int compare(Receita o1, Receita o2) {
                return o2.getData().compareTo(o1.getData());
            }
        });

        return listaReceitasFluxoCaixa;
    }

    public void setListaReceitasFluxoCaixa(List<Receita> listaReceitasFluxoCaixa) {
        this.listaReceitasFluxoCaixa = listaReceitasFluxoCaixa;
    }


}
