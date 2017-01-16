package com.book.library.dao;

import com.book.library.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by ryanneal on 1/16/17.
 */

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private SessionFactory sessionFactory;

    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, username);
        session.close();
        return user;
    }
}
