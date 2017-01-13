package com.book.library.dao;

import com.book.library.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book findById(Long id);
    void save(Book book);
    void delete(Book book);

}
