/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.assembleia.controle;

import br.assembleia.entidades.*;
import br.assembleia.service.CategoriaService;
import br.assembleia.service.DespesaService;
import br.assembleia.service.ReceitaService;
import com.google.gson.Gson;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author fernandosaltoleto
 */
@Controller
@Scope("session")
public class GraficoControle {

    private static final Locale BRASIL = new Locale("pt", "BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    private int anoPesquisa = Calendar.getInstance().get(Calendar.YEAR);
    private int mesPesquisa = Calendar.getInstance().get(Calendar.MONTH);

    private List<BigDecimal> receitas;
    private List<BigDecimal> despesas;
    private List<Categoria> categorias;
    private List<Despesa> despesasCategoria;
    private List<Receita> listaReceita;
    private List<ReceitasCatoriaDTO> receitasCatoriaDTOs;
    private List<DespesasCatoriaDTO> depesasCatoriaDTOs;

    @Autowired
    private ReceitaService receitaService;
    @Autowired
    private DespesaService despesaService;
    @Autowired
    private CategoriaService categoriaService;

    @PostConstruct
    private void init() {

        mesPesquisa = Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public void receitasDespesas() {
        List<BigDecimal> receitasTela = getReceitas();
        List<BigDecimal> despesasTela = getDespesas();
        RequestContext reqCtx = RequestContext.getCurrentInstance();
        reqCtx.addCallbackParam("receitas", new Gson().toJson(receitasTela));
        reqCtx.addCallbackParam("despesas", new Gson().toJson(despesasTela));

    }

    public void receitasCategoria() {
        List<ReceitasCatoriaDTO> receitasCategoria = getCatoriaDTOs();
        RequestContext reqCtx = RequestContext.getCurrentInstance();
        reqCtx.addCallbackParam("receitasCategoria", new Gson().toJson(receitasCategoria));
    }
    
      public void despesasCategoria() {
        List<DespesasCatoriaDTO> despesasCategoria = getDepesasCatoriaDTOs();
        RequestContext reqCtx = RequestContext.getCurrentInstance();
        reqCtx.addCallbackParam("despesasCategoria", new Gson().toJson(despesasCategoria));
    }

    public List<BigDecimal> getReceitas() {

        receitas = new ArrayList<BigDecimal>();
        for (int i = 1; i <= 12; i++) {
            BigDecimal receita;
            receita = receitaService.buscarReceitaGrafico(new Long(i), new Integer(anoPesquisa));
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

    public List<ReceitasCatoriaDTO> getCatoriaDTOs() {

        receitasCatoriaDTOs = new ArrayList<ReceitasCatoriaDTO>();
        categorias = new ArrayList<Categoria>();
        categorias = categoriaService.listarTodos();
        for (Categoria categoria : categorias) {
            ReceitasCatoriaDTO receitaCat = new ReceitasCatoriaDTO();
            receitaCat.setDescricao(categoria.getDescricao());
            receitaCat.setValorReceita(receitaService.listarReceitasCategoriaMesAno(mesPesquisa, anoPesquisa, categoria.getId()));
            if (receitaCat.getValorReceita() != null) {
                receitasCatoriaDTOs.add(receitaCat);
            }
            
        }
        return receitasCatoriaDTOs;
    }

    public List<DespesasCatoriaDTO> getDepesasCatoriaDTOs() {
        depesasCatoriaDTOs = new ArrayList<DespesasCatoriaDTO>();
        categorias = new ArrayList<Categoria>();
        categorias = categoriaService.listarTodos();
        for (Categoria categoria : categorias) {
            DespesasCatoriaDTO despesa = new DespesasCatoriaDTO();
            despesa.setDescricao(categoria.getDescricao());
            despesa.setValorDespesa(despesaService.listarDespesasCategoriaMesAno(mesPesquisa, anoPesquisa, categoria.getId()));
            if (despesa.getValorDespesa() !=null) {
                depesasCatoriaDTOs.add(despesa);
            }
            
        }
        return depesasCatoriaDTOs;
    }

    public List<Despesa> getDespesasCategoria() {
        return despesasCategoria;
    }

    public void setDespesasCategoria(List<Despesa> despesasCategoria) {
        this.despesasCategoria = despesasCategoria;
    }

    public void setReceitas(List<BigDecimal> receitas) {
        this.receitas = receitas;
    }

    public int getAnoPesquisa() {
        return anoPesquisa;
    }

    public void setAnoPesquisa(int anoPesquisa) {
        this.anoPesquisa = anoPesquisa;
    }

    public List<Categoria> getCategorias() {
        return categorias = categoriaService.listarTodos();
    }

    public int getMesPesquisa() {
        return mesPesquisa;
    }

    public void setMesPesquisa(int mesPesquisa) {
        this.mesPesquisa = mesPesquisa;
    }

    public List<ReceitasCatoriaDTO> getReceitasCatoriaDTOs() {
        return receitasCatoriaDTOs;
    }

    public void setReceitasCatoriaDTOs(List<ReceitasCatoriaDTO> receitasCatoriaDTOs) {
        this.receitasCatoriaDTOs = receitasCatoriaDTOs;
    }

    public void setDepesasCatoriaDTOs(List<DespesasCatoriaDTO> depesasCatoriaDTOs) {
        this.depesasCatoriaDTOs = depesasCatoriaDTOs;
    }

}
