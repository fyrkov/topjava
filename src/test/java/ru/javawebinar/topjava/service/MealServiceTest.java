package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        Meal meal = new Meal (LocalDateTime.now(), "NewMeal", 1555);
        Meal created = service.save(meal, AuthorizedUser.id());
        meal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL_2, MEAL_1), service.getAll(AuthorizedUser.id()));
        Assert.assertEquals(meal, created);
    }

    @Test
    public void testDelete() throws Exception {
        List<Meal> meals = new ArrayList<>(service.getAll(ADMIN.getId()));
        service.getAll(AuthorizedUser.id()).forEach(meal -> service.delete(meal.getId(), AuthorizedUser.id()));
        Assert.assertEquals(meals.size(), 2);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        Meal meal = service.getAll(ADMIN.getId()).stream().findFirst().get();
        service.delete(meal.getId(), AuthorizedUser.id());
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        Meal meal = service.getAll(ADMIN.getId()).stream().findFirst().get();
        service.get(meal.getId(), AuthorizedUser.id());
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_2, MEAL_1), all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = service.getAll(AuthorizedUser.id()).stream().findFirst().get();
        meal.setDescription("Updated meal");
        service.update(meal, AuthorizedUser.id());
        MATCHER.assertEquals(meal, service.get(meal.getId(), AuthorizedUser.id()));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Meal meal = service.getAll(AuthorizedUser.id()).stream().findFirst().get();
        meal.setDescription("Updated meal");
        service.update(meal, ADMIN.getId());
    }
}