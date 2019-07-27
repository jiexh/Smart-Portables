import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Registration")

public class Registration extends HttpServlet {
    private String error_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayRegistration(request, response, pw, false);
    }

	/*   Username, Password, Usertype information are Obtained from HttpServletRequest variable and validates whether
		 the User Credential Already Exists or User Details are Added to the Users HashMap */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String usertype = "customer";
        if (!utility.isLoggedin()) {
            usertype = request.getParameter("usertype");
        }

        //if password and repassword does not match show error message

        if (!password.equals(repassword)) {
            error_msg = "Passwords doesn't match!";
        } else {
            // if the user already exist show error that already exists
            if (MySqlDataStoreUtilities.selectUser().containsKey(username)) {
                error_msg = "Username " + username + " already exists as " + usertype;
            } else {
                MySqlDataStoreUtilities.insertUser(new User(username, password, usertype));
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Your " + usertype + " account " + username + " has been created. Please login");
                if (!utility.isLoggedin()) {
                    response.sendRedirect("Login");
                    return;
                } else {
                    response.sendRedirect("Account");
                    return;
                }
            }
        }

        //display the error message
        if (utility.isLoggedin()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", error_msg);
            response.sendRedirect("Account");
            return;
        }
        displayRegistration(request, response, pw, true);

    }

    /*  displayRegistration function displays the Registration page of New User */

    protected void displayRegistration(HttpServletRequest request,
                                       HttpServletResponse response, PrintWriter pw, boolean error) {
        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        pw.print("<div class='post' style='float: none; width: 100%'>");
        pw.print("<h2 class='title meta'><a style='font-size: 24px;'>User Registration</a></h2>"
                + "<div class='entry'>"
                + "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
        if (error)
            pw.print("<h4 style='color:red'>" + error_msg + "</h4>");
        pw.print("<form method='post' action='Registration'>"
                + "<table style='width:100%'><tr><td>"
                + "<h3>Username</h3></td><td><input type='text' name='username' value='' class='input' required></input>"
                + "</td></tr><tr><td>"
                + "<h3>Password</h3></td><td><input type='password' name='password' value='' class='input' required></input>"
                + "</td></tr><tr><td>"
                + "<h3>Re-Password</h3></td><td><input type='password' name='repassword' value='' class='input' required></input>"
                + "</td></tr><tr><td>"
                + "<h3>User Type</h3></td><td><select name='usertype' class='input'>"
                + "<option value='" + UserType.customer.name() + "' selected>Customer</option>"
                + "<option value='" + UserType.salesman.name() + "'>Salesman</option>"
                + "<option value='" + UserType.storeManager.name() + "'>Store Manager</option></select>"
                + "</td></tr></table>"
                + "<input type='submit' class='btnbuy' name='ByUser' value='Create User' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
                + "</form>" + "</div></div></div>");
        utility.printHtml("Footer.html");
    }
}
