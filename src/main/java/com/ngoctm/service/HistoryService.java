package com.ngoctm.service;

import com.ngoctm.dao.HistoryDAO;
import com.ngoctm.entity.History;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInStock;
import com.ngoctm.entity.ProductInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    Logger logger = Logger.getLogger(HistoryService.class);

    @Autowired
    HistoryDAO<History> historyDAO;


    public List<History> findAllHistory(Paging paging){
        logger.info("find all Product In Stock Service, Page " + paging.getIndexPage());
        return historyDAO.findAll(paging,"");
    }

    public List<History> searchProductInStock(Paging paging, ProductInfo productInfo){
        logger.info("find all Product In Stock Service, Page " + paging.getIndexPage());
        StringBuilder queryStr = new StringBuilder();
        if(productInfo!=null) {
            if(!StringUtils.isEmpty(productInfo.getCategory().getName())) {
                queryStr.append(" and model.productInfo.category.name like \'%" +
                        productInfo.getCategory().getName() + "%\'");
            }
            if(!StringUtils.isEmpty(productInfo.getCode())) {
                queryStr.append(" and model.productInfo.code="
                        + productInfo.getCode());

            }
            if( !StringUtils.isEmpty(productInfo.getName()) ) {
                queryStr.append(" and model.productInfo.name like \'%"
                        + productInfo.getName() + "%\'");
            }
        }
        return historyDAO.findAll(paging, queryStr.toString());
    }

}
