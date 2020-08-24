package com.ngoctm.controller;

import com.ngoctm.entity.*;
import com.ngoctm.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/stock")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/history/list")
    public String redirect(HttpSession session){
        if(session.getAttribute("searchFormH") != null){
            session.removeAttribute("searchFormH");
        }
        return "redirect:/stock/product-in-stock/list/1";
    }


}
