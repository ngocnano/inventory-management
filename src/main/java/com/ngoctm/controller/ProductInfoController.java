package com.ngoctm.controller;

import com.ngoctm.entity.Category;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInfo;
import com.ngoctm.service.ProductService;
import com.ngoctm.validate.ProductInfoValidater;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product/productInfo")
public class ProductInfoController {

    static final Logger logger = Logger.getLogger(ProductInfoController.class);

    private Paging paging;

    @Autowired
    ProductService productService;

    @Autowired
    ProductInfoValidater productInfoValidater;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        if(binder.getTarget() == null){
            return;
        }
        if(binder.getTarget().getClass() == ProductInfo.class){
            binder.setValidator(productInfoValidater);
        }
    }

    @RequestMapping("/list")
    public String redirect() {
        return "redirect:/product/list/1";
    }

    @GetMapping("/list/{page}")
    public String showProductInfoList(@PathVariable("page") int page,Model model){
        logger.info("show productInfo list");
        if(paging == null){
            paging = new Paging(10);
        }
        else {
            paging.setIndexPage(page);
        }

        List<ProductInfo> productInfoList = productService.findAllPrductInfo(paging);
        model.addAttribute("listProductInfo", productInfoList);
        model.addAttribute("searchForm", new Category());
        model.addAttribute("pageInfo", paging);
        return "product/product-list";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model){
        logger.info("show form product add");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategory(new Category());
        getListCategory(model);
        model.addAttribute("productInfo", productInfo);
        return "product/product-action";
    }

    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable("id") int id, Model model){

        ProductInfo productInfo = productService.findProductInfoById(id);

        if(productInfo == null){
            return "error/page_404";
        }
        getListCategory(model);

        model.addAttribute("productInfo", productInfo);
        return "product/product-action";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("productInfo")@Validated ProductInfo productInfo, Model model,
                               BindingResult result) throws IOException {

        logger.info("Save Product ===========================> with " + productInfo.getCategory().getId());
        if(result.hasErrors()){
            getListCategory(model);
            return "product/product-action";
        }


        if(productInfo.getId() == null || productInfo.getId() == 0){
            productService.saveProductInfo(productInfo);
        } else {
            productService.updateProductInfo(productInfo);
        }
        model.addAttribute("searchForm", new ProductInfo());

        return "product/product-list";
    }

    private void getListCategory(Model model) {
        List<Category> categoryList = productService.findAllCategory(null);
        Map<Integer,String> categoryMap = new HashMap<>();
        for (Category category: categoryList) {
            categoryMap.put(category.getId(),category.getName());
        }
        model.addAttribute("categoryMap", categoryMap);
    }

    @GetMapping("/delete/{id}")
    public String daleteProductInfo(@PathVariable("id") int id){

        ProductInfo productInfo = productService.findProductInfoById(id);
        if(productInfo == null){
            return "error/page_404";
        }
        productService.deleteProductInfo(productInfo);

        return "redirect:/product/productInfo/list";
    }

    @PostMapping("/search")
    public String searchCategory(@ModelAttribute("searchForm") ProductInfo productInfo, Model model){

        logger.info("search product" + productInfo);
        List<ProductInfo> productInfoList = productService.searchProductInfo(productInfo, paging);
        model.addAttribute("listProduct", productInfoList);
        model.addAttribute("searchForm", productInfo);
        return "product/product-list";
    }

}
