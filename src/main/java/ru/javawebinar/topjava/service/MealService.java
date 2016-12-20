package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
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

    List<Meal> getAll(int userID, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);

    void update(Meal meal, int userID);

}
