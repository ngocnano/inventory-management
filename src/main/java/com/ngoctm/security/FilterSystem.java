package com.ngoctm.security;

import com.ngoctm.entity.Menu;
import com.ngoctm.entity.Users;
import com.ngoctm.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FilterSystem implements HandlerInterceptor {

    Logger logger = Logger.getLogger(FilterSystem.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("Request URI " + request.getRequestURI());

        Users users = (Users) request.getSession().getAttribute(Constant.USER_INFO);
        List<Menu> menuList = (List<Menu>) request.getSession().getAttribute(Constant.MENU_SESSION);
        String url = request.getServletPath();

        //check login
        if (users == null){
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        else {
                if(url.equals("/")){
                    return true;
                }

                if(!hasPermission(menuList,url)){
                    response.sendRedirect(request.getContextPath() + "/access-denied");
                    return false;
                }
        }

        return true;
    }

    private boolean hasPermission(List<Menu> list, String url){
        System.out.println("Url day ne   " + url);
        for (Menu menu : list) {

            System.out.println("ok e yoyyy " + menu.getUrl());
            if(url.contains(menu.getUrl())){
                return true;
            }
        }

        return false;
    }
}
