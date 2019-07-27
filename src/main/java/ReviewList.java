import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReviewList")
public class ReviewList extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please login to add reviews!");

            response.sendRedirect("Login");
            return;
        }

        String productId = request.getParameter("productId");
        String retailerZip = request.getParameter("retailerZip");
        String retailerCity = request.getParameter("retailerCity");
        String retailerState = request.getParameter("retailerState");
        int userAge = Integer.parseInt(request.getParameter("userAge"));
        String userGender = request.getParameter("userGender");
        String userOccupation = request.getParameter("userOccupation");
        int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
        String reviewText = request.getParameter("reviewText");

        Product product = MySqlDataStoreUtilities.listProducts().get(productId);

        Review review = new Review();
        review.setProductId(productId);
        review.setProductCategory(product.getCategory());
        review.setProductPrice(product.getPrice());
        review.setProductManufacturer(product.getManufacturer());
        review.setProductOnSale(product.getDiscount() > 0);
        review.setProductManufacturerRebate(false);
        review.setRetailerName("SmartPortables");
        review.setRetailerZip(retailerZip);
        review.setRetailerCity(retailerCity);
        review.setRetailerState(retailerState);
        review.setUserId(utilities.username());
        review.setUserAge(userAge);
        review.setUserGender(userGender);
        review.setUserOccupation(userOccupation);
        review.setReviewDate(new Date());
        review.setReviewRating(reviewRating);
        review.setReviewText(reviewText);

        MongoDBDataStoreUtilities.insertReview(review);

        utilities.printHtml("Header.html");
        utilities.printHtml("LeftNavigationBar.html");

        pw.print("Successfully added a review<br/>");
        pw.print("<a href='ReviewList?id=" + productId + "'>Go Back to Review List</a>");

        utilities.printHtml("footer.html");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        String productId = request.getParameter("id");
        String reviewAction = request.getParameter("ReviewAction");
        Product product = MySqlDataStoreUtilities.listProducts().get(productId);

        if (product == null) {
            pw.write("Something goes wrong! The requested product does not exist");
        } else if ("Write Review".equals(reviewAction)) {
            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please Login to add reviews");
                response.sendRedirect("Login");
                return;
            }
            displayReviewEditor(utility, pw, product);
        } else {
            List<Review> reviews = MongoDBDataStoreUtilities.getReviewsByProductId(productId);
            displayProducts(pw, product, reviews);
        }

        utility.printHtml("Footer.html");
    }

    private void displayReviewEditor(Utilities utility, PrintWriter pw, Product product) {
        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta' style='font-size:24px;'>Add Review</h2>");
        pw.print("<div class='entry'>");
        pw.print("<table class='gridtable col-sm-6'>");
        pw.print("<form method='post' action='ReviewList'>");
        pw.print("<tr>");
        pw.print("<th> Name </th>");
        pw.print("<input type='hidden' name='productId' value='" + product.getId() + "'>");
        pw.print("<td><input type='text' name='itemName' placeholder='" + product.getName() + "' disabled></td>");
        pw.print("</tr>");
        pw.print("<tr>");
        pw.print("<th> Price </th>");
        pw.print("<td><input type='number' step='0.01' name='productPrice' value='" + product.getPrice() + "' disabled></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Retailer Zip </th>");
        pw.print("<td><input type='text' name='retailerZip' placeholder='e.g.: 60605' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Retailer City </th>");
        pw.print("<td><input type='text' name='retailerCity' placeholder='e.g.: Chicago' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Retailer State </th>");
        pw.print("<td><input type='text' name='retailerState' placeholder='e.g.: IL' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> User ID </th>");
        pw.print("<td><input type='text' name='userId' value='" + utility.username() + "' disabled></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> User Age </th>");
        pw.print("<td><input type='number' name='userAge' placeholder='30' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> User Gender </th>");
        pw.print("<td><select name='userGender'>");
        pw.print("<option value='Male' select>Male</option>");
        pw.print("<option value='Female' select>Female</option>");
        pw.print("</select></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> User Occupation </th>");
        pw.print("<td><input type='text' name='userOccupation' placeholder='e.g.: Doctor' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Review Rating </th>");
        pw.print("<td><input type='number' min='0' max='5' name='reviewRating' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<th> Review </th>");
        pw.print("<td><input name='reviewText' value='' required></td>");
        pw.print("</tr>");

        pw.print("<tr>");
        pw.print("<td></td>");
        pw.print("<td><input type='submit' value='Submit' class='btnbuy'></td>");
        pw.print("</tr>");
        pw.print("</form>");
        pw.print("</table>");


        pw.print("</div></div></div>");
    }

    private void displayProducts(PrintWriter pw, Product product, List<Review> reviews) {
        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta' style='font-size:24px;'>Product Detail</h2>");
        pw.print("<div class='entry'><table>");

        pw.print("<td><div id='shop_item'>");
        pw.print("<h3>" + product.getName() + "</h3>");

        if (product.getDiscount() > 0) {
            pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + "$" + (product.getPrice() - product.getDiscount()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp " + "<s>" + "$" + product.getPrice() + "</s><ul>");

        } else {
            pw.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + "$" + product.getPrice() + "<ul>");
        }
        pw.print("<li id='item'><img src='images/" + product.getImage() + "' alt='' /></li>");
        pw.print("<li><form method='post' action='Cart'>" +
                "<input type='hidden' name='id' value='" + product.getId() + "'>" +
                "<input type='submit' class='btnbuy' value='Buy      Now'></form></li>");
        pw.print("<li><form method='get' action='ReviewList'>" +
                "<input type='hidden' name='id' value='" + product.getId() + "'>" +
                "<input type='submit' name='ReviewAction' value='Write Review' class='btnbuy'></form></li>");
        pw.print("</ul></div></td>");
        pw.print("</table>");

        pw.print("<br/><br/>");

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            pw.print("<h3 class='title'>Review #" + (i + 1) + "</h3>");
            pw.print("<table class='gridtable' style='width:300px'>");
            pw.print("<tr>");
            pw.print("<td> Product Model Name </td>");
            pw.print("<td>" + product.getName() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Product Category </td>");
            pw.print("<td>" + review.getProductCategory() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Product Price </td>");
            pw.print("<td>" + review.getProductPrice() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Retailer Name</td>");
            pw.print("<td>" + review.getRetailerName() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Retailer Zip</td>");
            pw.print("<td>" + review.getRetailerZip() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Retailer City</td>");
            pw.print("<td>" + review.getRetailerCity() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Retailer State</td>");
            pw.print("<td>" + review.getRetailerState() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Product On Sale</td>");
            pw.print("<td>" + (review.isProductOnSale() ? "Yes" : "No") + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Manufacturer</td>");
            pw.print("<td>" + review.getProductManufacturer() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Manufacturer Rebase</td>");
            pw.print("<td>No</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> User Id</td>");
            pw.print("<td>" + review.getUserId() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> User Age</td>");
            pw.print("<td>" + review.getUserAge() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> User Gender</td>");
            pw.print("<td>" + review.getUserGender() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> User Occupation</td>");
            pw.print("<td>" + review.getUserOccupation() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Review Rating </td>");
            pw.print("<td>" + review.getReviewRating() + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Review Date</td>");
            pw.print("<td>" + format.format(review.getReviewDate()) + "</td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Review </td>");
            pw.print("<td>" + review.getReviewText() + "</td>");
            pw.print("</tr>");

            pw.print("</table>");
        }

        pw.print("</div></div></div>");
    }

}
