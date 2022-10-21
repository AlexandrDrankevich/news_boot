package com.example.news_boot.dao.impl;


import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.news_boot.dao.DaoException;
import com.example.news_boot.dao.IUserDAO;
import com.example.news_boot.entity.UserInfo;

@Repository
public class UserDAO implements IUserDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean registration(UserInfo user) throws DaoException {
        Session currentSession = entityManager.unwrap(Session.class);
        try {
            if (isloginExist(currentSession, user.getLogin())) {
                return false;
            }
            currentSession.save(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return true;
    }


    private boolean isloginExist(Session currentSession, String login) {
        Query<UserInfo> query = currentSession.createQuery("from UserInfo v where v.login=:login",
                UserInfo.class);
        query.setParameter("login", login);
        return query.uniqueResult() != null;
    }
}
