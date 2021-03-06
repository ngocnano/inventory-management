package com.ngoctm.controller;

import com.ngoctm.entity.*;
import com.ngoctm.service.UserService;
import com.ngoctm.util.Constant;
import com.ngoctm.validate.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    LoginValidator loginValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) return;
        if(binder.getTarget().getClass() == Users.class) {
            binder.setValidator(loginValidator);
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new Users());
        return "login/login";
    }
    @PostMapping("/processLogin")
    public String processLogin(Model model , @ModelAttribute("loginForm")@Validated Users users , BindingResult result , HttpSession session) {
        if(result.hasErrors()) {
            return "login/login";
        }
        Users user  = userService.findByProperty("userName", users.getUserName()).get(0);
        //get role user to decentralization
        List<UserRole> userRoles = new ArrayList<>(user.getUserRoles());
        Role role = userRoles.get(0).getRole();
        //manager table that user can access
        List<Menu> menuList = new ArrayList<>();
        List<Menu> menuChildList = new ArrayList<>();
        for (Object temp : role.getAuths()) {
            Auth auth = (Auth) temp;
            Menu menu = auth.getMenu();
            // Can
            Boolean checkAuth = auth.getActiveFlag()==1 && auth.getPermission()==1;
            if(menu.getParentId()==0 && menu.getActiveFlag()==1 && menu.getOrderIndex()!=-1
                && checkAuth){
                menuList.add(menu);
            } else if(menu.getParentId()!=0 && menu.getActiveFlag()==1 && menu.getOrderIndex()!=-1
                        && checkAuth){
                menuChildList.add(menu);
            }

        }

        //add child menu to parent menu
        for(Menu parent: menuList){
            for(Menu child : menuChildList){
                if(child.getParentId() == parent.getId()){
                    parent.addChildMenu(child);
                }
            }
        }

        session.setAttribute(Constant.MENU_SESSION, menuList);
        session.setAttribute(Constant.USER_INFO, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(Constant.MENU_SESSION);
        session.removeAttribute(Constant.USER_INFO);

        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){

        return "error/page_403";
    }

}
