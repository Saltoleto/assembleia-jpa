package br.com.assembleia.controllers;

import br.com.assembleia.entities.Cargo;
import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.entities.Membro;
import br.com.assembleia.entities.ModeloClassesVisao;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;


import br.com.assembleia.enums.EnumEstado;
import br.com.assembleia.enums.EnumEstadoCivil;
import br.com.assembleia.enums.EnumSexo;
import br.com.assembleia.enums.EnumSituacao;
import br.com.assembleia.services.CargoService;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.MembroService;
import br.com.assembleia.util.ReportsUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.PersistenceException;
//import javax.persistence.criteria.Path;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.springframework.stereotype.Component;

@ManagedBean
@SessionScoped
@Component
public class MembroControle {

    private Membro membro;
    private Membro membroRelatorio;
    private List<Membro> membros;
    private List<Membro> membrosFiltrados;
    private String titulo;
    private List<Cargo> cargos;
    private List<Membro> listaLider;
    private byte[] bimagem;
    private StreamedContent fotoBanco;
    private StreamedContent fotoBancoDialog;
    private String semFoto;
    private boolean skip;
    private boolean inicio;
    private UploadedFile file;
    private StreamedContent fileReport;
    private File arquivo;
    private List<ModeloClassesVisao> listaTotalMembros = new ArrayList<ModeloClassesVisao>();
    private ModeloClassesVisao membrosVisaoGeral;
    private List<Membro> listaAniversariantes = new ArrayList<Membro>();
    private Cargo cargo;
    private String str;
    private ReportsUtil report = new ReportsUtil();     

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
    String teste = servletContext.getRealPath("/Resources/img/semFoto2.jpg");
    @Autowired
    private MembroService service;
    @Autowired
    private CargoService CargoService;
    @Autowired
    private CongregacaoService serviceCongregacao;
    

    @PostConstruct
    private void init() {

    }

    public String novo() throws FileNotFoundException {
        membro = new Membro();
        titulo = "Cadastro de Membro";
        membro.setCodigoMembro("");
        arquivo = new File(teste);
        InputStream f = new FileInputStream(arquivo);
        StreamedContent img = new DefaultStreamedContent(f);        
        fotoBanco = img;        
        return "form?faces-redirect=true";
    }

    public void novoCargo() {
        cargo = new Cargo();
    }

    public String novoVisaoGeral() throws FileNotFoundException {
        membro = new Membro();
        titulo = "Cadastro de Membro";
        arquivo = new File(teste);
        InputStream f = new FileInputStream(arquivo);
        StreamedContent img = new DefaultStreamedContent(f);
        fotoBanco = img;
        inicio = true;
        return "/membro/form.xhtml?faces-redirect=true";
    }

    public String carregarCadastro() throws FileNotFoundException { 
        if (membro != null) {
            titulo = "Editar Membro";
            fotoBanco = new DefaultStreamedContent(new ByteArrayInputStream(membro.getFoto()));  
            membro.setCodigoMembro(membro.getCodigoMembroFormatado());
            return "form?faces-redirect=true";
        }
        adicionaMensagem("Nenhum membro foi selecionado para a alteração!", FacesMessage.SEVERITY_INFO);
        return "lista?faces-redirect=true";

    }

