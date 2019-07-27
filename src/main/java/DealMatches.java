import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DealMatches")
public class DealMatches extends HttpServlet {
    private static final int DEAL_COUNT = 2;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        utility.printHtml("Content.html");

        Collection<Product> products = MySqlDataStoreUtilities.listProducts().values();
        List<Product> matchedProducts = new ArrayList<>();
        List<String> matchedTweets = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(getServletContext().getRealPath("DealMatches.txt"))));
        String line;

        while ((line = reader.readLine()) != null) {
            for (Product product : products) {
                if (line.toUpperCase().contains(product.getName().toUpperCase()) && product.getInventoryCount() > 0) {
                    matchedProducts.add(product);
                    matchedTweets.add(line);
                }
            }
        }

        Set<Integer> selectedTweetNums = new HashSet<>();

        Random randomGen = new Random();
        int rand = 0;
        if (matchedProducts.size() > 0 && matchedTweets.size() > 0) {
            for (int i = 0; i < DEAL_COUNT; i++) {
                while (selectedTweetNums.contains((rand = randomGen.nextInt(matchedProducts.size())))) {}
                selectedTweetNums.add(rand);
            }
        }

        List<String> selectedTweets = new ArrayList<>();
        List<Product> selectedProducts = new ArrayList<>();

        for (int num : selectedTweetNums) {
            selectedTweets.add(matchedTweets.get(num));
            selectedProducts.add(matchedProducts.get(num));
        }

        // show tweets
        showtweets(pw, selectedTweets);

        // show deals
        showDeals(pw, selectedProducts);

        utility.printHtml("Footer.html");
    }

    protected void showtweets(PrintWriter pw, List<String> tweets) {
        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta' style='font-size:22px'>We beat our competitors in all aspects</h2>");
        pw.print("<h2 class='title meta' style='font-size:22px'>Price Match Guaranteed</h2>");
        pw.print("<div class='entry'>");

        if (tweets.size() == 0) {
            pw.write("No Deals Found!");
            return;
        }

        for (String twit : tweets) {
            pw.print("<p>" + twit + "</p>");
        }

        pw.print("</div></div></div>");

    }

    private void showDeals(PrintWriter pw, List<Product> productsToDisplay) {
        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta' style='font-size:20px'>Deal Matches</h2>");
        pw.print("<div class='entry'><table id='bestseller'>");
        int i = 1;

        int size = productsToDisplay.size();

        if (size == 0) {
            pw.write("No Deals Found!");
            return;
        }

        for (Product product : productsToDisplay) {
            if (product.getInventoryCount() <= 0) {
                continue;
            }

            pw.print("<td><div id='shop_item'>");
            pw.print("<h3>" + product.getName() + "</h3>");
            if (product.getDiscount() > 0) {
                pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + "$" + (product.getPrice() - product.getDiscount()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp " + "<s>" + "$" + product.getPrice() + "</s><ul>");
            } else {
                pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + "$" + product.getPrice() + "<ul>");
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

            i++;
        }

        pw.print("</table></div></div></div>");
    }
}
