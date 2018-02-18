package br.com.assembleia.controllers;


import br.com.assembleia.entities.Cargo;
import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.services.CargoService;
import br.com.assembleia.services.CongregacaoService;
import br.com.assembleia.services.MembroService;
import br.com.assembleia.util.ReportsUtil;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
@Component
public class RelatoriosGerenciaisControle {
   
    private DataSource dataSource;
    private Long mes;
    private Integer mesPalestra;
    private String str;
    private StreamedContent file;
    private ReportsUtil report = new ReportsUtil();
    private String mesExtenso;
    private String mesString;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
    private Integer idAtividade;
    String img = servletContext.getRealPath("report/PaeLogo.png");
    String sub = servletContext.getRealPath("report/");
    private Cargo mensagemHome;
    @Autowired
    private CargoService service;
    @Autowired
    private MembroService serviceMembro;
    @Autowired
    private CongregacaoService serviceCongregacao;

    public StreamedContent aniversariantePorMes() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        List<Congregacao> listaCong = new ArrayList<Congregacao>();
        listaCong = serviceCongregacao.listarTodos();

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
        }

//        return file = (StreamedContent) report.gerarRelatorioPDFcomDS(null, parametros, "/report/Aniversariantes.jasper", str);
        return file = (StreamedContent) report.gerarRelatorioPDF(parametros, "/report/FichaCadastralMembro.jasper", dataSource.getConnection(),str);

    }

//    public StreamedContent programacaoPalestras() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
//        Map parametros = new HashMap();
//        parametros.put("mesExtenso", mesExtenso(mesPalestra).toUpperCase());
//        parametros.put("mes", mesPalestra);
//        parametros.put("img", img);
//        parametros.put("ano", AplicacaoControle.dataAnoInteger());
//        parametros.put("mensagem", getMensagemHome().getMensagem());
//        parametros.put("rodape", getMensagemHome().getRodape());
//        str = "ProgramacaoPalestra" + "-" + mesExtenso;
//        file = report.gerarRelatorioPDF2(parametros, "/report/ProgramacaoPalestras.jasper", dataSource.getConnection(), str);
////AplicacaoControle.dataAnoInteger()
//        return file;
//    }
    public StreamedContent conferencia() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        try {
            Map parametros = new HashMap();
            parametros.put("img", img);
            parametros.put("idAtividade", idAtividade);
            parametros.put("sub", sub);
            file = (StreamedContent) report.gerarRelatorioPDF2(parametros, "/report/RecadastramentoTrabalhador.jasper", dataSource.getConnection(), str);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        return file;
    }

    public StreamedContent trabalhadotAtividade() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        try {
            Map parametros = new HashMap();
            if (idAtividade == 0) {
                parametros.put("img", img);
                file = (StreamedContent) report.gerarRelatorioPDF2(parametros, "/report/Trabalhador.jasper", dataSource.getConnection(), str);
            } else {
                parametros.put("img", img);
                parametros.put("idAtividade", idAtividade);
                file = (StreamedContent) report.gerarRelatorioPDF2(parametros, "/report/TrabalhadorAtividade.jasper", dataSource.getConnection(), str);
            }

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        return file;
    }

    public String primeiroNome(String parametro) {
        String nome = parametro;
        String primeiroNome = "";
        for (int i = 0; i < nome.length(); i++) {
            if ((i == 0) && (nome.substring(i, i + 1).equalsIgnoreCase(" "))) {
                System.out.println("Erro: Nome digitado iniciado com tecla ESPAÇO.");
                break;
            } else if (!nome.substring(i, i + 1).equalsIgnoreCase(" ")) {
                primeiroNome += nome.substring(i, i + 1);
            } else {
                break;
            }
        }
        return primeiroNome;
    }

    public StreamedContent getFile() throws SQLException, IOException, JRException, ClassNotFoundException, Throwable {
        Map parametros = new HashMap();
        parametros.put("img", img);
        return file = (StreamedContent) report.gerarRelatorioPDF2(parametros, "/report/atividades.jasper", dataSource.getConnection(), str);
    }

    public String mesExtenso(Integer mes) {

        if (mes == 1) {
            mesExtenso = "Janeiro";
            mesString = "01";
        }
        if (mes == 2) {
            mesExtenso = "Fevereiro";
            mesString = "02";
        }
        if (mes == 3) {
            mesExtenso = "Março";
            mesString = "03";
        }
        if (mes == 4) {
            mesExtenso = "Abril";
            mesString = "04";
        }
        if (mes == 5) {
            mesExtenso = "Maio";
            mesString = "05";
        }
        if (mes == 6) {
            mesExtenso = "Junho";
            mesString = "06";
        }
        if (mes == 7) {
            mesExtenso = "Julho";
            mesString = "07";
        }
        if (mes == 8) {
            mesExtenso = "Agosto";
            mesString = "08";
        }
        if (mes == 9) {
            mesExtenso = "Setembro";
            mesString = "09";
        }
        if (mes == 10) {
            mesExtenso = "Outubro";
            mesString = "10";
        }
        if (mes == 11) {
            mesExtenso = "Novembro";
            mesString = "11";
        }
        if (mes == 12) {
            mesExtenso = "Dezembro";
            mesString = "12";
        }
        if (mes == 13) {
            mesExtenso = "Todos";
        }
        return mesExtenso;
    }

    public String mesExtenso(Long mes) {

        if (mes == 1) {
            mesExtenso = "Janeiro";
            mesString = "01";
        }
        if (mes == 2) {
            mesExtenso = "Fevereiro";
            mesString = "02";
        }
        if (mes == 3) {
            mesExtenso = "Março";
            mesString = "03";
        }
        if (mes == 4) {
            mesExtenso = "Abril";
            mesString = "04";
        }
        if (mes == 5) {
            mesExtenso = "Maio";
            mesString = "05";
        }
        if (mes == 6) {
            mesExtenso = "Junho";
            mesString = "06";
        }
        if (mes == 7) {
            mesExtenso = "Julho";
            mesString = "07";
        }
        if (mes == 8) {
            mesExtenso = "Agosto";
            mesString = "08";
        }
        if (mes == 9) {
            mesExtenso = "Setembro";
            mesString = "09";
        }
        if (mes == 10) {
            mesExtenso = "Outubro";
            mesString = "10";
        }
        if (mes == 11) {
            mesExtenso = "Novembro";
            mesString = "11";
        }
        if (mes == 12) {
            mesExtenso = "Dezembro";
            mesString = "12";
        }
        if (mes == 13) {
            mesExtenso = "Todos";
        }
        return mesExtenso;
    }

    public static void adicionaMensagem(String message, FacesMessage.Severity tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(tipo, message, null));
    }

    public Integer getMesPalestra() {
        return mesPalestra;
    }

    public void setMesPalestra(Integer mesPalestra) {
        this.mesPalestra = mesPalestra;
    }

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    public String getMesString() {
        return mesString;
    }

    public void setMesString(String mesString) {
        this.mesString = mesString;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Long getMes() {
        return mes;
    }

    public void setMes(Long mes) {
        this.mes = mes;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getMesExtenso() {
        return mesExtenso;
    }

    public void setMesExtenso(String mesExtenso) {
        this.mesExtenso = mesExtenso;
    }

    public void setMensagemHome(Cargo mensagemHome) {
        this.mensagemHome = mensagemHome;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    

}
