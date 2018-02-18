package br.com.assembleia.controllers;


import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.entities.Despesa;
import br.com.assembleia.entities.ModeloClasseFluxocaixa;
import br.com.assembleia.entities.Receita;
import br.com.assembleia.enums.EnumMesInt;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.DespesaService;
import br.com.assembleia.services.ReceitaService;
import br.com.assembleia.util.ReportsUtil;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * @author fernandosaltoleto
 */
@ManagedBean
@SessionScoped
@Component
public class FluxoCaixaControle {

    private static final Locale BRASIL = new Locale("pt", "BR");
    private DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    private int mesPesquisa = Calendar.getInstance().get(Calendar.MONTH);
    private int anoPesquisa = Calendar.getInstance().get(Calendar.YEAR);
    private String str;

    private List<Receita> listaReceitasFluxoCaixa = new ArrayList<Receita>();
    private List<Despesa> despesas = new ArrayList<Despesa>();
    private BigDecimal saldoAtual;
    private BigDecimal totalReceitasRecebidas;
    private BigDecimal totalDespesasPagas;
    private List<ModeloClasseFluxocaixa> listaFlusxoCaixa = new ArrayList<ModeloClasseFluxocaixa>();
    private StreamedContent file;
    private ReportsUtil report = new ReportsUtil();


    //    LISTA DE RECEITAS PARA FLUXO DE CAIXA
    private BigDecimal valorPrevistoPeriodo;
    private BigDecimal receitasPeriodo;
    private BigDecimal despesasPeriodo;
    @Autowired
    private ReceitaService receitaService;
    @Autowired
    private DespesaService despesaService;
    @Autowired
    private CongregacaoService serviceCongregacao;

