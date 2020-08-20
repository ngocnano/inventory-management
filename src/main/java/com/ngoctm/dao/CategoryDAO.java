package com.ngoctm.dao;

import java.util.List;

public interface CategoryDAO<E> extends BaseDAO<E>{

    public List<E> searchCategory(String querry);
}
