package org.example;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.UserProfile;
import org.example.service.DBService;

import java.io.File;
import java.io.IOException;

@WebServlet("/files")
public class FileServlet extends HttpServlet {
    private final DBService dbService = new DBService();
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

        UserProfile user = dbService.getUserByLogin(login);
        if (dbService.getUserByLogin(login)==null || !dbService.getUserByLogin(login).getPassword().equals(pass)) {
            resp.sendRedirect("/login");
            return;
        }

        String path;
        String pathToUserDir = "/Users/macbookair/fileManager/" + login;

        String pathFromRequest = req.getParameter("path");

        if (req.getParameter("path") != null) {
            if (!pathFromRequest.startsWith(pathToUserDir)) {
                path = pathToUserDir;
            } else {
                path = pathFromRequest;
            }
        } else {
            path = pathToUserDir;
        }
        String parentDirPath = new File(path).getParent();
        File[] files = new File(path).listFiles(File::isFile);
        if (files == null)
            files = new File[0];

        File[] directories = new File(path).listFiles(File::isDirectory);
        if (directories == null)
            directories = new File[0];

        req.setAttribute("path", path);
        req.setAttribute("parentPath", parentDirPath);
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
