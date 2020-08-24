package com.ngoctm.controller;

import com.ngoctm.entity.Invoice;
import com.ngoctm.entity.Paging;
import com.ngoctm.entity.ProductInfo;
import com.ngoctm.service.InvoiceService;
import com.ngoctm.service.ProductService;
import com.ngoctm.util.Constant;
import com.ngoctm.validate.InvoiceValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stock/goods-receipt")
public class GoodsReceiptController {

    static final Logger logger = Logger.getLogger(GoodsReceiptController.class);
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceValidator invoiceValidator;

    @GetMapping("/list")
    public String redirect(){

        return "redirect:/stock/goods-receipt/list/1";
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if(binder.getTarget().getClass()== Invoice.class) {
            binder.setValidator(invoiceValidator);
        }
    }

    @RequestMapping("/list/{page}")
    public String getGoodsReceiptList(@PathVariable("page") int page, @ModelAttribute("searchForm")Invoice invoice,
                                        Model model){
        logger.info("get Goods Receipt List");
        Paging paging = new Paging(10,page);
        invoice.setType(Constant.GOODS_RECEIPT);
        logger.info("Invoice ===========> " + invoice);
        List<Invoice> invoices = invoiceService.findAll(invoice, paging);
        model.addAttribute("invoices", invoices);
        model.addAttribute("pageInfo", paging);
        return "stock/goods-receipt-list";
    }

    @GetMapping("/add")
    public String addInvoice(Model model){

        Invoice invoice = new Invoice();
        invoice.setProductInfo(new ProductInfo());
        model.addAttribute("modelForm", invoice);
        List<ProductInfo> productInfos = productService.findAllPrductInfo(null,null);
        Map<Integer,String> mapProduct = new HashMap<Integer,String>();
        for (ProductInfo productInfo : productInfos) {
            mapProduct.put(productInfo.getId(),productInfo.getName());
        }
        model.addAttribute("mapProduct", mapProduct);
        return "stock/goods-receipt-action";
    }

    @GetMapping("/edit/{id}")
    public String addInvoice(@PathVariable("id") int id, Model model){

        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("modelForm", invoice);
        List<ProductInfo> productInfos = productService.findAllPrductInfo(null,null);
        Map<Integer,String> mapProduct = new HashMap<Integer,String>();
        for (ProductInfo productInfo : productInfos) {
            mapProduct.put(productInfo.getId(),productInfo.getName());
        }
        model.addAttribute("mapProduct", mapProduct);
        return "stock/goods-receipt-action";
    }


    @PostMapping("/save")
    public String actionInvoice(@ModelAttribute("modelForm")@Validated Invoice invoice, Model model, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("modelForm", invoice);
            return "stock/goods-receipt-action";
        }

        if(invoice.getId() == null || invoice.getId()==0){
            logger.info("save Invoice" + invoice);
            invoice.setType(Constant.GOODS_RECEIPT);
            invoice.setActiveFlag(1);
            try {
                invoiceService.saveInvoice(invoice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            logger.info("update Invoice" + invoice);
            invoice.setType(Constant.GOODS_RECEIPT);
            try {
                invoiceService.updateInvoice(invoice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/stock/goods-receipt/list/1";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") int id) throws Exception {

        Invoice invoice = invoiceService.getInvoiceById(id);

        invoiceService.deleteInvoice(invoice);

        return "redirect:/stock/goods-receipt/list/1";
    }

}
