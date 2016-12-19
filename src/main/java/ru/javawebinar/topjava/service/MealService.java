package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {

    Meal save(Meal meal);

    void delete(int id, int userID) throws NotFoundException;

    Meal get(int id, int userID) throws NotFoundException;

    List<Meal> getAll(int userID);

    void update(Meal meal, int userID);

}
