package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.*;
import org.example.service.DBService;

import java.io.IOException;

@WebServlet("/login")
public class SessionServlet extends HttpServlet {
    private final DBService dbService = new DBService();
    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, httpServletResponse);
    }
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException, ServletException {


        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("password");
        //String path = httpServletRequest.getParameter("path");

        if (login.isEmpty() || pass.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует логин или пароль");
            return;
        }
        UserProfile profile = dbService.getUserByLogin(login);
        if (profile == null || !profile.getPassword().equals(pass)) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Неправильный логин или пароль");
            return;
        }
        httpServletRequest.getSession().setMaxInactiveInterval(-1);
        httpServletRequest.getSession().setAttribute("login",login);
        httpServletRequest.getSession().setAttribute("password",pass);

        httpServletResponse.sendRedirect("/files");
    }


}
