package br.com.assembleia.controllers;


import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.enums.EnumEstado;
import br.com.assembleia.services.SedeService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Scope("session")
public class SedeControle {

    private Congregacao congregacao;
    private List<Congregacao> congregacoes;
    private List<Congregacao> sedes;
    private String titulo;
    private StreamedContent fotoBanco;
    private UploadedFile file;
    private byte[] bimagem;
    private File arquivo;

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
    String semImagem = servletContext.getRealPath("/Resources/img/semFoto2.jpg");

    @Autowired
    private SedeService service;

    @PostConstruct
    private void init() {
        congregacao = new Congregacao();
    }

    public String novaSede() throws FileNotFoundException {
        congregacao = new Congregacao();
        congregacao.setIsSede(Boolean.TRUE);
        titulo = "Cadastrar Sede";

        arquivo = new File(semImagem);
        InputStream f = new FileInputStream(arquivo);
        StreamedContent img = new DefaultStreamedContent(f);
        fotoBanco = img;
        return "form?faces-redirect=true";
    }

    public String carregarCadastro(Congregacao congregacao) {
        this.congregacao = congregacao;
        if (congregacao != null) {
            titulo = "Editar Sede";
            fotoBanco = new DefaultStreamedContent(new ByteArrayInputStream(congregacao.getLogoIgreja()));
            return "form?faces-redirect=true";
        }        
        return "lista?faces-redirect=true";

    }

    public String salvar() throws IOException {

        try {
            if (congregacao.getId() == null && file == null) {
                Path path = Paths.get(semImagem);
                byte[] data = Files.readAllBytes(path);
                congregacao.setLogoIgreja(data);
                service.salvar(congregacao);
                adicionaMensagem("Sede salva com sucesso!", FacesMessage.SEVERITY_INFO);

                fotoBanco = null;
                arquivo = null;
            } else if (file == null && congregacao.getId() != null) {
                service.salvar(congregacao);
                adicionaMensagem("Sede salva com sucesso!", FacesMessage.SEVERITY_INFO);

                fotoBanco = null;
                arquivo = null;
            } else {
                bimagem = file.getContents();
                congregacao.setLogoIgreja(bimagem);
                service.salvar(congregacao);
                adicionaMensagem("Sede salva com sucesso!", FacesMessage.SEVERITY_INFO);
                congregacao = null;
                fotoBanco = null;
                arquivo = null;
            }

        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }


    public String deletar(Congregacao congregacao) {
        try {
            this.congregacao = congregacao;
            service.deletar(congregacao);
            congregacoes = null;
            adicionaMensagem("Sede excluída com sucesso!", FacesMessage.SEVERITY_INFO);

        } catch (DataIntegrityViolationException ex) {
            adicionaMensagem("Ops! Esta Sede não pode ser excluída, ela possui vínculos", FacesMessage.SEVERITY_ERROR);
        }

        return "lista?faces-redirect=true";
    }

    public String voltar() {
        congregacao = null;
        return "lista?faces-redirect=true";
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public List<Congregacao> getCongregacaos() {
        congregacoes = service.listarTodos();

        Collections.sort(congregacoes);

        return congregacoes;
    }

    public void setCongregacaos(List<Congregacao> congregacaos) {
        this.congregacoes = congregacaos;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<EnumEstado> getListaEstado() {
        return Arrays.asList(EnumEstado.values());
    }

    public void fileUploadAction(FileUploadEvent event) {
        try {

            InputStream is = event.getFile().getInputstream();
            fotoBanco = new DefaultStreamedContent(is);
            file = event.getFile();
        } catch (IOException ex) {
            Logger.getLogger(SedeControle.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public StreamedContent getFotoBanco() {
        return fotoBanco;
    }

    public void setFotoBanco(StreamedContent fotoBanco) {
        this.fotoBanco = fotoBanco;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public byte[] getBimagem() {
        return bimagem;
    }

    public void setBimagem(byte[] bimagem) {
        this.bimagem = bimagem;
    }

    public List<Congregacao> getSedes() {
       
        sedes = service.listarSedes();
        Collections.sort(sedes);

        return sedes;
    }

    public void setSedes(List<Congregacao> sedes) {
        this.sedes = sedes;
    }

}
