package com.codecool.servlet;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.bindings.MapBindings;
import au.com.codeka.carrot.resource.FileResourceLocator;
import com.codecool.logic.Item;
import com.codecool.logic.Stock;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "WebShop", urlPatterns = {"/"}, loadOnStartup = 1)
public class WebShopServlet extends HttpServlet {
    private Stock currentStock;

    public void populateStock() {

        Set<Item> itemsSet = new TreeSet<>();

        itemsSet.add(new Item("A Book", 22));
        itemsSet.add(new Item("Ink Pen", 20));
        itemsSet.add(new Item("Smart Phone", 300));
        itemsSet.add(new Item("Hunting Knife", 100));
        itemsSet.add(new Item("Red Bull", 346));


        this.currentStock = new Stock(itemsSet);


    }

    public void renderPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //Creates the writer that will output content on the page.
        PrintWriter pageWriter = resp.getWriter();


        //String builder containing table data from the stock
        StringBuffer tableData = new StringBuffer();

        //Each stock item is being added to the table
        for (Item item : currentStock.getItemsInStock()
        ) {
            tableData.append("<tr>");
            tableData.append(String.format("<td>%s</td>", item.getProductName()));
            tableData.append(String.format("<td>%s USD</td>", item.getPrice()));
            tableData.append("<td><a href = \"\"><button>Add</button></a></td>");
            tableData.append("<td><a href = \"\"><button>Remove</button></a></td>");
            tableData.append("</tr>");
        }

        //Actual HTML document content writing
        pageWriter.println("<!DOCTYPE html>");


        //Creating CSS styles until I find out how to serve static files.
        StringBuffer cssStyles = new StringBuffer();
        cssStyles.append("<style>");
        cssStyles.append("table {" +
                "border: 2px solid black;" +
                "border-collapse:collapse;" +
                "}");
        cssStyles.append("th, td {\n" +
                "    border: 1px solid black;\n" +
                "    padding: 5px;\n" +
                "    height: 30px;\n" +
                "    font-size: 24px;\n" +
                "    text-align: center;\n" +
                "}");
        cssStyles.append("td {\n" +
                "background-color: lightgrey;" +
                "\n}");
        cssStyles.append("h1 {\n" +
                "font-size: 24px;" +
                "\n}");
        cssStyles.append(".cart_button {\n" +
                "font-size:36px;\n" +
                "margin:20px;\n" +
                "}");
        cssStyles.append("</style>");

        //HTML head
        pageWriter.println("<html lang=\"en\">");
        pageWriter.println("<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Stock</title>" +
                cssStyles +
                "</head>"
        );

        //Body contents, table(from string buffer) and h1.
        pageWriter.println("<body>" +
                "<h1 align=\"center\">Products Stock</h1>" +
                "<table align=\"center\">" +
                "<thead>" +
                "<th>Product Name</th>" +
                "<th>Price</th>" +
                "<th>Add</th>" +
                "<th>Remove</th>" +
                "</thead>" +
                "<tbody>" +
                tableData +
                "</tbody>" +
                "</table>" +
                "<div style=\"text-align:center;\">"+
                "<a align=\"center\"><button align=\"center\" class=\"cart_button\">Check Cart</button></a>"+
                "</div>"+
                "</body>");
        pageWriter.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.populateStock();
        resp.setContentType("text/html");
        renderPage(req, resp);


    }
}
