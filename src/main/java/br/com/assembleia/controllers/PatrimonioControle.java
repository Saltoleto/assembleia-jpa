package br.com.assembleia.controllers;

import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.entities.Departamento;
import br.com.assembleia.entities.Patrimonio;
import br.com.assembleia.enums.EnumSituacaoPatrimonio;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.DepartamentoService;
import br.com.assembleia.services.PatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@ManagedBean
@SessionScoped
@Component
public class PatrimonioControle {

    private Patrimonio patrimonio;
    private List<Patrimonio> patrimonios;
    private String titulo;
    private List<Departamento> departamentos;
    private BigDecimal valorTotalPatrimonio;
    private static final Locale BRASIL = new Locale("pt", "BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);
    private List<Congregacao> congregacoes;

    @Autowired
    private PatrimonioService service;
    @Autowired
    private DepartamentoService serviceDepartamento;
    @Autowired
    private CongregacaoService serviceCongregacao;

    @PostConstruct
    private void init() {
        patrimonio = new Patrimonio();
    }

    public String novo() {
        patrimonio = new Patrimonio();
        titulo = "Cadastrar Patrimônio";
        return "form?faces-redirect=true";
    }

    public String editar(Patrimonio patrimonio) {
        if (patrimonio != null) {
            this.patrimonio = patrimonio;
            titulo = "Editar Patrimônio";
            return "form?faces-redirect=true";
        }
        adicionaMensagem("Nenhum Patrimonio foi selecionado para a alteração!", FacesMessage.SEVERITY_INFO);
        return "lista?faces-redirect=true";

    }

    public String salvar() {

        try {
            if (patrimonio.getQuantidade().intValue() == 0 || patrimonio.getQuantidade().toString().equals("")) {
                patrimonio.setQuantidade(BigDecimal.ONE);
            }
            patrimonio.setValorTotal(patrimonio.getValorUnitario().multiply(patrimonio.getQuantidade()));

            service.salvar(patrimonio);
            adicionaMensagem("Patrimonio salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            patrimonio = null;
        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String deletar(Patrimonio patrimonio) {
        try {
            if (patrimonio != null) {
                this.patrimonio = patrimonio;
                service.deletar(patrimonio);
                patrimonios = null;
                adicionaMensagem("Patrimônio excluído com Sucesso!", FacesMessage.SEVERITY_INFO);
            }
        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Esta Patrimônio não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        patrimonio = null;
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public List<Patrimonio> getPatrimonios() {
        patrimonios = service.listarPorIgreja(AplicacaoControle.getInstance().getIdIgreja());
        Collections.sort(patrimonios);
        return patrimonios;
    }

    public void setPatrimonios(List<Patrimonio> patrimonios) {
        this.patrimonios = patrimonios;
    }

    public Patrimonio getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Patrimonio patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<EnumSituacaoPatrimonio> getListaSituacaoPatrimonio() {
        return Arrays.asList(EnumSituacaoPatrimonio.values());
    }

    public List<Departamento> getDepartamentos() {
        return departamentos = serviceDepartamento.listarTodos();
    }

    public String getValorTotalPatrimonio() {
        valorTotalPatrimonio = service.valorPatrimonio();
        String teste = null;
        if (valorTotalPatrimonio != null) {
            DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", REAL);
            return teste = df.format(valorTotalPatrimonio);
        } else {
            return teste;
        }
    }

    public List<Congregacao> getCongregacoes() {
        return serviceCongregacao.listarTodos();
    }

}
