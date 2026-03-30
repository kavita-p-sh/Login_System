package com.kavita.loginsystem.service;

public interface UserService {
    String register(String email, String password);

    String login(String email, String password);
}
