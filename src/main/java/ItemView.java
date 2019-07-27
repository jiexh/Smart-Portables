import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ItemView")

public class ItemView extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.storeManager.name().equals(utilities.usertype())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please login in first!");

            response.sendRedirect("Login");
            return;
        }

        String itemId = request.getParameter("itemId");
        String type = request.getParameter("type");
        String action = request.getParameter("action");

        utilities.printHtml("Header.html");
        utilities.printHtml("StoreManagerNavBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Edit Product Detail</a>");
        pw.print("</h2><div class='entry'>");

        if ("Delete".equals(action)) {
            MySqlDataStoreUtilities.deleteProduct(itemId);
            pw.print("<h3> Successfully deleted " + itemId + "</h3>");
            pw.print("<a href=\"StoreManagerPortal\">Go Back to Product List</a>");
        }

        if ("Edit".equals(action)) {
            Product product = MySqlDataStoreUtilities.listProducts().get(itemId);
            product.setName(request.getParameter("name"));
            product.setPrice(Double.parseDouble(request.getParameter("price")));
            product.setCondition(request.getParameter("condition"));
            product.setDiscount(Double.parseDouble(request.getParameter("discount")));
            product.setManufacturerRebate(Double.parseDouble(request.getParameter("manufacturerRebate")));
            product.setInventoryCount(Integer.parseInt(request.getParameter("inventoryCount")));

            MySqlDataStoreUtilities.updateProduct(product);

            pw.print("<h3> Successfully updated " + itemId + "</h3>");
            pw.print("<a href=\"StoreManagerPortal\">Go Back to Product List</a>");
        }

        pw.print("</div></div></div>");
        utilities.printHtml("Footer.html");
    }

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


        String itemId = request.getParameter("itemId");

        utilities.printHtml("Header.html");
        utilities.printHtml("StoreManagerNavBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Edit Product Detail</a>");
        pw.print("</h2><div class='entry'>");


        if (itemId == null) {
            pw.write("<h3> Could not load product with id " + itemId + "</h3>");
            return;
        }

        Product product = MySqlDataStoreUtilities.listProducts().get(itemId);
        if (product == null) {
            pw.write("<h3> Could not load product with id " + itemId + "</h3>");
            return;
        }


        pw.print("<table class='gridtable'>");
        pw.print("<form method='post' action='ItemView'");
        pw.print("<tr>");
        pw.print("<th> ID: </th>");
        pw.print("<td>" + product.getId() + "</td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Name </th>");
        pw.print("<td><input type='text' name='name' value='" + product.getName() + "' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Price </th>");
        pw.print("<td><input type='number' step='0.01' name='price' value='" + product.getPrice() + "' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Condition </th>");
        pw.print("<td><input type='text' name='condition' value='" + product.getCondition() + "' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Discount </th>");
        pw.print("<td><input type='text' name='discount' value='" + product.getDiscount() + "' required></td>");
        pw.print("</tr>");

        String checkString = "checked";
        pw.print("<tr>");
        pw.print("<th> Manufacturer Rebate </th>");
        pw.print("<td><input type='number' step='0.01' name='manufacturerRebate' value='" + product.getManufacturerRebate() + "'></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Inventory </th>");
        pw.print("<td><input type='number' name='inventoryCount' value='" + product.getInventoryCount() + "' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<td><input type='hidden' name='type' value='wearable'><input type='hidden' name='itemId' value='" + product.getId() + "'></td>");
        pw.print("<td><input style='display:inline' type='submit' name='action' value='Edit' class='btnbuy'><input style='display:inline' type='submit' name='action' value='Delete' class='btnbuy'><span></td>");
        pw.print("</tr>");
        pw.print("</table>");
        pw.print("</form>");

        pw.print("</div></div></div>");

        utilities.printHtml("Footer.html");
    }
}








