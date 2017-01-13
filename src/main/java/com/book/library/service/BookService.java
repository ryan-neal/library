package com.book.library.service;

import com.book.library.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    void save(Book book);
    void delete(Book book);
    void toggleFavorite(Book book);
}
