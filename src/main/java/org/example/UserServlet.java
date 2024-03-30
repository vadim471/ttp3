package org.example;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.UserProfile;
import org.example.service.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
//import jakarta.servlet.http.HttpSession;

@WebServlet("/registration")
public class UserServlet extends HttpServlet {
    private final DBService dbService = new DBService() ;
    @Override
    protected void doGet(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("registration.jsp").forward(httpServletRequest, httpServletResponse);
    }

    protected void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String email = httpServletRequest.getParameter("email");
        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("password");

        if (email.isEmpty() || login.isEmpty() || pass.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Не все поля заполнены");
            return;
        }

        UserProfile userProfile = new UserProfile(login, pass, email);
        if (dbService.getUserByLogin(login) == null) {
            dbService.addUser(userProfile);

            httpServletRequest.getSession().setAttribute("login",login);
            httpServletRequest.getSession().setAttribute("password",pass);

            File folder = new File("/Users/macbookair/fileManager/" + login);
            folder.mkdir();
            httpServletResponse.sendRedirect("/files");
        }
        else {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Пользователь с таким логином уже есть в системе");
        }
    }
}
