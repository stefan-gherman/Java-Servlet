package com.codecool.servlet;

import com.codecool.logic.Cart;
import com.codecool.static_generators.CSSStyler;
import com.codecool.utils.Password;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "Cart", urlPatterns = {"/shoppingCart"}, loadOnStartup = 2)
public class ShoppingCartServlet extends HttpServlet {


    //Renders the page similar to the one in the other Servlet.
    public void renderPage(HttpServletRequest req, HttpServletResponse resp, String[] conversionResults) throws IOException {
        PrintWriter pageWriter = resp.getWriter();

        float totalSum = 0;

        StringBuffer tableWriter = new StringBuffer();

        //Table Contents are being pre processed here
        for(int i=0;i<conversionResults.length-1;i++) {
            //the current element is stored in a variable for ease of access
            String currentElement = conversionResults[i];

            //Start and end indexes are stored in variables, which makes it easier for substring methods.
            int currentElementFirstParamStart = currentElement.indexOf(':') + 1;
            int currentElementFirstParameterEnd = currentElement.indexOf('?');
            int currentElementSecondParameterStart = currentElement.lastIndexOf(':')+1;
            int currentElementSecondParameterEnd = currentElement.lastIndexOf('?');

            //table contents
            tableWriter.append("<tr>");
            tableWriter.append("<td>");
            tableWriter.append(currentElement, currentElementFirstParamStart, currentElementFirstParameterEnd);
            tableWriter.append("</td>");
            tableWriter.append("<td>");
            tableWriter.append(currentElement, currentElementSecondParameterStart, currentElementSecondParameterEnd);
            //The sum is being augmented with the value of the second parameter of the cart string parsed to int
            totalSum += Float.parseFloat(currentElement.substring(currentElementSecondParameterStart, currentElementSecondParameterEnd));
            tableWriter.append("</td>");
            tableWriter.append("</tr>");
        }

        //CSS styles, called from a class.
        CSSStyler styler = new CSSStyler();
        StringBuffer style = styler.applyPrebuiltCSS();

        HttpSession session = req.getSession(false);
        String placeHolder = "";
        String passPlaceholder = "";
        String testPw = "cal";
        if(session != null) {

            placeHolder = session.getAttribute("currentUser").toString();
            passPlaceholder = session.getAttribute("userPassword").toString();

            if (Password.checkPassword(testPw, passPlaceholder)) {
                passPlaceholder = "Guessed It";
            }
        } else {
            placeHolder = "";
        }
        pageWriter.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                        "<title> Cart Checkout</title>" +
                            style+
                "</head>\n" +
                "<body>\n" +
                "<h1 align=\"center\">Checkout</h1>"+
                "<table align=\"center\">" +
                        "<thead>" +
                        "<th>Product</th>" +
                        "<th>Price</th>" +
                        "</thead>" +
                        "<tbody>" +
                        tableWriter +
                        "</tbody>" +
                        "</table>"+
                "<div style=\"margin-top:30px; text-align:center;\">"+
                "<a href=\"/" + "\"><button class=\"cart_button\">Go Back</button></a>" +
                        "<h1> Sum of price " + totalSum + " USD </h1>"+
                //additional content from session
                "<h2>" + placeHolder + "</h2>" +
                "<h3>" + passPlaceholder + "</h3>" +
                "<form method=\"post\" action=\"/shoppingCart\">\n" +
                "    <input type=\"hidden\" id=\"logout\" name=\"logout\">\n" +
                "    <input type=\"submit\" value=\"Put your shades on\">\n" +
                "</form>" +
                "</div>"+
                "</body>\n" +
                "</html>");
    }

    //The response is formated in such a way that singular elements are split at '*', hackeareala, gen.
    public String[] convertResponseToArrayList(String response) {

        String[] result = response.split(Pattern.quote("*"));
        return result;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cartContents = req.getParameter("cart_contents");
        System.out.println(cartContents);
        //Cart contents from the request are parsed into a string array for further parsing
        if(cartContents != null) {
            String[] conversionResult = convertResponseToArrayList(cartContents);
            int i = 0;
            for (String str : conversionResult
            ) {
                System.out.println(str);
                i++;
            }

//            String str = "[productName:Hunting Knife? price:100.0?";
//            System.out.println(str.indexOf(':'));
//            System.out.println(str.indexOf('?'));
            this.renderPage(req, resp, conversionResult);
        } else
        {
            String[] conversionResult = new String[] {
                   "[productName:A Book? price:22.0?" , ", productName:Hunting Knife? price:100.0?]"
            };
            this.renderPage(req, resp, conversionResult);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        doGet(req, resp);

    }
}
