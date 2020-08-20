package com.ngoctm.service;

import com.ngoctm.dao.CategoryDAO;
import com.ngoctm.dao.ProductInfoDAO;
import com.ngoctm.entity.Category;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInfo;
import com.ngoctm.util.ConfigLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    Logger logger = Logger.getLogger(ProductService.class);

    @Autowired
    CategoryDAO<Category> categoryDAO;

    @Autowired
    ProductInfoDAO<ProductInfo> productInfoDAO;

    @Autowired
    ConfigLoader configLoader;

    public void saveCategory(Category category){
        logger.info("Save Category" + category);
        category.setActiveFlag(1);
        categoryDAO.save(category);
    }

    public void updateCategory(Category category){
        logger.info("Update Category" + category);
        categoryDAO.update(category);
    }

    public void deleteCategory(Category category){
        logger.info("Delete Category" + category);
        category.setActiveFlag(0);
        categoryDAO.update(category);
    }

    public List<Category> findCategoryByProperty(String property, Object value){
        logger.info("Find Category by property name="  + property + " Value=" + value);
        return categoryDAO.findByProperty(property,value);
    }

    public List<Category> findAllCategory(Paging paging){
        logger.info("Find all Category");
        return categoryDAO.findAll(paging, "");
    }

    public List<Category> searchCategory(Category category, Paging paging){

        StringBuilder queryStr = new StringBuilder();
        if(category!=null) {
            if(category.getId()!=null && category.getId()!=0) {
                queryStr.append(" and c.id=" + category.getId());
            }
            if(category.getCode()!=null && !StringUtils.isEmpty(category.getCode())) {
                queryStr.append(" and c.code=\'" + category.getCode() + "\'");
            }
            if(category.getName()!=null && !StringUtils.isEmpty(category.getName()) ) {
                queryStr.append(" and c.name=\'" + category.getName() + "\'");
            }
            logger.info(queryStr);
            return categoryDAO.findAll(paging,queryStr.toString());
        }

        return null;
    }

    public Category findCategoryById(int id){

        return categoryDAO.findById(Category.class, id);
    }

    public ProductInfo findProductInfoById(int id){

        return productInfoDAO.findById(ProductInfo.class, id);
    }

    public void saveProductInfo(ProductInfo productInfo) throws IOException {
        logger.info("Save ProductInfo" + productInfo);
        productInfo.setActiveFlag(1);
        processUploadFile(productInfo.getMultipartFile());
        productInfo.setImgUrl("/upload/"+System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename());
        productInfoDAO.save(productInfo);
    }

    public void updateProductInfo(ProductInfo productInfo) throws IOException {
        logger.info("Update ProductInfo" + productInfo);
        processUploadFile(productInfo.getMultipartFile());
        if(productInfo.getMultipartFile()!=null) {
            productInfo.setImgUrl("/upload/"+System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename());
        }
        productInfoDAO.update(productInfo);
    }

    public void deleteProductInfo(ProductInfo productInfo){
        logger.info("Delete ProductInfo" + productInfo);
        productInfo.setActiveFlag(0);
        productInfoDAO.update(productInfo);
    }

    public List<ProductInfo> findProductInfoByProperty(String property, Object value){
        logger.info("Find ProductInfo by property name="  + property + " Value=" + value);
        return productInfoDAO.findByProperty(property,value);
    }

    public List<ProductInfo> findAllPrductInfo(Paging paging){
        logger.info("Find all Category");
        return productInfoDAO.findAll(paging,"");
    }

    public List<ProductInfo> searchProductInfo(ProductInfo productInfo, Paging paging){
        StringBuilder queryStr = new StringBuilder();
        if(productInfo!=null) {
            if(productInfo.getId()!=null && productInfo.getId()!=0) {
                queryStr.append(" and c.id=" + productInfo.getId());
            }
            if(productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
                queryStr.append(" and c.code=\'" + productInfo.getCode() + "\'");
            }
            if(productInfo.getName()!=null && !StringUtils.isEmpty(productInfo.getName()) ) {
                queryStr.append(" and c.name=\'" + productInfo.getName() + "\'");
            }
            logger.info(queryStr);
            return productInfoDAO.findAll(paging,queryStr.toString());
        }

        return null;
    }

    private void processUploadFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        if(multipartFile!=null) {
            File dir = new File(configLoader.getValue("upload.location"));
            if(!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
            File file = new File(configLoader.getValue("upload.location"),fileName);
            logger.info("Create file ==============================> " + file.getPath());
            multipartFile.transferTo(file);
        }
    }


}
