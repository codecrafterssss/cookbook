package com.codegnan.controller;



import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.codegnan.dao.UserDAO;
import com.codegnan.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("CUSTOMER");

        UserDAO dao = new UserDAO();
        boolean success = dao.register(user);

        if (success) {
            resp.sendRedirect("login.jsp");
        } else {
            req.setAttribute("msg", "Registration failed. Try again.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}

