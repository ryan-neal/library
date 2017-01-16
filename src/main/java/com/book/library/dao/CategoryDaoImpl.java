package com.book.library.dao;

import com.book.library.model.Category;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements com.book.library.dao.CategoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Get all categories with a Hibernate criteria
        List<Category> categories = session.createCriteria(Category.class).list();

        // Close session
        session.close();

        return categories;
    }

    @Override
    public Category findById(Long id) {
        Session session = sessionFactory.openSession();

        // Get all categories with a Hibernate criteria
        Category category = session.get(Category.class, id);

        Hibernate.initialize(category.getBooks());

        // Close session
        session.close();

        return category;
    }

    @Override
    public void save(Category category) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Save the category
        session.saveOrUpdate(category);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @Override
    public void delete(Category category) {
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Save the category
        session.delete(category);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

    }
}
