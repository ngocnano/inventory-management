package com.ngoctm.controller;

import com.ngoctm.entity.Category;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInfo;
import com.ngoctm.service.ProductService;
import com.ngoctm.validate.ProductInfoValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product/product-info")
public class ProductInfoController {

    static final Logger logger = Logger.getLogger(ProductInfoController.class);

    @Autowired
    ProductService productService;

    @Autowired
    ProductInfoValidator productInfoValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        if(binder.getTarget() == null){
            return;
        }
        if(binder.getTarget().getClass() == ProductInfo.class){
            binder.setValidator(productInfoValidator);
        }
    }

    @RequestMapping("/list")
    public String redirect(HttpSession session) {

        if(session.getAttribute("searchFormP") != null){
            session.removeAttribute("searchFormP");
        }

        return "redirect:/product/product-info/list/1";
    }

    @RequestMapping("/list/{page}")
    public String showProductInfoList(@PathVariable("page") int page,Model model,
                                      @ModelAttribute("searchFormP") ProductInfo productInfo){
        logger.info("show productInfo list");
        Paging paging = new Paging(10,page);

        List<ProductInfo> productInfoList = productService.findAllPrductInfo(productInfo,paging);
        model.addAttribute("listProductInfo", productInfoList);
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
        model.addAttribute("searchFormP", new ProductInfo());

        return "product/product-list";
    }

    private void getListCategory(Model model) {
        List<Category> categoryList = productService.findAllCategory(null,null);
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

        return "redirect:/product/product-info/list";
    }

}
