package ru.gnezdilov.web;

import java.io.PrintWriter;
import java.util.List;

public abstract class AbstractServletNavigation extends AbstractServlet {
    protected abstract String getNamePage();

    protected abstract List<String> getNavigationUrl();

    protected void printPageInfo(PrintWriter writer) {
        writer.write("<h1>Welcome to " + this.getNamePage() + "!</h1>");
        writer.write("<b>info:</b>");
        getNavigationUrl().forEach(url -> writer.write("<p><em>" + url + "</em></p>"));
    }

    protected void printPageInfo(PrintWriter writer, String name) {
        writer.write("<h1>Welcome to " + this.getNamePage() + ", <em>" + name + "</em>!</h1>");
        writer.write("<b>info:</b>");
        getNavigationUrl().forEach(url -> writer.write("<p><em>" + url + "</em></p>"));
    }
}
