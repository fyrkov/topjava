package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        LOG.info("getAll");
        if (!service.getAll(AuthorizedUser.id()).isEmpty()) {
            return getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
        } else return Collections.emptyList();
    }

    public List<MealWithExceed> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LOG.info("getAll");
        if (!service.getAll(AuthorizedUser.id()).isEmpty()) {
            return getWithExceeded(service.getAll(AuthorizedUser.id(), startDate, startTime, endDate, endTime), AuthorizedUser.getCaloriesPerDay());
        } else return Collections.emptyList();
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("create " + meal);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id, int userID) {
        meal.setId(id);
        LOG.info("update " + meal);
        if (meal.getUserId() == userID) {
            service.update(meal, userID);
        } else {
            throw new NotFoundException("Update meal by ID: meal not found");
        }
    }

}
