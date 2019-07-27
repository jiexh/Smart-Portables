import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SalesmanViewOrder")
public class SalesmanViewOrder extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.salesman.name().equals(utilities.usertype())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please login in first!");

            response.sendRedirect("Login");
            return;
        }
        utilities.printHtml("Header.html");
        utilities.printHtml("SalesmanNavBar.html");

        displayOrder(utilities, pw, request);
        utilities.printHtml("Footer.html");
    }

    protected void displayOrder(Utilities utilities, PrintWriter pw, HttpServletRequest request) throws ServletException {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Order View</a>");
        pw.print("</h2><div class='entry'>");

        String orderId = request.getParameter("orderId");
        if (orderId == null || orderId.isEmpty()) {
            pw.print("Order id must be provided");
            pw.print("</div></div></div>");
            return;
        }

        List<OrderPayment> orderItems = MySqlDataStoreUtilities.getOrderByOrderId(Integer.parseInt(orderId));
        if (orderItems == null || orderItems.isEmpty()) {
            pw.print("Dog says miao! The order does not exist or has been cancelled");
            pw.print("</div></div></div>");

            return;
        }

        pw.print("<table  class='gridtable'>");
        pw.print("<form action='SalesmanViewOrder' method='post'>");
        pw.print("<tr>");
        pw.print("<th>OrderId</th>");
        pw.print("<th>UserName</th>");
        pw.print("<th>productOrdered</th>");
        pw.print("<th>productPrice</th>");
        pw.print("<th>Delivery Date</th>");
        pw.print("<input type='hidden' name='orderId' value='" + orderItems.get(0).getOrderId() + "'>");
        pw.print("<th><input type='submit' name='OrderAction' value='Cancel Order' class='btnbuy'></th></tr>");
        pw.print("</form>");

        for (OrderPayment item : orderItems) {
            pw.print("<form action='SalesmanViewOrder' method='post'>");
            pw.print("<tr>");
            pw.print("<td>" + item.getOrderId() + "</td>");
            pw.print("<td>" + item.getUserName() + "</td>");
            pw.print("<td>" + item.getItemId() + "</td>");
            pw.print("<td><input type='number' name='price' value='" + item.getItemPrice() + "'></td>");
            pw.print("<td><input type='date' name='deliveryDate' value='" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(item.getDeliveryTime())) + "'></td>");
            pw.print("<input type='hidden' name='orderId' value='" + item.getOrderId() + "'>");
            pw.print("<input type='hidden' name='itemId' value='" + item.getItemId() + "'>");
            pw.print("<td><input type='submit' name='OrderAction' value='Update Item' class='btnbuy'>");
            pw.print("<input type='submit' name='OrderAction' value='Cancel Item' class='btnbuy'></td>");
            pw.print("</tr>");
            pw.print("</form>");
        }
        pw.print("</table>");
        pw.print("</div></div></div>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.salesman.name().equals(utilities.usertype())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please login in first!");

            response.sendRedirect("Login");
            return;
        }

        String orderAction = request.getParameter("OrderAction");
        String orderIdStr = request.getParameter("orderId");
        String itemId = request.getParameter("itemId");

        switch (orderAction) {
            case "Cancel Order":
                if (orderIdStr != null) {
                    MySqlDataStoreUtilities.cancelOrder(Integer.parseInt(orderIdStr));
                } else {
                    pw.write("orderId must be provided");
                }
                break;
            case "Cancel Item":
                if (orderIdStr != null && itemId != null) {
                    int orderId = Integer.parseInt(orderIdStr);
                    MySqlDataStoreUtilities.cancelOrderItem(orderId, itemId);
                } else {
                    pw.write("orderId and itemId must be provided");
                }

                break;
            case "Update Item":
                if (orderIdStr != null && itemId != null) {
                    int orderId = Integer.parseInt(orderIdStr);
                    List<OrderPayment> orderItems = MySqlDataStoreUtilities.getOrderByOrderId(orderId);
                    String priceStr = request.getParameter("price");
                    String deliveryDate = request.getParameter("deliveryDate");
                    long deliveryTime;
                    try {
                        deliveryTime = new SimpleDateFormat("yyyy-MM-dd").parse(deliveryDate).getTime();
                    } catch (ParseException e) {
                        throw new ServletException(e);
                    }

                    for (OrderPayment orderItem : orderItems) {
                        if (orderId == orderItem.getOrderId() && Objects.equals(itemId, orderItem.getItemId())) {
                            orderItem.setItemPrice(Double.parseDouble(priceStr));
                            orderItem.setDeliveryTime(deliveryTime);

                            MySqlDataStoreUtilities.updateOrder(orderItem);
                            break;
                        }
                    }
                } else {
                    pw.write("orderId and itemId must be provided");
                }

                break;
            default:
                pw.write("Unknown action: " + orderAction);
        }

        response.sendRedirect("SalesmanViewOrder?orderId=" + orderIdStr);
    }

}
