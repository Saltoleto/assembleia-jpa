
package br.com.assembleia.controllers;


import br.com.assembleia.entities.*;
import br.com.assembleia.services.DespesaService;
import br.com.assembleia.services.ReceitaService;
import br.com.assembleia.services.TipoDeDespesaService;
import br.com.assembleia.services.TipoDeReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author fernandosaltoleto
 */
@ManagedBean
@SessionScoped
@Component
public class GraficoControle {

    private static final Locale BRASIL = new Locale("pt", "BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    private int anoPesquisa = Calendar.getInstance().get(Calendar.YEAR);
    private int mesPesquisa = Calendar.getInstance().get(Calendar.MONTH);

    private List<BigDecimal> receitas;
    private List<BigDecimal> despesas;
    private List<TipoDeDespesa> tipoDeDespesas;
    private List<TipoDeReceita> tipoDeReceitas;
    private List<Receita> listaReceita;
    private List<ReceitasTipoDTO> receitasTipoDTOS;

    @Autowired
    private ReceitaService receitaService;
    @Autowired
    private DespesaService despesaService;
    @Autowired
    private TipoDeDespesaService tipoDeDespesaService;
    @Autowired
    private TipoDeReceitaService tipoDeReceitaService;


    @PostConstruct
    private void init() {

        mesPesquisa = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }


    public List<BigDecimal> getReceitas() {

        receitas = new ArrayList<BigDecimal>();
        for (int i = 1; i <= 12; i++) {
            BigDecimal receita;
            receita = receitaService.buscarReceitaGrafico(i, new Integer(anoPesquisa));
            if (receita == null) {
                receita = new BigDecimal(0);
            }

            receitas.add(receita);
        }
        return receitas;
    }

    public List<BigDecimal> getDespesas() {

        despesas = new ArrayList<BigDecimal>();
        for (int i = 1; i <= 12; i++) {
            BigDecimal despesa;
            despesa = despesaService.buscarDespesaGrafico(i, new Integer(anoPesquisa));
            if (despesa == null) {
                despesa = new BigDecimal(0);
            }

            despesas.add(despesa);
        }
        return despesas;
    }

    public List<Receita> getListaReceita() {
        listaReceita = new ArrayList<Receita>();

        return listaReceita;
    }

    public List<ReceitasTipoDTO> getCatoriaDTOs() {

        receitasTipoDTOS = new ArrayList<ReceitasTipoDTO>();
        tipoDeReceitas = new ArrayList<TipoDeReceita>();
        tipoDeReceitas = tipoDeReceitaService.listarTodos();
        for (TipoDeReceita categoria : tipoDeReceitas) {
            ReceitasTipoDTO receitaCat = new ReceitasTipoDTO();
            receitaCat.setDescricao(categoria.getDescricao());
            receitaCat.setValorReceita(receitaService.listarReceitasTipoMesAno(categoria.getId(), mesPesquisa, anoPesquisa));
            if (receitaCat.getValorReceita() != null) {
                receitasTipoDTOS.add(receitaCat);
            }

        }
        return receitasTipoDTOS;
    }

    public List<DespesasTipoDTO> getListarDepesasTipoDto() {
        List<DespesasTipoDTO> despesasTipoDTOS = new ArrayList<>();
        despesasTipoDTOS = despesaService.listarDespesasTipoMesAno(mesPesquisa, anoPesquisa);
        return despesasTipoDTOS;
    }

    public String listarDespesasCategoria(){
        return "despesascategoria?faces-redirect=true";
    }

    public int getAnoPesquisa() {
        return anoPesquisa;
    }

    public void setAnoPesquisa(int anoPesquisa) {
        this.anoPesquisa = anoPesquisa;
    }

    public int getMesPesquisa() {
        return mesPesquisa;
    }

    public void setMesPesquisa(int mesPesquisa) {
        this.mesPesquisa = mesPesquisa;
    }

    public void setReceitas(List<BigDecimal> receitas) {
        this.receitas = receitas;
    }

    public void setDespesas(List<BigDecimal> despesas) {
        this.despesas = despesas;
    }

    public List<TipoDeDespesa> getTipoDeDespesas() {
        return tipoDeDespesas;
    }

    public void setTipoDeDespesas(List<TipoDeDespesa> tipoDeDespesas) {
        this.tipoDeDespesas = tipoDeDespesas;
    }

    public List<TipoDeReceita> getTipoDeReceitas() {
        return tipoDeReceitas;
    }

    public void setTipoDeReceitas(List<TipoDeReceita> tipoDeReceitas) {
        this.tipoDeReceitas = tipoDeReceitas;
    }

    public void setListaReceita(List<Receita> listaReceita) {
        this.listaReceita = listaReceita;
    }

    public List<ReceitasTipoDTO> getReceitasTipoDTOS() {
        return receitasTipoDTOS;
    }

    public void setReceitasTipoDTOS(List<ReceitasTipoDTO> receitasTipoDTOS) {
        this.receitasTipoDTOS = receitasTipoDTOS;
    }

    public ReceitaService getReceitaService() {
        return receitaService;
    }

    public void setReceitaService(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    public DespesaService getDespesaService() {
        return despesaService;
    }

    public void setDespesaService(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    public TipoDeDespesaService getTipoDeDespesaService() {
        return tipoDeDespesaService;
    }

    public void setTipoDeDespesaService(TipoDeDespesaService tipoDeDespesaService) {
        this.tipoDeDespesaService = tipoDeDespesaService;
    }

    public TipoDeReceitaService getTipoDeReceitaService() {
        return tipoDeReceitaService;
    }

    public void setTipoDeReceitaService(TipoDeReceitaService tipoDeReceitaService) {
        this.tipoDeReceitaService = tipoDeReceitaService;
    }
}
