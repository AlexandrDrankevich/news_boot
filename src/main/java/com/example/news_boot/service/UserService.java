package com.example.news_boot.service;

import com.example.news_boot.entity.UserInfo;

public interface UserService {

    boolean registration(UserInfo user) throws ServiceException;
}
