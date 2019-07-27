import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SalesReport")
public class SalesReport extends HttpServlet {
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
        Map<String, Product> productMap = MySqlDataStoreUtilities.listProducts();
        List<OrderPayment> orders = MySqlDataStoreUtilities.selectOrder();
        Map<String, Integer> productsSold = new HashMap<>();

        for (OrderPayment order : orders) {
            if (!productsSold.containsKey(order.getItemId())) {
                productsSold.put(order.getItemId(), 0);
            }

            productsSold.put(order.getItemId(), productsSold.get(order.getItemId()) + 1);
        }

        switch (action) {
            case "ListAll":
                displayTotalSales(pw, productMap, productsSold);
                break;
            case "BarChart":
                displayBarChart(pw, productMap, productsSold);
                break;
            case "DailySales":
                displayDailySales(pw, productMap, orders);

                break;
            default:
                throw new ServletException("Unknown action: " + action);
        }

        utilities.printHtml("Footer.html");

    }



    private void displayBarChart(PrintWriter pw, Map<String, Product> productMap, Map<String, Integer> productsSold) {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Total Sales Bar Chart</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<div id='chart_div'></div>");
        pw.print("<script>");

        pw.print("" +
                "google.charts.load('current', {packages: ['corechart', 'bar']});" +
                "google.charts.setOnLoadCallback(drawAxisTickColors);\n" +
                "function drawAxisTickColors() {\n" +
                "    var data = google.visualization.arrayToDataTable([\n        " +
                "        ['Product Name', 'Total Sales'],\n");

        for (String itemId : productsSold.keySet()) {
            Product product = productMap.get(itemId);
            pw.print("   ['" + product.getName() + "', " + product.getPrice() * productsSold.get(itemId) + "],");
        }

        pw.print("   ]);\n" +
                "    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));\n" +
                "    chart.draw(data, " +
                "    {height: 800, legend: {position: 'none'}}" +
                "    );\n     " +
                "}");
        pw.print("</script>");
        pw.print("</div></div></div>");
    }

    private void displayTotalSales(PrintWriter pw, Map<String, Product> productMap, Map<String, Integer> productsSold) {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Current Inventory Status</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<table class='gridtable'>");
        pw.print("<tr>");
        pw.print("<th>Name</th>");
        pw.print("<th>Price</th>");
        pw.print("<th>Number of Items Sold</th>");
        pw.print("<th>Total Sales</th>");
        pw.print("</tr>");


        for (String itemId : productsSold.keySet()) {
            Product product = productMap.get(itemId);
            pw.print("<tr>");
            pw.print("<td>" + product.getName() + "</td>");
            pw.print("<td>" + product.getPrice()  + "</td>");
            pw.print("<td>" + productsSold.get(itemId) + "</td>");
            pw.print("<td>" + productsSold.get(itemId) * product.getPrice() + "</td>");
            pw.print("</tr>");
        }

        pw.print("</table>");
        pw.print("</div></div></div>");
    }

    private void displayDailySales(PrintWriter pw, Map<String, Product> productMap, List<OrderPayment> orders) {
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Daily Sales Report</a>");
        pw.print("</h2><div class='entry'>");

        pw.print("<table class='gridtable'>");
        pw.print("<tr>");
        pw.print("<th>Date</th>");
        pw.print("<th>Total Sales</th>");
        pw.print("</tr>");

        // day -> total sales for the day
        Map<Long, Double> dailySales = new TreeMap<>(new Comparator<Long>() {
            // Return a reversed order by key
            @Override
            public int compare(final Long o1, final Long o2) {
                return o2.compareTo(o1);
            }
        });

        for (OrderPayment order : orders) {
            long orderTime = order.getOrderTime();
            long orderDateInMillis = orderTime - orderTime % (24 * 60 * 60 * 1000L);

            if (!dailySales.containsKey(orderDateInMillis)) {
                dailySales.put(orderDateInMillis, order.getItemPrice());
            } else {
                dailySales.put(orderDateInMillis, dailySales.get(orderDateInMillis) + order.getItemPrice());
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        for (Long dateInMillis : dailySales.keySet()) {
            pw.print("<tr>");
            pw.print("<td>" + formatter.format(new Date(dateInMillis)) + "</td>");
            pw.printf("<td>%.2f</td>", dailySales.get(dateInMillis));
            pw.print("</tr>");
        }

        pw.print("</table>");
        pw.print("</div></div></div>");
    }

}
