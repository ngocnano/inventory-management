package com.ngoctm.validate;

import com.ngoctm.entity.Users;
import com.ngoctm.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class LoginValidator implements Validator {

    static Logger logger = Logger.getLogger(LoginValidator.class);

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Users.class;
    }

    @Override
    public void validate(Object o, Errors errors) {

        Users users = (Users) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"userName","msg.required");
        ValidationUtils.rejectIfEmpty(errors,"password", "msg.required");

        // check user name and password
        if(!StringUtils.isEmpty(users.getUserName()) && !StringUtils.isEmpty(users.getPassword())){
            List<Users> usersList = userService.findByProperty("userName", users.getUserName());
            // found user name
            if(users!=null && !usersList.isEmpty()){
                // check password
                if(!users.getPassword().equals(usersList.get(0).getPassword())){
                    errors.rejectValue("password", "msg.wrong.password");
                }
            }else {
                // not found user name
                errors.rejectValue("userName", "msg.wrong.username");

            }

        }

    }
}
