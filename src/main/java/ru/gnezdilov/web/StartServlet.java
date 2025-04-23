package ru.gnezdilov.web;

import ru.gnezdilov.SpringContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Welcome to the Start Page</h1>");
            writer.write("<b>info:</b>");
            writer.write("<p><em>/login</em> - AuthServlet</p>");
            writer.write("<p><em>/register</em> - RegisterServlet</p>");
            writer.write("<p><em>/personal</em> - PersonalServlet</p>");
        }
    }
}

