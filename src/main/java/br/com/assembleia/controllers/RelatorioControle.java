package br.com.assembleia.controllers;

import br.com.assembleia.entities.*;
import br.com.assembleia.enums.EnumMes;
import br.com.assembleia.enums.EnumMesInt;
import br.com.assembleia.enums.EnumSexo;
import br.com.assembleia.enums.EnumTipoCartao;
import br.com.assembleia.services.*;
import br.com.assembleia.util.ReportsUtil;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fernandosaltoleto
 */
@ManagedBean
@SessionScoped
@Component
public class RelatorioControle {

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
    String teste = servletContext.getRealPath("/resources/img/FichaCadastralMembroFinal.jpg");
    String cartaoMembro = servletContext.getRealPath("/resources/img/cartaoMembro.jpg");
    String credencial = servletContext.getRealPath("/resources/img/credencial.jpg");
    private StreamedContent imagemFicha;
    private File arquivo;
    private List<Relatorios> relatorios;
    private List<Relatorios> relatoriosFiananceiro;
    private StreamedContent file;
    private ReportsUtil report = new ReportsUtil();
    private DataSource dataSource;
    private String str;
    private List<Membro> membros;
    private EnumMesInt mes;
    private BigDecimal totalReceita;
    private List<Membro> listaMembrosSelecionandos = new ArrayList<Membro>();
    private List<Membro> listaMembrosCartao = new ArrayList<Membro>();
    private List<Membro> listaCarteirinhaObreiros = new ArrayList<Membro>();
    private List<Membro> listaCarteirinhaObreirosSelecionados = new ArrayList<Membro>();
    private int mesAniversario;
    private int anoPesquisaAniversario;
    private EnumSexo sexo;
    private EnumTipoCartao tipoCartao;
    private Cargo cargo;
    private static final Locale BRASIL = new Locale("pt", "BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRASIL);

    @Autowired
    private CargoService service;
    @Autowired
    private MembroService serviceMembroService;
    @Autowired
    private CongregacaoService serviceCongregacao;
    @Autowired
    private PatrimonioService servicePatrimonio;
    @Autowired
    private ReceitaService serviceReceita;

    @PostConstruct
    private void init() {
        mes = EnumMesInt.busca(Calendar.getInstance().get(Calendar.MONTH));
        mesAniversario = Calendar.getInstance().get(Calendar.MONTH) + 1;
        anoPesquisaAniversario = Calendar.getInstance().get(Calendar.YEAR);
    }

    public StreamedContent fichaCadastralMembro() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        str = "FichaCadastralMembro";
        return file = (StreamedContent) report.gerarRelatorioPDF(preenhcerCongregacao(getCongregacao()), "/resources/report/FichaCadastralMembro.jasper", dataSource.getConnection(), str);
    }

    public Map preenhcerCongregacao(Congregacao congre) {
        Map parametros = new HashMap();
        InputStream is = new ByteArrayInputStream(congre.getLogoIgreja());
        parametros.put("nomeIgrena", congre.getNome());
        parametros.put("endereco", congre.getEndereco());
        parametros.put("telefone", congre.getTelefone());
        parametros.put("cidade", congre.getCidade());
        parametros.put("email", congre.getEmail());
        parametros.put("logo", is);
        parametros.put("cnpj", congre.getCnpj());
        parametros.put("uf", congre.getEstado().getUf());
        parametros.put("cep", congre.getCep());
        parametros.put("bairro", congre.getBairro());
        return parametros;
    }

