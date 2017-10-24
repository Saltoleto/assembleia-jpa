package br.com.assembleia.controllers;

import br.com.assembleia.entities.*;
import br.com.assembleia.enums.EnumMesInt;
import br.com.assembleia.enums.EnumSexo;
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
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernandosaltoleto
 */
@ManagedBean
@SessionScoped
@Component
public class RelatorioControle {

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
    String teste = servletContext.getRealPath("/Resources/img/FichaCadastralMembroFinal.jpg");
    String caminhoCarteiraMembro = servletContext.getRealPath("/Resources/img/cartaomasculino.jpg");
    String caminhoCredencia = servletContext.getRealPath("/Resources/img/credencial.jpg");
    private StreamedContent imagemFicha;
    private File arquivo;
    private List<Relatorios> relatorios;
    private List<Relatorios> relatoriosFiananceiro;
    private StreamedContent file;
    private ReportsUtil report = new ReportsUtil();
    private DataSource dataSource;
    private String str;
    private List<Membro> membros;
    private List<Patrimonio> patrimonios;
    private EnumMesInt mes;
    private List<Receita> receitasMembroAnalitico;
    private BigDecimal totalReceita;
    private List<Membro> listaCarteirinhaMasculina = new ArrayList<Membro>();
    private List<Membro> listaCarteirinhaMasculinaSelecionados = new ArrayList<Membro>();
    private List<Membro> listaCarteirinhaObreiros = new ArrayList<Membro>();
    private List<Membro> listaCarteirinhaObreirosSelecionados = new ArrayList<Membro>();
    private List<Membro> listaCarteirinhaFeminina = new ArrayList<Membro>();
    private List<Membro> listaCarteirinhaFemininaSelecionados = new ArrayList<Membro>();

    @Autowired
    private CargoService service;
    @Autowired
    private MembroService serviceMembro;
    @Autowired
    private CongregacaoService serviceCongregacao;
    @Autowired
    private PatrimonioService servicePatrimonio;
    @Autowired
    private ReceitaService serviceReceita;

    @PostConstruct
    private void init() {
        mes = EnumMesInt.busca(Calendar.getInstance().get(Calendar.MONTH));
    }

