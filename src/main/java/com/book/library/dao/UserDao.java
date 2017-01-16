package com.book.library.dao;

import com.book.library.model.User;

public interface UserDao{
    User findByUsername(String username);
}
