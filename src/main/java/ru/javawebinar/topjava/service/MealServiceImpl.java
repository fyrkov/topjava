package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userID) throws NotFoundException {
        if (repository.delete(id, userID)) {
        }
        else throw new NotFoundException("Delete meal by ID: meal not found");
    }

    @Override
    public Meal get(int id, int userID) throws NotFoundException {
        if (repository.get(id, userID)!=null) {
            return repository.get(id, userID);
        } else {
            throw new NotFoundException("Get meal by ID: meal not found");
        }
    }

    @Override
    public List<Meal> getAll(int userID) {
        List<Meal> l = new ArrayList<>(repository.getAll(userID));
        if (l.isEmpty()) {
            throw new NotFoundException("Get all meal: meal not found");
        }
        return l;
    }

    @Override
    public List<Meal> getAll(int userID, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<Meal> l = new ArrayList<>(repository.getAll(userID, startDate, startTime, endDate, endTime));
        if (l.isEmpty()) {
            throw new NotFoundException("Get all meal: meal not found");
        }
        return l;
    }
    @Override
    public void update(Meal meal, int userID) {
        repository.save(meal);
    }
}