    public String salvar() throws FileNotFoundException, IOException {
        try {

            if (membro.getId() == null && file == null) {
                Path path = Paths.get(teste);
                byte[] data = Files.readAllBytes(path);
                membro.setFoto(data);
                service.salvar(membro);
                adicionaMensagem("Membro salvo com sucesso!", FacesMessage.SEVERITY_INFO);

                membro = null;
                fotoBanco = null;
                arquivo = null;
            } else if (file == null && membro.getId() != null) {
//                String nomeArquivo = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);
//                bimagem = file.getContents();
//                membro.setFoto(bimagem);
                service.salvar(membro);
                adicionaMensagem("Membro salvo com sucesso!", FacesMessage.SEVERITY_INFO);

                membro = null;
                fotoBanco = null;
                arquivo = null;
            } else {
                bimagem = file.getContents();
                membro.setFoto(bimagem);
                service.salvar(membro);
                adicionaMensagem("Membro salvo com sucesso!", FacesMessage.SEVERITY_INFO);

                membro = null;
                fotoBanco = null;
                arquivo = null;
            }
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);

        }
        return "lista?faces-redirect=true";
    }

    public void salvarCargo() {
        try {
            if (!cargo.getDescricao().isEmpty()) {
                CargoService.salvar(cargo);
                cargos = CargoService.listarTodos();
                cargo = null;
                org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogCargo').hide();");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.addCallbackParam("loggedIn", false);
            }
        } catch (IllegalArgumentException e) {
            adicionaMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public void chamarExclusao() {
        if (new AplicacaoControle().validaUsuario()) {
            if (membro == null) {
                adicionaMensagem("Nenhum membro foi selecionado para a exclusão!", FacesMessage.SEVERITY_INFO);
                return;
            }
            org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('confirmacaoMe').show();");
        }
    }

    public String deletar() {
        try {
            if (membro != null) {

                service.deletar(membro);
                membros = null;
                adicionaMensagem("Membro excluido com sucesso!", FacesMessage.SEVERITY_INFO);
                file = new DefaultUploadedFile();
            }

        } catch (PersistenceException ex) {
            adicionaMensagem("O membro possui vínculos, não pode ser excluído(a)!", FacesMessage.SEVERITY_ERROR);
            voltar();
        }
        return "lista?faces-redirect=true";
    }

    public String voltar() {
        membro = null;
        fotoBanco = null;
        bimagem = null;
        arquivo = null;
        if (inicio) {
            inicio = false;
            return "/index.xhtml?faces-redirect=true";
        }
        return "lista?faces-redirect=true";
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public List<ModeloClassesVisao> totalMembros() {
        listaTotalMembros = new ArrayList<ModeloClassesVisao>();
        membrosVisaoGeral = new ModeloClassesVisao();

        membrosVisaoGeral.setNome("Total de Membros");
        membrosVisaoGeral.setQnt(service.buscarQtdTotalMembros());
        listaTotalMembros.add(membrosVisaoGeral);
        membrosVisaoGeral = new ModeloClassesVisao();

        membrosVisaoGeral.setNome("Ativos");
        membrosVisaoGeral.setQnt(service.buscarQtdMembrosSituacao(EnumSituacao.ATIVO));
        listaTotalMembros.add(membrosVisaoGeral);
        membrosVisaoGeral = new ModeloClassesVisao();

        membrosVisaoGeral.setNome("Dizimistas");
        membrosVisaoGeral.setQnt(service.buscarQtdMembrosDizimistas());
        listaTotalMembros.add(membrosVisaoGeral);
        membrosVisaoGeral = new ModeloClassesVisao();

        membrosVisaoGeral.setNome("Transferidos");
        membrosVisaoGeral.setQnt(service.buscarQtdMembrosSituacao(EnumSituacao.TRASFERIDO));
        listaTotalMembros.add(membrosVisaoGeral);
        membrosVisaoGeral = new ModeloClassesVisao();

        membrosVisaoGeral.setNome("Afastados");
        membrosVisaoGeral.setQnt(service.buscarQtdMembrosSituacao(EnumSituacao.AFASTADO));
        listaTotalMembros.add(membrosVisaoGeral);
        membrosVisaoGeral = new ModeloClassesVisao();

        membrosVisaoGeral.setNome("Visitantes");
        membrosVisaoGeral.setQnt(service.buscarQtdMembrosSituacao(EnumSituacao.VISITANTE));
        listaTotalMembros.add(membrosVisaoGeral);

        return listaTotalMembros;

    }

    public StreamedContent imprimir() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Membro> listaRelatorio = new ArrayList<Membro>();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        List<Membro> membrosComFoto = new ArrayList<Membro>();
        listaCong = serviceCongregacao.listarTodos();
        str = "FichaMembro";  
        listaRelatorio.add(membroRelatorio);

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
        }
        
         for (Membro membro : listaRelatorio) {
            membro.setIs(new ByteArrayInputStream(membro.getFoto()));
            membrosComFoto.add(membro);

        }

        return fileReport = (StreamedContent) report.gerarRelatorioPDFcomDSTeste(listaRelatorio, parametros, "/report/fichadadosmembro.jasper", str);

    }

    public String getSemFoto() {
        return semFoto;
    }

    public void setSemFoto(String semFoto) {
        this.semFoto = semFoto;
    }

    public byte[] getBimagem() {
        return bimagem;
    }

    public void setBimagem(byte[] bimagem) {
        this.bimagem = bimagem;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public List<Membro> getMembros() {
        return membros = service.listarTodos();
    }

    public void setMembros(List<Membro> membros) {
        this.membros = membros;
    }

    public List<Membro> getMembrosFiltrados() {
        return membrosFiltrados;
    }

    public void setMembrosFiltrados(List<Membro> membrosFiltrados) {
        this.membrosFiltrados = membrosFiltrados;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<EnumEstadoCivil> getListaEstadoCivil() {
        return Arrays.asList(EnumEstadoCivil.values());
    }

    public List<EnumSexo> getListaSexo() {
        return Arrays.asList(EnumSexo.values());
    }

    public List<EnumEstado> getListaEstado() {
        return Arrays.asList(EnumEstado.values());
    }

    public List<EnumSituacao> getListaSituacao() {
        return Arrays.asList(EnumSituacao.values());
    }

    public List<Cargo> getCargos() {
        return cargos = CargoService.listarTodos();
    }

    public List<Membro> getListaLider() {
        return listaLider = service.listarTodos();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public StreamedContent getFotoBanco() {
        return fotoBanco;
    }

    public void setFotoBanco(StreamedContent fotoBanco) {
        this.fotoBanco = fotoBanco;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public boolean isInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    public List<ModeloClassesVisao> getListaTotalMembros() {
        return listaTotalMembros = totalMembros();
    }

    public ModeloClassesVisao getMembrosVisaoGeral() {
        return membrosVisaoGeral;
    }

    public void setMembrosVisaoGeral(ModeloClassesVisao membrosVisaoGeral) {
        this.membrosVisaoGeral = membrosVisaoGeral;
    }

    public List<Membro> getListaAniversariantes() {
        listaAniversariantes = service.aniversariantesMes();

        Collections.sort(listaAniversariantes, new Comparator<Membro>() {
            @Override
            public int compare(Membro o1, Membro o2) {
                return o2.getDataNascimento().compareTo(o1.getDataNascimento());
            }
        });
        return listaAniversariantes;
    }

    public StreamedContent getFotoBancoDialog() {
        if (membro.getId() != null) {
            fotoBancoDialog = new DefaultStreamedContent(new ByteArrayInputStream(membro.getFoto()));
        }
        return fotoBancoDialog;
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

    public Membro getMembroRelatorio() {
        return membroRelatorio;
    }

    public void setMembroRelatorio(Membro membroRelatorio) {
        this.membroRelatorio = membroRelatorio;
    }
    
     

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public StreamedContent getFileReport() {
        return fileReport;
    }

    public void setFileReport(StreamedContent fileReport) {
        this.fileReport = fileReport;
    }

    public ReportsUtil getReport() { 
        return report;
    }

    public void setReport(ReportsUtil report) {
        this.report = report;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }
   

}
