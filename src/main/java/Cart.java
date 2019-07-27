import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Cart")

public class Cart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        String itemId = request.getParameter("id");
        String action = request.getParameter("action");

        if ("Remove".equals(action)) {
            CartDao.removeItem(utility.username(), itemId);
        } else {
            CartDao.updateUserCart(utility.username(), itemId);
        }

        displayCart(request, response);
    }

    /* displayCart Function shows the products that users has put in cart, these products will be displayed with Total Amount.*/

    protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        Carousel carousel = new Carousel();
        if (!utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to add items to cart");
            response.sendRedirect("Login");
            return;
        }

        List<CartItem> cartItems = CartDao.getUserCart(utility.username());
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Cart(" + cartItems.size() + ")</a>");
        pw.print("</h2><div class='entry'>");
        if (cartItems.size() > 0) {
            pw.print("<table  class='gridtable'>");
            int i = 1;
            double total = 0;
            Map<String, Product> productMap = MySqlDataStoreUtilities.listProducts();

            for (CartItem cartItem : cartItems) {

                Product product = productMap.get(cartItem.getItemId());
                pw.print("<tr>");
                pw.print("<td>" + i + ".</td><td>" + product.getName() + "</td><td>" + product.getPrice() + "</td>");

                pw.print("<td>");
                pw.print("<form name ='Cart' action='Cart' method='post'>");
                pw.print("<input type='hidden' name='id' value='" + cartItem.getItemId() + "'>");
                pw.print("<input type='submit' name='action' value='Remove' class='btnbuy'>");
                pw.print("</form></td>");
                pw.print("<input type='hidden' name='itemName' value='" + product.getName() + "'>");
                pw.print("<input type='hidden' name='orderPrice' value='" + cartItem.getPrice() + "'>");
                pw.print("</tr>");
                total = total + cartItem.getPrice();
                i++;
            }

            pw.print("<tr><th></th><th>Total</th><th>" + total + "</th>");

            pw.print("<th><form name ='Checkout' action='CheckOut' method='post'>");
            pw.print("<input type='hidden' name='orderTotal' value='" + total + "'>");
            pw.print("<input type='submit' name='CheckOut' value='CheckOut' class='btnbuy' /></th></form></tr>");

            pw.print("</table>");
        } else {
            pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
        }

        pw.print("</div></div></div>");

        pw.print(carousel.recommendedProductsCarousel(ProductRecommendationUtility.getRecommendationsForUser(utility.username())));

        utility.printHtml("Footer.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        displayCart(request, response);
    }
}
