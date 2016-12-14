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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.model.MealsMemoryDAO.getMealsMemoryDAO;
import static ru.javawebinar.topjava.util.MealsUtil.*;

/**
 * Created by user on 12.12.2016.
 */
public class mealServlet extends HttpServlet {

    MealsMemoryDAO dao = getMealsMemoryDAO();

    private static final Logger LOG = getLogger(UserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        //pw.println("This is mealServlet");
        //request.setAttribute("mealsMemorylist", getWithExceeded(getMealsMemoryDAO().readAll(), 2000));
        //LOG.debug("redirect to /meals.jsp with mealsMemorylist as Attribute");
        //request.getRequestDispatcher("/meals.jsp").forward(request, response);

        String forward;
        //String action = null;
        String action = request.getParameter("action");

        if (action!=null && action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);
            forward = "/meals.jsp";
            //request.setAttribute("mealsMemorylist", getWithExceeded(dao.readAll(), 2000));
        }  else if (action!=null && action.equalsIgnoreCase("edit")){
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.read(mealId);
            request.setAttribute("meal", meal);
            forward = "/meals.jsp";
        } else {
            forward = "/meals.jsp";
            //request.setAttribute("mealsMemorylist", getWithExceeded(dao.readAll(), 2000));
        }
        request.getSession().setAttribute("mealsMemorylist", getWithExceeded(dao.readAll(), 2000));
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String dateString = request.getParameter("dateTime");
        String parts[] = dateString.split("-|T|:");
        for (String part: parts) {
            System.out.println(part);
        }
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
        else dao.update(id,date,description,cal);

        String forward="/meals.jsp";
        request.getSession().setAttribute("mealsMemorylist", getWithExceeded(dao.readAll(), 2000));
        request.getRequestDispatcher(forward).forward(request, response);
    }
}
