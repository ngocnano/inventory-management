package com.ngoctm.controller;

import com.ngoctm.entity.Category;
import com.ngoctm.entity.Paging;
import com.ngoctm.service.ProductService;
import com.ngoctm.validate.CategoryValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/product/category")
public class CategoryController {

    static final Logger logger = Logger.getLogger(CategoryController.class);

    @Autowired
    ProductService productService;

    @Autowired
    CategoryValidator categoryValidater;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        if(binder.getTarget() == null){
            return;
        }
        if(binder.getTarget().getClass() == Category.class){
            binder.setValidator(categoryValidater);
        }
    }

    @RequestMapping("/list")
    public String redirect(HttpSession session) {
        if(session.getAttribute("searchFormC") != null){
            session.removeAttribute("searchFormC");
        }

        return "redirect:/product/category/list/1";
    }

    @RequestMapping("/list/{page}")
    public String showCategoryList(@PathVariable("page") int page,Model model, @ModelAttribute("searchFormC") Category category){
        logger.info("show category list");
        Paging paging = new Paging(10,page);
            List<Category> categoryList = productService.findAllCategory(category, paging);
            model.addAttribute("listCategory", categoryList);
            model.addAttribute("pageInfo", paging);
        return "category/category-list";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model){
        logger.info("show form category add");
        Category category = new Category();
        category.setActiveFlag(1);
        model.addAttribute("category", category);
        return "category/category-action";
    }

    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable("id") int id, Model model){

        Category category = productService.findCategoryById(id);

        if(category == null){
            return "error/page_404";
        }
        model.addAttribute("category", category);
        return "category/category-action";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category")@Validated Category category, Model model, BindingResult result){

        if(result.hasErrors()){
            return "category/category-action";
        }


        if(category.getId() == null || category.getId() == 0){
            productService.saveCategory(category);
        } else {
            productService.updateCategory(category);
        }
        model.addAttribute("searchFormC", new Category());

        return "category/category-list";
    }

    @GetMapping("/delete/{id}")
    public String daleteCategory(@PathVariable("id") int id){

        Category category = productService.findCategoryById(id);
        if(category == null){
            return "error/page_404";
        }
        productService.deleteCategory(category);

        return "redirect:/product/category/list";
    }

}
