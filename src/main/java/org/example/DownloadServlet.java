package org.example;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("path");
        String f_name = path.split("/")[path.split("/").length - 1];
        File file = new File(path);
        //resp.setContentType();
        resp.setContentLengthLong(file.length());
        resp.setHeader("Content-Disposition", "attachment; filename=" + f_name);

        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(file);

        byte[] buffer = new byte[4096];
        int length;

        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }

        in.close();
        out.flush();

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
