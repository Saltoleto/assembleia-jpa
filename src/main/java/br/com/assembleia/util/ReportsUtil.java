/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.assembleia.util;


import br.com.assembleia.controllers.RelatoriosGerenciaisControle;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author fernandosaltoleto
 */
public class ReportsUtil {

    public void gerarRelatorioXLScomDS(List lista, String caminho) throws IOException, JRException {
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        Map parametros = new HashMap();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(caminho);
        JasperPrint impressao = JasperFillManager.fillReport(reportStream, parametros, ds);

        JExcelApiExporter xlsEporter = new JExcelApiExporter();
        xlsEporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
        xlsEporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "relatorio.xls");
        xlsEporter.exportReport();

        InputStream in = new FileInputStream(new File("relatorio.xls"));
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=relatorio.xls");
        OutputStream out = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public StreamedContent gerarRelatorioPDF2(Map parametros, String caminho, Connection conn, String nomeArquivo) throws IOException, JRException, ClassNotFoundException, SQLException, Throwable {
        InputStream relatorio = null;
        ByteArrayOutputStream Teste = new ByteArrayOutputStream();
        try {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(caminho);
            JasperPrint print = JasperFillManager.fillReport(reportStream, parametros, conn);

            if (print.getPages().size() == 0) {
                nomeArquivo = "vazio";
            }
            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);
            exporter.exportReport();
            relatorio = new ByteArrayInputStream(Teste.toByteArray());
        } catch (JRException ex) {
            Logger.getLogger(RelatoriosGerenciaisControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new DefaultStreamedContent(relatorio, null, nomeArquivo + ".pdf");
    }

    public StreamedContent gerarRelatorioPDFcomDSTeste(List lista, Map parametros, String caminho, String nomeArquivo) throws IOException, JRException {

        InputStream relatorio = null;
        ByteArrayOutputStream Teste = new ByteArrayOutputStream();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        JasperPrint print = null;
        try {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(caminho);
            if (lista  == null) {
                print = JasperFillManager.fillReport(reportStream, parametros);
            }
            if (lista != null) {
                print = JasperFillManager.fillReport(reportStream, parametros, ds);
            }

            if (print.getPages().size() == 0) {
                nomeArquivo = "vazio";
            }
            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);
            exporter.exportReport();
            relatorio = new ByteArrayInputStream(Teste.toByteArray());
        } catch (JRException ex) {
            Logger.getLogger(RelatoriosGerenciaisControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new DefaultStreamedContent(relatorio, null, nomeArquivo + ".pdf");

    }

    public void gerarRelatorioPDFcomDS(List lista, Map parametros, String caminho) throws IOException, JRException {

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOS = response.getOutputStream();

        InputStream reportStream = FacesContext.getCurrentInstance().
                getExternalContext().getResourceAsStream(caminho);

        JasperRunManager.runReportToPdfStream(reportStream, servletOS, parametros, ds);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline;filename=relatorio.pdf");
        servletOS.flush();
        servletOS.close();
        FacesContext.getCurrentInstance().responseComplete();

    }

    public void gerarRelatorioPDF(Map parametros, String caminho, Connection conn) throws IOException, JRException, ClassNotFoundException, SQLException {
        String relatorio = "Fernando Saltoleto";
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = response.getOutputStream();
        InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(caminho);
        JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parametros, conn);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline;filename=relatorio.pdf");
        servletOutputStream.flush();
        servletOutputStream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public StreamedContent gerarRelatorioPDF(Map parametros, String caminho, Connection conn, String nomeArquivo) throws IOException, JRException, ClassNotFoundException, SQLException, Throwable {
        InputStream relatorio = null;
        ByteArrayOutputStream Teste = new ByteArrayOutputStream();
        try {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(caminho);
            JasperPrint print = JasperFillManager.fillReport(reportStream, parametros, conn);

            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);
            exporter.exportReport();
            relatorio = new ByteArrayInputStream(Teste.toByteArray());
        } catch (JRException ex) {
            Logger.getLogger(RelatoriosGerenciaisControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new DefaultStreamedContent(relatorio, null, nomeArquivo + ".pdf");
    }

    public StreamedContent geraRelatorioXLS(Map parametros, String caminho, Connection conn, String nomeArquivo) throws FileNotFoundException, IOException {
        InputStream relatorio = null;
        ByteArrayOutputStream Teste = new ByteArrayOutputStream();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(caminho);
            JasperPrint print = JasperFillManager.fillReport(reportStream, parametros, conn);
            if (print.getPages().size() == 0) {
                nomeArquivo = "vazio";
            }
            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);
//            exporter.exportReport();
//            relatorio = new ByteArrayInputStream(Teste.toByteArray());

            JExcelApiExporter xlsEporter = new JExcelApiExporter();
            xlsEporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            xlsEporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "nomeArquivo.xls");
            xlsEporter.exportReport();

            InputStream in = new FileInputStream(new File("relatorio.xls"));
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=nomeArquivo.xls");
            OutputStream out = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException ex) {
            Logger.getLogger(RelatoriosGerenciaisControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new DefaultStreamedContent(null);

    }
}
