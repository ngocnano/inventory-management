package com.ngoctm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class ConfigLoader {

    @Autowired
    Environment environment;

    public String getValue(String key){

        return environment.getProperty(key);
    }

}
