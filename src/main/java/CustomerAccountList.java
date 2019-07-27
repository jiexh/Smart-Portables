import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerAccountList")
public class CustomerAccountList extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.salesman.name().equals(utilities.usertype())) {
            response.sendRedirect("Login");
            return;
        }

        listCustomers(utilities, pw);

    }

    protected void listCustomers(Utilities utilities, PrintWriter pw) throws ServletException {
        utilities.printHtml("Header.html");
        utilities.printHtml("SalesmanNavBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Customer Account List</a>");
        pw.print("</h2><div class='entry'>");
        Collection<User> users = MySqlDataStoreUtilities.selectUser().values();

        pw.print("<table class='gridtable'>");
        pw.print("<tr>");
        pw.print("<th> Customer Name </th>");
        pw.print("<th> Open Orders </th></tr>");

        for (User user : users) {
            if (user.getUsertype().equals(UserType.customer.name())) {
                pw.print("<tr>");
                pw.print("<td>" + user.getName() + "</td>");
                List<OrderPayment> orders = MySqlDataStoreUtilities.getOrdersByUser(user.getName());
                int orderCount = orders == null ? 0 : orders.size();
                pw.print("<td>" + orderCount + "</td>");
                pw.print("</tr>");
            }
        }

        pw.print("</table>");
        pw.write("</div></div></div>");
        utilities.printHtml("Footer.html");
    }

}
