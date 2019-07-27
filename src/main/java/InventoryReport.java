import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/InventoryReport")

public class InventoryReport extends HttpServlet {
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

        utilities.printHtml("Header.html");
        utilities.printHtml("StoreManagerNavBar.html");

        String action = request.getParameter("action");
        Collection<Product> products = MySqlDataStoreUtilities.listProducts().values();

        switch (action) {
            case "ListAll":
                displayInventory(pw, products, false, false);
                break;
            case "BarChart":
                displayBarChart(pw, products);
                break;
            case "OnSale":
                List<Product> productsOnSale = new ArrayList<>();
                for (Product product : products) {
                    if (product.getDiscount() > 0) {
                        productsOnSale.add(product);
                    }
                }
                displayInventory(pw, productsOnSale, true, false);

                break;
            case "ManufacturerRebate":
                List<Product> productsWithRebate = new ArrayList<>();
                for (Product product : products) {
                    if (product.getManufacturerRebate() > 0) {
                        productsWithRebate.add(product);
                    }
                }
                displayInventory(pw, productsWithRebate, false, true);
                break;
            default:
                throw new ServletException("Unknown action: " + action);
        }

        utilities.printHtml("Footer.html");

    }


    private void displayBarChart(PrintWriter pw, Collection<Product> products) {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Current Inventory Bar Chart</a>");
        pw.print("</h2><div class='entry'>");


        pw.print("<div id='chart_div'></div>");

        pw.print("<script>");

        pw.print("" +
                "google.charts.load('current', {packages: ['corechart', 'bar']});" +
                "google.charts.setOnLoadCallback(drawAxisTickColors);\n" +
                "function drawAxisTickColors() {\n" +
                "    var data = google.visualization.arrayToDataTable([\n        " +
                "        ['Product Name', 'Inventory Count'],\n");

        for (Product product : products) {
            pw.print("   ['" + product.getName() + "', " + product.getInventoryCount() + "],");
        }

        pw.print("   ]);\n" +
                "    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));\n" +
                "    chart.draw(data, " +
                "    {height: 1200, legend: {position: 'none'}}" +
                "    );\n     " +
                "}");
        pw.print("</script>");
        pw.print("</div></div></div>");
    }

    private void displayInventory(PrintWriter pw, Collection<Product> products, boolean showDiscount, boolean showRebate) {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Current Inventory Status</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<table class='gridtable'>");
        pw.print("<tr>");
        pw.print("<th>Name</th>");
        pw.print("<th>Price</th>");
        pw.print("<th>Inventory Count</th>");
        if (showDiscount) {
            pw.print("<th>Discount</th>");
        }
        if (showRebate) {
            pw.print("<th>Manufacturer Rebate</th>");
        }
        pw.print("</tr>");

        for (Product product : products) {
            pw.print("<tr>");
            pw.print("<td>" + product.getName() + "</td>");
            pw.print("<td>" + product.getPrice() + "</td>");
            pw.print("<td>" + product.getInventoryCount() + "</td>");
            if (showDiscount) {
                pw.print("<td>" + product.getDiscount() + "</td>");
            }
            if (showRebate) {
                pw.printf("<td>%.2f</td>", product.getManufacturerRebate());
            }
            pw.print("</tr>");
        }

        pw.print("</table>");
        pw.print("</div></div></div>");
    }

}
