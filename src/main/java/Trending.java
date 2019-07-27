import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Trending")
public class Trending extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Trending</a></h2>");

        displayTrendingItem(pw, "Top Five Most Liked Products", MongoDBDataStoreUtilities.topFiveMostLikedProducts());
        displayTrendingItem(pw, "Top Five Zip-Codes Where Maximum Number of Products Sold", MongoDBDataStoreUtilities.topFiveZipCodesSoldMostProducts());
        displayTrendingItem(pw, "Top Five Most Sold Products regardless of The Rating", MongoDBDataStoreUtilities.topFiveMostSoldProducts());

        pw.print("</div></div>");

        utility.printHtml("Footer.html");
    }

    private void displayTrendingItem(PrintWriter pw, String title, List<KeyValuePair> items) {
        pw.print("<h4 class='title'><a style='font-size: 18px;color:gray'> " + title + "</a></h4>");
        pw.print("<div class='entry'>");
        pw.print("<table  class='gridtable col-sm-4'>");
        pw.print("<tr>");
        pw.print("<th>Key</th>");
        pw.print("<th>Value</th>");
        pw.print("</tr>");

        for (KeyValuePair pair : items) {
            pw.print("<tr>");
            pw.print("<td>" + pair.getKey() + "</td>");
            if (pair.getValue() instanceof Double) {
                pw.printf("<td>%.2f</td>", (double) pair.getValue());
            } else {
                pw.print("<td>" + pair.getValue() + "</td>");
            }
            pw.print("</tr>");
        }

        pw.print("</table>");
        pw.print("</div>");
    }
}
