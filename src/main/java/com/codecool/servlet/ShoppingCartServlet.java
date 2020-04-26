package com.codecool.servlet;

import com.codecool.logic.Cart;
import com.codecool.static_generators.CSSStyler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        //Table Contents are beign pre processed here
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

        //Cart contents from the request are parsed into a string array for further parsing
        String[] conversionResult = convertResponseToArrayList(cartContents);
        int i = 0;
        for (String str:conversionResult
             ) {
            System.out.println(i + str);
            i++;
        }

        String str = "[productName:Hunting Knife? price:100.0?";
        System.out.println(str.indexOf(':'));
        System.out.println(str.indexOf('?'));
        this.renderPage(req, resp,conversionResult);
    }
}
