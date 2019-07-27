import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NewProductOnboard")
public class NewProductOnboard extends HttpServlet {
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

        String category = request.getParameter("category");
        String subcategory = request.getParameter("subcategory");
        String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        double price = Double.parseDouble(request.getParameter("price"));
        double discount = Double.parseDouble(request.getParameter("discount"));
        String condition = request.getParameter("condition");
        String manufacturer = request.getParameter("manufacturer");
        double manufacturerRebate = Double.parseDouble(request.getParameter("manufacturerRebate"));
        String[] accessories = request.getParameterValues("accessories");
        Set<String> accessoryIds = new HashSet<>(accessories != null ? Arrays.asList(accessories) : new ArrayList<String>());
        int inventoryCount = Integer.parseInt(request.getParameter("inventoryCount"));

        Product product = new Product();
        product.setCategory(category);
        product.setSubcategory(subcategory);
        product.setId(itemId);
        product.setName(itemName);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setCondition(condition);
        //TODO: allow updating photos
        product.setImage("notfound.png");
        product.setManufacturer(manufacturer);
        product.setManufacturerRebate(manufacturerRebate);
        product.setAccessoryIds(accessoryIds);
        product.setInventoryCount(inventoryCount);

        MySqlDataStoreUtilities.insertProduct(product);

        displayPostResult(utilities, pw);
    }

    protected void displayPostResult(Utilities utilities, PrintWriter pw) {
        utilities.printHtml("Header.html");
        utilities.printHtml("StoreManagerNavBar.html");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Edit Product Detail</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<h4 style='color:green'> Successfully added a new product</h4>");
        pw.print("</div></div></div>");
        pw.print("Footer.html");
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
        String type = request.getParameter("type");

        utilities.printHtml("Header.html");
        utilities.printHtml("StoreManagerNavBar.html");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Edit Product Detail</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<table class='gridtable'>");
        pw.print("<form method='post' action='NewProductOnboard'");
        pw.print("<tr>");
        pw.print("<th>Product Category</th>");
        pw.print("<td><select name='category'>");
        pw.print("<option value='" + Catalog.wearable.name() + "'selected>Wearable</option>");
        pw.print("<option value='" + Catalog.phone.name() + "'>Phone</option>");
        pw.print("<option value='" + Catalog.laptop.name() + "'>Laptop</option>");
        pw.print("<option value='" + Catalog.smartSpeaker.name() + "'>Smart Speaker</option>");
        pw.print("<option value='" + Catalog.accessory.name() + "'>Accessory</option></select></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Product Subcategory </th>");
        pw.print("<td><input type='text' name='subcategory' placeholder='eg: Pet Tracker' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> ID </th>");
        pw.print("<td><input type='text' name='itemId' placeholder='random id' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Name </th>");
        pw.print("<td><input type='text' name='itemName' placeholder='item name' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Price </th>");
        pw.print("<td><input type='number' step='0.01' name='price' placeholder='25.99' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Condition </th>");
        pw.print("<td><input type='text' name='condition' placeholder='New or Mint' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Discount </th>");
        pw.print("<td><input type='number' step='0.01' name='discount' placeholder='24.99' required></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Manufacturer </th>");
        pw.print("<td><input type='text' name='manufacturer' placeholder='eg: Apple' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Manufacturer Rebate </th>");
        pw.print("<td><input type='number' step='0.01' name='manufacturerRebate' placeholder='99' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Accessories </th>");
        pw.print("<td>");
        pw.print("<select name='accessories' multiple>");

        for (Product accessory : MySqlDataStoreUtilities.listProducts().values()) {
            if (Category.Accessory.name().equals(accessory.getCategory())) {
                pw.print("<option value='" + accessory.getId() + "'>" + accessory.getName() + "</option>");
            }
        }

        pw.print("</select>");
        pw.print("</td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Initial Inventory </th>");
        pw.print("<td><input type='number' name='inventoryCount' value='100' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<td></td>");
        pw.print("<td><input type='submit' name='action' value='Onboard' class='btnbuy'></td>");
        pw.print("</tr>");
        pw.print("</table>");
        pw.print("</form>");

        pw.print("</div></div></div>");

        utilities.printHtml("Footer.html");
    }
}
