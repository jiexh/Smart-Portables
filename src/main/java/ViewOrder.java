import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewOrder")

public class ViewOrder extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        //check if the user is logged in
        if (!utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to View your Orders");
            response.sendRedirect("Login");
            return;
        }
        String username = utility.username();
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<form name ='ViewOrder' action='ViewOrder' method='get'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Order</a>");
        pw.print("</h2><div class='entry'>");

		/*check if the order button is clicked
		if order button is not clicked that means the view order page is visited freshly
		then user will get textbox to give order number by which he can view order
		if order button is clicked user will be directed to this same servlet and user has given order number
		then this page shows all the order details*/

        if (request.getParameter("Order") == null) {
            pw.print("<table align='center'><tr><td>Enter OrderNo &nbsp&nbsp<input name='orderId' type='text'></td>");
            pw.print("<td><input type='submit' name='Order' value='ViewOrder' class='btnbuy'></td></tr></table>");
        }

		/*if order button is clicked that is user provided a order number to view order
		order details will be fetched and displayed in  a table
		Also user will get an button to cancel the order */

        if ("ViewOrder".equals(request.getParameter("Order"))) {
            if (request.getParameter("orderId") != null && !request.getParameter("orderId").equals("")) {
                long cancelCutoffTime = new Date().getTime() + OrderPayment.CANCEL_CUTOFF_SCHEDULE_IN_MILLI;
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                pw.print("<input type='hidden' name='orderId' value='" + orderId + "'>");

				/* get the order size and check if there exist an order with given order number
				if there is no order present give a message no order stored with this id */
                List<OrderPayment> orderPaymentsForOrder = MySqlDataStoreUtilities.getOrderByOrderIdAndUser(orderId, username);
                if (orderPaymentsForOrder.size() > 0) {
                    pw.print("<table  class='gridtable'>");
                    pw.print("<tr>");
                    pw.print("<td>OrderId</td>");
                    pw.print("<td>UserName</td>");
                    pw.print("<td>productOrdered</td>");
                    pw.print("<td>productPrice</td>");
                    pw.print("<td>Delivery Date</td>");
                    if (hasCutoffOrders(orderPaymentsForOrder, cancelCutoffTime)) {
                        pw.print("<td><input type='submit' name='Order' value='Cancel Order' class='btnbuy' disabled></td></tr>");
                    } else {
                        pw.print("<td><input type='submit' name='Order' value='Cancel Order' class='btnbuy'></td></tr>");
                    }
                    for (OrderPayment item : orderPaymentsForOrder) {
                        pw.print("<tr>");
                        pw.print("<td>" + item.getOrderId() + "</td>");
                        pw.print("<td>" + item.getUserName() + "</td>");
                        pw.print("<td>" + item.getItemId() + "</td>");
                        pw.print("<td>" + item.getItemPrice() + "</td>");
                        pw.print("<td>" + new SimpleDateFormat("MM/dd/yyyy").format(new Date(item.getDeliveryTime())) + "</td>");
                        pw.print("<input type='hidden' name='itemId' value='" + item.getItemId() + "'>");
                        if (cancelCutoffTime > item.getDeliveryTime()) {
                            pw.print("<td><input type='submit' name='Order' value='Cancel Item' class='btnbuy' disabled></td>");
                        } else {
                            pw.print("<td><input type='submit' name='Order' value='Cancel Item' class='btnbuy'></td>");
                        }
                        pw.print("</tr>");
                    }
                    pw.print("</table>");
                    pw.print("<br/><strong>Note:</strong> Orders delivering in 5 days cannot be cancelled!");
                } else {
                    pw.print("<h4 style='color:red'>You have not placed any order with this order id or the order is cancelled.</h4>");
                }
            } else {
                pw.print("<h4 style='color:red'>Please enter the valid order number</h4>");
            }
        }

        if ("Cancel Item".equals(request.getParameter("Order"))) {
            // Cancel a single item in an order
            if (request.getParameter("itemId") != null) {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                String itemId = request.getParameter("itemId");

                MySqlDataStoreUtilities.cancelOrderItem(orderId, itemId);

                if (request.getParameter("redirectTo") != null) {
                    response.sendRedirect(request.getParameter("redirectTo"));
                } else {
                    response.sendRedirect("ViewOrder?orderId=" + orderId + "&Order=ViewOrder");
                }
            }
        }

        if ("Cancel Order".equals(request.getParameter("Order"))) {
            if (request.getParameter("orderId") != null) {
                int orderId = Integer.parseInt(request.getParameter("orderId"));

                MySqlDataStoreUtilities.cancelOrder(orderId);
                if (request.getParameter("redirectTo") != null) {
                    response.sendRedirect(request.getParameter("redirectTo"));
                } else {
                    response.sendRedirect("ViewOrder?orderId=" + orderId + "&Order=ViewOrder");
                }
            }
        }

        pw.print("</form></div></div></div>");
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
