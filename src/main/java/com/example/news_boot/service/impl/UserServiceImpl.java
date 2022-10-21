package com.example.news_boot.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.news_boot.dao.DaoException;
import com.example.news_boot.dao.IUserDAO;
import com.example.news_boot.entity.UserInfo;
import com.example.news_boot.service.ServiceException;
import com.example.news_boot.service.UserService;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

        @Autowired
    private IUserDAO userDAO;

    @Override
    @Transactional
    public boolean registration(UserInfo user) throws ServiceException {
        
        try {
            return userDAO.registration(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
