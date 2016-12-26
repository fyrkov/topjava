package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final Meal MEAL_1 = new Meal(LocalDateTime.of(2016, Month.JANUARY, 8, 7, 5), "user_breakfast", 1550);
    public static final Meal MEAL_2 = new Meal(LocalDateTime.of(2016, Month.JANUARY, 8, 12, 5), "user_lunch", 550);
    public static final Meal MEAL_3 = new Meal(LocalDateTime.of(2016, Month.JANUARY, 8, 8, 5), "'admin_breakfast'", 550);
    public static final Meal MEAL_4 = new Meal(LocalDateTime.of(2016, Month.JANUARY, 8, 12, 5), "'admin_lunch'", 550);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (
//                            Objects.equals(expected.getId(), actual.getId()) &&
                            Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );

}
