package com.ngoctm.dao;

import com.ngoctm.entity.Category;
import com.ngoctm.entity.Paging;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E> {

    final static Logger log = Logger.getLogger(BaseDAOImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    public List<E> findAll(Paging paging, String query) {
        log.info("find all record from db");
        // get session
        Session session = sessionFactory.getCurrentSession();
        StringBuilder queryString = new StringBuilder("");
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1").append(query);
        if(paging == null){
            return session.createQuery(queryString.toString()).getResultList();
        }

        // count record if query first time
        if(paging.getTotalRows() == 0 && paging != null){
            StringBuilder queryCount = new StringBuilder();
            queryCount.append("select count(id) from ").append(getGenericName()).append(" as model where model.activeFlag=1");
            long totalRows = (long) session.createQuery(queryCount.toString()).uniqueResult();
            paging.setTotalRows(totalRows);
        }


        log.info( "Query find all ====>" +queryString.toString());

        return session.createQuery(queryString.toString()).setFirstResult(paging.getOffset()).
                setMaxResults(paging.getRecordPerPage()).list();
    }

    public E findById(Class<E> e, Serializable id) {
        log.info("Find by ID ");
        return sessionFactory.getCurrentSession().get(e, id);
    }

    public List<E> findByProperty(String property, Object value) {
        log.info("Find by property");
      StringBuilder queryString = new StringBuilder();
      queryString.append("from ").append(getGenericName()).append(" c where c.activeFlag=1").
              append(" and c.").append(property).append("=\'").append(value).append("\'");

       // queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1 and model.")
              //  .append(property).append("=" + value);
        //log.info(" query find by property ===>"+queryString.toString());
      //  log.info(queryString);
      //  Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());

        return  sessionFactory.getCurrentSession().createQuery(queryString.toString()).list();

    }

    public void save(E instance) {
        log.info(" save instance");
        sessionFactory.getCurrentSession().persist(instance);
    }

    public void update(E instance) {
        log.info("update");
        sessionFactory.getCurrentSession().merge(instance);
    }

    @Override
    public void delete(E instance) {
        log.info("delete");
        sessionFactory.getCurrentSession().remove(instance);
    }


    public String getGenericName() {
        String s = getClass().getGenericSuperclass().toString();
        Pattern pattern = Pattern.compile("\\<(.*?)\\>");
        Matcher m = pattern.matcher(s);
        String generic="null";

        if(m.find()) {
            generic = m.group(1);
        }

        int lengtFullClassName = generic.length();
        for(int i = lengtFullClassName -1; i > 0; i--){
            if(generic.charAt(i) == '.'){
                generic = generic.substring(i + 1);
                break;
            }
        }

        return generic;
    }


}

