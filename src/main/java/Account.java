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
import javax.servlet.http.HttpSession;

@WebServlet("/Account")

public class Account extends HttpServlet {
    private String error_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayAccount(request, response);
    }

    /* Display Account Details of the Customer (Name and Usertype) */

    protected void displayAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        response.setContentType("text/html");
        if (!utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to add items to cart");
            response.sendRedirect("Login");
            return;
        }
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Account</a>");
        pw.print("</h2><div class='entry'>");

        User user = MySqlDataStoreUtilities.selectUser().get(utility.username());
        pw.print("<table class='gridtable'>");
        pw.print("<tr>");
        pw.print("<td> User Name: </td>");
        pw.print("<td>" + user.getName() + "</td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<td> User Type: </td>");
        pw.print("<td>" + user.getUsertype() + "</td>");
        pw.print("</tr>");
        pw.print("</table>");

        if (UserType.salesman.name().equals(user.getUsertype())) {
            response.sendRedirect("SalesmanPortal");
            return;
        }

        if (UserType.storeManager.name().equals(user.getUsertype())) {
            response.sendRedirect("StoreManagerPortal");
            return;
        }

        if (user.getUsertype().equals(UserType.customer.name())) {
            List<OrderPayment> orderPaymentForUser = MySqlDataStoreUtilities.getOrdersByUser(utility.username());

            long cancelCutoffTime = new Date().getTime() + OrderPayment.CANCEL_CUTOFF_SCHEDULE_IN_MILLI;
            Set<Integer> orderIds = new HashSet<>();
            for (OrderPayment orderPayment : orderPaymentForUser) {
                orderIds.add(orderPayment.getOrderId());
            }

            if (orderIds.isEmpty()) {
                pw.print("<h4 style='color:red'>You don't have any open orders</h4>");
            } else {
                pw.print("<br/><h3> Open Orders </h3>");
            }

            for (int orderId : orderIds) {
                List<OrderPayment> orderPaymentPerOrderId = MySqlDataStoreUtilities.getOrderByOrderId(orderId);
                if (orderPaymentPerOrderId.size() > 0) {
                    pw.print("<table class='gridtable'>");
                    pw.print("<form method='get' action='ViewOrder'>");
                    pw.print("<tr><td>OrderId</td>");
                    pw.print("<td>productOrdered</td>");
                    pw.print("<td>productPrice</td>");
                    pw.print(("<td>Delivery Date</td>"));
                    pw.print("<input type='hidden' name='redirectTo' value='Account'>");
                    pw.print("<input type='hidden' name='orderId' value='" + orderPaymentPerOrderId.get(0).getOrderId() + "'>");
                    if (hasCutoffOrders(orderPaymentPerOrderId, cancelCutoffTime)) {
                        pw.print("<td><input type='submit' name='Order' value='Cancel Order' class='btnbuy' disabled></td></tr>");
                    } else {
                        pw.print("<td><input type='submit' name='Order' value='Cancel Order' class='btnbuy'></td></tr>");
                    }

                    pw.print("</form>");

                    for (OrderPayment orderPayment : orderPaymentPerOrderId) {
                        pw.print("<form method='get' action='ViewOrder'>");
                        pw.print("<tr>");
                        pw.print("<td>" + orderPayment.getOrderId() + "</td>");
                        pw.print("<td>" + orderPayment.getItemId() + "</td>");
                        pw.print("<td>" + orderPayment.getItemPrice() + "</td>");
                        pw.print("<td>" + new SimpleDateFormat("MM/dd/yyyy").format(new Date(orderPayment.getDeliveryTime())) + "</td>");
                        pw.print("<input type='hidden' name='orderId' value='" + orderPayment.getOrderId() + "'>");
                        pw.print("<input type='hidden' name='itemId' value='" + orderPayment.getItemId() + "'>");
                        pw.print("<input type='hidden' name='redirectTo' value='Account'>");

                        if (cancelCutoffTime > orderPayment.getDeliveryTime()) {
                            pw.print("<td><input type='submit' name='Order' value='Cancel Item' class='btnbuy' disabled></td>");
                        } else {
                            pw.print("<td><input type='submit' name='Order' value='Cancel Item' class='btnbuy'></td>");
                        }
                        pw.print("</tr>");
                        pw.print("</form>");
                    }
                    pw.print("</table><br/>");

                }
            }
        }
        pw.print("<br/>Note: Orders delivering in 5 days cannot be cancelled!");

        pw.print("</h2></div></div></div>");

        utility.printHtml("Footer.html");
    }

    private boolean hasCutoffOrders(List<OrderPayment> orderPayments, long cutoffTime) {
        for (OrderPayment item : orderPayments) {
            if (cutoffTime > item.getDeliveryTime()) {
                return true;
            }
        }

        return false;
    }
}
