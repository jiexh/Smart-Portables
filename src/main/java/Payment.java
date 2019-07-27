import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Payment")

public class Payment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();


        Utilities utility = new Utilities(request, pw);
        if (!utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to Pay");
            response.sendRedirect("Login");
            return;
        }

        ArrayList<CartItem> cartItems = CartDao.getUserCart(utility.username());

        // get the payment details like credit card no address processed from cart servlet

        String userAddress = request.getParameter("userAddress");
        String creditCardNo = request.getParameter("creditCardNo");

        if (!userAddress.isEmpty() && !creditCardNo.isEmpty()) {
            int orderId = new Random().nextInt(Integer.MAX_VALUE);
            Date orderDate = new Date();
            Date deliveryDate = new Date(orderDate.getTime() + OrderPayment.DELIVERY_SCHEDULE_IN_MILLI);

            for (CartItem item : cartItems) {
                OrderPayment orderItem = new OrderPayment(orderId, utility.username(), item.getItemId(), item.getPrice(), userAddress, creditCardNo, deliveryDate.getTime(), orderDate.getTime());
                MySqlDataStoreUtilities.insertOrder(orderItem);

                Product product = MySqlDataStoreUtilities.listProducts().get(item.getItemId());
                product.setInventoryCount(product.getInventoryCount() - 1);
                MySqlDataStoreUtilities.updateProduct(product);
            }

            //remove the order details from cart after processing
            CartDao.cartMap.remove(utility.username());

            utility.printHtml("Header.html");
            utility.printHtml("LeftNavigationBar.html");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Order</a>");
            pw.print("</h2><div class='entry'>");

            pw.print("<br>Your Order No is " + (orderId));
            // Quick way to display the date without hours and seconds.
            pw.print(" and will be delivered by the end of " + new SimpleDateFormat("MM/dd/yyyy").format(deliveryDate));
            pw.print("</h2></div></div></div>");
            utility.printHtml("Footer.html");
        } else {
            utility.printHtml("Header.html");
            utility.printHtml("LeftNavigationBar.html");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Order</a>");
            pw.print("</h2><div class='entry'>");

            pw.print("<h4 style='color:red'>Please enter valid address and credit card number</h4>");
            pw.print("</h2></div></div></div>");
            utility.printHtml("Footer.html");
        }
    }
}
