import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductList")
public class ProductList extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        Map<String, Product> productMap = MySqlDataStoreUtilities.listProducts();
        Collection<Product> products = productMap.values();
        String categoryParam = request.getParameter("category");
        String subcategoryParam = request.getParameter("subcategory");
        String manufacturerParam = request.getParameter("manufacturer");
        String id = request.getParameter("id");

        List<Product> productsToDisplay = new ArrayList<>();

        if (subcategoryParam != null) {
            for (Product p : products) {
                if (Objects.equals(p.getSubcategory(), subcategoryParam)) {
                    productsToDisplay.add(p);
                }
            }

        } else if (manufacturerParam != null) {
            for (Product p : products) {
                if (Objects.equals(p.getManufacturer(), manufacturerParam) && Objects.equals(p.getCategory(), categoryParam)) {
                    productsToDisplay.add(p);
                }
            }

        } else if (categoryParam != null) {
            for (Product p : products) {
                if (p.getCategory().equals(categoryParam)) {
                    productsToDisplay.add(p);
                }
            }
        } else if (id != null) {
            if (productMap.containsKey(id)) {
                productsToDisplay.add(productMap.get(id));
            }
        }

        displayProducts(pw, productsToDisplay);

        utility.printHtml("Footer.html");
    }

    private void displayProducts(PrintWriter pw, List<Product> productsToDisplay) {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        int i = 1;
        int size = productsToDisplay.size();

        if (size == 0) {
            pw.write("No product found!");
            return;
        }

        for (Product product : productsToDisplay) {
            if (product.getInventoryCount() <= 0) {
                continue;
            }

            if (i % 4 == 1)
                pw.print("<tr>");
            pw.print("<td><div id='shop_item'>");
            pw.print("<h3>" + product.getName() + "</h3>");
            if (product.getDiscount() > 0) {
                pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + "$" + (product.getPrice() - product.getDiscount()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp " + "<s>" + "$" + product.getPrice() + "</s><ul>");

            } else {
                pw.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + "$" + product.getPrice() + "<ul>");
            }
            pw.print("<li id='item'><img src='images/"
                    + product.getImage() + "' alt='' /></li>");
            pw.print("<li><form method='post' action='Cart'>" +
                    "<input type='hidden' name='id' value='" + product.getId() + "'>" +
                    "<input type='submit' class='btnbuy' value='Buy      Now'></form></li>");
            pw.print("<li><form method='get' action='ReviewList'>" +
                    "<input type='hidden' name='id' value='" + product.getId() + "'>" +
                    "<input type='submit' name='ReviewAction' value='View Reviews' class='btnbuy'></form></li>");
            pw.print("<li><form method='get' action='ReviewList'>" +
                    "<input type='hidden' name='id' value='" + product.getId() + "'>" +
                    "<input type='submit' name='ReviewAction' value='Write Review' class='btnbuy'></form></li>");
            pw.print("</ul></div></td>");

            if (i % 4 == 0 || i == size) {
                pw.print("</tr>");
            }
            i++;
        }

        pw.print("</table></div></div></div>");
    }
}
