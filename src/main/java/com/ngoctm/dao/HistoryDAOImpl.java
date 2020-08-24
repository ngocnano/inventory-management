package com.ngoctm.dao;

import com.ngoctm.entity.History;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HistoryDAOImpl extends BaseDAOImpl<History> implements HistoryDAO<History>{
}
