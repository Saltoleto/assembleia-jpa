package br.com.assembleia.controllers;

import br.com.assembleia.enums.EnumEstado;
import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.services.CongregacaoService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;

@ManagedBean
@SessionScoped
@Component
public class CongregacaoControle {

    private Congregacao congregacao;
    private List<Congregacao> congregacaos;
    private List<Congregacao> congregacaosFiltrados;
    private String titulo;
    private StreamedContent fotoBanco;
    private UploadedFile file;
    private byte[] bimagem;
    private File arquivo;
    
    @Autowired
    private CongregacaoService service;

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
    String teste = servletContext.getRealPath("/Resources/img/semFoto2.jpg");
    

    @PostConstruct
    private void init() {
        congregacao = new Congregacao();
    }

    public String novo() throws FileNotFoundException {
        congregacao = new Congregacao();
        titulo = "Cadastrar Congregacao";

        arquivo = new File(teste);
        InputStream f = new FileInputStream(arquivo);
        StreamedContent img = new DefaultStreamedContent(f);
        fotoBanco = img;
        return "form?faces-redirect=true";
    }

    public String carregarCadastro() {
        if (congregacao != null) {
            titulo = "Editar Congregacao";
            fotoBanco = new DefaultStreamedContent(new ByteArrayInputStream(congregacao.getLogoIgreja()));
            return "form?faces-redirect=true";
        }
        adicionaMensagem("Nenhuma Congregacao foi selecionada para a alteração!", FacesMessage.SEVERITY_INFO);
        return "lista?faces-redirect=true";

    }

    public String salvar() throws IOException {

        try {
            if (congregacao.getId() == null && file == null) {
                Path path = Paths.get(teste);
                byte[] data = Files.readAllBytes(path);
                congregacao.setLogoIgreja(data);
                service.salvar(congregacao);
                adicionaMensagem("Congregacao salva com sucesso!", FacesMessage.SEVERITY_INFO);

                fotoBanco = null;
                arquivo = null;
            } else if (file == null && congregacao.getId() != null) {
                service.salvar(congregacao);
                adicionaMensagem("Congregacao salva com sucesso!", FacesMessage.SEVERITY_INFO);

                fotoBanco = null;
                arquivo = null;
            } else {
                bimagem = file.getContents();
                congregacao.setLogoIgreja(bimagem);
                service.salvar(congregacao);
                adicionaMensagem("Congregacao salva com sucesso!", FacesMessage.SEVERITY_INFO);
                congregacao = null;
                fotoBanco = null;
                arquivo = null;
            }

        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "lista?faces-redirect=true";
    }

    public void chamarExclusao() {
        if (new AplicacaoControle().validaUsuario()) {
            if (congregacao == null) {
                adicionaMensagem("Nenhuma Congregacao foi selecionada para a exclusão!", FacesMessage.SEVERITY_INFO);
                return;
            }

            org.primefaces.context.RequestContext.getCurrentInstance().execute("confirmacaoMe.show()");
        }
    }

    public String deletar() {
        try {
            service.deletar(congregacao);
            congregacaos = null;
            adicionaMensagem("Congregação Excluida com Sucesso!", FacesMessage.SEVERITY_INFO);

        } catch (PersistenceException ex) {
            adicionaMensagem(ex.getMessage(), FacesMessage.SEVERITY_INFO);
            voltar();
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
        congregacaos = service.listarTodos();

        Collections.sort(congregacaos);

        return congregacaos;
    }

    public void setCongregacaos(List<Congregacao> congregacaos) {
        this.congregacaos = congregacaos;
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

    public List<Congregacao> getCongregacaosFiltrados() {
        return congregacaosFiltrados;
    }

    public void setCongregacaosFiltrados(List<Congregacao> congregacaosFiltrados) {
        this.congregacaosFiltrados = congregacaosFiltrados;
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
            Logger.getLogger(MembroControle.class.getName()).log(Level.SEVERE,
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

}
