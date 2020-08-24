package com.ngoctm.service;

import com.ngoctm.dao.ProductInStockDAO;
import com.ngoctm.entity.Invoice;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInStock;
import com.ngoctm.entity.ProductInfo;
import com.ngoctm.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductInStockService {

    private static final Logger logger = Logger.getLogger(ProductInStockService.class);

    @Autowired
    private ProductInStockDAO<ProductInStock> productInStockDAO;

    public List<ProductInStock> findAllProductInStock(ProductInfo productInfo, Paging paging){
        logger.info("find all Product In Stock Service, Page " + paging.getIndexPage());

        StringBuilder queryStr = new StringBuilder();
        if(productInfo!=null) {
            if(productInfo.getCategory() != null) {
                queryStr.append(" and model.productInfo.category.name like \'%" +
                        productInfo.getCategory().getName() + "%\'");
            }
            if( productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
                queryStr.append(" and model.productInfo.code="
                        + productInfo.getCode());

            }
            if( productInfo.getName()!=null && !StringUtils.isEmpty(productInfo.getName()) ) {
                queryStr.append(" and model.productInfo.name like \'%"
                        + productInfo.getName() + "%\'");
            }
        }
        return productInStockDAO.findAll(paging, queryStr.toString());
    }

    public void updateOrsave(Invoice invoice) throws Exception{
        logger.info("update invoice product in stock " + invoice);

        // if product don't exist in stock
        if(invoice.getProductInfo().getId() != null){
            List<ProductInStock> productInStocks = productInStockDAO.findByProperty("productInfo",invoice.getProductInfo().getId());
            if(productInStocks == null || productInStocks.isEmpty()){
                ProductInStock productInStock = new ProductInStock();
                productInStock.setActiveFlag(1);
                productInStock.setProductInfo(invoice.getProductInfo());
                productInStock.setPrice(invoice.getPrice());
                if(invoice.getType() == 0) {
                    productInStock.setQty(productInStock.getQty() + invoice.getQty());
                }
                else {
                    throw new Exception("Product in stock don't exist, can't good issues");
                }
                logger.info("save product In stock ===============================> " + productInStock);
                productInStockDAO.save(productInStock);
            } else {
                ProductInStock productInStock = productInStocks.get(0);
                productInStock.setPrice(invoice.getPrice());
                int qty;
                if(invoice.getType() == Constant.GOODS_RECEIPT) {
                    qty = productInStock.getQty() + invoice.getQty();
                } else {
                    qty = productInStock.getQty() - invoice.getQty();
                }
                if(qty < 0){
                    throw new Exception("Quantity < 0");
                }
                productInStock.setQty(qty);
                logger.info("update product In stock ===============================> " + productInStock);
                productInStockDAO.update(productInStock);

            }


        }
    }


}
