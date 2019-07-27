import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SalesmanCustomerAccountCreation")
public class SalesmanCustomerAccountCreation extends HttpServlet {
    private String errorMessage;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.salesman.name().equals(utilities.usertype())) {
            response.sendRedirect("Login");
            return;
        }

        displayRegistration(request, response, pw, false);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utilities = new Utilities(request, pw);

        if (!utilities.isLoggedin() || !UserType.salesman.name().equals(utilities.usertype())) {
            response.sendRedirect("Login");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String usertype = "customer";
        if (!utilities.isLoggedin()) {
            usertype = request.getParameter("usertype");
        }

        //if password and repassword does not match show error message

        if (!password.equals(repassword)) {
            errorMessage = "Passwords doesn't match!";
        } else if (MySqlDataStoreUtilities.selectUser().containsKey(username)) {
            errorMessage = "Username " + username + " already exists as " + usertype;
        } else {
            MySqlDataStoreUtilities.insertUser(new User(username, password, usertype));
            displayRegistrationSuccess(utilities, response.getWriter(), "Your " + usertype + " account " + username + " has been created");
            return;
        }

        displayRegistration(request, response, pw, true);
    }

    protected void displayRegistrationSuccess(Utilities utilities, PrintWriter pw, String message) {
        utilities.printHtml("Header.html");
        utilities.printHtml("SalesmanNavBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Customer Account Creation</a>");
        pw.print("</h2><div class='entry'>");
        pw.write("<h4 style='color: green'>" + message + "</h4>");
        pw.write("</div></div></div>");
        utilities.printHtml("Footer.html");
    }

    /*  displayRegistration function displays the Registration page of New User */

    protected void displayRegistration(HttpServletRequest request,
                                       HttpServletResponse response, PrintWriter pw, boolean error) {
        Utilities utilities = new Utilities(request, pw);
        utilities.printHtml("Header.html");
        utilities.printHtml("SalesmanNavBar.html");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Customer Account Creation</a>");
        pw.print("</h2><div class='entry'>");

        if (error) {
            pw.print("<h4 style='color:red'>" + errorMessage + "</h4>");
        }

        pw.print("<form method='post' action='SalesmanCustomerAccountCreation'>"
                + "<table style='width:100%'><tr><td>"
                + "<h3>Username</h3></td><td><input type='text' name='username' value='' class='input' required></input>"
                + "</td></tr><tr><td>"
                + "<h3>Password</h3></td><td><input type='password' name='password' value='' class='input' required></input>"
                + "</td></tr><tr><td>"
                + "<h3>Re-Password</h3></td><td><input type='password' name='repassword' value='' class='input' required></input>"
                + "</td></tr><tr><td>"
                + "<input type='hidden' name='usertype' value='" + UserType.customer.name() + "'>"
                + "</td></tr></table>"
                + "<input type='submit' class='btnbuy' name='ByUser' value='Create Customer Account' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
                + "</form>" + "</div></div></div>");

        utilities.printHtml("Footer.html");
    }

}
