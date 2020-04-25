package com.codecool.servlet;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.bindings.MapBindings;
import au.com.codeka.carrot.resource.FileResourceLocator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "Test Servlet", urlPatterns = {"/test"}, loadOnStartup = 3)
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarrotEngine templateEngine = new CarrotEngine(new Configuration.Builder()
                .setResourceLocator(new FileResourceLocator.Builder("src/main/external/html"))
                .build());

        Map<String, Object> bind = new TreeMap<>();
        bind.put("str", "Test");
        try {
            templateEngine.process("test.html", new MapBindings(bind));
            PrintWriter out = resp.getWriter();
            out.println(templateEngine.process("test.html", new MapBindings(bind)));
        } catch (CarrotException e) {
            System.out.println(e.toString());
        }
    }


}
