package com.ngoctm.dao;

import com.ngoctm.entity.Category;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO<Category>{
    @Override
    public List<Category> searchCategory(String querry) {

        Session session = sessionFactory.getCurrentSession();
        StringBuilder querryBuilder = new StringBuilder();
        querryBuilder.append("from " + Category.class.getName() + " c where c.activeFlag=1").append(querry);
        return session.createQuery(querryBuilder.toString()).getResultList();
    }
}