    public StreamedContent aniversariantes() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        str = "Aniversariantes";
        Map map = preenhcerCongregacao(getCongregacao());
        map.put("mesExtenso", EnumMesInt.busca(mesAniversario - 1).getDescricao().toUpperCase());
        return file = (StreamedContent) report.gerarRelatorioPDFcomDS(getAniversariantesRelatorio(), map, "/resources/report/Aniversariantes.jasper", str);

    }

    public List<Membro> getAniversariantesRelatorio() {

        List<Membro> aniversariantes = new ArrayList<>();

        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            aniversariantes = serviceMembroService.aniversariantesMesAnoIgrejaRelatorio(AplicacaoControle.getInstance().getIdIgreja(), mesAniversario, anoPesquisaAniversario);
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            aniversariantes = serviceMembroService.aniversariantesMesAnoRelatorio(mesAniversario, anoPesquisaAniversario);
        } else {
            aniversariantes = serviceMembroService.aniversariantesMesAnoIgrejaRelatorio(AplicacaoControle.getInstance().getIdIgrejaPorUsuario(), mesAniversario, anoPesquisaAniversario);
        }

        return aniversariantes;
    }

    public StreamedContent printCartao() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        str = EnumTipoCartao.CARTAO.equals(tipoCartao) ? "Cartão de Membro" : "Credencial de Obreiro";
        Path path = Paths.get(EnumTipoCartao.CARTAO.equals(tipoCartao) ? cartaoMembro : credencial);
        byte[] data = Files.readAllBytes(path);
        InputStream f = new ByteArrayInputStream(data);

        parametros.put("imagemCarteirinha", f);

        for (Membro membro : listaMembrosSelecionandos) {
            membro.setIs(new ByteArrayInputStream(membro.getFoto()));
            membro.setLogoIgreja(new ByteArrayInputStream(membro.getCongregacao().getLogoIgreja()));
        }

        return file = (StreamedContent) report.gerarRelatorioPDFcomDS(listaMembrosSelecionandos, parametros, EnumTipoCartao.CARTAO.equals(tipoCartao) ? "/resources/report/cartaoMembro.jasper" : "/resources/report/credencial.jasper", str);
    }

    public void selectMembro(Membro membro) {
        this.listaMembrosSelecionandos.add(membro);
    }

    public void deselectMembro(Membro membro) {
        this.listaMembrosSelecionandos.remove(membro);
    }


    public StreamedContent printPatrimonio() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        str = "Patrimonio";
        return file = (StreamedContent) report.gerarRelatorioPDFcomDS(getPatrimonios(), preenhcerCongregacao(getCongregacao()), "/resources/report/Patrimonio.jasper", str);
    }

    private List<Patrimonio> getPatrimonios() {

        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            return servicePatrimonio.listarPorIgreja(AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            return servicePatrimonio.listarTodos();
        } else {
            return servicePatrimonio.listarPorIgreja(AplicacaoControle.getInstance().getIdIgrejaPorUsuario());
        }
    }

    private Congregacao getCongregacao() {
        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            return AplicacaoControle.getInstance().getCongregacaoSelecionada();
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            return getSede();
        } else {
            return AplicacaoControle.getInstance().getUsuario().getCongregacao();
        }
    }

    private Congregacao getSede() {
        return serviceCongregacao.buscarSede();
    }

    public StreamedContent receitaMembroAnalitico() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "MembroAnalitico";

        parametros = preenhcerCongregacao(getCongregacao());
        InputStream is = new ByteArrayInputStream(AplicacaoControle.getInstance().getUsuario().getCongregacao().getLogoIgreja());
        parametros.put("logo", is);
        parametros.put("totalReceitas", getTotalReceita());
        parametros.put("mesAno", EnumMes.busca(mesAniversario).getDescricao().toUpperCase() + "/" + Calendar.getInstance().get(Calendar.YEAR));

        return file = (StreamedContent) report.gerarRelatorioPDFcomDS(getReceitasMembroAnalitico(), parametros, "/resources/report/MembroAnalitico.jasper", str);

    }

    private String getTotalReceita() {
        BigDecimal total = BigDecimal.ZERO;
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            total = serviceReceita.receitasMembroParametroMeasAnoIgreja(mesAniversario, anoPesquisaAniversario, AplicacaoControle.getInstance().getIdIgreja(),true);
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            total= serviceReceita.receitasMembroParametroMeasAno(mesAniversario, anoPesquisaAniversario,true);
        } else {
            total= serviceReceita.receitasMembroParametroMeasAnoIgreja(mesAniversario, anoPesquisaAniversario, AplicacaoControle.getInstance().getUsuario().getCongregacao().getId(),true);
        }
        return df.format(total);
    }

    public int getMesAniversario() {
        return mesAniversario;
    }

    public void setMesAniversario(int mesAniversario) {
        this.mesAniversario = mesAniversario;
    }

    public int getAnoPesquisaAniversario() {
        return anoPesquisaAniversario;
    }

    public void setAnoPesquisaAniversario(int anoPesquisaAniversario) {
        this.anoPesquisaAniversario = anoPesquisaAniversario;
    }

    public List<EnumSexo> getListaSexo() {
        return Arrays.asList(EnumSexo.values());
    }

    public List<EnumTipoCartao> getListaTiposCartao() {
        return Arrays.asList(EnumTipoCartao.values());
    }

    public String listar() {
        return "aniversariantes?faces-redirect=true";
    }

    public String listarCartao() {
        this.listaMembrosSelecionandos.clear();
        return "cartao?faces-redirect=true";
    }

    public String listarMembroAnalitico() {
        return "membroanalitico?faces-redirect=true";
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String buscarAniversariantes() {
        return "aniversariantes?faces-redirect=true";
    }

    public List<EnumMesInt> getListaMes() {
        return Arrays.asList(EnumMesInt.values());
    }

    public EnumMesInt getMes() {
        return mes;
    }

    public void setMes(EnumMesInt mes) {
        this.mes = mes;
    }

    public StreamedContent getImagemFicha() {
        arquivo = new File(teste);
        InputStream f = null;
        try {
            f = new FileInputStream(arquivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelatorioControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        StreamedContent img = new DefaultStreamedContent(f);
        imagemFicha = img;
        return imagemFicha;
    }

    public void setImagemFicha(StreamedContent imagemFicha) {
        this.imagemFicha = imagemFicha;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public List<Receita> getReceitasMembroAnalitico() {
        List<Receita> receitas = new ArrayList<>();
        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            receitas = serviceReceita.listarReceitasMembroAnaliticoMesAnoIgreja(mesAniversario, anoPesquisaAniversario, AplicacaoControle.getInstance().getIdIgreja(), true);
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            receitas = serviceReceita.listarReceitasMembroAnaliticoMesAno(mesAniversario, anoPesquisaAniversario,true);
        } else {
            receitas = serviceReceita.listarReceitasMembroAnaliticoMesAnoIgreja(mesAniversario, anoPesquisaAniversario, AplicacaoControle.getInstance().getUsuario().getCongregacao().getId(),true);
        }
        return receitas;
    }




    public List<Membro> getListaCarteirinhaObreiros() {
        listaCarteirinhaObreiros = serviceMembroService.listarObreiros(EnumSexo.MASCULINO);
        return listaCarteirinhaObreiros;
    }

    public List<Membro> getListaCarteirinhaObreirosSelecionados() {
        return listaCarteirinhaObreirosSelecionados;
    }


    public void setListaCarteirinhaObreiros(List<Membro> listaCarteirinhaObreiros) {
        this.listaCarteirinhaObreiros = listaCarteirinhaObreiros;
    }

    public void setListaCarteirinhaObreirosSelecionados(List<Membro> listaCarteirinhaObreirosSelecionados) {
        this.listaCarteirinhaObreirosSelecionados = listaCarteirinhaObreirosSelecionados;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    public List<Membro> getListaMembrosCartao() {

        if (AplicacaoControle.getInstance().adminSedeSelecionouIgreja()) {
            listaMembrosCartao = serviceMembroService.listarPorSexoCargoCongregacao(this.sexo, this.cargo.getDescricao(), AplicacaoControle.getInstance().getIdIgreja());
        } else if (AplicacaoControle.getInstance().adminSedeNaoSelecionouIgreja()) {
            listaMembrosCartao = serviceMembroService.listarPorSexoCargo(this.sexo, this.cargo != null ? this.cargo.getDescricao() : "");
        } else {
            listaMembrosCartao = serviceMembroService.listarPorSexoCargoCongregacao(this.sexo, this.cargo.getDescricao(), AplicacaoControle.getInstance().getUsuario().getCongregacao().getId());
        }

        return listaMembrosCartao;
    }

    public void setListaMembrosCartao(List<Membro> listaMembrosCartao) {
        this.listaMembrosCartao = listaMembrosCartao;
    }

    public List<Membro> getListaMembrosSelecionandos() {
        return listaMembrosSelecionandos;
    }

    public void setListaMembrosSelecionandos(List<Membro> listaMembrosSelecionandos) {
        this.listaMembrosSelecionandos = listaMembrosSelecionandos;
    }

    public EnumTipoCartao getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(EnumTipoCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
    }
}
