package com.ngoctm.dao;

import com.ngoctm.entity.ProductInStock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductInStockDAOImpl extends BaseDAOImpl<ProductInStock> implements ProductInStockDAO<ProductInStock>{
}
