package com.ngoctm.dao;

import com.ngoctm.entity.ProductInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInfoDAOImpl extends BaseDAOImpl<ProductInfo> implements ProductInfoDAO<ProductInfo>{


}
