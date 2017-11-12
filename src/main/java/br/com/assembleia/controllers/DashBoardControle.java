package br.com.assembleia.controllers;


import br.com.assembleia.enums.EnumMes;
import br.com.assembleia.enums.EnumSexo;
import br.com.assembleia.services.*;
import br.com.assembleia.vo.DizimistaVO;
import br.com.assembleia.vo.ResumoFinanceiroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@ManagedBean
@SessionScoped
@Component
public class DashBoardControle {

    private static final Locale BRASIL = new Locale("pt", "BR");
    private DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    private int mesPesquisa = Calendar.getInstance().get(Calendar.MONTH);
    private int anoPesquisa = Calendar.getInstance().get(Calendar.YEAR);

    @Autowired
    private DespesaService serviceDespesa;
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
        mesPesquisa = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public Integer getTotalMembrosHomens() {
        Integer totalMembros;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            totalMembros = serviceMembroService.totalMembrosPorSexo(AplicacaoControle.getInstance().getIdIgreja(), EnumSexo.MASCULINO);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            totalMembros = serviceMembroService.totalMembrosPorSexoGeral(EnumSexo.MASCULINO);
        } else {
            totalMembros = serviceMembroService.totalMembrosPorSexo(AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), EnumSexo.MASCULINO);
        }

        return totalMembros != null ? totalMembros : 0;
    }

    public Integer getTotalMembrosAtivos() {

        Integer totalMembros;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            totalMembros = serviceMembroService.totalMembrosAtivos(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            totalMembros = serviceMembroService.buscarQtdTotalMembros();
        } else {
            totalMembros = serviceMembroService.totalMembrosAtivos(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        return totalMembros != null ? totalMembros : 0;
    }

    public Integer getTotalMembrosMulheres() {
        Integer totalMembros;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            totalMembros = serviceMembroService.totalMembrosPorSexo(AplicacaoControle.getInstance().getIdIgreja(), EnumSexo.FEMININO);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            totalMembros = serviceMembroService.totalMembrosPorSexoGeral(EnumSexo.FEMININO);
        } else {
            totalMembros = serviceMembroService.totalMembrosPorSexo(AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), EnumSexo.FEMININO);
        }
        return totalMembros != null ? totalMembros : 0;
    }

    public String receitasTotalMeasAnoCongregacao() {

        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceReceita.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceReceita.listarReceitas();
        } else {
            valorTotal = serviceReceita.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String receitasRecebidasMeasAnoCongregacao() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceReceita.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.TRUE);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceReceita.listarReceitasParametro(Boolean.TRUE);
        } else {
            valorTotal = serviceReceita.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.TRUE);
        }

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String receitasNaoRecebidasMeasAnoCongregacao() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceReceita.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.FALSE);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceReceita.listarReceitasParametro(Boolean.FALSE);
        } else {
            valorTotal = serviceReceita.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.FALSE);
        }

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String despesasTotalMeasAnoCongregacao() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceDespesa.valorDespesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceDespesa.listarDespesas();
        } else {
            valorTotal = serviceDespesa.valorDespesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }
        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String despesasPagasMeasAnoCongregacao() {

        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceDespesa.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.TRUE);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceDespesa.listarDespesasParametro(Boolean.TRUE);
        } else {
            valorTotal = serviceDespesa.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.TRUE);
        }

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String despesasNaoPagasMeasAnoCongregacao() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceDespesa.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.FALSE);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceDespesa.listarDespesasParametro(Boolean.FALSE);
        } else {
            valorTotal = serviceDespesa.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.FALSE);
        }

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String valorTotalReceitas() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceReceita.valorTotalReceitas(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceReceita.listarReceitas();
        } else {
            valorTotal = serviceReceita.valorTotalReceitas(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String valorTotalDespesas() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            valorTotal = serviceDespesa.valorTotalDespesasCongregacao(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            valorTotal = serviceDespesa.listarDespesas();
        } else {
            valorTotal = serviceDespesa.valorTotalDespesasCongregacao(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }

        return df.format(valorTotal != null ? valorTotal : BigDecimal.ZERO);
    }

    public String getNomeMes() {
        return EnumMes.busca(Calendar.getInstance().get(Calendar.MONTH)).getDescricao();
    }

    public String getSaldoPrevisto() {
        BigDecimal receitasPeriodo;
        BigDecimal despesasPeriodo;
        BigDecimal valorPrevistoPeriodo;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            receitasPeriodo = serviceReceita.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
            despesasPeriodo = serviceDespesa.valorDespesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            receitasPeriodo = serviceReceita.listarReceitasTipoMesAno(mesPesquisa, anoPesquisa);
            despesasPeriodo = serviceDespesa.valorDespesaPeriodo(mesPesquisa, anoPesquisa);
        } else {
            receitasPeriodo = serviceReceita.receitasRecebidasMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
            despesasPeriodo = serviceDespesa.valorDespesasMesAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
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

    public String getSaldoAtual() {
        BigDecimal totalReceitasRecebidas;
        BigDecimal totalDespesasPagas;
        BigDecimal saldoAtual;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            totalReceitasRecebidas = serviceReceita.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.TRUE);
            totalDespesasPagas = serviceDespesa.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja(), Boolean.TRUE);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            totalReceitasRecebidas = serviceReceita.receitasParametroMeasAno(mesPesquisa, anoPesquisa, Boolean.TRUE);
            totalDespesasPagas = serviceDespesa.despesaParametroMeasAno(mesPesquisa, anoPesquisa, Boolean.TRUE);
        } else {
            totalReceitasRecebidas = serviceReceita.receitasParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.TRUE);
            totalDespesasPagas = serviceDespesa.despesaParametroMeasAnoCongregacao(mesPesquisa, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.TRUE);
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

    public List<DizimistaVO> getListaDizimistas() {
        List<DizimistaVO> dizimistas = new ArrayList<>();
        Integer quantidadeDizimistas;
        Integer quantidadeNaoDizimistas;
        if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null) {
            quantidadeDizimistas = serviceMembroService.totalDizimistasPorParametro(AplicacaoControle.getInstance().getIdIgreja(), Boolean.TRUE);
            quantidadeNaoDizimistas = serviceMembroService.totalDizimistasPorParametro(AplicacaoControle.getInstance().getIdIgreja(), Boolean.FALSE);
        } else if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
            quantidadeDizimistas = serviceMembroService.totalDizimistas(Boolean.TRUE);
            quantidadeNaoDizimistas = serviceMembroService.totalDizimistas(Boolean.FALSE);
        } else {
            quantidadeDizimistas = serviceMembroService.totalDizimistasPorParametro(AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.TRUE);
            quantidadeNaoDizimistas = serviceMembroService.totalDizimistasPorParametro(AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), Boolean.FALSE);
        }

        dizimistas.add(new DizimistaVO("Dizimistas", quantidadeDizimistas != null ? quantidadeDizimistas : 0));
        dizimistas.add(new DizimistaVO("Não Dizimistas", quantidadeNaoDizimistas != null ? quantidadeNaoDizimistas : 0));

        return dizimistas;
    }

    public List<ResumoFinanceiroVO> getResumoFinanceiroReceitasGrafico() {
        List<ResumoFinanceiroVO> resumoFinanceiroVOS = new ArrayList<>();
        BigDecimal valorReceitaPeriodo;

        for (EnumMes mes : EnumMes.values()){
            if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null)  {
                valorReceitaPeriodo = serviceReceita.receitasRecebidasMeasAnoCongregacao(mes.ordinal() +1, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
            } else if(AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
                valorReceitaPeriodo = serviceReceita.valorReceitaPeriodo(mes.ordinal()+1,anoPesquisa);
            }else{
                valorReceitaPeriodo = serviceReceita.receitasRecebidasMeasAnoCongregacao(mes.ordinal()+1, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
            }
            resumoFinanceiroVOS.add(new ResumoFinanceiroVO(mes.getDescricao(), valorReceitaPeriodo !=null ? valorReceitaPeriodo : BigDecimal.ZERO));
        }

        return resumoFinanceiroVOS;
    }

    public List<ResumoFinanceiroVO> getResumoFinanceiroDespesasGrafico() {
        List<ResumoFinanceiroVO> resumoFinanceiroVOS = new ArrayList<>();
        BigDecimal valorDespesaPeriodo;

        for (EnumMes mes : EnumMes.values()){
            if (AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() != null)  {
                valorDespesaPeriodo = serviceDespesa.valorDespesasMesAnoCongregacao(mes.ordinal() +1, anoPesquisa, AplicacaoControle.getInstance().getIdIgreja());
            } else if(AplicacaoControle.getInstance().adminSede() && AplicacaoControle.getInstance().getIdIgreja() == null) {
                valorDespesaPeriodo = serviceDespesa.valorDespesaPeriodo(mes.ordinal()+1,anoPesquisa);
            }else{
                valorDespesaPeriodo = serviceDespesa.valorDespesasMesAnoCongregacao(mes.ordinal()+1, anoPesquisa, AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
            }
            resumoFinanceiroVOS.add(new ResumoFinanceiroVO(mes.getDescricao(), valorDespesaPeriodo !=null ? valorDespesaPeriodo : BigDecimal.ZERO));
        }

        return resumoFinanceiroVOS;
    }
}
