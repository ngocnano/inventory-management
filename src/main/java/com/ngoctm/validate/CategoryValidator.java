package com.ngoctm.validate;

import com.ngoctm.entity.Category;
import com.ngoctm.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CategoryValidator implements Validator {

    private static final Logger logger = Logger.getLogger(CategoryValidator.class);

    @Autowired
    private ProductService productService;
    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz == Category.class;
    }

    @Override
    public void validate(Object target, Errors errors) {

        Category category = (Category) target;
        logger.info("validate " + category);

        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");

        if(category.getCode()!=null) {
            List<Category> results=  productService.findCategoryByProperty("code", category.getCode());
            if(results!=null && !results.isEmpty() ) {
                errors.rejectValue("code", "msg.code.exist");
            }
        }


    }
}
