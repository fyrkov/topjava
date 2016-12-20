package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private AdminRestController adminUserController;
    LocalDate ld1=LocalDate.ofEpochDay(0), ld2=LocalDate.now();
    LocalTime lt1=LocalTime.MIN, lt2=LocalTime.of(23,59);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));
            mealRestController = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (action.equals("edit")) {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")), AuthorizedUser.id());

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew()) mealRestController.create(meal);
            else mealRestController.update(meal, meal.getId(), AuthorizedUser.id());
            response.sendRedirect("meals");
        } else if (action.equals("filter")) {

            if (!request.getParameter("dateFrom").isEmpty()) {
                ld1 = LocalDate.parse(request.getParameter("dateFrom"));
            }
            if (!request.getParameter("dateTo").isEmpty()) {
                ld2 = LocalDate.parse(request.getParameter("dateTo"));
            }
            if (!request.getParameter("timeFrom").isEmpty()) {
                lt1 = LocalTime.parse(request.getParameter("timeFrom"));
            }
            if (!request.getParameter("timeTo").isEmpty()) {
                lt2 = LocalTime.parse(request.getParameter("timeTo"));
            }
            LOG.info("getAll within period from "+ld1+lt1+" to "+ld2+lt2);
            request.setAttribute("meals",
                    mealRestController.getAll(ld1, lt1, ld2, lt2));
            request.setAttribute("ld1", ld1);
            request.setAttribute("ld2", ld2);
            request.setAttribute("lt1", lt1);
            request.setAttribute("lt2", lt2);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals",
                    mealRestController.getAll(ld1,lt1,ld2,lt2));
            request.setAttribute("ld1", ld1);
            request.setAttribute("ld2", ld2);
            request.setAttribute("lt1", lt1);
            request.setAttribute("lt2", lt2);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, AuthorizedUser.id()) :
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}