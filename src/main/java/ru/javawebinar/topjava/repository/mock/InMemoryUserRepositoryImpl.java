package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)) {
            LOG.info("delete " + id);
            repository.remove(id);
            return true;
        } else return false;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        if (repository.containsKey(id)) {
            LOG.info("get " + id);
            return repository.get(id);
        } else return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return new ArrayList<>(repository.values()).stream().sorted().collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        boolean isFound = repository.values().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
        if (isFound) {
            return repository.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).distinct().collect(Collectors.toList()).get(0);
        } else return null;
    }
}