    @PostConstruct
    private void init() {

        mesPesquisa = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public String listar() {
        return "lista?faces-redirect=true";
    }

    public List<ModeloClasseFluxocaixa> getListaFlusxoCaixa() {

        List<Receita> listReceita = new ArrayList<Receita>();
        List<Despesa> listDespesa = new ArrayList<Despesa>();
        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            listReceita = receitaService.listarReceitasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
            listDespesa = despesaService.despesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            listReceita = receitaService.listarReceitasMesAno(mesPesquisa,anoPesquisa);
            listDespesa = despesaService.listarDespesasMesAno(mesPesquisa,anoPesquisa);
        } else {
            listReceita = receitaService.listarReceitasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
            listDespesa = despesaService.despesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        Collections.sort(listReceita, new Comparator<Receita>() {
            @Override
            public int compare(Receita o1, Receita o2) {
                return o1.getData().compareTo(o2.getData());
            }
        });

        Collections.sort(listDespesa, new Comparator<Despesa>() {
            @Override
            public int compare(Despesa o1, Despesa o2) {
                return o1.getData().compareTo(o2.getData());
            }
        });


        listaFlusxoCaixa = new ArrayList<ModeloClasseFluxocaixa>();

        for (Receita rece : listReceita) {
            ModeloClasseFluxocaixa fluxo = new ModeloClasseFluxocaixa();
            fluxo.setDescricao(rece.getDescricao());
            fluxo.setCategoria(rece.getTipoDeReceita().getDescricao());
            if (rece.getMembro() != null) {
                fluxo.setMembroDepartamento(rece.getMembro().getNome());
            }
            fluxo.setValor(rece.getValorFormatado());
            fluxo.setRecebidoPago(rece.getRecebidoFormatado());
            fluxo.setData(rece.getData());
            fluxo.setTipo(0);
            listaFlusxoCaixa.add(fluxo);
        }

        for (Despesa desepes : listDespesa) {
            ModeloClasseFluxocaixa fluxo = new ModeloClasseFluxocaixa();
            fluxo.setDescricao(desepes.getDescricao());
            fluxo.setCategoria(desepes.getTipoDeDespesa().getDescricao());
            if (desepes.getDepartamento() != null) {
                fluxo.setMembroDepartamento(desepes.getDepartamento().getNome());
            }
            fluxo.setValor(desepes.getValorFormatado());
            fluxo.setRecebidoPago(desepes.getPagoFormatado());
            fluxo.setData(desepes.getData());
            fluxo.setTipo(1);
            listaFlusxoCaixa.add(fluxo);

        }

        return listaFlusxoCaixa;
    }

    public String getSaldoAtual() {

        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            totalReceitasRecebidas = receitaService.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.TRUE);
            totalDespesasPagas = despesaService.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.TRUE);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            totalReceitasRecebidas = receitaService.receitasParametroMeasAno(mesPesquisa, anoPesquisa, Boolean.TRUE);
            totalDespesasPagas = despesaService.despesaParametroMeasAno(mesPesquisa, anoPesquisa, Boolean.TRUE);
        } else {
            totalReceitasRecebidas = receitaService.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.TRUE);
            totalDespesasPagas = despesaService.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.TRUE);
        }

        if (totalReceitasRecebidas == null) {
            totalReceitasRecebidas = new BigDecimal(BigInteger.ZERO);
        }
        if (totalDespesasPagas == null) {
            totalDespesasPagas = new BigDecimal(BigInteger.ZERO);
        }

        saldoAtual = totalReceitasRecebidas.subtract(totalDespesasPagas);

        return df.format(saldoAtual);

    }

    public StreamedContent imprimir() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "Fluxo de Caixa - " + EnumMesInt.busca(mesPesquisa - 1).toString();

        for (Congregacao congre : listaCong) {
            InputStream is = new ByteArrayInputStream(congre.getLogoIgreja());
            parametros.put("nomeIgrena", congre.getNome());
            parametros.put("endereco", congre.getEndereco());
            parametros.put("telefone", congre.getTelefone());
            parametros.put("cidade", congre.getCidade());
            parametros.put("email", congre.getEmail());
            parametros.put("logo", is);
            parametros.put("cnpj", congre.getCnpj());
            parametros.put("uf", congre.getEstado().getUf());
            parametros.put("bairro", congre.getBairro());
            parametros.put("cep", congre.getCep());
            parametros.put("mesExtenso", EnumMesInt.busca(mesPesquisa - 1).toString().toUpperCase());
            parametros.put("ano", anoPesquisa);
            parametros.put("vp", getValorPrevistoPeriodo());
        }

        return file = (StreamedContent) report.gerarRelatorioPDFcomDS(listaFlusxoCaixa, parametros, "/report/fluxocaixa.jasper", str);

    }

    public String getValorPrevistoPeriodo() {

        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            receitasPeriodo = receitaService.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
            despesasPeriodo = despesaService.valorDespesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            receitasPeriodo = receitaService.listarReceitasTipoMesAno(mesPesquisa, anoPesquisa);
            despesasPeriodo = despesaService.valorDespesaPeriodo(mesPesquisa, anoPesquisa);
        } else {
            receitasPeriodo = receitaService.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
            despesasPeriodo = despesaService.valorDespesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }
        if (receitasPeriodo == null) {
            receitasPeriodo = new BigDecimal(BigInteger.ZERO);
        }
        if (despesasPeriodo == null) {
            despesasPeriodo = new BigDecimal(BigInteger.ZERO);
        }
        valorPrevistoPeriodo = receitasPeriodo.subtract(despesasPeriodo);

        return df.format(valorPrevistoPeriodo);
    }

    public String voltar() {
        return "lista?faces-redirect=true";
    }

    public void setMesPesquisa(int mesPesquisa) {
        this.mesPesquisa = mesPesquisa;
    }

    public int getMesPesquisa() {
        return mesPesquisa;
    }

    public int getAnoPesquisa() {
        return anoPesquisa;
    }

    public void setAnoPesquisa(int anoPesquisa) {
        this.anoPesquisa = anoPesquisa;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public ReportsUtil getReport() {
        return report;
    }

    public void setReport(ReportsUtil report) {
        this.report = report;
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


}
