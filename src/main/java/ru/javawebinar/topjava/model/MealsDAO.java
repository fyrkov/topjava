package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by user on 12.12.2016.
 */
public interface MealsDAO {

    void create(LocalDateTime dateTime, String description, int calories);
    Meal read(Integer id);
    List<Meal> readAll();
    void update(Integer id, LocalDateTime dateTime, String description, int calories);
    void delete(Integer id);


}
