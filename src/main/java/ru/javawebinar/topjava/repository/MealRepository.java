package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal Meal);

    boolean delete(int id, int userID);

    Meal get(int id, int userID);

    Collection<Meal> getAll(int userID);

    Collection<Meal> getAll(int userID, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);
}
