package com.book.library.service;

import com.book.library.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
