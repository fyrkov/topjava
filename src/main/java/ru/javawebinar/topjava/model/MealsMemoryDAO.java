package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user on 12.12.2016.
 */
public class MealsMemoryDAO implements MealsDAO {

    private MealsMemoryDAO() {}
    private static boolean isCreated = false;
    private static MealsMemoryDAO instance;

    public static MealsMemoryDAO getInstance() {
        if (!isCreated) {
            instance = new MealsMemoryDAO();
            isCreated = true;
        }
        return instance;
    }

    private static AtomicInteger idCounter = new AtomicInteger(0);
    public static Integer getIdCounter() {
        return idCounter.getAndIncrement();
    }

    private static Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    static {
        int id;
        String s[] = {"Завтрак", "Обед", "Ужин"};
        for (int i=0;i<4;i++) {
            LocalDate ld = LocalDate.of(2015, Month.of((int) Math.round(Math.random() * 11 + 1)), (int) Math.round(Math.random() * 27 + 1));
            for (int j = 1; j <= 3; j++) {
                LocalTime lt = LocalTime.of((int) Math.round(Math.random() * 4 + 6*j), 0);
                LocalDateTime ldt = LocalDateTime.of(ld, lt);
                id = getIdCounter();
                meals.put(id, new Meal(id, ldt, s[j-1], (int) Math.round(Math.random() * 800 + 300)));
            }
        }
    }

    @Override
    public  void create (LocalDateTime dateTime, String description, int calories)  {
        int id = getIdCounter();
        meals.put(id, new Meal(id, dateTime, description, calories));
    }

    @Override
    public Meal read(Integer id) {
        return meals.get(id);
    }

    @Override
    public  List<Meal> readAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void update(Integer id, LocalDateTime dateTime, String description, int calories) {
        meals.replace(id, new Meal(id, dateTime, description, calories));
    }

    @Override
    public void delete(Integer id) {
       meals.remove(id);
    }
}
