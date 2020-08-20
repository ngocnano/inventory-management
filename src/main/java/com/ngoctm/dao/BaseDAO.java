package com.ngoctm.dao;

import com.ngoctm.entity.Paging;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<E> {

    /**
     *
     * @param paging
     * @param query
     * @return
     */
    public List<E> findAll(Paging paging, String query);

    public E findById(Class<E> e, Serializable id);

    public List<E> findByProperty(String property, Object value);

    public void save(E instance);

    public void update(E instance);

    public void delete(E instance);

}
