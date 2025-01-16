package com.max.restfulwebservices.service;

import com.max.restfulwebservices.model.User;
import com.max.restfulwebservices.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class UserDaoService {
    private  static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Eve", new Date()));
        users.add(new User(3, "Jack Sparrow", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(users.size() + 1);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        //throw new UserNotFoundException("User not found with id " + id);
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User update(User user) {
        User userTmp = users.stream().filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + user.getId()));
        userTmp.setName(user.getName());
        userTmp.setBirthDate(user.getBirthDate());
        return userTmp;
    }

    public User deleteById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                return user;
            }
        }
        return null;
    }

}
