package ru.javawebinar.topjava.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

/**
 * GKislin
 * 13.03.2015.
 */
@ContextConfiguration(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class InMemoryAdminRestControllerSpringTest {
    protected final org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdminRestController userController;
    @Autowired
    private MealRestController mealController;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        MEAL_1.setId(null);
        mealRepository.save(MEAL_1, USER.getId());
        MEAL_2.setId(null);
        mealRepository.save(MEAL_2, USER.getId());
        MEAL_3.setId(null);
        mealRepository.save(MEAL_3, ADMIN.getId());
        MEAL_4.setId(null);
        mealRepository.save(MEAL_4, ADMIN.getId());
    }

//    @Test
//    public void testDelete() throws Exception {
//        userController.delete(userController.getAll().get(1).getId());
//        Collection<User> users = userController.getAll();
//        Assert.assertEquals(users.size(), 1);
//        Assert.assertEquals(users.iterator().next(), ADMIN);
//    }
//
//    @Test(expected = NotFoundException.class)
//    public void testDeleteNotFound() throws Exception {
//        userController.delete(10);
//    }

    @Test
    public void test123() throws Exception {
        Meal m = new Meal(LocalDateTime.now(),"asd",500);
        mealRepository.save(m, AuthorizedUser.id());
        mealRepository.getAll(AuthorizedUser.id()).forEach(meal->LOG.warn(meal.toString()));
    }
    @Test
    public void testGet() throws Exception {
        Meal m1;
        m1 = mealController.get(100005);
        LOG.warn(m1.toString());
    }
    @Test
    public void testGetAll() throws Exception {
        mealController.getAll().forEach(meal->LOG.warn(meal.toString()));
    }
    @Test
    public void testUpdate() throws Exception {
        List<MealWithExceed> m1 = mealController.getBetween(LocalDate.of(2016, Month.JANUARY,1), LocalTime.of(8,0), LocalDate.of(2016, Month.DECEMBER, 31), LocalTime.of(17,0));
        m1.forEach(meal->LOG.warn(meal.toString()));
    }

}
