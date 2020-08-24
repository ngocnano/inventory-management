package com.ngoctm.validate;

import com.ngoctm.entity.ProductInfo;
import com.ngoctm.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ProductInfoValidator implements Validator{

    private static final Logger logger = Logger.getLogger(ProductInfoValidator.class);

    @Autowired
    private ProductService productService;
    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz == ProductInfo.class;
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProductInfo productInfo = (ProductInfo) target;
        logger.info("validate " + productInfo.getMultipartFile().getOriginalFilename());

        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "multipartFile", "msg.required");


        if(productInfo.getCode()!=null) {
            List<ProductInfo> results=  productService.findProductInfoByProperty("code", productInfo.getCode());
            if(results!=null && !results.isEmpty() ) {
                errors.rejectValue("code", "msg.code.exist");
            }
        }

        logger.info(errors.getAllErrors().toString());
    }
}
