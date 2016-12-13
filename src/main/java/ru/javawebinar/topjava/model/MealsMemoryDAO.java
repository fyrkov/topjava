package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 12.12.2016.
 */
public class MealsMemoryDAO implements MealsDAO {

    private MealsMemoryDAO() {}
    private static boolean isCreated = false;
    private static MealsMemoryDAO mmDAO;

    public static MealsMemoryDAO getMealsMemoryDAO() {
        if (!isCreated) {
            mmDAO = new MealsMemoryDAO();
            isCreated = true;
        }
        return mmDAO;
    }

    private static Integer idCounter=0;
    public static Integer getIdCounter() {
        return idCounter++;
    }

    private static Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    static {
        int id = getIdCounter(); meals.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        id = getIdCounter(); meals.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        id = getIdCounter(); meals.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        id = getIdCounter(); meals.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        id = getIdCounter(); meals.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        id = getIdCounter(); meals.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        for (int i=0;i<10;i++) {
            id = getIdCounter();
            meals.put(id, new Meal(id,
            LocalDateTime.of(2015, Month.of((int)Math.round(Math.random()*11+1)),  (int)Math.round(Math.random()*27+1), (int)Math.round(Math.random()*23), 0),
            "lunch", (int)Math.round(Math.random()*1300+300)));
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
