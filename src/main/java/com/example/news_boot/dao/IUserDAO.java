package com.example.news_boot.dao;

import com.example.news_boot.entity.UserInfo;

public interface IUserDAO {

    boolean registration(UserInfo user) throws DaoException;
}
