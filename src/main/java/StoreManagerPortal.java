import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StoreManagerPortal")

public class StoreManagerPortal extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.storeManager.name().equals(utilities.usertype())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please login in first!");

            response.sendRedirect("Login");
            return;
        }

        utilities.printHtml("Header.html");
        utilities.printHtml("StoreManagerNavBar.html");

        listProducts(utilities, pw);

        //utilities.printHtml("Footer.html");
    }

    protected void listProducts(Utilities utilities, PrintWriter pw) throws ServletException {

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Product List</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<table class='gridtable'>");
        pw.print("<tr><th>ID</th>");
        pw.print("<th>Name</th>");
        pw.print("<th>Price</th>");
        pw.print("<th>Discount</th>");
        pw.print("<th>Actions</th></tr>");

        for (Product product : MySqlDataStoreUtilities.listProducts().values()) {
            pw.print("<form method='get' action='ItemView'>");
            pw.print("<tr><td>" + product.getId() + "</td>");
            pw.print("<td>" + product.getName() + "</td>");
            pw.print("<td>" + product.getPrice() + "</td>");
            pw.print("<td>" + product.getDiscount() + "</td>");
            pw.print("<input type='hidden' name='itemId' value='" + product.getId() + "'>");
            pw.print("<td><input type='submit' name='action' value='View Detail' class='btnbuy'></td></tr>");
            pw.print("</form>");
        }

        pw.print("</table>");
        pw.print("</div></div></div>");
    }
}
