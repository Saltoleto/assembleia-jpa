package br.com.assembleia.version;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletVersion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>Version</TITLE>");
        out.println("</HEAD>");
        out.println("<BODY>");
        out.println(" Versão do Sistema 1.0.0");
        out.println("</BODY>");
        out.println("</HTML>");
    }
}