    public StreamedContent fichaCadastralMembro() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "FichaCadastralMembro";

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
            parametros.put("cep", congre.getCep());
            parametros.put("bairro", congre.getBairro());
        }

        return file = (StreamedContent) report.gerarRelatorioPDF(parametros, "/report/FichaCadastralMembro.jasper", dataSource.getConnection(), str);

    }

    public StreamedContent aniversariantes() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "Aniversariantes";

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
            parametros.put("mesExtenso", mes.getDescricao().toUpperCase());
        }

        return file = (StreamedContent) report.gerarRelatorioPDFcomDSTeste(membros, parametros, "/report/Aniversariantes.jasper", str);

    }

    public StreamedContent credencial() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "Credencial";
        List<Membro> membrosComFoto = new ArrayList<Membro>();

        arquivo = new File(caminhoCredencia);
        InputStream f = null;
        try {
            f = new FileInputStream(arquivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelatorioControle.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Congregacao congre : listaCong) {
            InputStream is = new ByteArrayInputStream(congre.getLogoIgreja());
            parametros.put("nomeIgrena", congre.getNome().toUpperCase());
            parametros.put("endereco", congre.getEndereco());
            parametros.put("telefone", congre.getTelefone());
            parametros.put("cidade", congre.getCidade());
            parametros.put("email", congre.getEmail());
            parametros.put("logo", is);
            parametros.put("cnpj", congre.getCnpj());
            parametros.put("uf", congre.getEstado().getUf());
            parametros.put("bairro", congre.getBairro());
            parametros.put("cep", congre.getCep());
            parametros.put("imagemCarteirinha", f);
        }

        for (Membro membro : listaCarteirinhaObreirosSelecionados) {
            membro.setIs(new ByteArrayInputStream(membro.getFoto()));
            membrosComFoto.add(membro);

        }
        return file = (StreamedContent) report.gerarRelatorioPDFcomDSTeste(membrosComFoto, parametros, "/report/credencial.jasper", str);
    }

    public StreamedContent carteirinhaMasculina() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "Carteirinha(masculina)";
        List<Membro> membrosComFoto = new ArrayList<Membro>();

        arquivo = new File(caminhoCarteiraMembro);
        InputStream f = null;
        try {
            f = new FileInputStream(arquivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelatorioControle.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Congregacao congre : listaCong) {
            InputStream is = new ByteArrayInputStream(congre.getLogoIgreja());
            parametros.put("nomeIgrena", congre.getNome().toUpperCase());
            parametros.put("endereco", congre.getEndereco());
            parametros.put("telefone", congre.getTelefone());
            parametros.put("cidade", congre.getCidade());
            parametros.put("email", congre.getEmail());
            parametros.put("logo", is);
            parametros.put("cnpj", congre.getCnpj());
            parametros.put("uf", congre.getEstado().getUf());
            parametros.put("bairro", congre.getBairro());
            parametros.put("cep", congre.getCep());
            parametros.put("imagemCarteirinha", f);
        }

        for (Membro membro : listaCarteirinhaMasculinaSelecionados) {
            membro.setIs(new ByteArrayInputStream(membro.getFoto()));
            membrosComFoto.add(membro);

        }
        return file = (StreamedContent) report.gerarRelatorioPDFcomDSTeste(membrosComFoto, parametros, "/report/cartaoMasculino.jasper", str);
    }

    public StreamedContent carteirinhaFeminina() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "Carteirinha(feminina)";
        List<Membro> membrosComFoto = new ArrayList<Membro>();
        Path path = Paths.get(caminhoCarteiraMembro);
        byte[] data = Files.readAllBytes(path);
        InputStream f = new ByteArrayInputStream(data);

        for (Congregacao congre : listaCong) {
            InputStream is = new ByteArrayInputStream(congre.getLogoIgreja());
            parametros.put("nomeIgrena", congre.getNome().toUpperCase());
            parametros.put("endereco", congre.getEndereco());
            parametros.put("telefone", congre.getTelefone());
            parametros.put("cidade", congre.getCidade());
            parametros.put("email", congre.getEmail());
            parametros.put("logo", is);
            parametros.put("cnpj", congre.getCnpj());
            parametros.put("uf", congre.getEstado().getUf());
            parametros.put("bairro", congre.getBairro());
            parametros.put("cep", congre.getCep());
            parametros.put("imagemCarteirinha", f);
        }

        for (Membro membro : listaCarteirinhaFemininaSelecionados) {
            membro.setIs(new ByteArrayInputStream(membro.getFoto()));
            membrosComFoto.add(membro);

        }
        return file = (StreamedContent) report.gerarRelatorioPDFcomDSTeste(membrosComFoto, parametros, "/report/carteirinhaFeminina.jasper", str);
    }

    public StreamedContent patrimonio() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "Patrimonio";
        patrimonios = new ArrayList<Patrimonio>();
        patrimonios = servicePatrimonio.listarTodos();

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
            parametros.put("cep", congre.getCep());
            parametros.put("bairro", congre.getBairro());
        }

        return file = (StreamedContent) report.gerarRelatorioPDFcomDSTeste(patrimonios, parametros, "/report/Patrimonio.jasper", str);

    }

    public StreamedContent receitaMembroAnalitico() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();
        str = "MembroAnalitico";

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
            parametros.put("cep", congre.getCep());
            parametros.put("bairro", congre.getBairro());
            parametros.put("totalReceitas", getTotalReceita());
            parametros.put("mesAno", mes.getDescricao().toUpperCase() + "/" + Calendar.getInstance().get(Calendar.YEAR));

        }

        return file = (StreamedContent) report.gerarRelatorioPDFcomDSTeste(receitasMembroAnalitico, parametros, "/report/MembroAnalitico.jasper", str);

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

    public List<Relatorios> getRelatorios() {
        relatorios = new ArrayList<Relatorios>();
        for (int i = 0; i < 1; i++) {
            Relatorios rel = new Relatorios();
            rel.setDescricaoLink("Ficha Cadastral de Membro");
            rel.setLinkRelatorio("/relatorios/fichacadastral.jsf");
            rel.setDescricaoRelatorio("Permite a geração de uma ficha para obter as informações para o cadastro do membro.");
            relatorios.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Credencial de Obreiro");
            rel.setLinkRelatorio("/relatorios/credencial.jsf");
            rel.setDescricaoRelatorio("Permite a geração de credenciais de obreiros.");
            relatorios.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Cartão de Membro(Feminino)");
            rel.setLinkRelatorio("/relatorios/carteirinhamembrofeminina.jsf");
            rel.setDescricaoRelatorio("Permite a geração de cartões de membros do sexo feminino.");
            relatorios.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Cartão de Membro(Masculino)");
            rel.setLinkRelatorio("/relatorios/carteirinhamembro.jsf");
            rel.setDescricaoRelatorio("Permite a geração de cartões de membros do sexo masculino.");
            relatorios.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Aniversariantes");
            rel.setLinkRelatorio("/relatorios/aniversariantes.jsf");
            rel.setDescricaoRelatorio("Exibe as informações dos Aniversariantes.");
            relatorios.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Patrimônio");
            rel.setLinkRelatorio("/relatorios/patrimonio.jsf");
            rel.setDescricaoRelatorio("Exibe as informações do Patrimônio.");
            relatorios.add(rel);
        }

        return relatorios;
    }

    public List<Relatorios> getRelatoriosFiananceiro() {
        relatoriosFiananceiro = new ArrayList<Relatorios>();
        for (int i = 0; i < 1; i++) {
            Relatorios rel = new Relatorios();
            rel.setDescricaoLink("Receitas por Membros Analítico");
            rel.setLinkRelatorio("/relatorios/membroanalitico.jsf");
            rel.setDescricaoRelatorio("Exibe as Receitas por Membros.");
            relatoriosFiananceiro.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Receitas x Despesas");
            rel.setLinkRelatorio("/relatorios/receitasdespesas.jsf");
            rel.setDescricaoRelatorio("Gráfico comparativo entre Receitas e Despesas.");
            relatoriosFiananceiro.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Receitas por TipoDeDespesa");
            rel.setLinkRelatorio("/relatorios/receitascategoria.jsf");
            rel.setDescricaoRelatorio("Gráfico de Receitas agrupadas por TipoDeDespesa.");
            relatoriosFiananceiro.add(rel);
            rel = new Relatorios();
            rel.setDescricaoLink("Despesas por TipoDeDespesa");
            rel.setLinkRelatorio("/relatorios/despesascategoria.jsf");
            rel.setDescricaoRelatorio("Gráfico de Despesas agrupadas por TipoDeDespesa.");
            relatoriosFiananceiro.add(rel);
        }
        return relatoriosFiananceiro;
    }

    public String buscarAniversariantes() {
        return "aniversariantes?faces-redirect=true";
    }

    public String chamdaTelaVisao() {
        return "/relatorios/aniversariantes?faces-redirect=true";
    }

    public List<Receita> buscarReceitaMembroData() {
        receitasMembroAnalitico = new ArrayList<Receita>();
        receitasMembroAnalitico = serviceReceita.buscarReceitaMembroData(new Long(mes.getNumeroMes()));
        return receitasMembroAnalitico;
    }

    public List<Membro> listaMembros() {
        membros = new ArrayList<Membro>();
        return membros = serviceMembro.aniversariantesRelatorio(new Long(mes.getNumeroMes()));

    }

    public List<Patrimonio> getPatrimonios() {
        return patrimonios;
    }

    public List<EnumMesInt> getListaMes() {
        return Arrays.asList(EnumMesInt.values());
    }

    public List<Membro> getMembros() {
        return membros = listaMembros();
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
        return receitasMembroAnalitico = buscarReceitaMembroData();
    }

    public String getTotalReceita() {
        receitasMembroAnalitico = buscarReceitaMembroData();
        totalReceita = new BigDecimal(BigInteger.ZERO);
        for (Receita receita : receitasMembroAnalitico) {
            totalReceita = totalReceita.add(receita.getValor());
        }
        if (totalReceita != null) {
            DecimalFormat df = new DecimalFormat("###,###,##0.00");
            return teste = df.format(totalReceita);
        } else {
            return teste;
        }
    }

    public List<Membro> getListaCarteirinhaObreiros() {
        listaCarteirinhaObreiros = serviceMembro.listarObreiros(EnumSexo.MASCULINO);
        return listaCarteirinhaObreiros;
    }

    public List<Membro> getListaCarteirinhaObreirosSelecionados() {
        return listaCarteirinhaObreirosSelecionados;
    }

    public List<Membro> getListaCarteirinhaMasculina() {
        listaCarteirinhaMasculina = serviceMembro.listarPorSexoCargo(EnumSexo.MASCULINO);
        return listaCarteirinhaMasculina;
    }

    public List<Membro> getListaCarteirinhaFeminina() {
        listaCarteirinhaFeminina = serviceMembro.listarPorSexoCargo(EnumSexo.FEMININO);
        return listaCarteirinhaFeminina;
    }

    public List<Membro> getListaCarteirinhaFemininaSelecionados() {
        return listaCarteirinhaFemininaSelecionados;
    }

    public void setListaCarteirinhaFemininaSelecionados(List<Membro> listaCarteirinhaFemininaSelecionados) {
        this.listaCarteirinhaFemininaSelecionados = listaCarteirinhaFemininaSelecionados;
    }

    public void setTotalReceita(BigDecimal totalReceita) {
        this.totalReceita = totalReceita;
    }

    public List<Membro> getListaCarteirinhaMasculinaSelecionados() {
        return listaCarteirinhaMasculinaSelecionados;
    }

    public void setListaCarteirinhaMasculinaSelecionados(List<Membro> listaCarteirinhaMasculinaSelecionados) {
        this.listaCarteirinhaMasculinaSelecionados = listaCarteirinhaMasculinaSelecionados;
    }

    public String getCaminhoCarteiraMembro() {
        return caminhoCarteiraMembro;
    }

    public void setCaminhoCarteiraMembro(String caminhoCarteiraMembro) {
        this.caminhoCarteiraMembro = caminhoCarteiraMembro;
    }

    public void setListaCarteirinhaMasculina(List<Membro> listaCarteirinhaMasculina) {
        this.listaCarteirinhaMasculina = listaCarteirinhaMasculina;
    }

    public void setListaCarteirinhaObreiros(List<Membro> listaCarteirinhaObreiros) {
        this.listaCarteirinhaObreiros = listaCarteirinhaObreiros;
    }

    public void setListaCarteirinhaObreirosSelecionados(List<Membro> listaCarteirinhaObreirosSelecionados) {
        this.listaCarteirinhaObreirosSelecionados = listaCarteirinhaObreirosSelecionados;
    }

}
