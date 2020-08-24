package com.ngoctm.controller;

import com.ngoctm.dao.ProductInStockDAO;
import com.ngoctm.dao.ProductInfoDAO;
import com.ngoctm.entity.Category;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInStock;
import com.ngoctm.entity.ProductInfo;
import com.ngoctm.service.ProductInStockService;
import com.ngoctm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/stock")
public class ProductInStockController {

    @Autowired
    private ProductInStockService productInStockService;

    @GetMapping("/product-in-stock/list")
    public String redirect(HttpSession session){
        return "redirect:/stock/product-in-stock/list/1";
    }

    @RequestMapping("/product-in-stock/list/{page}")
    public String showProductInStockList(@PathVariable("page") int page, Model model,
                                          @ModelAttribute("searchForm") ProductInfo productInfo){

        //set page info
        Paging paging = new Paging(10);
        paging.setIndexPage(page);

        System.out.println("=================== product" + productInfo);
        List<ProductInStock> productInStockList = productInStockService.findAllProductInStock(productInfo,paging);
        model.addAttribute("productInStockList",productInStockList);
        model.addAttribute("pageInfo",paging);

        return "stock/product-in-stock";
    }


}
