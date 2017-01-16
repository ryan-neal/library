package com.book.library.dao;

import com.book.library.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements com.book.library.dao.BookDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findAll() {
        Session session = sessionFactory.openSession();
        List<Book> books = session.createCriteria(Book.class).list();

        session.close();
        return books;
    }

    @Override
    public Book findById(Long id) {
        Session session = sessionFactory.openSession();
        Book book = session.get(Book.class,id);
        session.close();
        return book;
    }

    @Override
    public void save(Book book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(book);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Book book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(book);
        session.getTransaction().commit();
        session.close();
    }
}
