package org.example;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AccountService;

import java.io.File;
import java.io.IOException;

@WebServlet("/files")
public class FileServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    /*
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        String pass = (String)req.getSession().getAttribute("password");

        if (AccountService.getUserByLogin(login)==null || !AccountService.getUserByLogin(login).getPassword().equals(pass)) {
            String currentURL = req.getRequestURL().toString();
            resp.sendRedirect("/login");
            return;
        }


        String path = req.getParameter("path");
        if (path == null)
            return;

        File[] files = new File(path).listFiles(File::isFile);
        if (files == null)
            files = new File[0];

        File[] directories = new File(path).listFiles(File::isDirectory);
        if (directories == null)
            directories = new File[0];

        req.setAttribute("files", files);
        req.setAttribute("directories", directories);
        RequestDispatcher dispatcher = req.getRequestDispatcher("files.jsp");
        dispatcher.forward(req, resp);
    }
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletRequest.getSession().removeAttribute("login");
        httpServletRequest.getSession().removeAttribute("password");

        httpServletResponse.sendRedirect("/login");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
