package com.ngoctm.dao;

import com.ngoctm.entity.Users;
import org.springframework.stereotype.Repository;

import javax.persistence.Inheritance;

@Repository
public class UserDAOImpl extends BaseDaoImpl<Users> implements UserDao<Users>{

}
