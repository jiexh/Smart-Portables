import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AutoComplete")
public class AutoComplete extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();

        String searchId = request.getParameter("searchId");
        List<Product> matches = AjaxUtility.queryProductsByName(searchId);

        pw.write("<products>");
        for (Product product : matches) {
            pw.write("<product>");
            pw.write("<id>" + product.getId() + "</id>");
            pw.write("<name>" + product.getName() + "</name>");
            pw.write("</product>");
        }
        pw.write("</products>");
    }
}
