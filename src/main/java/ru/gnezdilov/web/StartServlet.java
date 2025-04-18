package ru.gnezdilov.web;

import ru.gnezdilov.SpringContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Welcome to the Start Page<h1>");
            writer.write("<form action=\"/login\" method=\"post\">");
            writer.write("<button>login</button></form>");
            writer.write("<form action=\"/register\" method=\"post\">");
            writer.write("<button>register</button></form>");
        }
    }
}
