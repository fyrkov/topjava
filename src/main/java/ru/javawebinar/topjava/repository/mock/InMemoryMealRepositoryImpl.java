package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userID) {
        if (repository.containsKey(id) && repository.get(id).getUserId() == userID) {
            repository.remove(id);
            return true;
        } else return false;
    }

    @Override
    public Meal get(int id, int userID) {
        if (repository.containsKey(id) && (repository.get(id).getUserId() == userID)) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public Collection<Meal> getAll(int userID) {
        return repository.values().
                stream().
                filter(o -> o.getUserId() == userID).
                sorted((o1, o2) -> (o1.getDateTime().isBefore(o2.getDateTime()) ? 1 : -1)).collect(Collectors.toList());
    }

    public Collection<Meal> getAll(int userID, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return repository.values().
                stream().
                filter(o -> o.getUserId() == userID).
                filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime)).
                filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate)).
                sorted((o1, o2) -> (o1.getDateTime().isBefore(o2.getDateTime()) ? 1 : -1)).collect(Collectors.toList());
    }
}

