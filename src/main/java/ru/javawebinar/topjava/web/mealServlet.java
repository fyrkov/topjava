package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.model.MealsMemoryDAO.getMealsMemoryDAO;
import static ru.javawebinar.topjava.util.MealsUtil.*;

/**
 * Created by user on 12.12.2016.
 */
public class mealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(UserServlet.class);

    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }*/

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        //pw.println("This is mealServlet");
        request.setAttribute("mealsMemorylist", getWithExceeded(getMealsMemoryDAO().readAll(), 2000));
        LOG.debug("redirect to /meals.jsp with mealsMemorylist as Attribute");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
