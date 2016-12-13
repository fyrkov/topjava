package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by user on 12.12.2016.
 */
public interface MealsDAO {

    public  void create (LocalDateTime dateTime, String description, int calories);
    public  Meal read (Integer id);
    public  List<Meal> readAll();
    public  void update (Integer id, LocalDateTime dateTime, String description, int calories);
    public  void delete (Integer id);


}
