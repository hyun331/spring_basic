package com.beyond.basic.servletjsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello/servlet/jsp/get")
public class HelloServletJspGet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("myData", "hello world java");
        //src/main/webapp폴더를 찾아가는 것으로 약속되어 있음
        req.getRequestDispatcher("/WEB-INF/views/hello.jsp").forward(req, resp);
    }
}
