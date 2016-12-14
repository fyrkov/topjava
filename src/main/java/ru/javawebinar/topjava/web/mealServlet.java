package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealsMemoryDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.model.MealsMemoryDAO.getInstance;
import static ru.javawebinar.topjava.util.MealsUtil.*;

/**
 * Created by user on 12.12.2016.
 */
public class mealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(UserServlet.class);
    MealsMemoryDAO dao = getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        //pw.println("This is mealServlet");
        //LOG.debug("redirect to /meals.jsp with mealsMemorylist as Attribute");

        String forward;
        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.read(mealId);
            request.setAttribute("meal", meal);
        }
        forward = "/meals.jsp";
        request.getSession().setAttribute("mealsMemorylist", getWithExceeded(dao.readAll(), 2000));
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action.equals("submit")) {
            String dateString = request.getParameter("dateTime");
            if (dateString.isEmpty()) dateString = LocalDateTime.now().toString();
            String parts[] = dateString.split("-|T|:");
            LocalDateTime date = LocalDateTime.of(Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]));

            String description = request.getParameter("description");

            int cal = 0;
            try {
                cal = Integer.parseInt(request.getParameter("calories"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            int id = -1;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (id == -1) dao.create(date, description, cal);
            else dao.update(id, date, description, cal);
        } else if (action.equals("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);

        }
            String forward = "/meals.jsp";
            request.getSession().setAttribute("mealsMemorylist", getWithExceeded(dao.readAll(), 2000));
            request.getRequestDispatcher(forward).forward(request, response);

    }
}
