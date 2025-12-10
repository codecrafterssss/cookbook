package com.cookbook.controller;

import java.io.IOException;

import com.cookbook.dao.UserDAO;
import com.cookbook.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = "CUSTOMER"; // default role for registration

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Username and password are required.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        try {
            if (userDAO.findByUsername(username) != null) {
                req.setAttribute("error", "Username already exists.");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
                return;
            }

            // Optionally hash password here before storing
            User u = new User(username, password, role);
            boolean ok = userDAO.create(u);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("error", "Registration failed, try again.");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
