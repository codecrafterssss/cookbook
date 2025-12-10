package com.cookbook.controller;

import java.io.IOException;

import com.cookbook.dao.UserDAO;
import com.cookbook.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show login page
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            if (username == null || password == null) {
                req.setAttribute("error", "Username and password are required.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            boolean valid = userDAO.validateLogin(username, password);
            if (valid) {
                User user = userDAO.findByUsername(username);
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                // redirect customers to recipes, admins to admin panel (example)
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    resp.sendRedirect(req.getContextPath() + "/admin/recipes");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/recipes");
                }
            } else {
                req.setAttribute("error", "Invalid username or password.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
