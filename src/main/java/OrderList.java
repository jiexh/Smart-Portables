import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OrderList")

public class OrderList extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.salesman.name().equals(utilities.usertype())
                && !UserType.storeManager.name().equals(utilities.usertype())) {
            response.sendRedirect("Login");
            return;
        }
        utilities.printHtml("Header.html");
        utilities.printHtml("SalesmanNavBar.html");

        displayOrders(utilities, pw);
        utilities.printHtml("Footer.html");
    }

    protected void displayOrders(Utilities utilities, PrintWriter pw) throws ServletException {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Order List</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<table  class='gridtable'>");
        pw.print("<tr>");
        pw.print("<th>Order Id</th>");
        pw.print("<th>Customer Name</th>");
        pw.print(("<th>Delivery Date</th>"));
        pw.print(("<th>Actions</th>"));
        pw.print("</tr>");

        List<OrderPayment> orders = MySqlDataStoreUtilities.selectOrder();
        Set<Integer> uniqOrderIds = new HashSet<>();

        for (OrderPayment order : orders) {
            if (uniqOrderIds.contains(order.getOrderId())) {
                continue;
            }

            pw.print("<tr>");
            pw.print("<td>" + order.getOrderId() + "</td>");
            pw.print("<td>" + order.getUserName() + "</td>");
            pw.print("<td>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(order.getDeliveryTime())) + "</td>");
            pw.print("<form name ='SalesmanViewOrder' action='SalesmanViewOrder' method='get'>");
            pw.print("<input type='hidden' name='orderId' value='" + order.getOrderId() + "'>");
            pw.print("<td><input type='submit' name='ViewOrder' value='View Order' class='btnbuy'></td>");
            pw.print("</form>");
            pw.print("</tr>");

            uniqOrderIds.add(order.getOrderId());
        }

        pw.print("</table>");

        pw.print("</div></div></div>");
    }
}
