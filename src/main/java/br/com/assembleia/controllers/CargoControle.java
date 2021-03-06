package br.com.assembleia.controllers;

import br.com.assembleia.entities.Cargo;
import br.com.assembleia.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
public class CargoControle {

    private Cargo cargo;
    private List<Cargo> cargos;
    private List<Cargo> cargosFiltrados;
    private String titulo;

    @Autowired
    private CargoService service;

    @PostConstruct
    private void init() {
        cargo = new Cargo();
    }

    public String novo() {
        cargo = new Cargo();
        titulo = "Cadastro de Cargo";
        return "form?faces-redirect=true";
    }

    public String salvar() {
        try {
            service.salvar(cargo);
            adicionaMensagem("Cargo salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            cargo = null;
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public String editar(Cargo cargo) {
        if (cargo != null) {
            this.cargo = cargo;
            titulo = "Editar Cargo";
            return "form?faces-redirect=true";
        }
        return "lista?faces-redirect=true";

    }

    public String deletar(Cargo cargo) {
        try {
            if (cargo != null) {                
                this.cargo = cargo;
                service.deletar(cargo);
                cargos = null;
                adicionaMensagem("Cargo excluido com sucesso!", FacesMessage.SEVERITY_INFO);
            }

        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Este Cargo não pode ser excluído, ele possui vínculos", FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        cargo = null;
        return "lista?faces-redirect=true";
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Cargo> getCargos() {
        return cargos = service.listarTodos();
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public List<Cargo> getCargosFiltrados() {
        return cargosFiltrados;
    }

    public void setCargosFiltrados(List<Cargo> cargosFiltrados) {
        this.cargosFiltrados = cargosFiltrados;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
