package com.ngoctm.service;

import com.ngoctm.dao.UserDAO;
import com.ngoctm.entity.Users;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final static Logger log = Logger.getLogger(UserService.class);

    @Autowired
    private UserDAO<Users> userDao;

    public List<Users> findByProperty(String property, Object value){
        log.info("Find user by property start ");
       return userDao.findByProperty(property,value);
    }
}
